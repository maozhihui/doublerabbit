package com.comba.server.extensions.api.rules;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.ql.util.express.ExpressRunner;

import java.util.List;

public interface RuleContext {

    RuleId getRuleId();
    
    DeviceTelemetryAttributes getDeviceTelemetryAttributes();

	List<RuleFilter> getFilters();

	ExpressRunner getExpressRunner();
}
