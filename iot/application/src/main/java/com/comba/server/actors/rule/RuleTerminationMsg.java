package com.comba.server.actors.rule;

import com.comba.server.actors.shared.ActorTerminationMsg;
import com.comba.server.common.data.id.RuleId;

/**
 * @author Andrew Shvayka
 */
public class RuleTerminationMsg extends ActorTerminationMsg<RuleId> {

    public RuleTerminationMsg(RuleId id) {
        super(id);
    }
}
