package com.comba.alarm.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.jms.*;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Service
public class ConnectionProducer {


    @Value("${activemq.amqpUrl}")
    private String uri;

    @Value("${activemq.user}")
    private String USER;

    @Value("${activemq.password}")
    private String PASSWORD;

    private Connection connection;
    private Session session;

    ConcurrentHashMap<String,MessageProducer> producers = new ConcurrentHashMap<>();

    private MessageProducer getProducer(String topicName){
        if (producers.containsKey(topicName)){
            return producers.get(topicName);
        }
        return createProducer(topicName);
    }

    public void init(){
        log.info("producer init==============");
        JmsConnectionFactory factory = new JmsConnectionFactory(uri);
        try {
            connection = factory.createConnection(USER,PASSWORD);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            log.info("producer create success....");
        } catch (JMSException e) {
            log.error("can not create connection,cause [{}]",e.getMessage());
        }

    }

    public MessageProducer createProducer(String destName){
        if (session == null){
            init();
        }

        try {
            Destination destination = session.createTopic(destName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producers.put(destName, producer);
            return producer;
        } catch (JMSException e) {
            log.error("create producer fail {}",e.getMessage());
        }
        return null;
    }

    public void send(String destName,String msg){
        MessageProducer producer = getProducer(destName);
        if (producer == null) {
            log.error("producer is null,send message failed. msg {}",msg);
            return;
        }

        try {
            TextMessage message = session.createTextMessage(msg);
            producer.send(message);
            log.info("send message [{}],topic [{}]",msg,destName);
        } catch (JMSException e) {
            log.error("send message fail {} ",msg);
        }
    }

    @PreDestroy
    public void destroy() throws JMSException{
        if (connection != null){
            connection.close();
            log.info("connection closed.");
        }
    }
}
