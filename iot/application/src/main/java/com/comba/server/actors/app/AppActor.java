package com.comba.server.actors.app;

import akka.actor.*;
import akka.actor.SupervisorStrategy.Directive;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.plugin.ForwardLifecycleMsg;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.plugin.PluginTerminationMsg;
import com.comba.server.actors.rule.RuleTerminationMsg;
import com.comba.server.actors.service.ContextAwareActor;
import com.comba.server.actors.service.ContextBasedCreator;
import com.comba.server.actors.service.DefaultActorService;
import com.comba.server.actors.shared.plugin.PluginManager;
import com.comba.server.actors.shared.plugin.SystemPluginManager;
import com.comba.server.actors.shared.rule.RuleManager;
import com.comba.server.actors.shared.rule.SystemRuleManager;
import com.comba.server.actors.tenant.RuleChainDeviceMsg;
import com.comba.server.actors.tenant.TenantActor;
import com.comba.server.common.data.Tenant;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.cmd.CommandMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.plugin.ComponentLifecycleMsg;
import com.comba.server.dao.model.ModelConstants;
import com.comba.server.dao.tenant.TenantService;
import com.comba.server.extensions.api.device.ToDeviceActorNotificationMsg;
import com.comba.server.extensions.api.plugins.msg.ToPluginActorMsg;
import com.comba.server.extensions.api.rules.ToRuleActorMsg;

public class AppActor extends ContextAwareActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    public static final TenantId SYSTEM_TENANT = new TenantId(ModelConstants.NULL_UUID);
    private final RuleManager ruleManager;
    private final PluginManager pluginManager;
    private final TenantService tenantJpaService;
    private final Map<TenantId, ActorRef> tenantActors;

    private AppActor(ActorSystemContext systemContext) {
        super(systemContext);
        this.ruleManager = new SystemRuleManager(systemContext);
        this.pluginManager = new SystemPluginManager(systemContext);
        this.tenantJpaService = systemContext.getTenantJpaService();
        this.tenantActors = new HashMap<>();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void preStart() {
        logger.info("Starting main system actor.");
        try {
            ruleManager.init(this.context());
            pluginManager.init(this.context());

            // 加载所有tenant
            List<Tenant> tenantIterator = tenantJpaService.findTenants();
            for (Tenant tenantJpa : tenantIterator) {
            	logger.debug("[{}] Creating tenant actor", tenantJpa.getId());
                //getOrCreateTenantActor(tenantJpa.getId());
                getOrCreateTenantActor(new TenantId(tenantJpa.getId().getId()));
                logger.debug("Tenant actor created.");
			}
            
            logger.info("Main system actor started.");
        } catch (Exception e) {
            logger.error(e, "Unknown failure");
        }
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        logger.debug("Received message: {}", msg);
        if (msg instanceof ToDeviceActorMsg) {
            processDeviceMsg((ToDeviceActorMsg) msg);
        } else if (msg instanceof ToPluginActorMsg) {
            onToPluginMsg((ToPluginActorMsg) msg);
        } else if (msg instanceof ToRuleActorMsg) {
            onToRuleMsg((ToRuleActorMsg) msg);
        } else if (msg instanceof ToDeviceActorNotificationMsg) {
            onToDeviceActorMsg((ToDeviceActorNotificationMsg) msg);
        } else if (msg instanceof Terminated) {
            processTermination((Terminated) msg);
        } else if (msg instanceof ClusterEventMsg) {
            broadcast(msg);
        } else if (msg instanceof ComponentLifecycleMsg) {
            onComponentLifecycleMsg((ComponentLifecycleMsg) msg);
        } else if (msg instanceof PluginTerminationMsg) {
            onPluginTerminated((PluginTerminationMsg) msg);
        } else if(msg instanceof RuleTerminationMsg){
        	onRuleTerminated((RuleTerminationMsg)msg);
        } else if(msg instanceof CommandMsg){
        	onCommandMsg((CommandMsg)msg);
        } else if (msg instanceof ForwardLifecycleMsg) {
            onForwardLifecycleMsg((ForwardLifecycleMsg)msg);
        } else {
            logger.warning("Unknown message: {}!", msg);
        }
    }
    private void onRuleTerminated(RuleTerminationMsg msg) {
        ruleManager.remove(msg.getId());
    }
    private void onPluginTerminated(PluginTerminationMsg msg) {
        pluginManager.remove(msg.getId());
    }

    private void broadcast(Object msg) {
        pluginManager.broadcast(msg);
        tenantActors.values().forEach(actorRef -> actorRef.tell(msg, ActorRef.noSender()));
    }

    private void onToRuleMsg(ToRuleActorMsg msg) {
        ActorRef target;
        if (SYSTEM_TENANT.equals(msg.getTenantId())) {
            target = ruleManager.getOrCreateRuleActor(this.context(), msg.getRuleId());
        } else {
            target = getOrCreateTenantActor(msg.getTenantId());
        }
        target.tell(msg, ActorRef.noSender());
    }

    private void onToPluginMsg(ToPluginActorMsg msg) {
        ActorRef target;
        if (SYSTEM_TENANT.equals(msg.getPluginTenantId())) {
            target = pluginManager.getOrCreatePluginActor(this.context(), msg.getPluginId());
        } else {
            target = getOrCreateTenantActor(msg.getPluginTenantId());
        }
        target.tell(msg, ActorRef.noSender());
    }

    private void onComponentLifecycleMsg(ComponentLifecycleMsg msg) {
        ActorRef target = null;
        if (SYSTEM_TENANT.equals(msg.getTenantId())) {
            if (msg.getPluginId().isPresent()) {
                target = pluginManager.getOrCreatePluginActor(this.context(), msg.getPluginId().get());
            } else if (msg.getRuleId().isPresent()) {
                Optional<ActorRef> ref = ruleManager.update(this.context(), msg.getRuleId().get(), msg.getEvent());
                if (ref.isPresent()) {
                    target = ref.get();
                } else {
                    logger.debug("Failed to find actor for rule: [{}]", msg.getRuleId());
                    return;
                }
            }
        } else {
            target = getOrCreateTenantActor(msg.getTenantId());
        }
        if (target != null) {
            target.tell(msg, ActorRef.noSender());
        }
    }

    private void onToDeviceActorMsg(ToDeviceActorNotificationMsg msg) {
        getOrCreateTenantActor(msg.getTenantId()).tell(msg, ActorRef.noSender());
    }

    /**
     * 处理WEB界面参数查设请求
     * @param msg
     */
    private void onCommandMsg(CommandMsg msg){
    	getOrCreateTenantActor(msg.getTenantId()).tell(msg, ActorRef.noSender());
    }

    private void onForwardLifecycleMsg(ForwardLifecycleMsg msg){
        getOrCreateTenantActor(new TenantId(UUIDUtils.toUUID(msg.getTenantId()))).tell(msg, ActorRef.noSender());
    }

    private void processDeviceMsg(ToDeviceActorMsg toDeviceActorMsg) {
        TenantId tenantId = toDeviceActorMsg.getTenantId();
        // tenantId 为空，则表示设备还没归属到租户。不做处理
        if(tenantId == null) {
        	return;
        }
        ActorRef tenantActor = getOrCreateTenantActor(tenantId);
        if (toDeviceActorMsg.getPayload().getMsgType().requiresRulesProcessing()) {
            tenantActor.tell(new RuleChainDeviceMsg(toDeviceActorMsg, ruleManager.getRuleChain()), context().self());
        } else {
            tenantActor.tell(toDeviceActorMsg, context().self());
        }
    }

    private ActorRef getOrCreateTenantActor(TenantId tenantId) {
        ActorRef tenantActor = tenantActors.get(tenantId);
        if (tenantActor == null) {
            tenantActor = context().actorOf(Props.create(new TenantActor.ActorCreator(systemContext, tenantId))
                    .withDispatcher(DefaultActorService.CORE_DISPATCHER_NAME), tenantId.toString());
            tenantActors.put(tenantId, tenantActor);
        }
        return tenantActor;
    }

    private void processTermination(Terminated message) {
        ActorRef terminated = message.actor();
        if (terminated instanceof LocalActorRef) {
            logger.debug("Removed actor: {}", terminated);
        } else {
            throw new IllegalStateException("Remote actors are not supported!");
        }
    }

    public static class ActorCreator extends ContextBasedCreator<AppActor> {
        private static final long serialVersionUID = 1L;

        public ActorCreator(ActorSystemContext context) {
            super(context);
        }

        @Override
        public AppActor create() throws Exception {
            return new AppActor(context);
        }
    }

    private final SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create("1 minute"), new Function<Throwable, Directive>() {
        @Override
        public Directive apply(Throwable t) {
            logger.error(t, "Unknown failure");
            if (t instanceof RuntimeException) {
                return SupervisorStrategy.restart();
            } else {
                return SupervisorStrategy.stop();
            }
        }
    });
}
