package com.comba.transport.pshare;

import lombok.Data;

@Data
public class ConfigInfo {
	private String ip;
	private int port;
	private long interval;
	private long onlineTimeout;
	
	public ConfigInfo(String ip, int port, long interval, long onlineTimeout){
		this.ip = ip;
		this.port = port;
		this.interval = interval;
		this.onlineTimeout = onlineTimeout;	
	}
}
