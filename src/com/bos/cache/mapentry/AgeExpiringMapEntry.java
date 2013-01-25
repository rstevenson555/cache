package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.CacheDelegate;

public class AgeExpiringMapEntry<K, V> extends NonExpiringMapEntry<K,V> implements Cloneable
{
    private long birthTime;

    public AgeExpiringMapEntry(K theKey,V value,CacheDataFactory<K,V> factory)
    {
        super(theKey,value,factory);
	    birthTime = System.currentTimeMillis();
    }

    public AgeExpiringMapEntry(K theKey,int hash,CacheDataFactory<K,V> factory)
    {
        super(theKey,hash,factory);
	    birthTime = System.currentTimeMillis();
    }

    @Override
    public void reset()
    {
       birthTime = System.currentTimeMillis();
    }

    /**
     * returns when this object was
     * born or (created)
     **/
    public final long getBirthTime()
    {
        return birthTime;
    }

    /**
     * Returns null if the Key is NOT valid otherwise returns the value
     * @param theKey the key to validate
     * @return <V>
     */
    @Override
    final public V validateKey(K theKey,CacheDelegate dele)
    {
        V aResult = null;
        if ( key.equals(theKey))  {
            if ( dele != null) dele.keyMatched();
            long death = birthTime + ( ((AgeExpiringFactory<K,V>)factory).getTimeInMillis());
            if ( System.currentTimeMillis() < death) {
                aResult = value;
            } else {
                if ( dele != null) dele.keyExpired();
            }
            return aResult;
        } else {
        }
        return aResult;
    }

}


