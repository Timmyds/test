package com.fxb.world.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fxb.world.redis.RedisObjectSerializer;

/**
 * redis 配置
 */
@Configuration
@EnableCaching  
public class RedisConfig {

	 @Bean
	    public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
	        RedisCacheManager cacheManager = new  RedisCacheManager(redisTemplate);
	        cacheManager.setDefaultExpiration(1800);
	        return cacheManager;
	    }
	    
	    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }
}
