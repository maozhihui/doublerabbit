package com.comba.mqtt.message;

import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.service.MqttServiceContext;

/**
 * 上报数据处理接口
 * @author maozhihui
 * @date 2017年11月13日 下午7:32:11
 */
public interface DataProcessor {
	void onProcess(int deviceId,KvEntry kvEntry,MqttServiceContext context);
}
