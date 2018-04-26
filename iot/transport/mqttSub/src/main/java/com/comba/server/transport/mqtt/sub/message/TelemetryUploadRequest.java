package com.comba.server.transport.mqtt.sub.message;

import com.comba.server.common.data.kv.KvEntry;

import java.util.List;
import java.util.Map;

/**
 * 遥测数据上报请求
 * @author maozhihui
 *
 */
public interface TelemetryUploadRequest {

	/**
	 * 获取设备ID
	 * @return
	 */
	String getDeviceId();

	/**
	 * 获取应用标识名
	 * @return
	 */
	String getAppName();

	/**
	 * 获取设备类型
	 * @return
	 */
	int getDeviceType();

	/**
	 * 获取上报时间
	 * @return
	 */
	long getUpdateTime();

	String getData();

}
