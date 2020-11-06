package com.launch.diagdevice.common.configure;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	private Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	@Bean
	public RedisCacheConfiguration redisCacheConfiguration() {
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(objectMapper);
		return RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
				.entryTtl(Duration.ofSeconds(3600));
	}

	public class AppCacheErrorHandler implements CacheErrorHandler {
		@Override
		public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
			if (e instanceof QueryTimeoutException || e instanceof PoolException) {
				logger.warn("redis has lose connection:", e);
				return;
			}
			throw e;
		}

		@Override
		public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
			if (e instanceof QueryTimeoutException || e instanceof PoolException) {
				logger.warn("redis has lose connection:", e);
				return;
			}
			throw e;
		}

		@Override
		public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
			throw e;
		}

		@Override
		public void handleCacheClearError(RuntimeException e, Cache cache) {
			throw e;
		}
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new AppCacheErrorHandler();
	}
}
