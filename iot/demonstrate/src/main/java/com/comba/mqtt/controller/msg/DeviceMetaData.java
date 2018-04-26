package com.comba.mqtt.controller.msg;

import lombok.Data;

/**
 * 设备元信息
 * @author maozhihui
 * @date 2017年10月31日 下午3:34:00
 */
@Data
public class DeviceMetaData {

	private int id;
	private String devEui;
	private String devName;
	private int devType;
	private String location;
}
