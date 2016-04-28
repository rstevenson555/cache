package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.CacheDelegate;
import com.bos.cache.factory.impl.TimeExpiringFactory;

import java.util.Date;

public class AgeExpiringMapEntry<K, V> extends TimeExpiringMapEntry<K,V> implements Cloneable
{
    private long birthTime;

    public AgeExpiringMapEntry(K theKey,V value,TimeExpiringFactory<K,V> factory)
    {
        super(theKey,value,factory);
	    birthTime = System.currentTimeMillis();
    }

    public AgeExpiringMapEntry(K theKey,int hash,TimeExpiringFactory<K,V> factory)
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
    final public V validateKey(final Object theKey,final CacheDelegate delegate)
    {
        V aResult = null;
        if ( key.equals(theKey))  {
            if ( delegate != null) delegate.keyMatched(theKey);
            long death = birthTime + getFactory().getTimeInMillis();
            if ( System.currentTimeMillis() < death) {
                aResult = value;
            } else {
                if ( delegate != null) delegate.keyExpired(theKey);
            }
            return aResult;
        }
        return null;
    }

}


