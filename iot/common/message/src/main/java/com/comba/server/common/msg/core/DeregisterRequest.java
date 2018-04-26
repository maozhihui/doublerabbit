package com.comba.server.common.msg.core;

import java.util.List;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.msg.session.FromDeviceRequestMsg;

/**
 * @author xianhongdong
 * 设备去注册请求接口
 */
public interface DeregisterRequest extends FromDeviceRequestMsg{
	String getDeviceId();
	String getRegisterToken();
	/**
	 * 【获取请求消息体中的key-value数据】
	 * 
	 * @author xianhongdong 2017年6月5日
	 * @return
	 */
	List<KvEntry> getData();
}
