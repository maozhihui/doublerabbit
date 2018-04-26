package com.comba.server.actors.shared.rule;

import java.util.List;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.service.DefaultActorService;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.page.PageDataIterable.FetchFunction;
import com.comba.server.common.data.rule.RuleMetaData;

public class TenantRuleManager extends RuleManager {
    
    public TenantRuleManager(ActorSystemContext systemContext, TenantId tenantId) {
        super(systemContext, tenantId);
    }

    @Override
    String getDispatcherName() {
        return DefaultActorService.TENANT_RULE_DISPATCHER_NAME;
    }

	@Override
	List<Rule> getFetchRules() {
		return ruleJpaService.findTenantRules(tenantId);
	}

}
