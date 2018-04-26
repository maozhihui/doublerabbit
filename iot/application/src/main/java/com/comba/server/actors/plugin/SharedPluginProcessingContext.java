package com.comba.server.actors.plugin;

import akka.actor.ActorRef;
import lombok.extern.slf4j.Slf4j;
import scala.concurrent.duration.Duration;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ServerAddress;
import com.comba.server.dao.attributes.AttributesService;
import com.comba.server.dao.device.ConfigAttributesService;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.HistoryDataService;
import com.comba.server.dao.device.TelemetryAttributesService;
import com.comba.server.extensions.api.device.DeviceAttributesEventNotificationMsg;
import com.comba.server.extensions.api.plugins.msg.TimeoutMsg;
import com.comba.server.extensions.api.plugins.msg.ToDeviceRpcRequest;
import com.comba.server.extensions.api.plugins.msg.ToDeviceRpcRequestPluginMsg;

@Slf4j
public final class SharedPluginProcessingContext {
    final ActorRef parentActor;
    final ActorRef currentActor;
    final ActorSystemContext systemContext;
    final DeviceService deviceService;
    final ConfigAttributesService attributesService;
    final TelemetryAttributesService telemetryAttributesService;
    final HistoryDataService historyDataService;
    final PluginId pluginId;
    final TenantId tenantId;

    public SharedPluginProcessingContext(ActorSystemContext sysContext, TenantId tenantId, PluginId pluginId,
                                         ActorRef parentActor, ActorRef self) {
        super();
        this.tenantId = tenantId;
        this.pluginId = pluginId;
        this.parentActor = parentActor;
        this.currentActor = self;
        this.systemContext = sysContext;
        this.attributesService = sysContext.getConfigAttributesService();
        this.telemetryAttributesService = sysContext.getTelemetryAttributesService();
        this.historyDataService = sysContext.getHistoryDataService();
        this.deviceService = sysContext.getDeviceService();
    }

    public PluginId getPluginId() {
        return pluginId;
    }

    public ActorRef self() {
        return currentActor;
    }
}
