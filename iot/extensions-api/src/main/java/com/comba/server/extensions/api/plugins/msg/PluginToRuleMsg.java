package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;

import com.comba.server.extensions.api.rules.ToRuleActorMsg;

import java.io.Serializable;
import java.util.UUID;

/**
 * The basic interface for messages that are sent from particular plugin to rule
 * instance
 * 
 * @author ashvayka
 * @see RuleToPluginMsg
 *
 */
public interface PluginToRuleMsg<V extends Serializable> extends ToRuleActorMsg, Serializable {

    /**
     * Returns the unique identifier of the message
     * 
     * @return unique identifier of the message.
     */
    UUID getUid();

    /**
     * Returns the unique identifier of the tenant that owns the rule
     *
     * @return unique identifier of the tenant that owns the rule.
     *
     */
    TenantId getTenantId();

    /**
     * Returns the unique identifier of the rule
     * 
     * @return unique identifier of the rule.
     */
    RuleId getRuleId();

    /**
     * Returns the serializable message payload.
     * 
     * @return the serializable message payload.
     */
    V getPayload();

}
