package com.comba.server.extensions.api.plugins.msg;

import lombok.Data;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Andrew Shvayka
 */
@Data
public class ToDeviceRpcRequest implements Serializable {
    private final UUID id;
    private final TenantId tenantId;
    private final DeviceId deviceId;
    private final boolean oneway;
    private final long expirationTime;
    private final ToDeviceRpcRequestBody body;
}

