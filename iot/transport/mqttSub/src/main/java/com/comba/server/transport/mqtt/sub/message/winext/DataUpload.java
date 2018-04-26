package com.comba.server.transport.mqtt.sub.message.winext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.transport.mqtt.sub.message.BasicTelemetryUploadRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author maozhihui
 * @date 2017年11月10日 下午8:33:26
 */
@Slf4j
@Data
public class DataUpload extends BasicTelemetryUploadRequest {
	private String devEUI;
	private String time;
	private Integer fPort;
	private Integer gatewayCount;
	private Integer rssi;
	private String data;
	
	@Override
	public String getDeviceId(){
		return this.devEUI;
	}
	
	@Override
	public int getDeviceType(){
		return this.fPort;
	}
	
	@Override
	public String getAppName(){
		return "";
	}

	@Override
	public long getUpdateTime(){
		return parseTimeData(time);
	}

	@Override
	public String getData(){
		return data;
	}

	private long parseTimeData(String timeStr){
		long res = 0L;
		if (StringUtils.isBlank(timeStr) || (!timeStr.contains("T"))) {
			log.warn("timeStr [{}] is invalid.",timeStr);
			return res;
		}
		timeStr = timeStr.replace("Z", " UTC");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		try {
			Date date = format.parse(timeStr);
			res = date.getTime();
		} catch (ParseException e) {
			log.error("parse timeStr [{}] failed,cause [{}].",timeStr,e.getCause());
		}
		return res;
	}
}
