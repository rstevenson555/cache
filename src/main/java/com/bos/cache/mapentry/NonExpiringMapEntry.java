package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.CacheDelegate;

/**
 * defines a class which stores data that does not expire based on time
 * @author i0360b6
 * @param <K>
 * @param <V>
 */
public class NonExpiringMapEntry<K, V> extends CacheData<K, V> {
    transient protected CacheDataFactory<K,V> factory;

    public NonExpiringMapEntry(K key, V value,CacheDataFactory<K,V> factory) {
        super(key, value);
	    this.factory = factory;
    }

    public NonExpiringMapEntry(K key, int hash,CacheDataFactory<K,V> factory) {
        super(key, hash);
	    this.factory = factory;
    }

    protected CacheDataFactory<K,V> getFactory() {
        return factory;
    }

    /**
     * validates this key to see if its value
     * @return returns the value matched or null if it is determined to not matched
     */
    public V validateKey() {
        return validateKey(key,null);
    }

    /**
     * reset does nothing for non expiring
     */
    public void reset() {
    }

    /**
     * compare the key's to see if the key provided matches this objects key
     * @param theKey the key to validate
     * @return returns the value matched or null if it is deternined to be not matched
     */
    public V validateKey(final Object theKey,final CacheDelegate cacheDelegate) {

        if (key.equals(theKey)) {
            if (cacheDelegate !=null) cacheDelegate.keyMatched(theKey);
            return value;
        }
        return null;
    }

}
    
