package com.doublerabbit.cache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.doublerabbit.cache.dao.UserDao;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private UserDao userDao;
	
	public User addUser(User user){
		System.out.println(user);
		redisTemplate.opsForValue().set(user.getName(), user);
		return user;
	}
	
	public User getUser(String name){
		Object obj = redisTemplate.opsForValue().get(name);
		return (obj == null) ? null : (User)obj;
	}
	
	public void addStrCache(String key,String value){
		stringRedisTemplate.opsForValue().set(key, value);
	}
	
	public String getStrCache(String key){
		return stringRedisTemplate.opsForValue().get(key);
	}
	
	public String findNameById(String id){
		return userDao.findNameById(id);
	}
	
	public void deleteNameById(String id){
		userDao.deleteById(id);
	}
	
	public List<User> findAllById(String id){
		return userDao.findAllById(id);
	}
}
