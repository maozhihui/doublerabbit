package com.comba.server.actors.plugin;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.service.ComponentActor;
import com.comba.server.actors.service.ContextBasedCreator;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.plugin.ComponentLifecycleMsg;
import com.comba.server.extensions.api.plugins.msg.TimeoutMsg;
import com.comba.server.extensions.api.plugins.msg.ToPluginRpcResponseDeviceMsg;
import com.comba.server.extensions.api.rules.RuleException;

import akka.actor.ActorContext;
import akka.actor.ActorRef;

public class PluginActor extends ComponentActor<PluginId, PluginActorMessageProcessor> {

    private PluginActor(ActorSystemContext systemContext, TenantId tenantId, PluginId pluginId) {
        super(systemContext, tenantId, pluginId);
        setProcessor(new PluginActorMessageProcessor(tenantId, pluginId, systemContext,
                logger, context().parent(), context().self()));
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof PluginCallbackMessage) {
            onPluginCallback((PluginCallbackMessage) msg);
        } else if (msg instanceof RuleToPluginMsgWrapper) {
            onRuleToPluginMsg((RuleToPluginMsgWrapper) msg);
        } else if (msg instanceof ClusterEventMsg) {
            onClusterEventMsg((ClusterEventMsg) msg);
        } else if (msg instanceof ComponentLifecycleMsg) {
            onComponentLifecycleMsg((ComponentLifecycleMsg) msg);
        } else if (msg instanceof ToPluginRpcResponseDeviceMsg) {
            onRpcResponse((ToPluginRpcResponseDeviceMsg) msg);
        } else if (msg instanceof PluginTerminationMsg) {
            logger.info("[{}][{}] Going to terminate plugin actor.", tenantId, id);
            context().parent().tell(msg, ActorRef.noSender());
            context().stop(self());
        } else if (msg instanceof TimeoutMsg) {
            onTimeoutMsg(context(), (TimeoutMsg) msg);
        } else {
            logger.debug("[{}][{}] Unknown msg type.", tenantId, id, msg.getClass().getName());
        }
    }

    private void onPluginCallback(PluginCallbackMessage msg) {
        try {
            processor.onPluginCallbackMsg(msg);
        } catch (Exception e) {
            logAndPersist("onPluginCallbackMsg", e);
        }
    }

    private void onTimeoutMsg(ActorContext context, TimeoutMsg msg) {
        processor.onTimeoutMsg(context, msg);
    }

    private void onRpcResponse(ToPluginRpcResponseDeviceMsg msg) {
        processor.onDeviceRpcMsg(msg.getResponse());
    }

    private void onRuleToPluginMsg(RuleToPluginMsgWrapper msg) throws RuleException {
        logger.debug("[{}] Going to process rule msg: {}", id, msg.getMsg());
        try {
            processor.onRuleToPluginMsg(msg);
            increaseMessagesProcessedCount();
        } catch (Exception e) {
            // 插件执行失败
            logger.error("pluginId [{}] execute failed,cause [{}]",msg.getPluginId(),e.getMessage());
        }
    }

    public static class ActorCreator extends ContextBasedCreator<PluginActor> {
        private static final long serialVersionUID = 1L;

        private final TenantId tenantId;
        private final PluginId pluginId;

        public ActorCreator(ActorSystemContext context, TenantId tenantId, PluginId pluginId) {
            super(context);
            this.tenantId = tenantId;
            this.pluginId = pluginId;
        }

        @Override
        public PluginActor create() throws Exception {
            return new PluginActor(context, tenantId, pluginId);
        }
    }

    @Override
    protected long getErrorPersistFrequency() {
        return systemContext.getPluginErrorPersistFrequency();
    }
}
