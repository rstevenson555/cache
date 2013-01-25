package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.factory.impl.RelativeExpiringFactory;
import com.bos.cache.CacheDelegate;

/**
 * Defines a class to store a Key/value pair that is valid from a relative fixed time
 * For example you could use this 15 minute offsets from 12:00:00 am; if you want something
 * to happen on the quarter hour.  Another example application is use it to expire
 * something relative to a system event.
 * @author i0360b6
 * @param <K>
 * @param <V>
 */
public class RelativeExpiringMapEntry<K, V> extends NonExpiringMapEntry<K,V> implements Cloneable
{
    private long offsetTime = 0L;

    public RelativeExpiringMapEntry(K key,V value,CacheDataFactory<K,V> factory)
    {
        super(key,value,factory);
	    long now = System.currentTimeMillis();
	    long otime =((RelativeExpiringFactory<K,V>)factory).getOffsetTimeInMillis();
	    long maxAge =((RelativeExpiringFactory<K,V>)factory).getTimeInMillis();
	    offsetTime = (now - otime )/ maxAge;
    }

    public RelativeExpiringMapEntry(K key,int hash,CacheDataFactory<K,V> factory)
    {
        super(key,hash,factory);
	    long now = System.currentTimeMillis();
	    long otime =((RelativeExpiringFactory<K,V>)factory).getOffsetTimeInMillis();
	    long maxAge =((RelativeExpiringFactory<K,V>)factory).getTimeInMillis();
	    offsetTime = (now - otime ) / maxAge;
    }

    /**
     * reset the offset, this can be used as a trigger from
     * a system event occurring
     */
    @Override
    public void reset()
    {
	    long now = System.currentTimeMillis();
	    long otime =((RelativeExpiringFactory<K,V>)factory).getOffsetTimeInMillis();
	    long maxAge =((RelativeExpiringFactory<K,V>)factory).getTimeInMillis();
	    offsetTime = (now - otime ) / maxAge;
    }

    /**
     * @param theKey the key to validate
     * @return Returns null if the Key is NOT valid otherwise returns the value
     */
    @Override
    final public V validateKey(K theKey,CacheDelegate dele)
    {
        V aResult = null;

        if ( key.equals(theKey))  {
            if ( dele !=null) dele.keyMatched();
            long now = System.currentTimeMillis();
	        long otime =((RelativeExpiringFactory<K,V>)factory).getOffsetTimeInMillis();
	        long maxAge =((RelativeExpiringFactory<K,V>)factory).getTimeInMillis();
	        long noffsetTime =  (now - otime ) / maxAge;
            if ( noffsetTime <= offsetTime) {
                aResult = value;
            } else {
                if (dele !=null) dele.keyExpired();
            }
            return aResult;
        } else {
        }
        return aResult;
    }

}


