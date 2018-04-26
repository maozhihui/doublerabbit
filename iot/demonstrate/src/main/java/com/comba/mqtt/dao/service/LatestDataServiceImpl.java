package com.comba.mqtt.dao.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.mqtt.controller.msg.StatusInfo;
import com.comba.mqtt.controller.msg.TelemetryData;
import com.comba.mqtt.controller.msg.TopStatData;
import com.comba.mqtt.controller.msg.DeviceData;
import com.comba.mqtt.controller.msg.GraphData;
import com.comba.mqtt.dao.BaseDao;
import com.comba.mqtt.dao.LatestDataDao;
import com.comba.mqtt.dao.model.LatestDataEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LatestDataServiceImpl implements LatestDataService {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("######0.00");
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private LatestDataDao latestDataDao;
	
	@Override
	public Optional<LatestDataEntity> findByDevIdAndAttrName(int devId,String attrName) {
		LatestDataEntity entity = latestDataDao.findByDevIdAndAttrName(devId,attrName);
		if (entity == null) {
			log.warn("can not find latest data by devId [{}]",devId);
		}
		return Optional.ofNullable(entity);
	}

	@Override
	public void save(LatestDataEntity latestData) {
		latestDataDao.saveAndFlush(latestData);
	}

	@Override
	public List<LatestDataEntity> findByDevId(int devId) {
		return latestDataDao.findByDevId(devId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatusInfo> findAll() {
		List<StatusInfo> res = new ArrayList<>();
		StringBuilder sb = new StringBuilder("SELECT d.id,d.dev_name,ld.alarm_level,ld.alarm_cause,ld.update_time FROM device AS d,latest_data AS ld WHERE d.id = ld.dev_id ORDER BY update_time DESC");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Object[]> queryRes = baseDao.queryBySql(sb.toString(), paramMap);
		for (Object[] row : queryRes) {
			StatusInfo statusInfo = new StatusInfo((int)row[0], 
									(String)row[1], 
									((Date)row[4]).getTime(), 
									(String)row[2], 
									(String)row[3]);
			res.add(statusInfo);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GraphData> getTopStat(String attrName, int limit) {
		List<GraphData> res = new ArrayList<>();
		StringBuilder sb = new StringBuilder("SELECT ld.attr_value,d.location FROM device d,latest_data ld WHERE d.id=ld.dev_id AND attr_name=:attrName ORDER BY ld.attr_value DESC LIMIT :limit");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attrName", attrName);
		paramMap.put("limit", limit);
		List<Object[]> queryRes = baseDao.queryBySql(sb.toString(), paramMap);
		for (Object[] row : queryRes) {
			res.add(new GraphData((String)row[1], Double.parseDouble((String)row[0])));
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TopStatData getTopStat(String attrName, Date startTime, Date endTime, int limit) {
		TopStatData statData = new TopStatData();
		StringBuilder sb = new StringBuilder("SELECT t1.topLocation,t1.latestValue,t2.avgtemp FROM (SELECT ld.attr_value AS latestValue,d.location AS topLocation FROM device d,latest_data ld WHERE d.id=ld.dev_id AND attr_name=:attrName1 AND update_time >=:startTime AND update_time <=:endTime ORDER BY ld.attr_value DESC LIMIT :limit) AS t1,( SELECT d1.location AS allLocation, AVG(attr_value) AS avgtemp FROM device d1,device_data dd WHERE d1.id=dd.dev_id AND attr_name=:attrName2 AND update_time >=:startTime AND update_time <=:endTime GROUP BY d1.location) AS t2 WHERE t1.topLocation = t2.allLocation");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attrName1", attrName);
		paramMap.put("attrName2", attrName);
		paramMap.put("limit", limit);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<Object[]> queryRes = baseDao.queryBySql(sb.toString(), paramMap);
		if (queryRes.size() > 0) {
			List<GraphData> currentData = new ArrayList<>();
			List<GraphData> avgData = new ArrayList<>();
			for (Object[] row : queryRes) {
				currentData.add(new GraphData((String)row[0], Double.parseDouble((String)row[1])));
				avgData.add(new GraphData((String)row[0], Double.parseDouble(DECIMAL_FORMAT.format((Double)row[2]))));
			}
			statData.setAttrName(attrName);
			statData.setCurrentValue(currentData);
			statData.setAvgValue(avgData);
		}
		return statData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeviceData queryByDevId(int devId) {
		DeviceData deviceData = null;
		StringBuilder sb = new StringBuilder("SELECT d.id,d.dev_name,d.dev_type,ld.attr_name,ld.attr_value,ld.update_time FROM device d,latest_data ld WHERE d.id=ld.dev_id AND d.id=:devId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("devId", devId);
		List<Object[]> queryRes = baseDao.queryBySql(sb.toString(), paramMap);
		if (queryRes.size() > 0) {
			deviceData = new DeviceData();
			deviceData.setDevId(devId);
			List<TelemetryData> data = new ArrayList<>();
			for (Object[] row : queryRes) {
				deviceData.setDevName((String)row[1]);
				deviceData.setDevType((Byte)row[2]);
				TelemetryData telemetryData = new TelemetryData();
				telemetryData.setTime(((Date)row[5]).getTime());
				Map<String, String> dp = new HashMap<>();
				dp.put((String)row[3], (String)row[4]);
				telemetryData.setDataPoint(dp);
				data.add(telemetryData);
			}
			deviceData.setDataStream(data);;
		}
		
		return deviceData;
	}

	@Override
	public List<StatusInfo> findAllByEn() {
		List<StatusInfo> res = new ArrayList<>();
		StringBuilder sb = new StringBuilder("SELECT d.id,d.dev_name_en,ld.alarm_level_en,ld.alarm_cause_en,ld.update_time FROM device AS d,latest_data AS ld WHERE d.id = ld.dev_id ORDER BY update_time DESC");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Object[]> queryRes = baseDao.queryBySql(sb.toString(), paramMap);
		for (Object[] row : queryRes) {
			StatusInfo statusInfo = new StatusInfo((int)row[0], 
									(String)row[1], 
									((Date)row[4]).getTime(), 
									(String)row[2], 
									(String)row[3]);
			res.add(statusInfo);
		}
		return res;
	}

	@Override
	public TopStatData getTopStatByEn(String attrName, Date startTime, Date endTime, int limit) {
		TopStatData statData = new TopStatData();
		StringBuilder sb = new StringBuilder("SELECT t1.topLocation,t1.latestValue,t2.avgtemp FROM (SELECT ld.attr_value AS latestValue,d.location_en AS topLocation FROM device d,latest_data ld WHERE d.id=ld.dev_id AND attr_name=:attrName1 AND update_time >=:startTime AND update_time <=:endTime ORDER BY ld.attr_value DESC LIMIT :limit) AS t1,( SELECT d1.location_en AS allLocation, AVG(attr_value) AS avgtemp FROM device d1,device_data dd WHERE d1.id=dd.dev_id AND attr_name=:attrName2 AND update_time >=:startTime AND update_time <=:endTime GROUP BY d1.location_en) AS t2 WHERE t1.topLocation = t2.allLocation");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attrName1", attrName);
		paramMap.put("attrName2", attrName);
		paramMap.put("limit", limit);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<Object[]> queryRes = baseDao.queryBySql(sb.toString(), paramMap);
		if (queryRes.size() > 0) {
			List<GraphData> currentData = new ArrayList<>();
			List<GraphData> avgData = new ArrayList<>();
			for (Object[] row : queryRes) {
				currentData.add(new GraphData((String)row[0], Double.parseDouble((String)row[1])));
				avgData.add(new GraphData((String)row[0], Double.parseDouble(DECIMAL_FORMAT.format((Double)row[2]))));
			}
			statData.setAttrName(attrName);
			statData.setCurrentValue(currentData);
			statData.setAvgValue(avgData);
		}
		return statData;
	}

}
