package com.comba.mqtt.service.util;

import java.util.List;

import com.comba.mqtt.message.comba.DataUpload;
import com.google.gson.Gson;

import lombok.Data;

public class JsonTest2 {

	private static final Gson GSON = new Gson();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jsonStr = "{\"applicationID\":\"4\",\"applicationName\":\"pan_lora\",\"nodeName\":\"test_appkey_04\",\"devEUI\":\"3436363663376406\",\"rxInfo\":[{\"mac\":\"aa555a0000000105\",\"rssi\":-38,\"loRaSNR\":9.2,\"name\":\"lora_gw\",\"latitude\":23.1167,\"longitude\":113.25,\"altitude\":10}],\"txInfo\":{\"frequency\":488300000,\"dataRate\":{\"modulation\":\"LORA\",\"bandwidth\":125,\"spreadFactor\":12},\"adr\":true,\"codeRate\":\"4/5\"},\"fCnt\":203,\"fPort\":2,\"data\":\"eyJ0eXBlIjoidGVzdCIsImRhdGEiOiIxIn0=\"}";
		DataUpload data = GSON.fromJson(jsonStr, DataUpload.class);
		System.out.println("json str=" + data);
	}

	/*@Data
	public static class DataUpload{
		private String applicationID;
		private String applicationName;
		private String nodeName;
		private String devEUI;
		private List<RxInfoItem> rxInfo;
		private TxInfo txInfo;
		private Integer fCnt;
		private Integer fPort;
		private String data;
	}
	
	@Data
	public static class RxInfoItem{
		private String mac;
		private Integer rssi;
		private Float loRaSNR;
		private String name;
		private Double latitude;
		private Double longitude;
		private Integer altitude;
	}
	
	@Data
	public static class TxInfo{
		private Integer frequency;
		private DataRate dataRate;
		private Boolean adr;
		private String codeRate;
	}
	
	@Data
	public static class DataRate{
		private String modulation;
		private Integer bandwidth;
		private Integer spreadFactor;
	}*/
}
