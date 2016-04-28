package com.bos.cache.factory.impl;

import com.bos.cache.impl.CacheData;
import com.bos.cache.mapentry.NonExpiringMapEntry;

/**
 * Create a non-expiring AGE_EXPIRY cache behavior
 * @author i0360b6
 * @param <K> the key type
 * @param <V> the value type
 */
public class NonExpiringFactory<K,V> extends CacheDataFactory<K,V> {

    public CacheData<K,V> createPair(final K key,final V value)
    {
        return new NonExpiringMapEntry<K,V>(key,value,this);
    }

    public CacheData<K,V> createPair(final K key,final int hash)
    {
        return new NonExpiringMapEntry<K,V>(key,hash,this);
    }

}

