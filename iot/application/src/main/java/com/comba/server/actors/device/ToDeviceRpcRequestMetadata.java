package com.comba.server.actors.device;

import com.comba.server.extensions.api.plugins.msg.ToDeviceRpcRequestPluginMsg;

import lombok.Data;

/**
 * @author Andrew Shvayka
 */
@Data
public class ToDeviceRpcRequestMetadata {
    private final ToDeviceRpcRequestPluginMsg msg;
    private final boolean sent;
}
