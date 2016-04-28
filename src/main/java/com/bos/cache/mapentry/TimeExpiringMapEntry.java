package com.bos.cache.mapentry;

import com.bos.cache.CacheDelegate;
import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.factory.impl.TimeExpiringFactory;
import com.bos.cache.impl.CacheData;

/**
 * Created by 1328975 on 4/27/16.
 */
public class TimeExpiringMapEntry<K,V> extends CacheData<K, V> {
    transient protected TimeExpiringFactory<K,V> factory;

    public TimeExpiringMapEntry(K key, V value,TimeExpiringFactory<K,V> factory) {
        super(key, value);
        this.factory = factory;
    }

    public TimeExpiringMapEntry(K key, int hash,TimeExpiringFactory<K,V> factory) {
        super(key, hash);
        this.factory = factory;
    }

    protected TimeExpiringFactory<K,V> getFactory() {
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
    public void reset() {}

    /**
     * compare the key's to see if the key provided matches this objects key
     * @param theKey the key to validate
     * @return returns the value matched or null if it is deternined to be not matched
     */
    public V validateKey(K theKey,CacheDelegate dele) {

        if (key.equals(theKey)) {
            if (dele !=null) dele.keyMatched();
            return value;
        }
        return null;
    }
}
