package com.comba.server.extensions.api.plugins;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.TenantId;

import java.io.Serializable;

public final class PluginApiCallSecurityContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private final TenantId pluginTenantId;
    private final PluginId pluginId;
    private final TenantId tenantId;
    private final CustomerId customerId;

    public PluginApiCallSecurityContext(TenantId pluginTenantId, PluginId pluginId, TenantId tenantId, CustomerId customerId) {
        super();
        this.pluginTenantId = pluginTenantId;
        this.pluginId = pluginId;
        this.tenantId = tenantId;
        this.customerId = customerId;
    }

    public TenantId getPluginTenantId(){
        return pluginTenantId;
    }

    public PluginId getPluginId() {
        return pluginId;
    }

    public boolean isSystemAdmin() {
        return tenantId == null || EntityId.NULL_UUID.equals(tenantId.getId());
    }

    public boolean isTenantAdmin() {
        return !isSystemAdmin() && (customerId == null || EntityId.NULL_UUID.equals(customerId.getId()));
    }

    public boolean isCustomerUser() {
        return !isSystemAdmin() && !isTenantAdmin();
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

}
