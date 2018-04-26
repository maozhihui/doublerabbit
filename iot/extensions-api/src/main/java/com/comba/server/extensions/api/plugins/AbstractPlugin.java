package com.comba.server.extensions.api.plugins;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ServerAddress;

import com.comba.server.extensions.api.plugins.handlers.*;
import com.comba.server.extensions.api.plugins.msg.FromDeviceRpcResponse;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.TimeoutMsg;
import com.comba.server.extensions.api.rules.RuleException;

/**
 * @author Andrew Shvayka
 */
public abstract class AbstractPlugin<T> implements Plugin<T> {

    @Override
    public void process(PluginContext ctx, TenantId tenantId, RuleId ruleId, RuleToPluginMsg<?> msg) throws RuleException {
        getRuleMsgHandler().process(ctx, tenantId, ruleId, msg);
    }

    @Override
    public void process(PluginContext ctx, FromDeviceRpcResponse msg) {
        throw new IllegalStateException("Device RPC messages is not handled in current plugin!");
    }

    @Override
    public void process(PluginContext ctx, TimeoutMsg<?> msg) {
        throw new IllegalStateException("Timeouts is not handled in current plugin!");
    }

    @Override
    public void onServerAdded(PluginContext ctx, ServerAddress server) {
    }

    @Override
    public void onServerRemoved(PluginContext ctx, ServerAddress server) {
    }

    protected RuleMsgHandler getRuleMsgHandler() {
        return new DefaultRuleMsgHandler();
    }

}
