package com.bos.cache.factory.impl;

import com.bos.cache.impl.CacheData;
import com.bos.cache.mapentry.AgeExpiringMapEntry;

/**
 * Create a age expiring behavior for the cache, this cache will automatically
 * invalidate an object if the specified time expires from when the object
 * was put into the cache
 * @author i0360b6
 * @param <K> the key type
 * @param <V> the value type
 */
public class AgeExpiringFactory<K,V> extends TimeExpiringFactory<K,V> {

    /**
     * Create a age expiring behavior, from it's creation time
     * @param liveuntilinmillis specify the age at which the object will expire
     */
    public AgeExpiringFactory(long liveuntilinmillis)
    {
        super(liveuntilinmillis);
    }

    /**
     * creates a pair that is age expiring
     * @param key
     * @param value
     * @return CacheData<K,V>
     */
    @Override
    public CacheData<K,V> createPair(final K key,final V value)
    {
        return new AgeExpiringMapEntry<K,V>(key,value, this);
    }

    /**
     * creates a pair that is age expiring
     * @param key
     * @param hash
     * @return CacheData<K,V>
     */
    @Override
    public CacheData<K,V> createPair(final K key,final int hash)
    {
        return new AgeExpiringMapEntry<K,V>(key,hash, this);
    }

}

