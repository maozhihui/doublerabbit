package com.comba.server.extensions.core.plugin.mq;

import com.comba.server.extensions.api.component.EmptyComponentConfiguration;
import com.comba.server.extensions.api.component.Plugin;
import com.comba.server.extensions.api.plugins.AbstractPlugin;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.RuleMsgHandler;
import com.comba.server.extensions.core.action.mq.SendMQAction;
import lombok.extern.slf4j.Slf4j;

/**
 * PublisherPlugin
 *
 * @author maozhihui
 * @create 2017-10-12 20:18
 **/
@Plugin(name = "Publisher Plugin", actions = {SendMQAction.class})
@Slf4j
public class PublisherPlugin extends AbstractPlugin<EmptyComponentConfiguration> {

    private final PublisherPluginHandler ruleMsgHandler;

    public PublisherPlugin(){
        this.ruleMsgHandler = new PublisherPluginHandler();
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
        return this.ruleMsgHandler;
    }
}
