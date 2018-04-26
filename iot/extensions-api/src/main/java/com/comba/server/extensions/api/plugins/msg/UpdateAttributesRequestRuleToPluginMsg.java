package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.UpdateAttributesRequest;

public class UpdateAttributesRequestRuleToPluginMsg extends AbstractRuleToPluginMsg<UpdateAttributesRequest> {

    private static final long serialVersionUID = 1L;

    public UpdateAttributesRequestRuleToPluginMsg(TenantId tenantId, CustomerId customerId, DeviceId deviceId, UpdateAttributesRequest payload) {
        super(tenantId, customerId, deviceId, payload);
    }

}
