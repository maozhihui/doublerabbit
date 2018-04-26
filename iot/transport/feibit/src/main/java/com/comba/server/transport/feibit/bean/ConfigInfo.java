package com.comba.server.transport.feibit.bean;

import lombok.Data;

@Data
public class ConfigInfo {
	private String ip;
	private int tcpport;
	private long interval;
	private long onlineTimeout;
	
	public ConfigInfo(String ip, int tcpport, long interval, long onlineTimeout){
		this.ip = ip;
		this.tcpport = tcpport;
		this.interval = interval;
		this.onlineTimeout = onlineTimeout;	
	}
}
