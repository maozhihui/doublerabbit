package com.comba.server.actors.rule;

import akka.event.LoggingAdapter;
import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.shared.AbstractContextAwareMsgProcessor;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.data.rule.ForwardRule;
import com.comba.server.common.msg.forward.ForwardMsgRequest;
import com.comba.server.common.msg.plugin.ForwardLifecycleMsg;
import com.comba.server.service.mq.MQConfig;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;
import java.util.*;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 12:56
 **/
public class ForwardActorMessageProcessor extends AbstractContextAwareMsgProcessor {

    private final MQConfig config;
    private Session session;
    private Map<String,List<ForwardRule>> forwardRuleMap;
    private Map<String,MessageProducer> producerMap;

    public ForwardActorMessageProcessor(ActorSystemContext systemContext, LoggingAdapter logger) {
        super(systemContext, logger);
        forwardRuleMap = new HashMap<>();
        producerMap = new HashMap<>();
        config = systemContext.getMqConfig();
        init();
        initMq();
    }

    public void init(){
        forwardRuleMap.clear();
        List<ForwardRule> forwardRules = systemContext.getForwardRuleService().findAll();
        for (ForwardRule rule : forwardRules){
            List<ForwardRule> lists = forwardRuleMap.get(rule.getTenantId());
            if (lists == null){
                lists = new ArrayList<>();
                lists.add(rule);
                forwardRuleMap.put(rule.getTenantId(),lists);
            }else {
                lists.add(rule);
            }
        }
        logger.info("load forwardRule size [{}]",forwardRuleMap.size());
    }

    private void initMq(){
        JmsConnectionFactory factory = new JmsConnectionFactory(config.getBrokerUrl());
        try {
            Connection connection = factory.createConnection(config.getUserName(), config.getPassword());
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }catch (JMSException e){
            logger.error("init mq session failed,cause [{}]",e.getMessage());
        }
    }

    public void onLifecycleMsg(ForwardLifecycleMsg msg){
        logger.info("ForwardActorMessageProcessor recieve msg [{}]",msg);
        List<ForwardRule> lists = forwardRuleMap.get(msg.getTenantId());
        if (msg.getEvent() == ComponentLifecycleEvent.CREATED){
            Optional<ForwardRule> forwardRule = systemContext.getForwardRuleService().findById(msg.getForwardRuleId());
            if (forwardRule.isPresent()){
                if (lists == null){
                    lists = new ArrayList<>();
                    lists.add(forwardRule.get());
                    forwardRuleMap.put(msg.getTenantId(),lists);
                }else {
                    deleteForwardRule(lists,msg.getForwardRuleId());
                    lists.add(forwardRule.get());
                }
            }else {
                logger.warning("forwardRule id [{}] is not exists",msg.getForwardRuleId());
            }
        }else if (msg.getEvent() == ComponentLifecycleEvent.DELETED){
            deleteForwardRule(lists,msg.getForwardRuleId());
        }else {
            logger.info("[{}] LifecycleMsg type not support.");
        }
    }

    private void deleteForwardRule(List<ForwardRule> ruleList,String ruleId){
        if (ruleList != null){
            int j = -1;
            for (int i = 0 ; i < ruleList.size(); i++){
                if (ruleList.get(i).getId().equals(ruleId)){
                    j = i;
                }
            }
            if (j != -1){
                // 表示原己存在此规则
                MessageProducer producer = producerMap.get(ruleList.get(j).getDst());
                if (producer != null){
                    try {
                        logger.info("close producer,topic [{}]",producer.getDestination().toString());
                        producerMap.remove(ruleList.get(j).getDst());
                        producer.close();
                    } catch (JMSException e) {
                        logger.error("close produce exception,cause [{}]",e.getMessage());
                    }
                }
                ruleList.remove(j);
            }
        }
    }

    public void onForwardMsg(ForwardMsgRequest forwardMsg){
        List<ForwardRule> lists = forwardRuleMap.get(forwardMsg.getTenantId());
        if (lists == null || lists.size() == 0){
            logger.warning("[{}] does not find forward rule",forwardMsg.getTenantId());
        }else {
            for (ForwardRule rule : lists){
                if (rule.getEvent().equals(forwardMsg.getEventType())){
                    if (rule.getType().equals("url")){
                        logger.warning("url does not support.");
                        break;
                    }
                    // 发送消息
                    try {
                        if (session == null){
                            logger.error("mq session init failed.");
                            return;
                        }
                        MessageProducer producer = producerMap.get(rule.getDst());
                        if (producer == null){
                            Destination destination;
                            if (rule.getDst().startsWith(config.getTopicPrefix())) {
                                destination = session.createTopic(rule.getDst().substring(config.getTopicPrefix().length()));
                            } else {
                                destination = session.createQueue(rule.getDst());
                            }
                            producer = session.createProducer(destination);
                            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                            producerMap.put(rule.getDst(),producer);
                        }
                        TextMessage message = session.createTextMessage(forwardMsg.getData());
                        producer.send(message);
                        logger.debug("send message [{}],topic [{}]",forwardMsg.getData(),rule.getDst());
                    }catch (Exception e){
                        logger.error("send msg failed,cause [{}]",e.getMessage());
                    }
                    break;
                }
            }
        }
    }
}
