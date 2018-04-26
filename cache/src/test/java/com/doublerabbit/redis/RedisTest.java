package com.doublerabbit.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doublerabbit.cache.service.RedisService;
import com.doublerabbit.cache.service.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

	@Autowired
	private RedisService redisService;
	
	@Test
	public void addUser(){
		User user = new User("san", 20);
		redisService.addUser(user);
	}
	
	@Test
	public void getUser(){
		User user = redisService.getUser("san");
		System.out.println(user);
	} 
	
	@Test
	public void addStr(){
		redisService.addStrCache("lisi", "caca");
	}
	
	@Test
	public void getStr(){
		System.out.println(redisService.getStrCache("lisi"));
	}
	
	@Test
	public void findNameById(){
		for (int i = 0; i < 3; i++) {
			System.out.println(redisService.findNameById("124"));
		}
	}
	
	@Test
	public void deleteNameById(){
		redisService.deleteNameById("124");
	}
	
	@Test
	public void findAllById(){
		redisService.findAllById("all").forEach(r -> System.out.println(r));
	}
}
