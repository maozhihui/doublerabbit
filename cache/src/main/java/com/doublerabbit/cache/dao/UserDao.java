package com.doublerabbit.cache.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.doublerabbit.cache.service.User;

@Repository
@CacheConfig(cacheNames = "user")
public class UserDao {

	//@CachePut(key = "#id")
	@Cacheable(key = "#id")
	public String findNameById(String id){
		return getStr();
	}
	
	@CacheEvict(key = "#id")
	public void deleteById(String id){
		System.out.println("delete obj by id = " + id);
	}
	
	private String getStr(){
		System.out.println("call getStr method===");
		return "zhangsan";
	}
	
	/**
	 * 测试存储list对象
	 * @param id
	 * @return
	 */
	@Cacheable(key = "#id")
	public List<User> findAllById(String id){
		System.out.println("find all User=======");
		List<User> res = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			res.add(new User(String.valueOf(i), i*10));
		}
		return res;
	}
}
