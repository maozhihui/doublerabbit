package com.comba.mqtt.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.comba.mqtt.data.DataType;
import com.comba.mqtt.data.KvEntry;

import lombok.Getter;
import lombok.Setter;

public abstract class BasicTelemetryUploadRequest implements TelemetryUploadRequest {

	private String deviceId;
	private String devName;
	private int deviceType;
	protected Map<Long, List<KvEntry>> dataMap;
	@Getter @Setter private String alarmLevel = ALARM_NORMAL;
	@Getter private String alarmCause = "";
	
	public static String ALARM_NORMAL = "正常";
	public static String ALARM_WARN = "告警";
	public static String NORMAL_CAUSE = "正常";
	
	public static String ALARM_NORMAL_EN = "normal";
	public static String ALARM_WARN_EN = "warn";
	public static String NORMAL_CAUSE_EN = "normal";
	
	public BasicTelemetryUploadRequest(){
		dataMap = new HashMap<>();
	}
	
	public BasicTelemetryUploadRequest(String deviceId,int deviceType) {
		// TODO Auto-generated constructor stub
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		dataMap = new HashMap<>();
	}
	
	public String getDevName(){
		return deviceId.substring(6, deviceId.length());
	}
	
	public void setAlarmCause(String alarmCause) {
		if (StringUtils.isBlank(this.alarmCause)) {
			this.alarmCause = alarmCause;
		}else {
			this.alarmCause = this.alarmCause+","+alarmCause;
		}
	}
	
	@Override
	public String getDeviceId() {
		// TODO Auto-generated method stub
		return deviceId;
	}

	@Override
	public int getDeviceType() {
		// TODO Auto-generated method stub
		return deviceType;
	}

	@Override
	public Map<Long, List<KvEntry>> getPayloadData() {
		parseData();
		return dataMap;
	}

	/**
	 * 定义具体解析数据的方法，让子类进行实现
	 */
	abstract protected void parseData();
	
	public void addData(long ts,List<KvEntry> kvEntries){
		dataMap.put(ts, kvEntries);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{devId:").append(deviceId)
			.append(",deviceType:").append(deviceType)
			.append(",");
		for (Map.Entry<Long, List<KvEntry>> entry : dataMap.entrySet()) {
			sb.append("[time:").append(entry.getKey());
			for (KvEntry entry2 : entry.getValue()) {
				DataType type = entry2.getDataType();
				switch (type) {
				case STRING:
					sb.append(",").append(entry2.getKey()).append(":")
						.append(entry2.getStrValue());
					break;
				case DOUBLE:
					sb.append(",").append(entry2.getKey()).append(":")
						.append(entry2.getDoubleValue());
					break;
				default:
					break;
				}
			}
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
}
