package com.comba.mqtt.dao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.comba.mqtt.controller.msg.StatusInfo;
import com.comba.mqtt.controller.msg.TopStatData;
import com.comba.mqtt.controller.msg.DeviceData;
import com.comba.mqtt.controller.msg.GraphData;
import com.comba.mqtt.dao.model.LatestDataEntity;

/**
 * 
 * @author maozhihui
 *
 */
public interface LatestDataService {

	Optional<LatestDataEntity> findByDevIdAndAttrName(int devId,String attrName);
	
	void save(LatestDataEntity latestData);
	
	List<LatestDataEntity> findByDevId(int devId);
	
	DeviceData queryByDevId(int devId);
	
	/**
	 * 获取设备所有的业务属性状态信息
	 * @return
	 */
	List<StatusInfo> findAll();
	
	List<StatusInfo> findAllByEn();
	
	/**
	 * 按属性获取二维图表TOP N数据
	 * @param attrName
	 * @param limit
	 * @return
	 */
	List<GraphData> getTopStat(String attrName,int limit);
	
	TopStatData getTopStat(String attrName,Date startTime,Date endTime,int limit);
	
	TopStatData getTopStatByEn(String attrName,Date startTime,Date endTime,int limit);
}
