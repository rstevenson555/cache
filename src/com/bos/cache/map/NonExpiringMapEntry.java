package com.bos.cache.map;

import com.bos.cache.datavalue.Value;
import com.bos.cache.data.CacheData;

public class NonExpiringMapEntry<K, V> extends CacheData<K, V> {

    public NonExpiringMapEntry(K key, Value<V> value) {
        super(key,value);
    }
    public NonExpiringMapEntry(K key, V value) {
        super(key, new Value<V>(value));
    }

    public V validateKey() {
        return validateKey(getKey());
    }
    public V validateKey(K key) {
        if ( getContainerValue()==null)
            return null;

        if (getKey().equals(key)) {
            return getContainerValue().getValue();
        }
        return null;
    }

    public V setValue(V value) {
        super.setValue(new Value<V>(value));
        return getContainerValue().getValue();
    }
}
    
