package com.comba.server.service.mq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/7 12:32
 **/
@Component
@Data
public class MQConfig {

    @Value("${activemq.amqpUrl}")
    private String brokerUrl;

    @Value("${activemq.user}")
    private String userName;

    @Value("${activemq.password}")
    private String password;

    @Value("${activemq.topicPrefix}")
    private String topicPrefix;
}
