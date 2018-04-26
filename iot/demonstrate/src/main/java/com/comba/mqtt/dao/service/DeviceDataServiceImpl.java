package com.comba.mqtt.dao.service;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.mqtt.controller.msg.AttributeAverageData;
import com.comba.mqtt.dao.BaseDao;
import com.comba.mqtt.dao.DeviceDataDao;
import com.comba.mqtt.dao.model.DeviceDataEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceDataServiceImpl implements DeviceDataService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("######0.00"); 
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private DeviceDataDao deviceDataDao;
	
	@Override
	public void save(DeviceDataEntity entity) {
		deviceDataDao.saveAndFlush(entity);
	}

	@Override
	public List<DeviceDataEntity> findHistoryData(int devId, Date startTime, Date endTime) {
		return deviceDataDao.findByDevIdAndUpdateTimeBetween(devId, startTime, endTime);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttributeAverageData> getAttributeAverageData(String attrName, Date startTime, Date endTime) {
		List<AttributeAverageData> res = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DATE_FORMAT(update_time, '%Y-%m-%d') AS `time`,AVG(attr_value) AS avgtemp FROM device_data WHERE attr_name=:attrName AND update_time >=:startTime AND update_time <=:endTime GROUP BY DATE_FORMAT(update_time, '%Y-%m-%d') ORDER  BY `time` ASC");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("attrName", attrName);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<Object[]> queryRes = baseDao.queryBySql(sb.toString(), paramMap);
		if (queryRes.size() > 0) {
			Map<Long, String> avgData = new TreeMap<>();
			try {
				for (Object[] row : queryRes) {
					Date time = DATE_FORMAT.parse((String)row[0]);
					avgData.put(time.getTime(), (DECIMAL_FORMAT.format((Double)row[1])).toString());
				}
			} catch (Exception e) {
				log.error("time parse failed [{}].",e.getCause());
			}
			AttributeAverageData data = new AttributeAverageData(attrName, avgData);
			res.add(data);
		}
		return res;
	}

}
