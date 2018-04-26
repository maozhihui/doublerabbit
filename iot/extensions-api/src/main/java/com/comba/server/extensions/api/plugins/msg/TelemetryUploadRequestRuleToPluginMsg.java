package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.TelemetryUploadRequest;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;

public class TelemetryUploadRequestRuleToPluginMsg extends AbstractRuleToPluginMsg<TelemetryUploadRequest> {

    private static final long serialVersionUID = 1L;
    private DeviceTelemetryAttributes telemetryAttributes;
    public TelemetryUploadRequestRuleToPluginMsg(TenantId tenantId, CustomerId customerId, DeviceId deviceId, DeviceTelemetryAttributes telemetryAttributes, TelemetryUploadRequest payload) {
        super(tenantId, customerId, deviceId, payload);
        this.telemetryAttributes = telemetryAttributes;
    }
    
    public DeviceTelemetryAttributes getTelemetryAttributes() {
    	return telemetryAttributes;
    }
}
