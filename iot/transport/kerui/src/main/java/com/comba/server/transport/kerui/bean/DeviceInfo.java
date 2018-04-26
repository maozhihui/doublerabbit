package com.comba.server.transport.kerui.bean;

import lombok.Data;

@Data
public class DeviceInfo {
	//所属的节点控制器地址
	private byte controllerAddr;
	//设备地址
	private byte deviceAddr;
	//设备SN，根据控制器地址和设备地址分组 组成。如所属第3控制器，设备地址65，则SN=3-3-1。
	//第一个3是控制器地址，第二个3是所属分组，第三个1是组内编号。一个组有32个地址。65排在第3组。(32+32+1=65)
	private String sn;
	//设备类型
	private byte type;
	//探测器状态， 
	private byte state;
	//设备是否注册状态
	private boolean registered;
}
