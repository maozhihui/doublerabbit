package com.comba.mqtt.message.comba;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年11月10日 下午8:34:36
 */
@Data
public class RxInfoItem {
	private String mac;
	private Integer rssi;
	private Float loRaSNR;
	private String name;
	private Double latitude;
	private Double longitude;
	private Integer altitude;
}
