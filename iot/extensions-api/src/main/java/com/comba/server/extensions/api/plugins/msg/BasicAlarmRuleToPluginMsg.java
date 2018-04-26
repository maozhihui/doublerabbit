package com.comba.server.extensions.api.plugins.msg;

import com.comba.server.common.data.id.CustomerId;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.BasicAlarmMsg;

/**
 * BasicAlarmRuleToPluginMsg
 * 由规则到插件的告警相关信息
 * @author maozhihui
 * @create 2017-10-12 20:01
 **/
public class BasicAlarmRuleToPluginMsg extends AbstractRuleToPluginMsg<BasicAlarmMsg> {

    public BasicAlarmRuleToPluginMsg(TenantId tenantId, CustomerId customerId, DeviceId deviceId, BasicAlarmMsg payload) {
        super(tenantId, customerId, deviceId, payload);
    }
}
