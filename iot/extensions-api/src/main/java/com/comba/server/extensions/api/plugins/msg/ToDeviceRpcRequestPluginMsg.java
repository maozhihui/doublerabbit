package com.comba.server.extensions.api.plugins.msg;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ServerAddress;

import com.comba.server.extensions.api.device.ToDeviceActorNotificationMsg;

import java.util.Optional;

/**
 * @author Andrew Shvayka
 */
@ToString
@RequiredArgsConstructor
public class ToDeviceRpcRequestPluginMsg implements ToDeviceActorNotificationMsg {

    private final ServerAddress serverAddress;
    @Getter
    private final PluginId pluginId;
    @Getter
    private final TenantId pluginTenantId;
    @Getter
    private final ToDeviceRpcRequest msg;

    public ToDeviceRpcRequestPluginMsg(PluginId pluginId, TenantId pluginTenantId, ToDeviceRpcRequest msg) {
        this(null, pluginId, pluginTenantId, msg);
    }

    public Optional<ServerAddress> getServerAddress() {
        return Optional.ofNullable(serverAddress);
    }

    @Override
    public DeviceId getDeviceId() {
        return msg.getDeviceId();
    }

    @Override
    public TenantId getTenantId() {
        return msg.getTenantId();
    }
}

