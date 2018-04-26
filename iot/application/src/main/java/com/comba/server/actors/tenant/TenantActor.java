package com.comba.server.actors.tenant;

import java.util.*;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.device.DeviceActor;
import com.comba.server.actors.device.DeviceTerminationMsg;
import com.comba.server.actors.plugin.PluginTerminationMsg;
import com.comba.server.actors.rule.ComplexRuleActorChain;
import com.comba.server.actors.rule.RuleActorChain;
import com.comba.server.actors.rule.RuleTerminationMsg;
import com.comba.server.actors.service.ContextAwareActor;
import com.comba.server.actors.service.ContextBasedCreator;
import com.comba.server.actors.service.DefaultActorService;
import com.comba.server.actors.shared.plugin.PluginManager;
import com.comba.server.actors.shared.plugin.TenantPluginManager;
import com.comba.server.actors.shared.rule.RuleManager;
import com.comba.server.actors.shared.rule.TenantRuleManager;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.rule.ForwardRule;
import com.comba.server.common.data.rule.TelemetryData;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.cmd.CommandMsg;
import com.comba.server.common.msg.core.BasicAlarmUploadRequest;
import com.comba.server.common.msg.core.TelemetryUploadRequest;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.forward.ForwardMsgRequest;
import com.comba.server.common.msg.plugin.ComponentLifecycleMsg;
import com.comba.server.common.msg.plugin.ForwardLifecycleMsg;
import com.comba.server.common.msg.session.MsgType;
import com.comba.server.extensions.api.device.ToDeviceActorNotificationMsg;
import com.comba.server.extensions.api.plugins.msg.ToPluginActorMsg;
import com.comba.server.extensions.api.rules.ToRuleActorMsg;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.apache.commons.lang3.StringUtils;

public class TenantActor extends ContextAwareActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private final TenantId tenantId;
    private final RuleManager ruleManager;
    private final PluginManager pluginManager;
    private final Map<DeviceId, ActorRef> deviceActors;
    private final List<String> forwardEvents;

    private TenantActor(ActorSystemContext systemContext, TenantId tenantId) {
        super(systemContext);
        this.tenantId = tenantId;
        this.ruleManager = new TenantRuleManager(systemContext, tenantId);
        this.pluginManager = new TenantPluginManager(systemContext, tenantId);
        this.deviceActors = new HashMap<>();
        this.forwardEvents = new ArrayList<>();
    }

    @Override
    public void preStart() {
        logger.info("[{}] Starting tenant actor.", tenantId);
        try {
            ruleManager.init(this.context());
            pluginManager.init(this.context());
            initEvent();
            loadDevice();
            logger.info("[{}] Tenant actor started.", tenantId);
        } catch (Exception e) {
            logger.error(e, "[{}] Unknown failure", tenantId);
        }
    }

    private void initEvent(){
        this.forwardEvents.clear();
        List<ForwardRule> rules = systemContext.getForwardRuleService().findByTenantId(UUIDUtils.toUUID(tenantId.getId()));
        if (rules != null){
            for (ForwardRule rule : rules)
                this.forwardEvents.add(rule.getEvent());
        }else {
            logger.warning("load tenantId [{}] is empty.",tenantId);
        }
    }

    /**
     * 初始化时加载所有DeviceActor
     */
    private void loadDevice(){
        List<Device> devices = systemContext.getDeviceService().findByTenantId(UUIDUtils.toUUID(tenantId.getId()));
        for (Device device : devices){
            getOrCreateDeviceActor(new DeviceId(UUIDUtils.toUUID(device.getDevId())));
        }
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        logger.debug("[{}] Received message: {}", tenantId, msg);
        if (msg instanceof RuleChainDeviceMsg) {
            process((RuleChainDeviceMsg) msg);
        } else if (msg instanceof ToDeviceActorMsg) {
            onToDeviceActorMsg((ToDeviceActorMsg) msg);
        } else if (msg instanceof ToPluginActorMsg) {
            onToPluginMsg((ToPluginActorMsg) msg);
        } else if (msg instanceof ToRuleActorMsg) {
            onToRuleMsg((ToRuleActorMsg) msg);
        } else if (msg instanceof ToDeviceActorNotificationMsg) {
            onToDeviceActorMsg((ToDeviceActorNotificationMsg) msg);
        } else if (msg instanceof ClusterEventMsg) {
            broadcast(msg);
        } else if (msg instanceof ComponentLifecycleMsg) {
            onComponentLifecycleMsg((ComponentLifecycleMsg) msg);
        } else if (msg instanceof PluginTerminationMsg) {
            onPluginTerminated((PluginTerminationMsg) msg);
        }else if(msg instanceof RuleTerminationMsg){
        	onRuleTerminated((RuleTerminationMsg)msg);
        } else if (msg instanceof DeviceTerminationMsg) {
			onDeviceTermination((DeviceTerminationMsg)msg);
		}else if (msg instanceof CommandMsg) {
			onToDeviceActorMsg((CommandMsg)msg);
		}else if (msg instanceof ForwardMsgRequest){
            onToForwardActorMsg((ForwardMsgRequest) msg);
        } else if (msg instanceof ForwardLifecycleMsg){
		    onForwardLifecycleMsg((ForwardLifecycleMsg)msg);
        }
        else {
            logger.warning("[{}] Unknown message: {}!", tenantId, msg);
        }
    }
    private void onRuleTerminated(RuleTerminationMsg msg) {
        ruleManager.remove(msg.getId());
    }
    private void broadcast(Object msg) {
        pluginManager.broadcast(msg);
        deviceActors.values().forEach(actorRef -> actorRef.tell(msg, ActorRef.noSender()));
    }

    private void onToDeviceActorMsg(ToDeviceActorMsg msg) {
        //如果是设备上报的告警，则转到告警模块处理
        if (MsgType.ALARM_MESSAGE.equals(msg.getPayload().getMsgType())){
            BasicAlarmUploadRequest request = (BasicAlarmUploadRequest) msg.getPayload();
            systemContext.getAlarmRuleService().receiveAlarm(request.getDeviceId(),request.getNotifyType(),request.getAlarmLevel(),request.getAlarmId(),request.getAlarmContent(),request.getStartTime());
            return;
        }
        getOrCreateDeviceActor(msg.getDeviceId()).tell(msg, ActorRef.noSender());
    }

    /**
     * 将参数查设命令消息转至DeviceActor
     * @param msg
     */
    private void onToDeviceActorMsg(CommandMsg msg){
    	getOrCreateDeviceActor(msg.getDeviceId()).tell(msg, ActorRef.noSender());
    }

    private void onForwardLifecycleMsg(ForwardLifecycleMsg msg){
        logger.debug("tenant event refresh, forward rule id [{}],event [{}]",msg.getForwardRuleId(),msg.getEvent());
        initEvent();
    }

    private void onToDeviceActorMsg(ToDeviceActorNotificationMsg msg) {
        getOrCreateDeviceActor(msg.getDeviceId()).tell(msg, ActorRef.noSender());
    }

    private void onToRuleMsg(ToRuleActorMsg msg) {
        ActorRef target = ruleManager.getOrCreateRuleActor(this.context(), msg.getRuleId());
        target.tell(msg, ActorRef.noSender());
    }

    private void onToPluginMsg(ToPluginActorMsg msg) {
        if (msg.getPluginTenantId().equals(tenantId)) {
            ActorRef pluginActor = pluginManager.getOrCreatePluginActor(this.context(), msg.getPluginId());
            pluginActor.tell(msg, ActorRef.noSender());
        } else {
            context().parent().tell(msg, ActorRef.noSender());
        }
    }

    private void onComponentLifecycleMsg(ComponentLifecycleMsg msg) {
        if (msg.getPluginId().isPresent()) {
            ActorRef pluginActor = pluginManager.getOrCreatePluginActor(this.context(), msg.getPluginId().get());
            pluginActor.tell(msg, ActorRef.noSender());
        } else if (msg.getRuleId().isPresent()) {
            ActorRef target;
            Optional<ActorRef> ref = ruleManager.update(this.context(), msg.getRuleId().get(), msg.getEvent());
            if (ref.isPresent()) {
                target = ref.get();
            } else {
                logger.debug("Failed to find actor for rule: [{}]", msg.getRuleId());
                return;
            }
            target.tell(msg, ActorRef.noSender());
        } else {
            logger.debug("[{}] Invalid component lifecycle msg.", tenantId);
        }
    }

    private void onPluginTerminated(PluginTerminationMsg msg) {
        pluginManager.remove(msg.getId());
    }

    private void process(RuleChainDeviceMsg msg) {
        ToDeviceActorMsg toDeviceActorMsg = msg.getToDeviceActorMsg();
        ActorRef deviceActor = getOrCreateDeviceActor(toDeviceActorMsg.getDeviceId());
        RuleActorChain chain = new ComplexRuleActorChain(msg.getRuleChain(), ruleManager.getRuleChain());
        deviceActor.tell(new RuleChainDeviceMsg(toDeviceActorMsg, chain), context().self());
        dataForward(toDeviceActorMsg);
        forwardToAlarmService(toDeviceActorMsg);
    }

    private void dataForward(ToDeviceActorMsg toDeviceActorMsg){
        // 判断是否需要数据转发
        if (toDeviceActorMsg.getPayload().getMsgType() == MsgType.POST_TELEMETRY_REQUEST &&
                forwardEvents.contains("data")){
            TelemetryData telemetryData = new TelemetryData(UUIDUtils.toUUID(toDeviceActorMsg.getDeviceId().getId()));
            TelemetryUploadRequest request = (TelemetryUploadRequest)toDeviceActorMsg.getPayload();
            Map<Long,List<KvEntry>> realData = request.getData();
            for (Map.Entry<Long,List<KvEntry>> entry : realData.entrySet()){
                for (KvEntry kvEntry : entry.getValue()){
                    TelemetryData.DataPoint dataPoint = new TelemetryData.DataPoint(kvEntry.getKey(),kvEntry.getValueAsString(),entry.getKey());
                    telemetryData.getData().add(dataPoint);
                }
            }
            ForwardMsgRequest forwardMsgRequest = new ForwardMsgRequest(UUIDUtils.toUUID(tenantId.getId()),
                    "data",systemContext.getGson().toJson(telemetryData));
            onToForwardActorMsg(forwardMsgRequest);
        }else {
            logger.warning("tenant id [{}] does not have event type data",tenantId.getId());
        }
    }

    /**
     * 将上报数据转发至告警模块进行告警处理
     * @param toDeviceActorMsg
     */
    private void forwardToAlarmService(ToDeviceActorMsg toDeviceActorMsg){
    	 if (toDeviceActorMsg.getPayload().getMsgType() == MsgType.POST_TELEMETRY_REQUEST){
	        Map<String,String> dataMap = new HashMap<>();
	        TelemetryUploadRequest request = (TelemetryUploadRequest)toDeviceActorMsg.getPayload();
	        for (Map.Entry<Long,List<KvEntry>> entry : request.getData().entrySet()){
	            for (KvEntry kvEntry : entry.getValue()){
	                dataMap.put(kvEntry.getKey(),kvEntry.getValueAsString());
	            }
	        }
	        String tenantIdStr = UUIDUtils.toUUID(tenantId.getId());
	        String did = UUIDUtils.toUUID(toDeviceActorMsg.getDeviceId().getId());
	        systemContext.getAlarmRuleService().analyse(tenantIdStr,did,dataMap);
	        logger.debug("forwardToAlarmService tanentId [{}],did [{}],data [{}]",tenantIdStr,did,dataMap);
    	}
    }

    private ActorRef getOrCreateDeviceActor(DeviceId deviceId) {
        ActorRef deviceActor = deviceActors.get(deviceId);
        if (deviceActor == null) {
            deviceActor = context().actorOf(Props.create(new DeviceActor.ActorCreator(systemContext, tenantId, deviceId))
                    .withDispatcher(DefaultActorService.CORE_DISPATCHER_NAME), deviceId.toString());
            deviceActors.put(deviceId, deviceActor);
        }
        return deviceActor;
    }

    public static class ActorCreator extends ContextBasedCreator<TenantActor> {
        private static final long serialVersionUID = 1L;

        private final TenantId tenantId;

        public ActorCreator(ActorSystemContext context, TenantId tenantId) {
            super(context);
            this.tenantId = tenantId;
        }

        @Override
        public TenantActor create() throws Exception {
            return new TenantActor(context, tenantId);
        }
    }

    private void onDeviceTermination(DeviceTerminationMsg msg) {
        DeviceId deviceId = DeviceId.fromString(msg.getId().toUidStr());
        ActorRef deviceActor = deviceActors.remove(deviceId);
        if (deviceActor != null) {
            logger.debug("[{}] Removed device actor.", deviceId.toString());
        } else {
            logger.debug("[{}] device actor was already removed.", deviceId.toString());
        }
    }

    private void onToForwardActorMsg(ForwardMsgRequest msg){
        if (forwardEvents.contains(msg.getEventType())){
            if (StringUtils.isBlank(msg.getTenantId()))
                msg.setTenantId(UUIDUtils.toUUID(tenantId.getId()));
            systemContext.getForwardActor().tell(msg,ActorRef.noSender());
        } else {
            logger.warning("tenant id [{}] does not have event type [{}]",tenantId.getId(),msg.getEventType());
        }
    }
}
