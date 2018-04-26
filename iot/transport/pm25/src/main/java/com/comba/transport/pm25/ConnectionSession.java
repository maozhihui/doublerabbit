package com.comba.transport.pm25;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import org.apache.mina.core.session.IoSession;

import com.comba.transport.pm25.message.PM25ReadDataRespMessage;

@Data
public class ConnectionSession {
	private IoSession session;
	private String mac;
	private String sessionId;
	//设备列表 <mac+addr,DevicesInfo>
	private ConcurrentHashMap<String,PM25ReadDataRespMessage> devicesMap = new ConcurrentHashMap<String,PM25ReadDataRespMessage>();
	//已注册的设备列表 <mac+addr,DevicesInfo>
	//private ConcurrentHashMap<String,PM25ReadDataRespMessage> registeredDevicesMap = new ConcurrentHashMap<String,PM25ReadDataRespMessage>();
	private List<String> registeredDevices = new ArrayList<String>();
	//对应mac适配器下的设备地址
	private List<Integer> addressList;
	
	public ConnectionSession(IoSession session){
		this.session = session;
		this.sessionId = UUID.randomUUID().toString();
		this.addressList = new ArrayList<>();
	}

	public ConnectionSession(IoSession session,String mac){
		this.mac = mac;
	}
}
