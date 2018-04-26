package com.comba.mqtt.dao.service;

import java.util.Date;
import java.util.List;

import com.comba.mqtt.controller.msg.AttributeAverageData;
import com.comba.mqtt.dao.model.DeviceDataEntity;

/**
 * 设备上报数据接口
 * @author maozhihui
 * TODO 将DAO层实体与DTO分开定义，不共用
 */
public interface DeviceDataService {

	void save(DeviceDataEntity entity);
	
	List<DeviceDataEntity> findHistoryData(int devId,Date startTime,Date ednTime);
	
	List<AttributeAverageData> getAttributeAverageData(String attrName,Date startTime,Date endTime);
}
