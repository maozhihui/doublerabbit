package com.comba.mqtt.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Server {
	public static final String HOST = "tcp://10.10.107.104:1883";  
	  
    public static final String TOPIC = "application/ap";  
    private static final String clientid ="server";   
  
    private MqttClient client;  
    private MqttTopic topic;  
    //private String userName = "test";  
    //private String passWord = "test";  
  
    private MqttMessage message;  
  
    public Server() throws MqttException {  
         //MemoryPersistence设置clientid的保存形式，默认为以内存保存  
        client = new MqttClient(HOST, clientid, new MemoryPersistence());  
        connect();  
    }  
      
    private void connect() {  
        MqttConnectOptions options = new MqttConnectOptions();  
        options.setCleanSession(false);  
        //options.setUserName(userName);  
        //options.setPassword(passWord.toCharArray());  
        // 设置超时时间  
        options.setConnectionTimeout(10);  
        // 设置会话心跳时间  
        options.setKeepAliveInterval(20);  
        try {  
               client.setCallback(new PushCallbackBak());  
               client.connect(options);  
               topic = client.getTopic(TOPIC);  
        } catch (Exception e) {  
               e.printStackTrace();  
        }  
    }  
      
    public void publish(MqttMessage message) throws MqttPersistenceException, MqttException{  
        MqttDeliveryToken token = topic.publish(message);  
        token.waitForCompletion();  
        System.out.println(token.isComplete()+"========");  
    }  
  
    private List<String> UploadData(){
    	List<String> lists = new ArrayList<>();
    	File file = new File(this.getClass().getClassLoader().getResource("test.log").getPath());
    	BufferedReader reader = null;
    	if (file.exists()) {
    		try {
    			reader = new BufferedReader(new FileReader(file));;
    			String temp = null;
    			while((temp = reader.readLine()) != null){
    				lists.add(temp);
    			}
    			reader.close();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}else {
			System.out.println("file is not exists.");
		}
    	return lists;
    }
    
    public static void main(String[] args) throws MqttException {  
        Server server =  new Server();  
        /*server.message = new MqttMessage();  
        server.message.setQos(1);  
        server.message.setRetained(true);  
        server.message.setPayload("eeeeeaaaaaawwwwww---".getBytes());  
         server.publish(server.message);  
         System.out.println(server.message.isRetained()+"------ratained状态");*/
         
         List<String> uploadData = server.UploadData();
		System.out.println("uploadData size="+uploadData.size());
		for (String str : uploadData) {
			server.message = new MqttMessage();
			server.message.setQos(1);
			server.message.setRetained(true);
			server.message.setPayload(str.getBytes());
			try {
				server.publish(server.message);
				Thread.sleep(2*1000L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }  
}
