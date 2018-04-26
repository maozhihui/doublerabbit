package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.GetAttributesRequest;

/**
 * @author Andrew Shvayka
 */
public class GetAttributesRequestRuleToPluginMsg extends AbstractRuleToPluginMsg<GetAttributesRequest> {

    private static final long serialVersionUID = 1L;

    public GetAttributesRequestRuleToPluginMsg(TenantId tenantId, CustomerId customerId, DeviceId deviceId, GetAttributesRequest payload) {
        super(tenantId, customerId, deviceId, payload);
    }
}
