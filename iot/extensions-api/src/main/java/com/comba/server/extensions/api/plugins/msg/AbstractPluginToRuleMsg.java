package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;

import java.io.Serializable;
import java.util.UUID;

public class AbstractPluginToRuleMsg<T extends Serializable> implements PluginToRuleMsg<T> {

    private static final long serialVersionUID = 1L;

    private final UUID uid;
    private final TenantId tenantId;
    private final RuleId ruleId;
    private final T payload;

    public AbstractPluginToRuleMsg(UUID uid, TenantId tenantId, RuleId ruleId, T payload) {
        super();
        this.uid = uid;
        this.tenantId = tenantId;
        this.ruleId = ruleId;
        this.payload = payload;
    }

    @Override
    public UUID getUid() {
        return uid;
    }

    @Override
    public TenantId getTenantId() {
        return tenantId;
    }

    @Override
    public T getPayload() {
        return payload;
    }

    @Override
    public RuleId getRuleId() {
        return ruleId;
    }



}
