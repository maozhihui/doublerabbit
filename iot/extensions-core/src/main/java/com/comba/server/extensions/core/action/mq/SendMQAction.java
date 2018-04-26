package com.comba.server.extensions.core.action.mq;

import com.comba.server.common.msg.core.BasicAlarmMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.extensions.api.component.Action;
import com.comba.server.extensions.api.component.EmptyComponentConfiguration;
import com.comba.server.extensions.api.plugins.PluginAction;
import com.comba.server.extensions.api.plugins.msg.BasicAlarmRuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.PluginToRuleMsg;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleProcessingMetaData;
import com.comba.server.extensions.api.rules.SimpleRuleLifecycleComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * SendMQAction
 *
 * @author maozhihui
 * @create 2017-10-12 19:32
 **/
@Slf4j
@Action(name = "MQ Plugin Action",descriptor = "AlarmActionDescriptor.json",configuration = AlarmActionConfiguration.class)
public class SendMQAction extends SimpleRuleLifecycleComponent implements PluginAction<AlarmActionConfiguration> {

    private AlarmActionConfiguration configuration;

    @Override
    public Optional<RuleToPluginMsg<?>> convert(RuleContext ctx, ToDeviceActorMsg toDeviceActorMsg, RuleProcessingMetaData deviceMsgMd) {
        log.info("alarm info SendMQAction,deviceId [{}]",toDeviceActorMsg.getDeviceId().toString());
        return Optional.of(new BasicAlarmRuleToPluginMsg(toDeviceActorMsg.getTenantId(),toDeviceActorMsg.getCustomerId(),toDeviceActorMsg.getDeviceId(),
                new BasicAlarmMsg(toDeviceActorMsg.getDeviceId().toString(),configuration.getType(),Integer.parseInt(configuration.getStatus()))));
    }

    @Override
    public Optional<ToDeviceMsg> convert(PluginToRuleMsg<?> response) {
        return null;
    }

    @Override
    public boolean isOneWayAction() {
        return false;
    }

    @Override
    public void init(AlarmActionConfiguration configuration) {
        this.configuration = configuration;
    }
}
