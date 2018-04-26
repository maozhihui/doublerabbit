package com.comba.server.extensions.api.plugins;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ServerAddress;

import com.comba.server.extensions.api.component.ConfigurableComponent;
import com.comba.server.extensions.api.plugins.msg.FromDeviceRpcResponse;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.TimeoutMsg;
import com.comba.server.extensions.api.rules.RuleException;

public interface Plugin<T> extends ConfigurableComponent<T> {

    void process(PluginContext ctx, TenantId tenantId, RuleId ruleId, RuleToPluginMsg<?> msg) throws RuleException;

    void process(PluginContext ctx, FromDeviceRpcResponse msg);

    void process(PluginContext ctx, TimeoutMsg<?> msg);

    void onServerAdded(PluginContext ctx, ServerAddress server);

    void onServerRemoved(PluginContext ctx, ServerAddress server);

    void resume(PluginContext ctx);

    void suspend(PluginContext ctx);

    void stop(PluginContext ctx);

}
