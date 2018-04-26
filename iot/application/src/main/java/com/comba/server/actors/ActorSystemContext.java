package com.comba.server.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Scheduler;

import com.comba.alarm.rule.AnalyseAlarmRuleService;
import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.DataConstants;
import com.comba.server.common.data.Event;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.msg.cluster.ServerAddress;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.dao.attributes.AttributesService;
import com.comba.server.dao.device.*;
import com.comba.server.dao.plugin.PluginJpaService;
import com.comba.server.dao.plugin.PluginService;
import com.comba.server.dao.rule.ForwardRuleService;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.server.dao.rule.RuleService;
import com.comba.server.dao.tenant.TenantService;
import com.comba.server.dao.upgrade.record.UpgradeRecordService;
import com.comba.server.service.component.ComponentDiscoveryService;
import com.comba.server.service.mq.MQConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.ql.util.express.ExpressRunner;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@Component
public class ActorSystemContext {
    private static final String AKKA_CONF_FILE_NAME = "actor-system.conf";

    protected final ObjectMapper mapper = new ObjectMapper();

    @Getter @Setter private ActorService actorService;

    @Autowired
    @Getter @Setter private ComponentDiscoveryService componentService;

    @Autowired
    @Getter private DeviceAuthService deviceAuthService;

    @Autowired
    @Getter private DeviceService deviceService;

    @Autowired
    @Getter private TenantService tenantJpaService;

    
    @Autowired
    @Getter private RuleJpaService ruleJpaService;

    @Autowired
    @Getter private PluginJpaService pluginJpaService;
    
    @Autowired
    @Getter private AttributesService attributesService;

    @Autowired
    @Getter private ConfigAttributesService configAttributesService;
    
    @Autowired
    @Getter private TelemetryAttributesService telemetryAttributesService;
    
    @Autowired
    @Getter private HistoryDataService historyDataService;

    @Autowired
    @Getter private ForwardRuleService forwardRuleService;

    @Autowired
    @Getter private UpgradeRecordService upgradeRecordService;

    @Autowired
    @Getter private LatestDataService latestDataService;

    @Autowired
    @Getter private AnalyseAlarmRuleService alarmRuleService;

    @Autowired
    @Getter private MQConfig mqConfig;

    @Getter private Gson gson = new Gson();

    @Value("${actors.session.sync.timeout}")
    @Getter private long syncSessionTimeout;

    @Value("${actors.plugin.termination.delay}")
    @Getter private long pluginActorTerminationDelay;

    @Value("${actors.plugin.processing.timeout}")
    @Getter private long pluginProcessingTimeout;

    @Value("${actors.plugin.error_persist_frequency}")
    @Getter private long pluginErrorPersistFrequency;

    @Value("${actors.rule.termination.delay}")
    @Getter private long ruleActorTerminationDelay;

    @Value("${actors.rule.error_persist_frequency}")
    @Getter private long ruleErrorPersistFrequency;

    @Value("${actors.statistics.enabled}")
    @Getter private boolean statisticsEnabled;

    @Value("${actors.statistics.persist_frequency}")
    @Getter private long statisticsPersistFrequency;

    @Value("${device.setting.timeout}")
    @Getter private long deviceSettingTimeout;
    
    @Getter @Setter private ActorSystem actorSystem;

    @Getter @Setter private ActorRef appActor;

    @Getter @Setter private ActorRef sessionManagerActor;

    @Getter @Setter private ActorRef forwardActor;

    @Value("${device.setting.AuthEnable}")
    @Getter @Setter private boolean authEnable;

    @Value("${http.register_timeout}")
    @Getter @Setter private long registerTimeout;

    @Value("${global.onlineState.checkTime}")
    @Getter private long checkTime;
    @Value("${global.onlineState.timeout}")
    @Getter private long globalOnlineStateTimeout;
    @Getter private final Config config;

    @Getter private final ExpressRunner expressRunner = new ExpressRunner();

    public ActorSystemContext() {
        config = ConfigFactory.parseResources(AKKA_CONF_FILE_NAME).withFallback(ConfigFactory.load());
    }

    public Scheduler getScheduler() {
        return actorSystem.scheduler();
    }

    public void persistError(TenantId tenantId, EntityId entityId, String method, Exception e) {
        Event event = new Event();
        event.setTenantId(tenantId);
        event.setEntityId(entityId);
        event.setType(DataConstants.ERROR);
        //event.setBody(toBodyJson(discoveryService.getCurrentServer().getServerAddress(), method, toString(e)));
        persistEvent(event);
    }

    public void persistLifecycleEvent(TenantId tenantId, EntityId entityId, ComponentLifecycleEvent lcEvent, Exception e) {
        Event event = new Event();
        event.setTenantId(tenantId);
        event.setEntityId(entityId);
        event.setType(DataConstants.LC_EVENT);
        //event.setBody(toBodyJson(discoveryService.getCurrentServer().getServerAddress(), lcEvent, Optional.ofNullable(e)));
        persistEvent(event);
    }

    private void persistEvent(Event event) {
        //eventService.save(event);
    }

    private String toString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private JsonNode toBodyJson(ServerAddress server, ComponentLifecycleEvent event, Optional<Exception> e) {
        ObjectNode node = mapper.createObjectNode().put("server", server.toString()).put("event", event.name());
        if (e.isPresent()) {
            node = node.put("success", false);
            node = node.put("error", toString(e.get()));
        } else {
            node = node.put("success", true);
        }
        return node;
    }

    private JsonNode toBodyJson(ServerAddress server, String method, String body) {
        return mapper.createObjectNode().put("server", server.toString()).put("method", method).put("error", body);
    }
}
