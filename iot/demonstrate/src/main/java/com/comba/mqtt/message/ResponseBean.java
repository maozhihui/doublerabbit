package com.comba.mqtt.message;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年11月6日 上午9:28:34
 * @param <T>
 */
@Data
public class ResponseBean<T> {

	private String code;
	private String msg;
	private T data;
	
}
