package com.comba.mqtt.service;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Service
public class Client {
	
	//@Autowired
	private MqttConfig config;
	
	//@Autowired
	private MqttCallback callback;
	
    private MqttClient client;  
    private MqttConnectOptions options;  
    
    /*@Value("${mqtt.enabled}")
    private boolean enabled;*/
  
    public Client(MqttConfig config,MqttCallback callback){
    	this.config = config;
    	this.callback = callback;
    }
    
    //@PostConstruct
    public void init(){
    	if (!config.isEnabled()) {
    		log.info("mqqt service disabled.");
			return;
		}
    	try {  
            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存  
            client = new MqttClient(config.getUri(), UUID.randomUUID().toString(), new MemoryPersistence());  
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
            // 设置回调  
            client.setCallback(callback);  
            MqttTopic topic = client.getTopic(config.getTopic());  
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息    
            options.setWill(topic, "close".getBytes(), 0, true);  
              
            client.connect(options);  
            //订阅消息  
            int[] Qos  = {Integer.parseInt(config.getQos())};  
            String[] topic1 = {config.getSubTopic()};  
            client.subscribe(topic1, Qos);  
        } catch (Exception e) {  
            e.printStackTrace();
            log.error("connect server [{}] failed.",config.getUri());
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
                        client.connect(options);  
                    } catch (MqttSecurityException e) {  
                        e.printStackTrace();  
                    } catch (MqttException e) {  
                        e.printStackTrace();  
                    }*/
                }  
            }  
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);  
    }  
  
    //@PreDestroy
    public void disconnect() {  
         try {  
            client.disconnect();  
        } catch (MqttException e) {  
            e.printStackTrace();  
        }  
    }  
      
}
