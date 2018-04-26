package com.comba.server.common.msg.core;

import com.comba.server.common.msg.session.FromDeviceRequestMsg;

public interface PingRequest extends FromDeviceRequestMsg {

	// 获取PING更新时间
	long getFreshTime();
}
