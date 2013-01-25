package com.bos.cache.map;

import com.bos.cache.datavalue.AgedValue;
import com.bos.cache.factory.impl.AgeExpiringFactory;

public class AgeExpiringMapEntry<K, V> extends IdleExpiringMapEntry<K,V> implements Cloneable
{
    public AgeExpiringMapEntry(K key,V value,AgeExpiringFactory factory)
    {
        super(key,value,factory);
    }

    @Override
    public V validateKey(K key)
    {
        V aResult = null;
        long now = System.currentTimeMillis();

        if ( getContainerValue()==null)
            return aResult;

        if ( getKey().equals(key))  {
            long btime =((AgedValue<V>)getContainerValue()).getBirthTime();
            long death = btime + (getFactory().getStaleObjectTimeInMillis());
            if ( now < death) {
                aResult =  getContainerValue().getValue();
            }
            return aResult;
        }
        return aResult;
    }

}

