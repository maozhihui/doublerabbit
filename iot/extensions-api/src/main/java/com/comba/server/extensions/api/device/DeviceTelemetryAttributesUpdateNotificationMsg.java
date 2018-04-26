package com.comba.server.extensions.api.device;

import lombok.Data;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;

/**
 * @author huangjinlong
 */
@Data
public class DeviceTelemetryAttributesUpdateNotificationMsg implements ToDeviceActorNotificationMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3638328956527820135L;
	private final TenantId tenantId;
    private final DeviceId deviceId;
    private final DeviceTelemetryAttributes deviceTelemetryAttributes;
}
