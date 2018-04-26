package com.comba.server.common.msg.core;

import java.util.ArrayList;
import java.util.List;

/**
 * PING包响应消息体内容
 * @author maozhihui
 *
 */
public class PingResponseBodyMsg extends ResponseBodyMsg {

	private static final long serialVersionUID = -3632435143942793704L;

	// 要求设备属性更新的设备，由设备从平台取
	private final List<String> updateAttributeData;
	// 上报设备属性的设备，由设备上报给平台
	private final List<String> reportAtrributeData;
	// 上报设备遥测参数的设备
	private final List<String> reportTelemetryData;
	//需要升级的设备
    private final List<String> upgradeGWData;
	
	public PingResponseBodyMsg(int errno, String error) {
		super(errno, error);
		this.updateAttributeData = new ArrayList<>();
		this.reportAtrributeData = new ArrayList<>();
		this.reportTelemetryData = new ArrayList<>();
		this.upgradeGWData = new ArrayList<>();
	}

	public List<String> getUpdateAttributeData() {
		return updateAttributeData;
	}

	public List<String> getReportAtrributeData() {
		return reportAtrributeData;
	}

	public List<String> getReportTelemetryData() {
		return reportTelemetryData;
	}

    public List<String> getUpgradeGWData() {
        return upgradeGWData;
    }

    public void addUpgradeGWData(String deviceId) {
        if (!deviceId.isEmpty())
            upgradeGWData.add(deviceId);
    }

    public void addUpdateAttributeData(String deviceId){
		if (!deviceId.isEmpty())
			updateAttributeData.add(deviceId);
	}
	
	public void addReportAtrributeData(String deviceId){
		if (!deviceId.isEmpty()) 
			reportAtrributeData.add(deviceId);
	}
	
	public void addReportTelemetryData(String deviceId){
		if (!deviceId.isEmpty())
			reportTelemetryData.add(deviceId);
	}
}
