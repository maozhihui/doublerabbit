package com.comba.server.transport.kerui.bean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

@Data
public class ControllerInfo {
	//控制器地址
	private byte address;
	//控制器下的设备
	private ConcurrentHashMap<String,DeviceInfo> devicesMap = new ConcurrentHashMap<String,DeviceInfo>();
}
