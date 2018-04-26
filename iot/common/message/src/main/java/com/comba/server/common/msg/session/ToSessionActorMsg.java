/**
 * creater:xianhongdong
 * createTime:2017-6-2
 */
package com.comba.server.common.msg.session;

import com.comba.server.common.msg.aware.CustomerAwareMsg;
import com.comba.server.common.msg.aware.DeviceAwareMsg;
import com.comba.server.common.msg.aware.SessionAwareMsg;
import com.comba.server.common.msg.aware.TenantAwareMsg;

public interface ToSessionActorMsg extends DeviceAwareMsg, CustomerAwareMsg, TenantAwareMsg, SessionAwareMsg{
	AdaptorToSessionActorMsg getSessionMsg();
}
