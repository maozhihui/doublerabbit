package com.comba.mqtt.controller.msg;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceData {

	private int devId;
	private String devName;
	private int devType;
	private List<TelemetryData> dataStream;
}
