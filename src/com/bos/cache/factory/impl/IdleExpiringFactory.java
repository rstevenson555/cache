package com.bos.cache.factory.impl;

import com.bos.cache.data.CacheData;
import com.bos.cache.factory.CacheDataFactory;
import com.bos.cache.map.IdleExpiringMapEntry;

/**
 * Create a idle expiring behavior for the cache, this cache will automatically
 * invalidate an object if the specified time expires from when the object
 * was last touched
 * @author i0360b6
 * @param <K>
 * @param <V>
 */
public class IdleExpiringFactory<K,V> extends CacheDataFactory<K,V> {

        // default 1 minute
    private long staleObjectTimeInMillis = ((60 * 1000) * 30);          // how long before object becomes stale

    /**
     * Create a idle expiring behavior
     * @param milliseconds
     */
    public IdleExpiringFactory(long milliseconds)
    {
        this.staleObjectTimeInMillis = milliseconds;
    }
    
    public CacheData<K,V> createPair(K key,V value)
    {
        return new IdleExpiringMapEntry<K,V>(key,value, this);
    }
    
    /**
     * gets the period that an object is considered
     * to be 'stale' or out of date
     **/
    public long getStaleObjectTimeInMillis() {
        return staleObjectTimeInMillis;
    }

}

