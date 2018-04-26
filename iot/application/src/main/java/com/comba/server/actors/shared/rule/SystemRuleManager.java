package com.comba.server.actors.shared.rule;

import java.util.List;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.service.DefaultActorService;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.page.PageDataIterable.FetchFunction;
import com.comba.server.common.data.rule.RuleMetaData;
import com.comba.server.dao.model.ModelConstants;

public class SystemRuleManager extends RuleManager {

    public SystemRuleManager(ActorSystemContext systemContext) {
        super(systemContext, new TenantId(ModelConstants.NULL_UUID));
    }

    @Override
    String getDispatcherName() {
        return DefaultActorService.SYSTEM_RULE_DISPATCHER_NAME;
    }

	@Override
	List<Rule> getFetchRules() {
		return ruleJpaService.findSystemRules();
	}
}
