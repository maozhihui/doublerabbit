package com.doublerabbit.kafkaconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = {"register"})
	public void consumer(String message){
		System.out.println("recieve [test] topic msg:" + message);
	}
	
}
