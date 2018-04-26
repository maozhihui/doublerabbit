package com.comba.server.extensions.core.action.sms;

import lombok.extern.slf4j.Slf4j;

import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.extensions.api.component.Action;
import com.comba.server.extensions.api.plugins.PluginAction;
import com.comba.server.extensions.api.plugins.msg.PluginToRuleMsg;
import com.comba.server.extensions.api.plugins.msg.ResponsePluginToRuleMsg;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.SendSmsActionMsg;
import com.comba.server.extensions.api.plugins.msg.SendSmsRuleToPluginActionMsg;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleProcessingMetaData;
import com.comba.server.extensions.api.rules.SimpleRuleLifecycleComponent;

import java.util.Optional;

/**
 * @author Andrew Shvayka
 */
@Action(name = "Send Sms Action", descriptor = "SendSmsActionDescriptor.json", configuration = SendSmsActionConfiguration.class)
@Slf4j
public class SendSmsAction extends SimpleRuleLifecycleComponent implements PluginAction<SendSmsActionConfiguration> {

    private SendSmsActionConfiguration configuration;

    @Override
    public void init(SendSmsActionConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Optional<RuleToPluginMsg<?>> convert(RuleContext ctx, ToDeviceActorMsg toDeviceActorMsg, RuleProcessingMetaData metadata) {

        	SendSmsActionMsg.SendSmsActionMsgBuilder builder = SendSmsActionMsg.builder();
        	builder.dstNum(configuration.getDstNum());
        	builder.content(configuration.getContent());
        	builder.toDeviceActorMsg(toDeviceActorMsg);
        	builder.filters(ctx.getFilters());
            return Optional.of(new SendSmsRuleToPluginActionMsg(toDeviceActorMsg.getTenantId(), toDeviceActorMsg.getCustomerId(), toDeviceActorMsg.getDeviceId(),
                    builder.build()));
    }

    @Override
    public Optional<ToDeviceMsg> convert(PluginToRuleMsg<?> response) {
        if (response instanceof ResponsePluginToRuleMsg) {
            return Optional.of(((ResponsePluginToRuleMsg) response).getPayload());
        }
        return Optional.empty();
    }

    @Override
    public boolean isOneWayAction() {
        return false;
    }

}
