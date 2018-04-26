package com.comba.server.extensions.api.plugins;

import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.ToDeviceMsg;

import com.comba.server.extensions.api.component.ConfigurableComponent;
import com.comba.server.extensions.api.plugins.msg.PluginToRuleMsg;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleLifecycleComponent;
import com.comba.server.extensions.api.rules.RuleProcessingMetaData;

import java.util.Optional;

public interface PluginAction<T> extends ConfigurableComponent<T>, RuleLifecycleComponent {

    Optional<RuleToPluginMsg<?>> convert(RuleContext ctx, ToDeviceActorMsg toDeviceActorMsg, RuleProcessingMetaData deviceMsgMd);

    Optional<ToDeviceMsg> convert(PluginToRuleMsg<?> response);

    boolean isOneWayAction();

}
