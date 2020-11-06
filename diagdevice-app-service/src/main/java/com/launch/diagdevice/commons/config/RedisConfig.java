package com.launch.diagdevice.commons.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

// 1. 开启缓存注解
@EnableCaching
@Configuration
public class RedisConfig {

	
	public static final int NO_PARAM_KEY = 0;
	public static final int NULL_PARAM_KEY = 53;
	
	/**
	 * 指定key的生成策略
	 * @return
	 */
	@Bean
	public KeyGenerator simpleKeyGenerator() {
		return (o, method, objects) -> {
			
			StringBuilder key = new StringBuilder();
			
			key.append(o.getClass().getSimpleName());
			key.append(".");
			key.append(method.getName());
			
			key.append(": [");
			// 当参数为空时
			if (objects.length == 0) {
				key.append(NO_PARAM_KEY).append("]");
				key.append("]");
				return key.toString();
			}
			
			for (Object obj : objects) {
				if (obj == null) 
					key.append(NULL_PARAM_KEY);
				else
					key.append(obj.toString());
			}
			key.append("]");

			return key.toString();
		};
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				this.getRedisCacheConfigurationWithTtl(600), // 默认策略，未配置的 key，会使用这个
																
				this.getRedisCacheConfigurationMap() // 指定 key 策略
		);
	}

	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		// 指定缓存名为 getDiagSoftByPdtTypeId 的失效时间为1800秒
		redisCacheConfigurationMap.put("diagdevice_app_service", this.getRedisCacheConfigurationWithTtl(1800));
		//redisCacheConfigurationMap.put("UserInfoListAnother", this.getRedisCacheConfigurationWithTtl(18000));

		return redisCacheConfigurationMap;
	}

	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
				.entryTtl(Duration.ofSeconds(seconds));

		return redisCacheConfiguration;
	}

}
