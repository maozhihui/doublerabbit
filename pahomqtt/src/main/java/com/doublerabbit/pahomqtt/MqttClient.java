package com.doublerabbit.pahomqtt;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClient {

    private MqttConfig config;

    private org.eclipse.paho.client.mqttv3.MqttClient client;
    private MqttConnectOptions options;

    public MqttClient(MqttConfig config){
        this.config = config;
        init();
    }

    private void init(){
        try {
            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new org.eclipse.paho.client.mqttv3.MqttClient(config.getUri(), config.getClientId(), new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            //if (config.isAuthEnable()) {
            if (config.isAuthEnable()) {
                // 设置连接的用户名
                options.setUserName(config.getUserName());
                // 设置连接的密码
                options.setPassword(config.getPwd().toCharArray());
            }
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(config.getConnectingTimeOut());
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(config.getIntervalTime());
            options.setCleanSession(false);
            // 设置回调
            //client.setCallback(callback);
            //MqttTopic topic = client.getTopic(config.getTopic());
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            //options.setWill(topic, "close".getBytes(), 0, true);

            client.connect(options);
            //订阅消息
            /*int[] Qos  = {Integer.parseInt(config.getQos())};
            String[] topic1 = {config.getSubTopic()};
            client.subscribe(topic1, Qos);*/
            System.out.println("connect server success " + config.getUri());
        } catch (Exception e) {
            System.err.println("connect server failed,cause " + e.getMessage());
        }
    }

    private void send(String message,MqttMessage mqttMessage){
    	try {
			client.publish(config.getPushTopic(), mqttMessage);
		} catch (MqttPersistenceException e) {
			System.err.println("MqttPersistenceException cause:" + e.getMessage());
		} catch (MqttException e) {
			System.err.println("MqttException cause:" + e.getMessage());
		}
    }
    
    /**
     * 默认的qos为1
     * @param message
     */
    public void send(String message){
    	MqttMessage mqttMessage = new MqttMessage(message.getBytes());
    	mqttMessage.setQos(Integer.valueOf(config.getQos()));
    	this.send(message, mqttMessage);
    }
    
    public void send(String message,int qos){
    	MqttMessage mqttMessage = new MqttMessage(message.getBytes());
    	if (qos < 0 || qos > 2) {
			System.err.println("qos is invalid.");
			return;
		}
    	mqttMessage.setQos(qos);
    	this.send(message, mqttMessage);
    }
    
    public void subscribe(String topic,MqttCallback callback){
    	client.setCallback(callback);
    	int[] Qos  = {Integer.parseInt(config.getQos())};
        String[] topic1 = {config.getSubTopic()};
        try {
			client.subscribe(topic1, Qos);
		} catch (MqttException e) {
			System.err.println("MqttException cause:" + e.getMessage());;
		}
    }
    
    private ScheduledExecutorService scheduler;

    //重新链接
    public void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (!client.isConnected()) {
                    init();
                    /*try {
                        //client.connect(options);
                    } catch (MqttSecurityException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    public void disconnect() {
        try {
            scheduler.shutdownNow();
            client.disconnect();
            System.out.println("mqtt client close the connection.");
        } catch (MqttException e) {
            System.err.println("mqtt client disconnect failed,msg:" + e.getMessage());
        }
    }
}