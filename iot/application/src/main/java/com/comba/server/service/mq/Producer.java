package com.comba.server.service.mq;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.qpid.jms.*;
import javax.jms.*;

/**
 * Producer
 *
 * @author maozhihui
 * @create 2017-10-12 10:18
 **/
@Slf4j
public class Producer {

    private final String TOPIC_PREFIX = "topic://";
    private static String USER = "admin";
    private static String PASSWORD = "password";
    private String uri;
    private String destName;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public Producer(String uri,String destName){
        this(USER,PASSWORD,uri,destName);
    }

    public Producer(String user,String pwd,String uri,String destName){
        USER = user;
        PASSWORD = pwd;
        this.uri = uri;
        this.destName = destName;
        init();
    }

    public void init(){
        log.info("producer init==============");
        JmsConnectionFactory factory = new JmsConnectionFactory(uri);
        try {
            connection = factory.createConnection(USER,PASSWORD);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = null;
            if (destName.startsWith(TOPIC_PREFIX)) {
                destination = session.createTopic(destName.substring(TOPIC_PREFIX.length()));
            } else {
                destination = session.createQueue(destName);
            }

            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            log.info("producer create success....");
        } catch (JMSException e) {
            log.error("can not create connection,cause [{}]",e.getMessage());
        }
    }

    public void send(String msg) throws JMSException{
        if (producer != null){
            TextMessage message = session.createTextMessage(msg);
            producer.send(message);
            log.info("send message [{}],topic [{}]",msg,destName);
        }else {
            log.error("producer is null,send message failed.");
        }
    }

    public void destroy() throws JMSException{
        if (connection != null){
            connection.close();
            log.info("connection closed.");
        }
    }
}
