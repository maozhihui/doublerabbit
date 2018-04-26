package com.comba.server.actors.device;

import java.util.concurrent.TimeUnit;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.rule.RulesProcessedMsg;
import com.comba.server.actors.service.ContextAwareActor;
import com.comba.server.actors.service.ContextBasedCreator;
import com.comba.server.actors.shared.RegisterSessionTimeoutMsg;
import com.comba.server.actors.tenant.RuleChainDeviceMsg;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.DeviceSessionId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.cmd.CommandMsg;
import com.comba.server.common.msg.cmd.CommandSendTimeoutMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.extensions.api.device.DeviceAttributesEventNotificationMsg;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributesUpdateNotificationMsg;
import com.comba.server.extensions.api.device.ToDeviceActorNotificationMsg;
import com.comba.server.extensions.api.plugins.msg.*;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

public class DeviceActor extends ContextAwareActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private final TenantId tenantId;
    private final DeviceId deviceId;
    private final DeviceActorMessageProcessor processor;

    private DeviceActor(ActorSystemContext systemContext, TenantId tenantId, DeviceId deviceId) {
        super(systemContext);
        this.tenantId = tenantId;
        this.deviceId = deviceId;
        this.processor = new DeviceActorMessageProcessor(systemContext, logger, deviceId);
        // 注册会话超时机制定时器第一次
        RegisterSessionTimeoutMsg timeoutMsg = new RegisterSessionTimeoutMsg(new DeviceSessionId(deviceId.getId()));
        systemContext.getScheduler().scheduleOnce(Duration.create(systemContext.getCheckTime(), TimeUnit.MILLISECONDS)
        		, context().self(), timeoutMsg, systemContext.getActorSystem().dispatcher(), null);

    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof RuleChainDeviceMsg) {
            processor.process(context(), (RuleChainDeviceMsg) msg);
        } else if (msg instanceof RulesProcessedMsg) {
            processor.onRulesProcessedMsg(context(), (RulesProcessedMsg) msg);
        } else if (msg instanceof ToDeviceActorMsg) {
            processor.process(context(), (ToDeviceActorMsg) msg);
        } else if (msg instanceof ToDeviceActorNotificationMsg) {
            if (msg instanceof DeviceAttributesEventNotificationMsg) {
                processor.processAttributesUpdate(context(), (DeviceAttributesEventNotificationMsg) msg);
            } else if (msg instanceof DeviceTelemetryAttributesUpdateNotificationMsg){
                processor.processTelemetryAttributesUpdate(context(), (DeviceTelemetryAttributesUpdateNotificationMsg) msg);
            }
        } else if (msg instanceof TimeoutMsg) {
            processor.processTimeout(context(), (TimeoutMsg) msg);
        } else if (msg instanceof ClusterEventMsg) {
            processor.processClusterEventMsg((ClusterEventMsg) msg);
        } else if (msg instanceof RegisterSessionTimeoutMsg) {
        	processor.processRegisterTimeoutMsg(context(),(RegisterSessionTimeoutMsg)msg);
		} else if (msg instanceof CommandMsg) {
			processor.processCommandMsg(context(),(CommandMsg) msg);
		} else if (msg instanceof CommandSendTimeoutMsg) {
			processor.processCommandTimeoutMsg((CommandSendTimeoutMsg)msg);
		}
        else {
            logger.debug("[{}][{}] Unknown msg type.", tenantId, deviceId, msg.getClass().getName());
        }
    }

    public static class ActorCreator extends ContextBasedCreator<DeviceActor> {
        private static final long serialVersionUID = 1L;

        private final TenantId tenantId;
        private final DeviceId deviceId;

        public ActorCreator(ActorSystemContext context, TenantId tenantId, DeviceId deviceId) {
            super(context);
            this.tenantId = tenantId;
            this.deviceId = deviceId;
        }

        @Override
        public DeviceActor create() throws Exception {
            return new DeviceActor(context, tenantId, deviceId);
        }
    }

}
