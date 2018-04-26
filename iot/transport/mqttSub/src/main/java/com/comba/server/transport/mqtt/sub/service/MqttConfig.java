package com.comba.server.transport.mqtt.sub.service;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * MQTT Config
 *
 * @author maozhihui
 * @create 2017-10-10 11:35
 **/
@Component
@Data
public class MqttConfig {

    // 是否开启
    private boolean enabled;
    // 用户名
    private String userName;
    // 密码
    private String pwd;
    // 发布的topic
    private String topic;
    // QOS配置
    private String qos;
    // 是否需要鉴权
    private boolean authEnable;
    // 连接的超时时长
    private int connectingTimeOut;
    // 心跳间隔时长
    private int intervalTime;
    // 连接的URL
    private String uri;
    // 订阅的topic
    private String subTopic;

    private String pushTopic;

    private String modelClass;
}
