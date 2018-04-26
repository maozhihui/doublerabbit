package com.comba.server.common.msg.cmd;

import java.util.ArrayList;
import java.util.List;
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
public class QueryCmdResponseMsg implements CommandAwareMsg {

	private DeviceId deviceId;
	private String hardIdentity;
//	private ConcurrentMap<String, String> attributeMap;
	private List<String> attributeList;
	private int statusCode;		// 0-成功, 1-超时
	private String cause;
	
//	public QueryCmdResponseMsg(DeviceId deviceId) {
//		attributeMap = new ConcurrentHashMap<>();
//		this.deviceId = deviceId;
//	}
//
//	public void addAttribute(String key, String value){
//		if (!key.isEmpty())
//			attributeMap.put(key, value);
//	}
//
//	public ConcurrentMap<String, String> getAttributes(){
//		return attributeMap;
//	}
	public QueryCmdResponseMsg(DeviceId deviceId) {
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
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public String getCause() {
		return cause;
	}
	
	public void setHardIdentity(String hardIdentity) {
		this.hardIdentity = hardIdentity;
	}
	
	public String getHardIdentity() {
		return hardIdentity;
	}
	@Override
	public DeviceId getDeviceId() {
		return deviceId;
	}

	@Override
	public CommandMsgType getMsgType() {
		return CommandMsgType.QUERY_RESPONSE;
	}
}
