package com.comba.server.extensions.api.plugins.msg;

import lombok.Data;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.TenantId;

/**
 * @author Andrew Shvayka
 */
@Data
public class ToPluginRpcResponseDeviceMsg implements ToPluginActorMsg {
    private final PluginId pluginId;
    private final TenantId pluginTenantId;
    private final FromDeviceRpcResponse response;
}

