package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractRuleToPluginMsg<T extends Serializable> implements RuleToPluginMsg<T> {

    private static final long serialVersionUID = 1L;

    private final UUID uid;
    private final TenantId tenantId;
    private final CustomerId customerId;
    private final DeviceId deviceId;
    private final T payload;

    public AbstractRuleToPluginMsg(TenantId tenantId, CustomerId customerId, DeviceId deviceId, T payload) {
        super();
        this.uid = UUID.randomUUID();
        this.tenantId = tenantId;
        this.customerId = customerId;
        this.deviceId = deviceId;
        this.payload = payload;
    }

    @Override
    public UUID getUid() {
        return uid;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "AbstractRuleToPluginMsg [uid=" + uid + ", tenantId=" + tenantId + ", customerId=" + customerId
                + ", deviceId=" + deviceId + ", payload=" + payload + "]";
    }

}
