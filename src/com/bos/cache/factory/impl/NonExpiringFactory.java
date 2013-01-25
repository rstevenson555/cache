package com.bos.cache.factory.impl;

import com.bos.cache.data.CacheData;
import com.bos.cache.factory.CacheDataFactory;
import com.bos.cache.map.NonExpiringMapEntry;

/**
 * Create a non-expiring MRU cache behavior
 * @author i0360b6
 * @param <K>
 * @param <V>
 */
public class NonExpiringFactory<K,V> extends CacheDataFactory<K,V> {

    public CacheData<K,V> createPair(K key,V value)
    {
        return new NonExpiringMapEntry<K,V>(key,value);
    }
}

