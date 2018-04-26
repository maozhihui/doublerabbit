package com.comba.server.actors.service;

import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.msg.cmd.CommandMsg;
import com.comba.server.common.transport.SessionMsgProcessor;

public interface ActorService extends SessionMsgProcessor{

    void onPluginStateChange(TenantId tenantId, PluginId pluginId, ComponentLifecycleEvent state);

    void onRuleStateChange(TenantId tenantId, RuleId ruleId, ComponentLifecycleEvent state);
    
    void onCommandProcess(CommandMsg cmdMsg);

    void onForwardRuleStateChange(TenantId tenantId, String forwardId, ComponentLifecycleEvent state);
}
