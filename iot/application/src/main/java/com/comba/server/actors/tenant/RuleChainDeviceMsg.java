package com.comba.server.actors.tenant;

import com.comba.server.actors.rule.RuleActorChain;
import com.comba.server.common.msg.device.ToDeviceActorMsg;

public class RuleChainDeviceMsg {

    private final ToDeviceActorMsg toDeviceActorMsg;
    private final RuleActorChain ruleChain;

    public RuleChainDeviceMsg(ToDeviceActorMsg toDeviceActorMsg, RuleActorChain ruleChain) {
        super();
        this.toDeviceActorMsg = toDeviceActorMsg;
        this.ruleChain = ruleChain;
    }

    public ToDeviceActorMsg getToDeviceActorMsg() {
        return toDeviceActorMsg;
    }

    public RuleActorChain getRuleChain() {
        return ruleChain;
    }

}
