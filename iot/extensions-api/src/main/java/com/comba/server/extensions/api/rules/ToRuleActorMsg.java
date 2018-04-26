package com.comba.server.extensions.api.rules;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.msg.aware.TenantAwareMsg;

public interface ToRuleActorMsg extends TenantAwareMsg {

    RuleId getRuleId();
    
}
