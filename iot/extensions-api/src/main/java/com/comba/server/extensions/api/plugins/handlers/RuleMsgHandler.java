package com.comba.server.extensions.api.plugins.handlers;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;

import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.rules.RuleException;

/**
 * @author Andrew Shvayka
 */
public interface RuleMsgHandler {

    void process(PluginContext ctx, TenantId tenantId, RuleId ruleId, RuleToPluginMsg<?> msg) throws RuleException;

}
