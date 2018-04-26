package com.comba.server.actors.rule;

import java.util.List;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.common.data.id.*;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleFilter;
import com.ql.util.express.ExpressRunner;

public class RuleProcessingContext implements RuleContext {

    private final RuleId ruleId;
    private TenantId tenantId;
    private CustomerId customerId;
    private DeviceId deviceId;
    private DeviceTelemetryAttributes deviceTelemetryAttributes;
    List<RuleFilter> filters;
    private final ActorSystemContext systemContext;

    RuleProcessingContext(ActorSystemContext systemContext, RuleId ruleId) {
        this.ruleId = ruleId;
        this.systemContext = systemContext;
    }

    void update(ToDeviceActorMsg toDeviceActorMsg, DeviceTelemetryAttributes telemetryAttributes, List<RuleFilter> filters) {
        this.tenantId = toDeviceActorMsg.getTenantId();
        this.customerId = toDeviceActorMsg.getCustomerId();
        this.deviceId = toDeviceActorMsg.getDeviceId();
        this.deviceTelemetryAttributes = telemetryAttributes;
        this.filters = filters;
    }

    @Override
    public RuleId getRuleId() {
        return ruleId;
    }
    
    @Override
    public DeviceTelemetryAttributes getDeviceTelemetryAttributes() {
        return deviceTelemetryAttributes;
    }
    
    @Override
    public List<RuleFilter> getFilters() {
        return filters;
    }

    @Override
    public ExpressRunner getExpressRunner() {
        return systemContext.getExpressRunner();
    }

}
