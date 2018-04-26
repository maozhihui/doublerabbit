package com.comba.mqtt.controller.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备业务属性状态数据对象
 * @author maozhihui
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusInfo {
	private int devId;
	private String devName;
	private long time;
	private String alarmLevel;
	private String alarmCause;
	
}
