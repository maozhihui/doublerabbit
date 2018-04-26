package com.comba.server.extensions.core.plugin.telemetry;

import lombok.extern.slf4j.Slf4j;
import com.comba.server.common.msg.cluster.ServerAddress;

import com.comba.server.extensions.api.component.EmptyComponentConfiguration;
import com.comba.server.extensions.api.component.Plugin;
import com.comba.server.extensions.api.plugins.AbstractPlugin;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.RuleMsgHandler;
import com.comba.server.extensions.core.action.telemetry.TelemetryPluginAction;
import com.comba.server.extensions.core.plugin.telemetry.handlers.TelemetryRuleMsgHandler;

@Plugin(name = "Telemetry Plugin", actions = {TelemetryPluginAction.class})
@Slf4j
public class TelemetryStoragePlugin extends AbstractPlugin<EmptyComponentConfiguration> {

    private final TelemetryRuleMsgHandler ruleMsgHandler;

    public TelemetryStoragePlugin() {
        this.ruleMsgHandler = new TelemetryRuleMsgHandler();
    }

    @Override
    public void init(EmptyComponentConfiguration configuration) {

    }

    @Override
    protected RuleMsgHandler getRuleMsgHandler() {
        return ruleMsgHandler;
    }

    @Override
    public void onServerAdded(PluginContext ctx, ServerAddress server) {
        //subscriptionManager.onClusterUpdate(ctx);
    }

    @Override
    public void onServerRemoved(PluginContext ctx, ServerAddress server) {
        //subscriptionManager.onClusterUpdate(ctx);
    }

    @Override
    public void resume(PluginContext ctx) {
        log.info("Plugin activated!");
    }

    @Override
    public void suspend(PluginContext ctx) {
        log.info("Plugin suspended!");
    }

    @Override
    public void stop(PluginContext ctx) {
        //subscriptionManager.clear();
        //websocketMsgHandler.clear(ctx);
        log.info("Plugin stopped!");
    }
}
