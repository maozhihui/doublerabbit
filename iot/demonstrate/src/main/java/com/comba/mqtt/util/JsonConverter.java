package com.comba.mqtt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comba.mqtt.controller.msg.AttributeAverageData;
import com.comba.mqtt.controller.msg.DeviceData;
import com.comba.mqtt.controller.msg.GraphData;
import com.comba.mqtt.controller.msg.StatusInfo;
import com.comba.mqtt.controller.msg.TelemetryData;
import com.comba.mqtt.controller.msg.TopStatData;
import com.comba.mqtt.dao.model.DeviceDataEntity;
import com.comba.mqtt.dao.model.LatestDataEntity;
import com.google.gson.Gson;

public class JsonConverter {
	
	private static Gson GSON = new Gson();
	
	public static String toJson(int devId,List<LatestDataEntity> entities){
		DeviceData deviceData = new DeviceData();
		deviceData.setDevId(devId);
		if (entities.size() > 0) {
			List<TelemetryData> lists = new ArrayList<>();
			Map<String, String> attrMap = null;
			for (LatestDataEntity latestDataEntity : entities) {
				attrMap = new HashMap<String, String>();
				TelemetryData data = new TelemetryData();
				data.setTime(latestDataEntity.getUpdateTime().getTime());
				attrMap.put(latestDataEntity.getAttrName(), latestDataEntity.getAttrValue());
				data.setDataPoint(attrMap);
				lists.add(data);
			}
			deviceData.setDataStream(lists);
		}
		return GSON.toJson(deviceData);
	}
	
	public static String deviceDatatoJson(int devId,List<DeviceDataEntity> entities){
		DeviceData deviceData = new DeviceData();
		deviceData.setDevId(devId);
		if (entities.size() > 0) {
			List<TelemetryData> lists = new ArrayList<>();
			Map<String, String> attrMap = null;
			for (DeviceDataEntity deviceDataEntity : entities) {
				attrMap = new HashMap<String, String>();
				TelemetryData data = new TelemetryData();
				data.setTime(deviceDataEntity.getUpdateTime().getTime());
				attrMap.put(deviceDataEntity.getAttrName(), deviceDataEntity.getAttrValue());
				data.setDataPoint(attrMap);
				lists.add(data);
			}
			deviceData.setDataStream(lists);
		}
		return GSON.toJson(deviceData);
	}
	
	public static String toJson(List<StatusInfo> statusInfos){
		return GSON.toJson(statusInfos);
	}
	
	public static String toStatJson(List<AttributeAverageData> avgStatData){
		return GSON.toJson(avgStatData);
	}
	
	public static String toTopStatJson(List<GraphData> topStatData){
		return GSON.toJson(topStatData);
	}
	
	public static String toTopStatJson(TopStatData topStatData){
		return GSON.toJson(topStatData);
	}
	
	public static String toJson(DeviceData deviceData){
		return GSON.toJson(deviceData);
	}
}
