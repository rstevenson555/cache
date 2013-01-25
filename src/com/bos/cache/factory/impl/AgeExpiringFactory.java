package com.bos.cache.factory.impl;

import com.bos.cache.data.CacheData;
import com.bos.cache.map.AgeExpiringMapEntry;

/**
 * Create a age expiring behavior for the cache, this cache will automatically
 * invalidate an object if the specified time expires from when the object
 * was put into the cache
 * @author i0360b6
 * @param <K>
 * @param <V>
 */
public class AgeExpiringFactory<K,V> extends IdleExpiringFactory<K,V> {

    /**
     * Create a age expiring behavior
     * @param milliseconds
     */
    public AgeExpiringFactory(long milliseconds)
    {
        super(milliseconds);
    }

    @Override
    public CacheData<K,V> createPair(K key,V value)
    {
        return new AgeExpiringMapEntry<K,V>(key,value, this);
    }

}

