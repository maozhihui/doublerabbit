package com.comba.server.common.data.security;

public class DeviceIdCredentials implements DeviceCredentialsFilter{

	private final String deviceId;
	
	public DeviceIdCredentials(String deviceId)
	{
		this.deviceId = deviceId;
	}
	@Override
	public String getCredentialsId() {
		// TODO Auto-generated method stub
		return deviceId;
	}

	@Override
	public DeviceCredentialsType getCredentialsType() {
		// TODO Auto-generated method stub
		return DeviceCredentialsType.ACCESS_DEVICEID;
	}
	@Override
    public String toString() {
        return "DeviceTokenCredentials [deviceId=" + deviceId + "]";
    }
}
