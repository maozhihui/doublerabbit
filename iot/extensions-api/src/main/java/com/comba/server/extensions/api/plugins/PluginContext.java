package com.comba.server.extensions.api.plugins;

import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.id.*;
import com.comba.server.common.data.kv.AttributeKvEntry;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.msg.cluster.ServerAddress;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.plugins.msg.*;

import java.util.List;
import java.util.Optional;

public interface PluginContext {

    PluginId getPluginId();

    void reply(PluginToRuleMsg<?> msg);

    /*
        Device RPC API
     */
    Optional<ServerAddress> resolve(DeviceId deviceId);

    /*
        Attributes API
     */
    void saveAttributes(TenantId tenantId, DeviceId deviceId, String attributeType, List<AttributeKvEntry> attributes, PluginCallback<Void> callback);

    List<ConfigAttributes> loadAttributes(DeviceId deviceId);
    
    /*
    Telemetry API
     */
	void saveTelemetres(TenantId tenantId, DeviceId deviceId, String clientScope, DeviceTelemetryAttributes originalAttributesList,
			List<KvEntry> uploadAttributesList,	PluginCallback<Void> pluginCallback);
    // Sms API
	void sendSms(TenantId tenantId, DeviceId deviceId, String scope,
			SendSmsRuleToPluginActionMsg msg, PluginCallback<Void> callback);

	// send MQ
    void sendMQMsg(TenantId tenantId, DeviceId deviceId, String scope,
                   BasicAlarmRuleToPluginMsg msg, PluginCallback<Void> callback);

    void sendCommandMsg(TenantId tenantId,
                        BasicCmdRuleToPluginMsg msg,PluginCallback<Void> callback);
}
