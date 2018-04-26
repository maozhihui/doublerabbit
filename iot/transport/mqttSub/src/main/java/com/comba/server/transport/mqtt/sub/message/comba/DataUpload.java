package com.comba.server.transport.mqtt.sub.message.comba;

import java.util.List;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.transport.mqtt.sub.message.BasicTelemetryUploadRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author maozhihui
 * @date 2017年11月10日 下午8:35:21
 */
@Slf4j
@Data
public class DataUpload extends BasicTelemetryUploadRequest {
	private String applicationID;
	private String applicationName;
	private String nodeName;
	private String devEUI;
	private List<RxInfoItem> rxInfo;
	private TxInfo txInfo;
	private Integer fCnt;
	private Integer fPort;
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
		return applicationName;
	}

	@Override
	public long getUpdateTime(){
		return System.currentTimeMillis();
	}

	@Override
	public String getData(){
		return data;
	}

}
