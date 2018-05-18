package com.doublerabbit.pahomqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PushCallback implements MqttCallback {

	public PushCallback(){}
	
	public void connectionLost(Throwable cause) {
		// 连接丢失后，一般在这里面进行重连
        System.out.println("the connection is closed,please retry cause " + cause.getMessage());

	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String mqttMsg = new String(message.getPayload());
        System.out.println("recieve mqtt msg:"+mqttMsg);

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		System.out.println("delivery message Complete status " + token.isComplete());
	}

}
