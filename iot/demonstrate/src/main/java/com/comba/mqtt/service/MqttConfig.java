package com.comba.mqtt.service;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MqttConfig {

	private boolean enabled;
	
	private String userName;
	
	private String pwd;
	
	private String topic;
	
	private String qos;
	
	private boolean authEnable;
	
	private int connectingTimeOut;
	
	private int intervalTime;
	
	private String uri;
	
	private String subTopic;
	
	private String pushTopic;
	
	private String modelClass;
}
