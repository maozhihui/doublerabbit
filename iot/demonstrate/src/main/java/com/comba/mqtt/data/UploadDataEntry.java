package com.comba.mqtt.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadDataEntry {

	private String deviceId;
	private int deviceType;
	private long uploadTime;
	private String data;
}
