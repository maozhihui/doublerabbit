package com.comba.mqtt.controller.msg;

import java.util.Map;

import lombok.Data;

@Data
public class TelemetryData {

	private long time;
	private Map<String, String> dataPoint;
}
