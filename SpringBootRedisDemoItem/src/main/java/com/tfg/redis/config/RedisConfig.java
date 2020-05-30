package com.tfg.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.tfg.redis.model.Item;

@Configuration
public class RedisConfig {

	
	@Bean
	  JedisConnectionFactory jedisConnectionFactory(){
	    return new JedisConnectionFactory();
	  }

	  @Bean
	  RedisTemplate<String, Item> redisTemplate(){
	    RedisTemplate<String,Item> redisTemplate = new RedisTemplate<String, Item>();
	    redisTemplate.setConnectionFactory(jedisConnectionFactory());
	    return redisTemplate;
	  }
	
	
}
