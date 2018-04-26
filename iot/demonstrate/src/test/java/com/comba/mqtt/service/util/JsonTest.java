package com.comba.mqtt.service.util;

import com.comba.mqtt.message.winext.DataUpload;
import com.google.gson.Gson;

import lombok.Data;

public class JsonTest {

	private static final Gson GSON = new Gson();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jsonStr = "{\"devEUI\":\"ffffff1000000094\",\"time\":\"2017-11-10T03:02:26.838778Z\",\"fPort\":4,\"gatewayCount\":1,\"rssi\":-70,\"data\":\"Cm4YxA==\"}";
		DataUpload data = GSON.fromJson(jsonStr, DataUpload.class);
		System.out.println("upload data = " + data);
		System.out.println("deviceid = " + data.getDeviceId());
	}

	/*@Data
	public static class DataUpload{
		private String devEUI;
		private String time;
		private Integer fPort;
		private Integer gatewayCount;
		private Integer rssi;
		private String data;
	}*/
}
