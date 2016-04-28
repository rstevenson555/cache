package com.bos.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.DefaultKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 1328975 on 4/26/16.
 */
@Configuration
@EnableCaching
@Profile("boscache")
public class BOSCacheConfiguration implements CachingConfigurer {
    private String name;
    private int maxEntriesLocalHeap;
    private long timeToLiveMillis;
    private long relativeOffsetMillis;
    private BOSCache.ExpiryType expiryType;

    public BOSCache.ExpiryType getExpiryType() {
        return expiryType;
    }

    public void setExpiryType(BOSCache.ExpiryType expiryType) {
        this.expiryType = expiryType;
    }

    public long getRelativeOffsetMillis() {
        return relativeOffsetMillis;
    }

    public void setRelativeOffsetMillis(long relativeOffsetMillis) {
        this.relativeOffsetMillis = relativeOffsetMillis;
    }

    public long getTimeToLiveMillis() {
        return timeToLiveMillis;
    }

    public void setTimeToLiveMillis(long timeToLiveMillis) {
        this.timeToLiveMillis = timeToLiveMillis;
    }

    public int getMaxEntriesLocalHeap() {
        return maxEntriesLocalHeap;
    }

    public void setMaxEntriesLocalHeap(int maxEntriesLocalHeap) {
        this.maxEntriesLocalHeap = maxEntriesLocalHeap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bean
    public CacheManager cacheManager() {
        CacheManager cacheManager;
        try {
            cacheManager = new BOSCacheManager(internalCaches());
            return cacheManager;
        } catch (final URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public CacheResolver cacheResolver() {
        return null;
    }

    public BOSCache create() {
        switch (expiryType) {
            case RELATIVE_EXPIRY:
                return new BOSCache(name, expiryType, getMaxEntriesLocalHeap(), getTimeToLiveMillis(), getRelativeOffsetMillis());
            case IDLE_EXPIRY:
                return new BOSCache(name, expiryType, getMaxEntriesLocalHeap(), getTimeToLiveMillis());
            case NO_EXPIRY:
                return new BOSCache(name, expiryType, getMaxEntriesLocalHeap());
            case AGE_EXPIRY:
                return new BOSCache(name, expiryType, getMaxEntriesLocalHeap(), getTimeToLiveMillis());
            default:
                return null;
        }
    }

    @Bean
    public Collection<BOSCache> internalCaches() throws URISyntaxException {
        final Collection<BOSCache> caches = new ArrayList<BOSCache>();
        caches.add(create());
        return caches;
    }

    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }

    public CacheErrorHandler errorHandler() {
        return null;
    }

}
