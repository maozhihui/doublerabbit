package com.comba.server.transport.mqtt.sub.service;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.DeviceSessionId;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.core.BasicTelemetryUploadRequest;
import com.comba.server.common.msg.device.BasicToDeviceActorMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.BasicNotificationMsg;
import com.comba.server.common.msg.session.SessionType;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.transport.mqtt.sub.message.TelemetryUploadRequest;
import com.comba.server.transport.mqtt.sub.message.winext.DataUpload;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * PushCallback 推送消息回调处理类
 *
 * @author maozhihui
 * @create 2017-10-10 12:17
 **/
@Slf4j
public class PushCallback implements MqttCallback {

    private static final Gson GSON = new Gson();
    private SessionMsgProcessor processor;
    private DeviceAuthService authService;
    private MqttConfig config;
    private TelemetryUploadRequest dataModel;
    private MqttMsgHandler msgHandler;

    public PushCallback(MqttServiceContext context, MqttConfig config,MqttMsgHandler msgHandler){
        this.processor = context.getProcessor();
        this.authService = context.getAuthService();
        this.config = config;
        this.msgHandler = msgHandler;
        initConfiguration();
    }

    public void initConfiguration(){
        try {
            Class<TelemetryUploadRequest> clazz = (Class<TelemetryUploadRequest>)Class.forName(config.getModelClass());
            dataModel = clazz.newInstance();
        } catch (Exception e) {
            log.error("dynamic load class [{}] failed.",config.getModelClass());
        }
    }

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        log.warn("the connection is closed,please retry cause [{}]",cause.getMessage());
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            String mqttMsg = new String(message.getPayload());
            log.info("recieve mqtt msg:"+mqttMsg);
            if (dataModel instanceof com.comba.server.transport.mqtt.sub.message.comba.DataUpload) {
                dataModel = GSON.fromJson(mqttMsg, com.comba.server.transport.mqtt.sub.message.comba.DataUpload.class);
            }else if (dataModel instanceof DataUpload) {
                dataModel = GSON.fromJson(mqttMsg, DataUpload.class);
            }else {
                log.error("data model class not support [{}]",config.getModelClass());
                return;
            }
            Optional<Device> device = authService.findDeviceByHardIdentity(dataModel.getDeviceId());
            if (device.isPresent()){
                Map<Long,List<KvEntry>> parseData = msgHandler.parseData(dataModel);
                if (parseData.isEmpty()){
                    log.warn("mqtt msg data body parse failed.");
                    return;
                }
                SessionId sessionId = new DeviceSessionId(UUID.randomUUID());
                BasicTelemetryUploadRequest request = new BasicTelemetryUploadRequest(dataModel.getDeviceId());
                for (Map.Entry<Long, List<KvEntry>> entry : parseData.entrySet()){
                    for (KvEntry kvEntry : entry.getValue())
                        request.add(entry.getKey(),kvEntry);
                }
                ToDeviceActorMsg toDeviceActorMsg = new BasicToDeviceActorMsg(null,new TenantId(UUIDUtils.toUUID(device.get().getTenantId())),
                        null,new DeviceId(UUIDUtils.toUUID(device.get().getDevId())),sessionId,
                        SessionType.SUB_PUBLISH,request);
                processor.process(new BasicNotificationMsg(sessionId,toDeviceActorMsg));
            }else {
                log.error("device [{}] is not exists.",dataModel.getDeviceId());
            }
        } catch (Exception e) {
            log.error("recieve msg convert josn failure,{}",e.getMessage());
        }

    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        // publish后会执行到这里
        log.info("delivery message Complete status [{}]", token.isComplete());
    }

}
