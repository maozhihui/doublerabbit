package com.comba.mqtt.message.comba;

import java.util.List;

import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.message.BasicTelemetryUploadRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author maozhihui
 * @date 2017年11月10日 下午8:35:21
 */
@Slf4j
@Data
public class DataUpload extends BasicTelemetryUploadRequest{
	private String applicationID;
	private String applicationName;
	private String nodeName;
	private String devEUI;
	private List<RxInfoItem> rxInfo;
	private TxInfo txInfo;
	private Integer fCnt;
	private Integer fPort;
	private String data;
	private AbstractDataParser dataParser;
	
//	public DataUpload(AbstractDataParser dataParser){
//		this.dataParser = dataParser;
//	}
	
	@Override
	public String getDeviceId(){
		return this.devEUI;
	} 
	
	protected void parseData() {
		List<KvEntry> kvEntries = dataParser.convertToKvEntry(fPort,data);
		dataMap.put(System.currentTimeMillis(), kvEntries);
		//log.info("datamap.size [{}]",dataMap.size());
	}
}
