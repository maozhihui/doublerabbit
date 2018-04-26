package com.comba.transport.pshare;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import org.apache.mina.core.session.IoSession;

import com.comba.transport.pshare.message.PSHAREGetLockStateRespMessage;

@Data
public class ConnectionSession {
	private IoSession session;
	private String mac;
	private String sessionId;
	//设备列表 <mac+addr,DevicesInfo>
	private ConcurrentHashMap<String,PSHAREGetLockStateRespMessage> devicesMap = new ConcurrentHashMap<String,PSHAREGetLockStateRespMessage>();
	//已注册的设备列表 <mac+addr>
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
