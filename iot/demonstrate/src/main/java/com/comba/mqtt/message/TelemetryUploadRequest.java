package com.comba.mqtt.message;

import java.util.List;
import java.util.Map;

import com.comba.mqtt.data.KvEntry;

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
	 * 获取设备类型
	 * @return
	 */
	int getDeviceType();
	
	/**
	 * 获取设备上报时间
	 * @return
	 */
	Map<Long, List<KvEntry>> getPayloadData();
}
