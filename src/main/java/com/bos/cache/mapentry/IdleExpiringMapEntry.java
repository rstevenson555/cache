package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.TimeExpiringFactory;
import com.bos.cache.CacheDelegate;

public class IdleExpiringMapEntry<K, V> extends TimeExpiringMapEntry<K, V> {

    volatile private long idleTime;

    public IdleExpiringMapEntry(K key, V value, TimeExpiringFactory<K,V> factory) {
        super(key, value,factory);
        
	    idleTime = System.currentTimeMillis();
    }

    public IdleExpiringMapEntry(K key, int hash, TimeExpiringFactory<K,V> factory) {
        super(key, hash,factory);

	    idleTime = System.currentTimeMillis();
    }


    @Override
    public void reset()
    {
	    idleTime = System.currentTimeMillis();
    }

    /**
     *
     * returns how long this data has been idle
     **/
    final long getIdleTime() {
        return System.currentTimeMillis() - idleTime;
    }

    /**
     * updates the timestamp for the last time this data
     * element was touched
     **/
    void touch() {
        idleTime = System.currentTimeMillis();
    }

    /**
     * touches the last update time
     * and returns the data
     **/
    @Override
    public V getValue() {
        return getValueTouch(true);
    }

    /**
     * modifies the last touched timetime
     * and returns the data assosicated
     **/
    private V getValueTouch(boolean touch) {
        if (touch) {
            touch();
        }
        return value;
    }

    /**
     * validateKey the key that is passed in else return null if no validateKey
     * @param theKey the key to validateKey against
     * @return <V>
     */
	@Override
    final public V validateKey(final Object theKey,final CacheDelegate cacheDelegate) {
        V aResult = null;

        if (key.equals(theKey)) {
            if ( cacheDelegate != null) cacheDelegate.keyMatched(theKey);
            long itime = getIdleTime();
            if ( itime < getFactory().getExpireTimeMillis())  { // ?? minutes of inactivity, so get rid of it
                aResult = getValueTouch(true);
            } else {
                if ( cacheDelegate != null) cacheDelegate.keyExpired(theKey);
            }
            return aResult;
        } 
        return null;
    }

    /**
     * Overrride setValue so we can touch the value mark it as modified
     * @param theValue the value to store
     * @return
     */
	@Override
    public V setValue(V theValue) {
        value = theValue;
        return getValueTouch(true);
    }
}
    

