package com.bos.cache.mapentry;

import com.bos.cache.CacheDelegate;
import com.bos.cache.factory.impl.RelativeExpiringFactory;
import com.bos.cache.factory.impl.TimeExpiringFactory;

/**
 * Defines a class to store a Key/value pair that is valid from a relative fixed time
 * For example you could use this 15 minute offsets from 12:00:00 am; if you want something
 * to happen on the quarter hour.  Another example application is use it to expire
 * something relative to a system event.
 *
 * @param <K>
 * @param <V>
 * @author i0360b6
 */
public class RelativeExpiringMapEntry<K, V> extends TimeExpiringMapEntry<K, V> implements Cloneable {
    private long offsetTime = 0L;

    public RelativeExpiringMapEntry(K key, V value, TimeExpiringFactory<K, V> factory) {
        super(key, value, factory);
        long now = System.currentTimeMillis();
        long otime = ((RelativeExpiringFactory<K, V>) factory).getOffsetTimeInMillis();
        long maxAge = getFactory().getTimeInMillis();
        offsetTime = (now - otime) / maxAge;
    }

    public RelativeExpiringMapEntry(K key, int hash, TimeExpiringFactory<K, V> factory) {
        super(key, hash, factory);
        long now = System.currentTimeMillis();
        long otime = ((RelativeExpiringFactory<K, V>) factory).getOffsetTimeInMillis();
        long maxAge = getFactory().getTimeInMillis();
        offsetTime = (now - otime) / maxAge;
    }

    /**
     * reset the offset, this can be used as a trigger from
     * a system event occurring
     */
    @Override
    public void reset() {
        long now = System.currentTimeMillis();
        long otime = ((RelativeExpiringFactory<K, V>) factory).getOffsetTimeInMillis();
        long maxAge = factory.getTimeInMillis();
        offsetTime = (now - otime) / maxAge;
    }

    /**
     * @param theKey the key to validate
     * @return Returns null if the Key is NOT valid otherwise returns the value
     */
    @Override
    final public V validateKey(final Object theKey, final CacheDelegate cacheDelegate) {
        V aResult = null;

        if (key.equals(theKey)) {
            if (cacheDelegate != null) {
                cacheDelegate.keyMatched(theKey);
            }
            long now = System.currentTimeMillis();
            long otime = ((RelativeExpiringFactory<K, V>) factory).getOffsetTimeInMillis();
            long maxAge = getFactory().getTimeInMillis();
            long noffsetTime = (now - otime) / maxAge;
            if (noffsetTime <= offsetTime) {
                aResult = value;
            } else {
                if (cacheDelegate != null) {
                    cacheDelegate.keyExpired(theKey);
                }
            }
            return aResult;
        } 
        return null;
    }

}


