package com.bos.cache.map;

import com.bos.cache.datavalue.AgedValue;
import com.bos.cache.factory.impl.IdleExpiringFactory;

public class IdleExpiringMapEntry<K, V> extends NonExpiringMapEntry<K, V> {

    transient private IdleExpiringFactory factory;

    public IdleExpiringMapEntry(K key, V value, IdleExpiringFactory factory) {
        super(key, new AgedValue<V>(value));
        this.factory = factory;
    }

    protected IdleExpiringFactory getFactory() {
        return factory;
    }

    /**
     * validateKey the key that is passed in else return null if no validateKey
     * @param key the key to validateKey against
     * @return
     */
	@Override
    public V validateKey(K key) {
        V aResult = null;

        if ( getContainerValue()==null)
            return aResult;

        if (getKey().equals(key)) {
            long itime =((AgedValue<V>)getContainerValue()).getIdleTime();
            if (itime < (factory.getStaleObjectTimeInMillis()) ) { // ?? minutes of inactivity, so get rid of it
                aResult = getContainerValue().getValue();
            }
            return aResult;
        }
        return aResult;
    }

	@Override
    public V setValue(V value) {
        super.setValue(new AgedValue<V>(value));
        return getContainerValue().getValue();
    }
}
    