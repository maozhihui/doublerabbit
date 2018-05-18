package com.doublerabbit.pahomqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 * MQTT消息订阅者
 * @author maozhihui
 * @date 2018年5月17日 下午4:32:40
 */
public class Subscriber {
	
	private static final String clientId = "1234";
	private static final String userName = "comba";
	private static final String pwd = "zaq1XSW2";
	private static final String subTopic = "application/#";
	private static final String pushTopic = "application/ap";
	private static final String qos = "2";
	private static final boolean authEnable = false;
	private static final int connectingTimeOut = 10;
	private static final int intervalTime = 20;
	private static final String uri = "tcp://10.10.107.104:1883";


	public static void main(String[] args) {
		MqttCallback callback = new PushCallback();
		MqttConfig config = new MqttConfig(clientId, userName, pwd, qos, authEnable, 
											connectingTimeOut, intervalTime, uri, subTopic, pushTopic);
		MqttClient client = new MqttClient(config);
		client.subscribe(config.getSubTopic(), callback);
	}

}
