package com.comba.server.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.JMSException;

/**
 * AlarmProducer
 *
 * @author maozhihui
 * @create 2017-10-11 20:58
 **/
@Slf4j
//@Service
public class AlarmProducer {

    //@Value("${activemq.amqpUrl}")
    private String brokerUrl;

    //@Value("${activemq.user}")
    private String userName;

    //@Value("${activemq.password}")
    private String password;

    //@Value("${activemq.topic.alarm}")
    private String destName;

    //@Value("${activemq.enabled}")
    private boolean enabled;

    private Producer producer;

    //@PostConstruct
    public void init(){
        if (enabled){
            producer = new Producer(userName,password,brokerUrl,destName);
        }else {
            log.warn("alarmProducer disabled,topic [{}]",destName);
        }
    }

    public void send(String msg){
        try {
            producer.send(msg);
        } catch (JMSException e) {
            log.error("AlarmProducer send msg [{}] failed.cause [{}]",msg,e.getMessage());
        }
    }

    //@PreDestroy
    public void destroy(){
        try {
            producer.destroy();
        } catch (JMSException e) {
            log.error("AlarmProducer destroy failed,cause [{}]",e.getMessage());
        }
    }
}
