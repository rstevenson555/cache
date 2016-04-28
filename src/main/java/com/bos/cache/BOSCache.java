package com.bos.cache;

import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.factory.impl.IdleExpiringFactory;
import com.bos.cache.factory.impl.NonExpiringFactory;
import com.bos.cache.factory.impl.RelativeExpiringFactory;
import com.bos.cache.impl.MRUCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Created by 1328975 on 4/27/16.
 */
public class BOSCache implements Cache {
    enum CacheType {
        MRU,
        IDLE,
        NO_EXPIRY,
        RELATIVE
    }
    private final String name;
    private static Logger LOGGER = LoggerFactory.getLogger(BOSCache.class);
    private MRUCache<Object,Object> cache;

    public BOSCache(final String name,CacheType cacheType,int size, int timeToLiveSeconds ) {
        this.name = name;
        switch (cacheType) {
            case MRU:
                cache = new MRUCache<Object,Object>(size, new AgeExpiringFactory<Object,Object>(timeToLiveSeconds));
                break;
            case IDLE:
                cache = new MRUCache<Object,Object>(size, new IdleExpiringFactory<Object,Object>(timeToLiveSeconds));
                break;
            case NO_EXPIRY:
                cache = new MRUCache<Object,Object>(size, new NonExpiringFactory<Object,Object>());
                break;
        }
    }

    public BOSCache(final String name,CacheType cacheType,int size, int timeToLiveSeconds, int offsetTimeInSeconds ) {
        this.name = name;
        switch (cacheType) {
            case RELATIVE:
                cache = new MRUCache<Object,Object>(size, new RelativeExpiringFactory<Object,Object>(offsetTimeInSeconds, timeToLiveSeconds));
                break;
        }
    }

    public String getName() {
        return name;
    }

    public Object getNativeCache() {
        return cache;
    }

    public ValueWrapper get(final Object key) {
        Object value = null;
        try {
            value = cache.get(key);
        } catch (final Exception e) {
            LOGGER.warn("error getting key from cache:", e);
        }
        if (value == null) {
            return null;
        }
        return new SimpleValueWrapper(value);
    }

    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    public void put(final Object key, final Object value) {
        cache.put(key, value);
    }

    public ValueWrapper putIfAbsent(Object key, final Object value) {
        if (cache.get(key) == null) {
            cache.put(key, value);
        }
        return new ValueWrapper() {
            public Object get() {
                return value;
            }
        };
    }

    public void evict(final Object key) {
        this.cache.remove(key.toString());
    }

    public void clear() {
        cache.clear();
    }
}
