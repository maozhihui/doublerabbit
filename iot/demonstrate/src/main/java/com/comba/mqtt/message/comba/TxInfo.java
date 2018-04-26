package com.comba.mqtt.message.comba;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年11月10日 下午8:34:06
 */
@Data
public class TxInfo {
	private Integer frequency;
	private DataRate dataRate;
	private Boolean adr;
	private String codeRate;
}
