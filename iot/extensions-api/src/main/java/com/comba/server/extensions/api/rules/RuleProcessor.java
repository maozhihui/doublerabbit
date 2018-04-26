package com.comba.server.extensions.api.rules;

import com.comba.server.common.msg.device.ToDeviceActorMsg;

import com.comba.server.extensions.api.component.ConfigurableComponent;

/**
 * @author Andrew Shvayka
 */
public interface RuleProcessor<T> extends ConfigurableComponent<T>, RuleLifecycleComponent {

    RuleProcessingMetaData process(RuleContext ctx, ToDeviceActorMsg msg) throws RuleException;
}
