package com.doublerabbit.cache.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doublerabbit.cache.service.RedisReciever;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @EnableCaching 表示支持缓存，当与@Configuration配合使用时，
 * spring会触发post processor，扫描bean查看是否存在对应的缓存注解，如果有则生成拦截代理 
 * @author maozhihui
 * @date 2018年4月26日 上午11:30:25
 */
@Configuration
@EnableCaching	
public class RedisConfig extends CachingConfigurerSupport{

	/**
	 * 自定义缓存键生成器，当没有指定key时，会按此生成器生成key
	 */
	@Bean
	public KeyGenerator keyGenerator(){
		return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                System.out.println("keygenerator = " + sb.toString());
                return sb.toString();
            }
        };
	}
	
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory){
		RedisCacheManager rcm = new RedisCacheManager(redisTemplate(factory));
		// 设置缓存过期时间
		//rcm.setDefaultExpiration(60000L);
		return rcm;
	}
	
	/*@Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout);
        factory.setPassword(password);
        factory.setDatabase(database);
        return factory;
    }*/
	
	@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        //StringRedisTemplate的构造方法中默认设置了stringSerializer
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //set value serializer
        template.setDefaultSerializer(jackson2JsonRedisSerializer);

        template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        return template;
    }
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory factory,MessageListenerAdapter listenerAdapter){
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		container.addMessageListener(listenerAdapter, new PatternTopic("/rule/update"));
		return container;
	}
	
	/**
	 * 利用反射注入监听到消息后的处理方法
	 * @param redisReciever
	 * @return
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(RedisReciever redisReciever){
		return new MessageListenerAdapter(redisReciever, "receiveMessage");
	}
}
