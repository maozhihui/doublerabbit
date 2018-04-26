package com.comba.server.common.msg.cmd;

import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.aware.CommandAwareMsg;

/**
 * 
 * @author maozhihui
 *
 */
public class BasicCommandMsg<T extends CommandAwareMsg> implements CommandMsg {

	private static final long serialVersionUID = -6790327545594924457L;
	
	private final TenantId tenantId;
	// 消息路由的设备ID，如果该设备由网关代理，此ID为网关的设备ID，否则为本设备的ID
	private final DeviceId deviceId;
	// 命令消息的类型，查询请求/响应，设置请求/响应
	private final CommandMsgType msgType;
	// 浏览器的会话sessionId
	private final String sessionId;
	private boolean isProxy = false; 
	protected long createTime;
	// 具体的参数查询与设置的内容
	private T data;
	
	public BasicCommandMsg(TenantId tenantId,DeviceId deviceId,String sessionId,T data) {
		this.tenantId = tenantId;
		this.deviceId = deviceId;
		this.sessionId = sessionId;
		this.data = data;
		this.createTime = System.currentTimeMillis();
		if (data.getDeviceId().getId().compareTo(deviceId.getId()) != 0)
			isProxy = true;
		this.msgType = data.getMsgType();
	}
	
	@Override
	public TenantId getTenantId() {
		return tenantId;
	}

	@Override
	public DeviceId getDeviceId() {
		return deviceId;
	}

	@Override
	public CommandMsgType getCmdType() {
		return msgType;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public T getData(){
		return data;
	}

	public boolean isProxy() {
		return isProxy;
	}
	
}
