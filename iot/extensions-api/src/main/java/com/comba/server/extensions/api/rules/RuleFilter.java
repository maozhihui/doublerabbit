package com.comba.server.extensions.api.rules;

import com.comba.server.common.msg.device.ToDeviceActorMsg;

import com.comba.server.extensions.api.component.ConfigurableComponent;

/**
 * @author Andrew Shvayka
 */
public interface RuleFilter<T> extends ConfigurableComponent<T>, RuleLifecycleComponent {

    boolean filter(RuleContext ctx, ToDeviceActorMsg msg);

    /**
     * 获取条件名称
     * @return
     */
    String getName();

    /**
     * 获取条件本身是否匹配
     * @return
     */
    boolean isMatched();
}
