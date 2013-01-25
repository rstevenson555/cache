/* Generated by Together */
package com.bos.cache.impl;

import com.bos.cache.CacheFactory;
import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.factory.impl.IdleExpiringFactory;
import com.bos.cache.factory.impl.NonExpiringFactory;
import com.bos.cache.factory.impl.RelativeExpiringFactory;
import java.util.Map;

/**
 * This is the factory used to createPair instances of the MRU(Most recently used) cache
 * and the expiring cache
 * To use first createPair an instance of the factory with newInstance, then createPair the
 * cache from that instance
 **/
public class CacheFactoryImpl extends CacheFactory {

    public static CacheFactory newInstance() {
        return new CacheFactoryImpl();
    }

    /**
     * creates a MRUCache with the size specified
     * @param size how many cache entries can be held, should be a <b>prime number</b>
     **/
    public Map createMRUCache(int size) {
        return new MRUCache(size, new NonExpiringFactory());
    }

    public Map createIdleExpiringMRUCache(int size,long milliseconds ) {
        return new MRUCache(size,new IdleExpiringFactory( milliseconds ));
    }

    public Map createAgeExpiringMRUCache(int size,long milliseconds ) {
        return new MRUCache(size,new AgeExpiringFactory(milliseconds ));
    }

    public Map createRelativeExpiringMRUCache(int size,long relativetime,long milliseconds ) {
        return new MRUCache(size,new RelativeExpiringFactory(relativetime,milliseconds ));
    }

    public Map createIdleExpiringHashMap(int size,long milliseconds ) {
        return new ExpiringHashMap(size,new IdleExpiringFactory(milliseconds ));
    }

    public Map createAgeExpiringHashMap(int size,long milliseconds ) {
        return new ExpiringHashMap(size,new AgeExpiringFactory(milliseconds ));
    }

    public Map createRelativeExpiringHashMap(int size,long relativetime,long milliseconds ) {
        return new ExpiringHashMap(size,new RelativeExpiringFactory(relativetime,milliseconds ));
    }
}
