package com.comba.server.extensions.core.plugin.sms;

import lombok.extern.slf4j.Slf4j;

import com.comba.server.extensions.api.component.EmptyComponentConfiguration;
import com.comba.server.extensions.api.component.Plugin;
import com.comba.server.extensions.api.plugins.AbstractPlugin;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.RuleMsgHandler;
import com.comba.server.extensions.core.action.sms.SendSmsAction;

/**
 * @author Andrew Shvayka
 */
@Plugin(name = "Sms Plugin", actions = {SendSmsAction.class})
@Slf4j
public class SmsPlugin extends AbstractPlugin<EmptyComponentConfiguration> {

    private EmptyComponentConfiguration configuration;    
    private final SmsMsgHandler smsMsgHandler;

    public  SmsPlugin() {
        this.smsMsgHandler = new SmsMsgHandler();
    }
    @Override
    public void init(EmptyComponentConfiguration configuration) {
        log.info("Initializing plugin using configuration {}", configuration);
        this.configuration = configuration;
    }

    @Override
    public void resume(PluginContext ctx) {
    }

    @Override
    public void suspend(PluginContext ctx) {
//        mailSender = null;
    }

    @Override
    public void stop(PluginContext ctx) {
//        mailSender = null;
    }

    @Override
    protected RuleMsgHandler getRuleMsgHandler() {
    	return smsMsgHandler;
    }
}
