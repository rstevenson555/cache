package com.bos.cache;

import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.factory.impl.IdleExpiringFactory;
import com.bos.cache.factory.impl.NonExpiringFactory;
import com.bos.cache.factory.impl.RelativeExpiringFactory;
import com.bos.cache.impl.MRUCache;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.logging.Logger;

/**
 * Created by 1328975 on 4/27/16.
 */
public class BOSCache implements Cache {
    public enum ExpiryType {
        AGE_EXPIRY,
        IDLE_EXPIRY,
        NO_EXPIRY,
        RELATIVE_EXPIRY
    }

    private final String name;
//    private static Logger LOGGER = LoggerFactory.getLogger(BOSCache.class);
    private MRUCache<Object, Object> cache;

    public BOSCache(final String name, ExpiryType expiryType, int size, long timeToLiveMillis) {
        this.name = name;
        switch (expiryType) {
            case AGE_EXPIRY:
                cache = new MRUCache<Object, Object>(size, new AgeExpiringFactory<Object, Object>(timeToLiveMillis));
                break;
            case IDLE_EXPIRY:
                cache = new MRUCache<Object, Object>(size, new IdleExpiringFactory<Object, Object>(timeToLiveMillis));
                break;
        }
    }

    public BOSCache(final String name, ExpiryType expiryType, int size, long timeToLiveMillis, long offsetTimeInMillis) {
        this.name = name;
        switch (expiryType) {
            case RELATIVE_EXPIRY:
                cache = new MRUCache<Object, Object>(size, new RelativeExpiringFactory<Object, Object>(offsetTimeInMillis, timeToLiveMillis));
                break;
        }
    }

    public BOSCache(final String name, ExpiryType expiryType, int size) {
        this.name = name;
        switch (expiryType) {
            case NO_EXPIRY:
                cache = new MRUCache<Object, Object>(size, new NonExpiringFactory<Object, Object>());
                break;
        }
    }
    
    public void setCacheDelegate(CacheDelegate cacheDelegate) {
        cache.setCacheDelegate(cacheDelegate);
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
//            LOGGER.warn("error getting key from cache:", e);
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
