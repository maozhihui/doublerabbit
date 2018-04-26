package com.comba.server.transport.mqtt.sub.service;

import com.comba.server.transport.mqtt.sub.message.DataParser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.dtools.ini.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/11/29 17:39
 **/
@Slf4j
@Service
public class MqttManager {

    @Autowired
    private MqttServiceContext context;

    private static final Gson GSON = new Gson();
    private List<MqttConfig> configs;
    private List<MqttClient> clients;
    private MqttMsgHandler msgHandler;

    @PostConstruct
    public void init(){
        log.info("MqttManager init===========================");
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
        } catch (Exception e) {
            log.error("load mqtt.ini failed [{}]",e.getMessage());
        }

        Map<String,DataParser> parserMap = new HashMap<>();
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
            }else if (sec.getName().contains("parser")){
                ParserConfig config = GSON.fromJson(sb.toString(), ParserConfig.class);
                log.info("config [{}]",config);
                try {
                    Class<DataParser> clazz = (Class<DataParser>)Class.forName(config.getModelClass());
                    DataParser parser = clazz.newInstance();
                    parserMap.put(config.getApplicationName(), parser);
                } catch (Exception e) {
                    log.error("exception cause [{}]",e.getMessage());
                }
            }
        }
        log.info("parser map size [{}]",parserMap.size());
        msgHandler = new MqttMsgHandler(parserMap);
    }

    public void initMqttClient(){
        log.info("load config size [{}]",configs.size());
        for (MqttConfig config : configs) {
            if (config.isEnabled()) {
                PushCallback callback = new PushCallback(context,config,msgHandler);
                MqttClient client = new MqttClient(config, callback);
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
        for (MqttClient client : clients) {
            client.disconnect();
        }
    }

    public static void main(String[] args){
        System.out.println("=====");
        IniFile iniFile = new BasicIniFile();
        try {
            File file = ResourceUtils.getFile("classpath:mqtt.ini");
            IniFileReader reader = new IniFileReader(iniFile, file);
            reader.read();
        } catch (Exception e) {
            System.err.println("cause error:" + e.getMessage());
        }

        System.out.println("sectionNum=" + iniFile.getNumberOfSections());

        for(int i=0;i<iniFile.getNumberOfSections();i++){
            IniSection sec = iniFile.getSection(i);

            //System.out.println("---- " + sec.getName() + " ----");
            StringBuilder sb = new StringBuilder("{");
            for(IniItem item : sec.getItems()){
                //  System.out.println(item.getName() + " = " + item.getValue());
                sb.append("\"").append(item.getName()).append("\":")
                        .append("\"").append(item.getValue()).append("\",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("}");
            //System.out.println("json str=" + sb.toString());
            MqttConfig config = GSON.fromJson(sb.toString(), MqttConfig.class);
            System.out.println("mqtt config = " + config);
        }
    }

}
