package com.bos.cache.factory.impl;

import com.bos.cache.impl.CacheData;
import com.bos.cache.mapentry.RelativeExpiringMapEntry;

/**
 * Defines a class used to create a expiring Key/value pair that is valid from a relative fixed time
 * For example you could use this 15 minute offsets from 12:00:00 am; if you want something
 * to happen on the quarter hour.  Another example application is use it to expire
 * something relative to a system event.
 * @author i0360b6
 * @param <K> the key type
 * @param <V> the value type
 */
public class RelativeExpiringFactory<K,V> extends TimeExpiringFactory<K,V> {

    private long offsetTimeInMillis = 0L;

    /**
     * Create a age expiring behavior, from it's creation time
     * @param liveuntilinmillis specify the age at which the object will expire
     */
    public RelativeExpiringFactory(long offsettimeinmillis,long liveuntilinmillis)
    {
        super(liveuntilinmillis);
	    offsetTimeInMillis = offsettimeinmillis;
    }

    public long getOffsetTimeInMillis()
    {
        return offsetTimeInMillis;
    }

    @Override
    public CacheData<K,V> createPair(K key,V value)
    {
        return new RelativeExpiringMapEntry<K,V>(key,value, this);
    }

    @Override
    public CacheData<K,V> createPair(K key,int hash)
    {
        return new RelativeExpiringMapEntry<K,V>(key,hash, this);
    }

}

