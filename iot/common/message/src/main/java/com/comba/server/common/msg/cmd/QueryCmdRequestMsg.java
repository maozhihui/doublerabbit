package com.comba.server.common.msg.cmd;

import java.util.ArrayList;
import java.util.List;

import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.msg.aware.CommandAwareMsg;

import lombok.ToString;

/**
 * 
 * @author maozhihui
 *
 */
@ToString
public class QueryCmdRequestMsg implements CommandAwareMsg {

	private DeviceId deviceId;
	private String hardIdentity;
	private String gatewayId;
	private List<String> attributeList;
	private int timeOut;
	
	public QueryCmdRequestMsg(DeviceId deviceId) {
		attributeList = new ArrayList<String>();
		this.deviceId = deviceId;
	}

	public void addAttribute(String keyName){
		if (!keyName.isEmpty())
			attributeList.add(keyName);
	}

	public List<String> getAttributes(){
		return attributeList;
	}

	public void setAttributes(List<String>  attributeList){
		 this.attributeList = attributeList;
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
		return CommandMsgType.QUERY_REQUEST;
	}
}
