package com.comba.mqtt.message.winext;

import java.util.List;

import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.message.BasicTelemetryUploadRequest;
import com.comba.mqtt.message.comba.AbstractDataParser;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年11月10日 下午8:33:26
 */
@Data
public class DataUpload extends BasicTelemetryUploadRequest{
	private String devEUI;
	private String time;
	private Integer fPort;
	private Integer gatewayCount;
	private Integer rssi;
	private String data;
	private AbstractDataParser dataParser;
	
//	public DataUpload(AbstractDataParser dataParser){
//		this.dataParser = dataParser;
//	}
	
	@Override
	public String getDeviceId(){
		return this.devEUI;
	}
	
	@Override
	public int getDeviceType(){
		return this.fPort;
	}
	
	protected void parseData(){
		long uploadTime = WinextDataParser.parseTimeData(time);
		List<KvEntry> kvEntries = dataParser.convertToKvEntry(fPort, data);
		dataMap.put(uploadTime, kvEntries);
	}
}
