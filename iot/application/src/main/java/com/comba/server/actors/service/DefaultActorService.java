package com.comba.server.actors.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import com.comba.server.actors.rule.ForwardActor;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.plugin.ForwardLifecycleMsg;
import com.comba.server.common.msg.session.NotificationMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.app.AppActor;
import com.comba.server.actors.session.SessionManagerActor;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.msg.aware.SessionAwareMsg;
import com.comba.server.common.msg.cluster.ToAllNodesMsg;
import com.comba.server.common.msg.cmd.CommandMsg;
import com.comba.server.common.msg.plugin.ComponentLifecycleMsg;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Slf4j
public class DefaultActorService implements ActorService {

    private static final String ACTOR_SYSTEM_NAME = "Akka";

    public static final String APP_DISPATCHER_NAME = "app-dispatcher";
    public static final String CORE_DISPATCHER_NAME = "core-dispatcher";
    public static final String SYSTEM_RULE_DISPATCHER_NAME = "system-rule-dispatcher";
    public static final String SYSTEM_PLUGIN_DISPATCHER_NAME = "system-plugin-dispatcher";
    public static final String TENANT_RULE_DISPATCHER_NAME = "rule-dispatcher";
    public static final String TENANT_PLUGIN_DISPATCHER_NAME = "plugin-dispatcher";
    public static final String SESSION_DISPATCHER_NAME = "session-dispatcher";

    @Autowired
    private ActorSystemContext actorContext;

    private ActorSystem system;

    private ActorRef appActor;

    private ActorRef sessionManagerActor;

    private ActorRef forwardActor;

    @PostConstruct
    public void initActorSystem() {
        log.info("Initializing Actor system. {}", actorContext.getRuleJpaService());
        actorContext.setActorService(this);
        system = ActorSystem.create(ACTOR_SYSTEM_NAME, actorContext.getConfig());
        actorContext.setActorSystem(system);

        appActor = system.actorOf(Props.create(new AppActor.ActorCreator(actorContext)).withDispatcher(APP_DISPATCHER_NAME), "appActor");
        actorContext.setAppActor(appActor);

        sessionManagerActor = system.actorOf(Props.create(new SessionManagerActor.ActorCreator(actorContext)).withDispatcher(CORE_DISPATCHER_NAME),
                "sessionManagerActor");
        actorContext.setSessionManagerActor(sessionManagerActor);

        forwardActor = system.actorOf(Props.create(new ForwardActor.ActorCreator(actorContext)).withDispatcher(CORE_DISPATCHER_NAME),
                "forwardActor");
        actorContext.setForwardActor(forwardActor);
        log.info("Actor system initialized.");
    }

    @PreDestroy
    public void stopActorSystem() {
        Future<Terminated> status = system.terminate();
        try {
            Terminated terminated = Await.result(status, Duration.Inf());
            log.info("Actor system terminated: {}", terminated);
        } catch (Exception e) {
            log.error("Failed to terminate actor system.", e);
        }
    }

    @Override
    public void process(SessionAwareMsg msg) {
        if (msg instanceof NotificationMsg) {
            // 针对MQTT订阅之后的推送消息处理
            log.info("Processing NotificationMsg,sessionId [{}]",msg.getSessionId());
            appActor.tell(((NotificationMsg)msg).getToDeviceActorMsg(),ActorRef.noSender());
            return;
        }
        if (msg instanceof SessionAwareMsg) {
            log.debug("Processing session aware msg: {}", msg);
            sessionManagerActor.tell(msg, ActorRef.noSender());
        }
    }

    @Override
    public void onPluginStateChange(TenantId tenantId, PluginId pluginId, ComponentLifecycleEvent state) {
        log.trace("[{}] Processing onPluginStateChange event: {}", pluginId, state);
        broadcast(ComponentLifecycleMsg.forPlugin(tenantId, pluginId, state));
    }

    @Override
    public void onRuleStateChange(TenantId tenantId, RuleId ruleId, ComponentLifecycleEvent state) {
        log.trace("[{}] Processing onRuleStateChange event: {}", ruleId, state);
        broadcast(ComponentLifecycleMsg.forRule(tenantId, ruleId, state));
    }

    public void broadcast(ToAllNodesMsg msg) {
        appActor.tell(msg, ActorRef.noSender());
    }
    
    @Override
    public void onCommandProcess(CommandMsg cmdMsg){
    	this.appActor.tell(cmdMsg, ActorRef.noSender());
    }

    @Override
    public void onForwardRuleStateChange(TenantId tenantId, String forwardId, ComponentLifecycleEvent state){
        ForwardLifecycleMsg msg = new ForwardLifecycleMsg(UUIDUtils.toUUID(tenantId.getId()),forwardId,state);
        this.appActor.tell(msg,ActorRef.noSender());
        this.forwardActor.tell(msg,ActorRef.noSender());
    }
}
