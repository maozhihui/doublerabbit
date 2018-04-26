package com.comba.server.common.data.session;

public class RegisterSession {
	private String sessionToken;
	private String deviceId;
	private long registerTime = 0L;
	private long freshTime = 0L;
	private Status status;
	
	public RegisterSession(){
		this.sessionToken = "";
		this.deviceId = "";
		this.status = Status.OFFLINE;
	}
	
	public RegisterSession(String token,String deviceId,Status status){
		this();
		this.sessionToken = token;
		this.deviceId = deviceId;
		this.status = status;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public String getToken(){
		return sessionToken;
	}
	
	public void setToken(String token){
		this.sessionToken = token;
	}
	
	public long getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
		this.freshTime = registerTime;
	}
	public long getFreshTime() {
		return freshTime;
	}
	public void setFreshTime(long freshTime) {
		this.freshTime = freshTime;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public enum Status{
		OFFLINE,ONLINE,NOT_REGISTERED,DEREGISERED
	}
}
