package com.comba.server.common.msg.cmd;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.msg.aware.CommandAwareMsg;

import lombok.ToString;

/**
 * 
 * @author huangjinlong
 *
 */
@ToString
public class SetCmdRequestMsg implements CommandAwareMsg {

	private DeviceId deviceId;
	private String hardIdentity;
	private String gatewayId;
	private ConcurrentMap<String, String> attributeMap;
	private int timeOut;
	
	public SetCmdRequestMsg(DeviceId deviceId) {
		attributeMap = new ConcurrentHashMap<>();
		this.deviceId = deviceId;
	}

	public void addAttribute(String key, String value){
		if (!key.isEmpty())
			attributeMap.put(key, value);
	}

	public ConcurrentMap<String, String> getAttributes(){
		return attributeMap;
	}

	public void setHardIdentity(String hardIdentity) {
		this.hardIdentity = hardIdentity;
	}
	
	public String getHardIdentity() {
		return hardIdentity;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	
	public String getGatewayId() {
		return gatewayId;
	}
	
	@Override
	public DeviceId getDeviceId() {
		return deviceId;
	}

	@Override
	public CommandMsgType getMsgType() {
		return CommandMsgType.SET_REQUEST;
	}
}
