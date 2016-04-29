package com.bos.cache.factory.impl;

/**
 * Base class for factories that have time expiring behavior for the cache
 * @author i0360b6
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract class TimeExpiringFactory<K,V> extends CacheDataFactory<K,V> {

        // default 1 minute
    private long expireTimeMillis = ((60 * 1000) * 30);          // how long before object becomes stale

    /**
     * Create a idle expiring behavior
     * @param milliseconds
     */
    public TimeExpiringFactory(long milliseconds)
    {
        this.expireTimeMillis = milliseconds;
    }

    /**
     * gets the period that an object is considered
     * to be 'stale' or out of date
     **/
    public long getExpireTimeMillis() {
        return expireTimeMillis;
    }

}

