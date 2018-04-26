package com.doublerabbit.cache.service;

import org.springframework.stereotype.Service;

/**
 * redis订阅消息的接收者
 * @author maozhihui
 * @date 2018年4月26日 下午3:13:30
 */
@Service
public class RedisReciever {

	public void receiveMessage(String message){
		System.out.println("redis recieve msg = " + message);
	}
}
