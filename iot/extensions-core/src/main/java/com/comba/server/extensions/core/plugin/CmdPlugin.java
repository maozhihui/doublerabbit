package com.comba.server.extensions.core.plugin;

import com.comba.server.extensions.api.component.EmptyComponentConfiguration;
import com.comba.server.extensions.api.component.Plugin;
import com.comba.server.extensions.api.plugins.AbstractPlugin;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.RuleMsgHandler;
import com.comba.server.extensions.core.action.DeviceCtrlAction;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/1 17:04
 **/
@Plugin(name = "CmdPlugin",actions = {DeviceCtrlAction.class})
public class CmdPlugin extends AbstractPlugin<EmptyComponentConfiguration> {

    private final CmdPluginHandler handler;

    public CmdPlugin(){
        this.handler = new CmdPluginHandler();
    }

    @Override
    public void resume(PluginContext ctx) {

    }

    @Override
    public void suspend(PluginContext ctx) {

    }

    @Override
    public void stop(PluginContext ctx) {

    }

    @Override
    public void init(EmptyComponentConfiguration configuration) {

    }

    @Override
    public RuleMsgHandler getRuleMsgHandler(){
        return this.handler;
    }
}
