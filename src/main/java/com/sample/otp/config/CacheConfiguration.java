package com.sample.otp.config;

import com.google.common.cache.CacheBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration implements CachingConfigurer {

    @Override

    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected @NotNull Cache createConcurrentMapCache(final @NotNull String name) {
            	//Configure ttl for otp in cache
                return new ConcurrentMapCache(name,
                        CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build().asMap(), false);
            }
        };
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }
}
