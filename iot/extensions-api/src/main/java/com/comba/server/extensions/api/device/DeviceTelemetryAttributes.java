package com.comba.server.extensions.api.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comba.server.common.data.device.TelemetryAttributes;
/**
 * @author huangjinlong
 */
public class DeviceTelemetryAttributes {
	private List<TelemetryAttributes> deviceTelemetryAttributes;
	 
	public DeviceTelemetryAttributes(List<TelemetryAttributes> telemetryAttributes){
		this.deviceTelemetryAttributes = telemetryAttributes;
	}
	 
	private static Map<String, String> mapAttributes(List<TelemetryAttributes> attributes) {
		Map<String, String> result = new HashMap<>();
		for (TelemetryAttributes attribute : attributes) {
			result.put( attribute.getId().toString(),attribute.getAttributeName());
		}
		return result;
	}
	
	public List<TelemetryAttributes> getDeviceTelemetryAttributes() {
		return deviceTelemetryAttributes;
	}
	
	public void setDeviceTelemetryAttributes(List<TelemetryAttributes> telemetryAttributesList) {
		this.deviceTelemetryAttributes = telemetryAttributesList;
	}
	
	public  Map<String, String> mapDeviceTelemetryAttributes() {
		return mapAttributes(deviceTelemetryAttributes);
	}
}
