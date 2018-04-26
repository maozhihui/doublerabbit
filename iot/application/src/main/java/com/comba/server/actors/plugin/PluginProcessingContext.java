package com.comba.server.actors.plugin;

import java.util.*;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.id.*;
import com.comba.server.common.data.kv.AttributeKvEntry;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.cluster.ServerAddress;
import com.comba.server.common.msg.cmd.BasicCommandMsg;
import com.comba.server.common.msg.cmd.SetCmdRequestMsg;
import com.comba.server.common.msg.core.BasicCmdMsg;
import com.comba.server.dao.model.device.TelemetryAttributesEntity;
import com.comba.server.dao.util.DaoUtil;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributesUpdateNotificationMsg;
import com.comba.server.extensions.api.plugins.PluginApiCallSecurityContext;
import com.comba.server.extensions.api.plugins.PluginCallback;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.msg.*;
import com.comba.server.service.mq.AlarmProducer;
import com.comba.server.utils.SpringUtil;

import lombok.extern.slf4j.Slf4j;
import akka.actor.ActorRef;

import org.apache.commons.lang3.StringUtils;

@Slf4j
public final class PluginProcessingContext implements PluginContext {

    private final SharedPluginProcessingContext pluginCtx;
    private final Optional<PluginApiCallSecurityContext> securityCtx;

    public PluginProcessingContext(SharedPluginProcessingContext pluginCtx, PluginApiCallSecurityContext securityCtx) {
        super();
        this.pluginCtx = pluginCtx;
        this.securityCtx = Optional.ofNullable(securityCtx);
    }

    public void saveTelemetres(final TenantId tenantId, final DeviceId deviceId, final String scope, DeviceTelemetryAttributes originalAttributesList, 
    		final List<KvEntry> uploadAttributesList, final PluginCallback<Void> callback) {
    	long startTime = System.currentTimeMillis();
    	List<TelemetryAttributes> telemetryAttributesList = originalAttributesList.getDeviceTelemetryAttributes();
    	// 更新遥测属性
    	List<KvEntry> res = pluginCtx.telemetryAttributesService.save(deviceId, uploadAttributesList, telemetryAttributesList);

    	if(res.size() > 0) {
        	// 若遥测属性有更新，则需重新加载一下遥测属性
        	List<TelemetryAttributesEntity> attributesList =  pluginCtx.telemetryAttributesService.getListByDevId(UUIDUtils.toUUID(deviceId.getId()));
        	telemetryAttributesList = DaoUtil.convertDataList(attributesList);
        	
        	// 通知DeviceActor，更新telemetryAttributesList
        	originalAttributesList.setDeviceTelemetryAttributes(telemetryAttributesList);
        	DeviceTelemetryAttributesUpdateNotificationMsg notificationMsg = new DeviceTelemetryAttributesUpdateNotificationMsg(tenantId, deviceId, originalAttributesList);
        	pluginCtx.parentActor.tell(notificationMsg, ActorRef.noSender());
    	}

    	// 遥测属性保存到 history_data
    	List<KvEntry> res2 = pluginCtx.historyDataService.save(deviceId, uploadAttributesList, telemetryAttributesList);
    	log.debug("data saved into database,cost time [{}] ms",(System.currentTimeMillis()-startTime));
    	if (res2.size() == uploadAttributesList.size()) {
    		pluginCtx.self().tell(PluginCallbackMessage.onSuccess(callback, null), ActorRef.noSender());
    	}else{
    		pluginCtx.self().tell(PluginCallbackMessage.onError(callback, new Exception("upload failed")), ActorRef.noSender());
    	}
    } 
    @Override
    public void saveAttributes(final TenantId tenantId, final DeviceId deviceId, final String scope, final List<AttributeKvEntry> attributes, final PluginCallback<Void> callback) {

    	List<AttributeKvEntry> res = pluginCtx.attributesService.save(deviceId, attributes);

        pluginCtx.self().tell(PluginCallbackMessage.onSuccess(callback, null), ActorRef.noSender());
    }

    @Override
    public void reply(PluginToRuleMsg<?> msg) {
        pluginCtx.parentActor.tell(msg, ActorRef.noSender());
    }

    @Override
    public PluginId getPluginId() {
        return pluginCtx.pluginId;
    }

    private void validate(DeviceId deviceId, ValidationCallback callback) {
        /*if (securityCtx.isPresent()) {
            final PluginApiCallSecurityContext ctx = securityCtx.get();
            if (ctx.isTenantAdmin() || ctx.isCustomerUser()) {
                ListenableFuture<Device> deviceFuture = pluginCtx.deviceService.findDeviceByIdAsync(deviceId);
                Futures.addCallback(deviceFuture, getCallback(callback, device -> {
                    if (device == null) {
                        return Boolean.FALSE;
                    } else {
                        if (!device.getTenantId().equals(ctx.getTenantId())) {
                            return Boolean.FALSE;
                        } else if (ctx.isCustomerUser() && !device.getCustomerId().equals(ctx.getCustomerId())) {
                            return Boolean.FALSE;
                        } else {
                            return Boolean.TRUE;
                        }
                    }
                }));
            } else {
                callback.onSuccess(this, Boolean.FALSE);
            }
        } else {
            callback.onSuccess(this, Boolean.TRUE);
        }*/
    }

    @Override
    public Optional<ServerAddress> resolve(DeviceId deviceId) {
        //return pluginCtx.routingService.resolve(deviceId);
    	return null;
    }

	@Override
	public void sendSms(TenantId tenantId, DeviceId deviceId, String scope,
			SendSmsRuleToPluginActionMsg msg, PluginCallback<Void> callback) {

		/*boolean ret = false;
		try {
			// 获取过滤器信息
			BasicTelemetryUploadRequest request = (BasicTelemetryUploadRequest) msg.getPayload().getToDeviceActorMsg().getPayload();
			String hardIdentity  = request.getHardIdentity();
			MyDeviceTelemetryFilter filter = (MyDeviceTelemetryFilter)msg.getPayload().getFilters().get(1);
			String attributeName = filter.getAttributeName();
			String time = DateConvertUtil.localDateTimeConvertToStr(LocalDateTime.now());
		
			// 获取上报的遥测数据
	        List<KvEntry> telemetryAttributesList = null;
	        String attributeValue = null; 
	        for (Map.Entry<Long, List<KvEntry>> entry : request.getData().entrySet()) {
	        	telemetryAttributesList = entry.getValue();
	        	for(KvEntry attributes:telemetryAttributesList) {
	        		if(attributes.getKey().equals(attributeName)) {
	        			attributeValue = attributes.getValueAsString();
	        			break;
	        		}
	        	}
	        }
			
			String isdn = msg.getPayload().getDstNum();
			String content = msg.getPayload().getContent();
			String sendContent = "[硬件标识:"+hardIdentity+",参数:"+attributeName+",当前值:"+attributeValue+","+time+"]"+content;
			log.info("Sending sms {}", sendContent);
			SmsSender sender = SpringUtil.getBean(SmsSender.class);
			ret = sender.send(isdn,sendContent);

			if (ret) {
				pluginCtx.self().tell(PluginCallbackMessage.onSuccess(callback, null), ActorRef.noSender());
				log.info("Sms sent {}", msg.getPayload());
			}else{
				pluginCtx.self().tell(PluginCallbackMessage.onError(callback, null), ActorRef.noSender());
			}
		} 
		catch (RestClientException e) {
			pluginCtx.self().tell(PluginCallbackMessage.onError(callback, e), ActorRef.noSender());
			log.error("RestClientException:{}",e.getMostSpecificCause());
		}*/
	}

	@Override
	public List<ConfigAttributes> loadAttributes(DeviceId deviceId) {
		// TODO Auto-generated method stub
		return pluginCtx.attributesService.findAllByDeviceId(deviceId);
	}

	@Override
    public void sendMQMsg(TenantId tenantId, DeviceId deviceId, String scope,
                   BasicAlarmRuleToPluginMsg msg, PluginCallback<Void> callback){
        boolean ret = true;
        String mqMsg = pluginCtx.systemContext.getGson().toJson(msg.getPayload());

        AlarmProducer producer = SpringUtil.getBean(AlarmProducer.class);
        producer.send(mqMsg);
        if (ret) {
            pluginCtx.self().tell(PluginCallbackMessage.onSuccess(callback, null), ActorRef.noSender());
            log.info("ready send alarm msg [{}]",mqMsg);
        }else{
            pluginCtx.self().tell(PluginCallbackMessage.onError(callback, null), ActorRef.noSender());
        }
    }

    /**
     * 执行命令下发
     * @param tenantId
     * @param msg
     * @param callback
     */
    @Override
    public void sendCommandMsg(TenantId tenantId, BasicCmdRuleToPluginMsg msg, PluginCallback<Void> callback) {

        CtrlCmdMsg ctrlCmdMsg = msg.getPayload();
        if (ctrlCmdMsg.getCmdMsgs() == null){
            log.warn("does not have ctrl command msg");
            return;
        }
        for (BasicCmdMsg inputCmd : ctrlCmdMsg.getCmdMsgs()){
            // 查找该设备的是否有网关ID
            DeviceId dstDeviceId = new DeviceId(UUIDUtils.toUUID(inputCmd.getDst()));
            Device dstDevice = pluginCtx.systemContext.getDeviceService().findDeviceById(dstDeviceId);
            SetCmdRequestMsg setCmdRequestMsg = new SetCmdRequestMsg(dstDeviceId);
            for (Map.Entry<String,String> entry : inputCmd.getData().entrySet()){
                setCmdRequestMsg.addAttribute(entry.getKey(),entry.getValue());
            }
            setCmdRequestMsg.setHardIdentity(dstDevice.getHardIdentity());
            if (StringUtils.isNoneBlank(dstDevice.getGatewayId())){
                setCmdRequestMsg.setGatewayId(dstDevice.getGatewayId());
            }else {
                log.warn("deviceId [{}] does not has gateway.",inputCmd.getDst());
            }

            BasicCommandMsg<SetCmdRequestMsg> outputCmd = new BasicCommandMsg<>(tenantId,dstDeviceId,null,setCmdRequestMsg);
            // 向相对应的deviceActor发送消息
            pluginCtx.parentActor.tell(outputCmd,ActorRef.noSender());
            log.info("sendCommandMsg [{}]",inputCmd);
        }
        // 向对应的ruleActor发送执行结果消息
        pluginCtx.self().tell(PluginCallbackMessage.onSuccess(callback, null), ActorRef.noSender());
    }
}
