package com.bos.cache.factory.impl;

import com.bos.cache.impl.CacheData;
import com.bos.cache.mapentry.IdleExpiringMapEntry;

/**
 * Create a age expiring behavior for the cache, this cache will automatically
 * invalidate an object if the specified time expires from when the object
 * was put into the cache
 * @author i0360b6
 * @param <K> the key type
 * @param <V> the value type
 */
public class IdleExpiringFactory<K,V> extends TimeExpiringFactory<K,V> {

    /**
     * Create a idle expiring object
     * @param idletimeinmillis specify how long the object needs to be idle before
     * it can be expired
     */
    public IdleExpiringFactory(long idletimeinmillis)
    {
        super(idletimeinmillis);
    }

    /**
     * defines the createpair for creating idle expiring map entries
     * @param key
     * @param value
     * @return returns the CacheData pair object
     */
    @Override
    public CacheData<K,V> createPair(K key,V value)
    {
        return new IdleExpiringMapEntry<K,V>(key,value, this);
    }

    /**
     * defines the createpair for creating idle expiring map entries
     * @param key
     * @param hash
     * @return returns the CacheData pair object
     */
    @Override
    public CacheData<K,V> createPair(K key,int hash)
    {
        return new IdleExpiringMapEntry<K,V>(key,hash, this);
    }

}

