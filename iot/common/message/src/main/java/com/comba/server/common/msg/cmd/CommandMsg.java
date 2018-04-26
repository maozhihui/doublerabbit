package com.comba.server.common.msg.cmd;

import java.io.Serializable;

import com.comba.server.common.msg.aware.DeviceAwareMsg;
import com.comba.server.common.msg.aware.TenantAwareMsg;

/**
 * 前端到后端的命令消息接口
 * @author maozhihui
 *
 */
public interface CommandMsg extends TenantAwareMsg,DeviceAwareMsg,Serializable {

	//TenantId geTenantId();
	//DeviceId getDeviceId();
	
	CommandMsgType getCmdType();
	
	String getSessionId();
}
