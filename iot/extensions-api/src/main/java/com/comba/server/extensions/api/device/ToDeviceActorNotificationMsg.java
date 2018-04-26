package com.comba.server.extensions.api.device;

import com.comba.server.common.msg.aware.DeviceAwareMsg;
import com.comba.server.common.msg.aware.TenantAwareMsg;

import java.io.Serializable;

/**
 * @author Andrew Shvayka
 */
public interface ToDeviceActorNotificationMsg extends TenantAwareMsg, DeviceAwareMsg, Serializable {

}
