/**
 * This class contains the concrete implementation of the Most Recently Used Cache
 * The buckets is created from the Factory method createExpiringCache
 **/
package com.bos.cache.impl;

import com.bos.cache.Cache;
import com.bos.cache.data.CacheData;
import com.bos.cache.factory.CacheDataFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This is a implementation of a very fast MRU buckets, the size of the buckets
 * is a fixed size, so we don't have to deal with growing
 * and shrinking of the buckets
 * The Cache will tend to have items in it that are most often used and will
 * throw out the Least Used Items
 **/
public class MRUCache<K,V> implements Cache<K,V> {

    // the initial size is 23 (prime) but you should change this to
    // fit your needs
    private CacheData<K,V> buckets[] = null;
    private CacheDataFactory factory = null;

    protected CacheDataFactory getFactory() {
        return factory;
    }

    /**
     * Create a new buckets the size defaults to 23
     * @param fact
     */
    public MRUCache(CacheDataFactory fact) {
        factory = fact;

        buckets = new CacheData[23];
        for (int i = 0; i < 23; i++) {
            buckets[i] = null;
        }
    }

    /**
     * Contructor initialize the buckets to the specified size
     * @param size, the number of buckets entries that the buckets can maintain, should be a prime number
     **/
    public MRUCache(int size, CacheDataFactory fact) {
        this(fact);
        buckets = new CacheData[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = null;
        }

    }

    /**
     * returns a buckets key based on it's position in the buckets
     * @return the Object placed at pos
     **/
    Object get(int pos) {
        /*CacheData pair = null;
        if ((pair = buckets[Math.abs(key) % buckets.length]) != null) {
            return pair.getContainerValue().getValue();
        }
        return null; */
        return buckets[pos];
    }

    /**
     * @param pos to element to remove
     **/
    void remove(int pos) {
        buckets[pos]= null;
    }

    /**
     * returns the size of the mru buckets table
     **/
    public int size() {
        return buckets.length;
    }

    /**
     * Looks up an object and returns the value object found or null if the key is not found
     * @param key the value to look for
     **/
    public V get(Object key) {
        CacheData<K,V> pair = null;
        V value = null;

        if (key == null) {
            return null;
        }

        // validateKey the key in the buckets

        // synchronization is not a problem here
        // if someone changes or resets a key/value right before this
        // it will be properly handled, either it won't find a validateKey in the if or the
        // obj being returned will be null, that is fine and expected for a no-validateKey
        // then the client will just validateKey whatever data he was looking for in the buckets

        if ((pair = buckets[Math.abs(key.hashCode()) % buckets.length]) != null) {
            // see if the key passed in matches this one
            if ((value = pair.validateKey((K)key)) != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * clear resets any buckets entries to null
     **/
    public void clear() {
        // synchronization is not a problem here
        // if this code were to be called while someone was getting or setting data
        // in the buckets, it would still work
        // in the case of someone setting a value in the buckets, then this coming along
        // and resetting the buckets-entry to null is fine, because the client still has the data reference
        // in the case of someone getting a value from the buckets while this is being reset
        // is fine as well because, if they get the data before it's set, they got it
        // if they as for it after it gets set to null, that's fine as well it's null and
        // that's a normal expected condition
        for (int i = 0, tot = buckets.length; i < tot; i++) {
            buckets[i] = null;
        }
    }

    /**
     * returns a iterator, to loop over all java.util.Map.Entry's in the buckets
     **/
    public Iterator iterator() {
        return new CacheIterator(this);
    }

    /**
     * remove a reference to the given key
     * @param the key to validateKey and remove
     **/
    public V remove(Object key) {
        if (key == null) {
            return null;
        }

        // find the place to put it and stuff it in, overwriting what was
        // previously there
        // synchronization is not needed here, if this value changes to null
        // either before or after a client looks at it, it's not a problem
        int pos = Math.abs(key.hashCode()) % buckets.length;
        V mvalue = (V)get(pos);
        buckets[pos]= null;
        return mvalue;
    }

    /**
     * Updates the buckets with the key and value provided
     * @param key the key value used to validateKey this value
     * @param value the value object associated with the given key
     **/
    public V put(K key, V value) {
        if (key == null) {
            return null;
        }

        // find the place to put it and stuff it in, overwriting what was
        // previously there
        int pos = Math.abs(key.hashCode()) % buckets.length;
        V pvalue = (V)get(pos);
        buckets[pos]= factory.createPair(key, value);
        return pvalue;
    }

    /**
     * returns the set of keys
     * @return Set the set of keys for this buckets
     **/
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<K>();

        for (CacheData<K,V> pair : buckets) {
            if (pair != null) {
                if ( pair.validateKey()!=null)
                    set.add(pair.getKey());
            }
        }
        return set;
    }

    /**
     * returns the set of keys
     * @return Set the set of keys for this buckets
     **/
    public Set<Map.Entry<K,V>> entrySet() {
        TreeSet<Map.Entry<K,V>> set = new TreeSet<Map.Entry<K,V>>();

        for (CacheData<K,V> pair : buckets) {
            if (pair != null) {
                if ( pair.validateKey()!=null)
                    set.add(pair);
            }
        }
        return set;
    }

    /**
     * gets a collections of all the values in the cache
     * @return
     */
    public Collection<V> values() {
        // We don't bother overriding many of the optional methods, as doing so
        // wouldn't provide any significant performance advantage.
        final Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        ArrayList values = new ArrayList();
        while (it.hasNext()) {
            //final Map.Entry e = it.next();
            final CacheData<K,V> pair = (CacheData<K,V>)it.next();
            // Optimize in case the Entry is one of our own.
            if ( pair !=null) {
                if ( pair.validateKey()!=null)
                    values.add(pair.getContainerValue().getValue());
            }
        }
        return values;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        final Map<K,V> addMap = (Map<K,V>) m;
        final Iterator<Map.Entry<K,V>> it = addMap.entrySet().iterator();
        while (it.hasNext()) {
            final CacheData<K,V> e = (CacheData<K,V>)it.next();
            // Optimize in case the Entry is one of our own.
            put(e.getKey(), e.getContainerValue().getValue());
        }
    }

    /**
     * scan through the cache to see if the specified value exists or not
     * @param value
     * @return true if the value is contained in the cache
     */
    public boolean containsValue(Object value) {
        final Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        while (it.hasNext()) {
            final CacheData<K,V> pair = (CacheData<K,V>)it.next();
            // Optimize in case the Entry is one of our own.
            if ( pair.getContainerValue().getValue().equals(value) && pair.validateKey()!=null)
                return true;
        }
        return false;
    }

    /**
     * scan through the cache to see if the specified key exists or not
     * @param key
     * @return true if the key is contained in the cache
     */
    public boolean containsKey(Object key) {
        final Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        while (it.hasNext()) {
            final CacheData<K,V> pair = (CacheData<K,V>)it.next();
            // Optimize in case the Entry is one of our own.
            if (pair.validateKey((K)key)!=null)
                return true;
        }
        return false;
    }

    public boolean isEmpty()
    {
        for(int i =0,tot=buckets.length;i<tot;i++) {
            if (buckets[i]!=null)
                return false;
        }
        return true;
    }
    /**
     * Returns a shallow clone of this HashMap. The Map itself is cloned,
     * but its contents are not.  This is O(n).
     *
     * @return the clone
     */
    @Override
    public Object clone() {
        MRUCache<K,V> copy = null;
        try {
            copy = (MRUCache<K,V>) super.clone();
        } catch (CloneNotSupportedException x) {
            // This is impossible.
        }
        final Iterator<Map.Entry<K,V>> it = entrySet().iterator();
        int next = 0;
        while (it.hasNext()) {
            final Map.Entry pair = it.next();
            // Optimize in case the Entry is one of our own.
            copy.buckets[next++] =factory.createPair(pair.getKey(), pair.getValue());
        }
        return copy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.omx.buckets.Cache#getKeys()
     */
    public ArrayList getKeys() {
        ArrayList allKeys = new ArrayList(keySet());

        /*if (isDebug()) {
        logDebug("Key Count: " + allKeys.size());
        } */
        return allKeys;
    }
}
