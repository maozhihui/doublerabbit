package com.doublerabbit.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@EnableScheduling
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@Scheduled(cron = "00/1 * * * * ?")
	public void send(){
		String message = UUID.randomUUID().toString();
		ListenableFuture future = kafkaTemplate.send("register",message);
		future.addCallback(o -> System.out.println("send-消息发送成功:" + message), 
							throwable -> System.out.println("消息发送失败:" + message));
	}
}
