package com.comba.server.common.data.id;

import java.util.UUID;

/**
 * 设备会话ID，是指针对设备业务会话层面的ID
 * HttpSessionId是针对设备连接层面的ID
 * @author maozhihui
 *
 */
public class DeviceSessionId implements SessionId {

	private static final long serialVersionUID = 3423948014765881930L;

	// 一般此ID用deviceID进行替代
	private final UUID id;
	
	public DeviceSessionId() {
		id = UUID.randomUUID();
	}

	public DeviceSessionId(UUID id){
		this.id = id;
	}
	
	@Override
	public String toUidStr() {
		return id.toString();
	}

	@Override
	public String toString(){
		return toUidStr();
	}
}
