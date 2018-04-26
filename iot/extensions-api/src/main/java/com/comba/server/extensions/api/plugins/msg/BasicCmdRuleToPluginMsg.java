package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.BasicCmdMsg;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/1 20:35
 **/
public class BasicCmdRuleToPluginMsg extends AbstractRuleToPluginMsg<CtrlCmdMsg> {

    public BasicCmdRuleToPluginMsg(TenantId tenantId, CustomerId customerId, DeviceId deviceId, CtrlCmdMsg payload) {
        super(tenantId, customerId, deviceId, payload);
    }

}
