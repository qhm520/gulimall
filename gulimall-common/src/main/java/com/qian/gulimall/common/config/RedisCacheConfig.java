package com.qian.gulimall.common.config;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * RedisCacheConfig is
 *
 * @author QIAN
 * Date 2020/05/20
 * Time 09:52
 */
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                System.out.println(key);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                System.out.println(value);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {

            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {

            }
        };
        return cacheErrorHandler;
    }
}
