package com.comba.server.transport.mqtt.sub.message.comba;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年11月10日 下午8:33:13
 */
@Data
public class DataRate {
	private String modulation;
	private Integer bandwidth;
	private Integer spreadFactor;
}
