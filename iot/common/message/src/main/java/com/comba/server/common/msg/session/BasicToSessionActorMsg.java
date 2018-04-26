/**
 * creater:xianhongdong
 * createTime:2017-6-2
 */
package com.comba.server.common.msg.session;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.data.id.TenantId;

public class BasicToSessionActorMsg implements ToSessionActorMsg{

	private final TenantId tenantId;
    private final CustomerId customerId;
    private final DeviceId deviceId;
    private final AdaptorToSessionActorMsg msg;
    
    public BasicToSessionActorMsg(Device device, AdaptorToSessionActorMsg msg) {
        super();
        this.tenantId = TenantId.fromString2(device.getTenantId());
        this.customerId = null;//device.getCustomerId();
        this.deviceId = DeviceId.fromString2(device.getDevId());
        this.msg = msg;
    }

    public BasicToSessionActorMsg(ToSessionActorMsg deviceMsg) {
        this.tenantId = deviceMsg.getTenantId();
        this.customerId = deviceMsg.getCustomerId();
        this.deviceId = deviceMsg.getDeviceId();
        this.msg = deviceMsg.getSessionMsg();
    }

    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    @Override
    public CustomerId getCustomerId() {
        return customerId;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    @Override
    public SessionId getSessionId() {
        return msg.getSessionId();
    }

    @Override
    public AdaptorToSessionActorMsg getSessionMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "BasicToSessionActorMsg [tenantId=" + tenantId + ", customerId=" + customerId + ", deviceId=" + deviceId + ", msg=" + msg
                + "]";
    }
}
