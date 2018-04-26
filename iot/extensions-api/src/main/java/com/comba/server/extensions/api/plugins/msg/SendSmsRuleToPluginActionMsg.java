package com.comba.server.extensions.api.plugins.msg;

import lombok.Data;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;

/**
 * @author huangjinlong
 */
@Data
public class SendSmsRuleToPluginActionMsg extends AbstractRuleToPluginMsg<SendSmsActionMsg> {

    public SendSmsRuleToPluginActionMsg(TenantId tenantId, CustomerId customerId, DeviceId deviceId,
                                     SendSmsActionMsg payload) {
        super(tenantId, customerId, deviceId, payload);
    }

}
