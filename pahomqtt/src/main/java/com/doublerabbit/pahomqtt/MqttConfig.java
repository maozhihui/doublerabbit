package com.doublerabbit.pahomqtt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MqttConfig {

	private String clientId;
    // 用户名
    private String userName;
    // 密码
    private String pwd;
    // 发布的topic
    //private String topic;
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

}
