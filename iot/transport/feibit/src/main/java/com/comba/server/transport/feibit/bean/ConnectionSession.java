package com.comba.server.transport.feibit.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import org.apache.mina.core.session.IoSession;

import com.comba.server.transport.feibit.message.FEIBITGetDevicesRespMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataRespMessage;
@Data
public class ConnectionSession {
	private IoSession session;
	private String gatewaySn;
	private String sessionId;
	//设备列表 <shortAddr+endpoint,DevicesInfo>
	private ConcurrentHashMap<String,FEIBITGetDevicesRespMessage> devicesMap = new ConcurrentHashMap<String,FEIBITGetDevicesRespMessage>();
	//红外学习保存的数据 key=irId
	private ConcurrentHashMap<String,FEIBITInfraredDataRespMessage> infraredMap = new ConcurrentHashMap<String,FEIBITInfraredDataRespMessage>();
	//已注册的设备列表 <shortAddr+endpoint,DevicesInfo>
	private List<String> registeredDevices = new ArrayList<String>();
	
	public ConnectionSession(IoSession session){
		this.session = session;
		this.sessionId = UUID.randomUUID().toString();
	}

	public ConnectionSession(IoSession session,String mac){
		this.gatewaySn = mac;
	}
}
