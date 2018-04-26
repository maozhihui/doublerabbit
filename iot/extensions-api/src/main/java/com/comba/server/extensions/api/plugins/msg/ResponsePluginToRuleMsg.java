package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.session.ToDeviceMsg;

import java.util.UUID;

public class ResponsePluginToRuleMsg extends AbstractPluginToRuleMsg<ToDeviceMsg>{

    private static final long serialVersionUID = 1L;

    public ResponsePluginToRuleMsg(UUID uid, TenantId tenantId, RuleId ruleId, ToDeviceMsg payload) {
        super(uid, tenantId, ruleId, payload);
    }

}
