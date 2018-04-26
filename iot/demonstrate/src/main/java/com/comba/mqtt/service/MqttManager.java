package com.comba.mqtt.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniItem;
import org.dtools.ini.IniSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.comba.mqtt.message.TelemetryUploadRequest;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * 负责加载MQTT配置及初始化连接
 * @author maozhihui
 * @date 2017年11月10日 下午1:33:44
 */
@Slf4j
@Service
public class MqttManager {
	
	@Autowired
	private MqttServiceContext context;
	
	private static final Gson GSON = new Gson();
	private List<MqttConfig> configs;
	private List<Client> clients;
	private Map<String, TelemetryUploadRequest> parserMap;
	
	@PostConstruct
	public void init(){
		log.info("init===========================");
		configs = new ArrayList<>();
		clients = new ArrayList<>();
		loadConfig();
		initMqttClient();
	}
	
	/**
	 * 加载MQTT配置信息
	 */
	public void loadConfig(){
		log.info("load config=================");
		IniFile iniFile = new BasicIniFile(); 
		try {
			File file = ResourceUtils.getFile("classpath:mqtt.ini");
			IniFileReader reader = new IniFileReader(iniFile, file);
	        reader.read();
		} catch (IOException e) {
			log.error("load mqtt.ini failed [{}]",e.getMessage());
		}
		
		StringBuilder sb = new StringBuilder();
        for(int i=0;i<iniFile.getNumberOfSections();i++){
        	sb.setLength(0);
            IniSection sec = iniFile.getSection(i);
            sb.append("{");
            for(IniItem item : sec.getItems()){  
                sb.append("\"").append(item.getName()).append("\":")
                	.append("\"").append(item.getValue()).append("\",");
            }  
            sb.deleteCharAt(sb.length()-1);
            sb.append("}");
            if (sec.getName().contains("mqtt")){
            	MqttConfig config = GSON.fromJson(sb.toString(), MqttConfig.class);
                log.info("config [{}]",config);
                configs.add(config);
            }/*else if (sec.getName().contains("parser")) {
            	ParserConfig config = GSON.fromJson(sb.toString(), ParserConfig.class);
                log.info("config [{}]",config);
				try {
					Class<TelemetryUploadRequest> clazz = (Class<TelemetryUploadRequest>)Class.forName(config.getModelClass());
	                TelemetryUploadRequest dataModel = clazz.newInstance();
					parserMap.put(config.getApplicationName(), dataModel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("exception cause [{}]",e.getMessage());
				}
			}*/  
            
        }
	}
	
	public void initMqttClient(){
		log.info("load config size [{}]",configs.size());
		for (MqttConfig config : configs) {
			if (config.isEnabled()) {
				PushCallback callback = new PushCallback(context,config);
				Client client = new Client(config, callback);
				// 进行连接初始化
				client.init();
				client.startReconnect();
				clients.add(client);
			}else {
				log.warn("mqtt server uri [{}] disabled connect.",config.getUri());
			}
		}
	}
	
	@PreDestroy
	public void destroy(){
		for (Client client : clients) {
			client.disconnect();
		}
	}


//	public static void main(String[] args){
//		System.out.println("=====");
//		IniFile iniFile = new BasicIniFile();
//		try {
//			File file = ResourceUtils.getFile("classpath:mqtt.ini");
//			IniFileReader reader = new IniFileReader(iniFile, file);
//	        reader.read();
//		} catch (IOException e) {
//			System.err.println("cause error:" + e.getMessage());
//		}
//
//        System.out.println("sectionNum=" + iniFile.getNumberOfSections());
//
//        List<MqttConfig> mqttConfigs = new ArrayList<>();
//        List<ParserConfig> parserConfigs = new ArrayList<>();
//
//        for(int i=0;i<iniFile.getNumberOfSections();i++){
//            IniSection sec = iniFile.getSection(i);
//
//            if (sec.getName().contains("mqtt")) {
//            	StringBuilder sb = new StringBuilder("{");
//                for(IniItem item : sec.getItems()){
//                  //  System.out.println(item.getName() + " = " + item.getValue());
//                    sb.append("\"").append(item.getName()).append("\":")
//                    	.append("\"").append(item.getValue()).append("\",");
//                }
//                sb.deleteCharAt(sb.length()-1);
//                sb.append("}");
//                //System.out.println("json str=" + sb.toString());
//                MqttConfig config = GSON.fromJson(sb.toString(), MqttConfig.class);
//                System.out.println("mqtt config = " + config);
//			}else if (sec.getName().contains("parser")) {
//				StringBuilder sb = new StringBuilder("{");
//                for(IniItem item : sec.getItems()){
//                    sb.append("\"").append(item.getName()).append("\":")
//                    	.append("\"").append(item.getValue()).append("\",");
//                }
//                sb.deleteCharAt(sb.length()-1);
//                sb.append("}");
//                ParserConfig config = GSON.fromJson(sb.toString(), ParserConfig.class);
//                System.out.println("parser config = " + config);
//			}
//
//        }
//	}
	
}
