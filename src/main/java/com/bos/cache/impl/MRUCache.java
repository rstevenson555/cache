/**
 * This class contains the concrete implementation of the Most Recently Used Cache The buckets is created from the
 * Factory method createExpiringCache
 */
package com.bos.cache.impl;

import com.bos.cache.CacheDelegate;
import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.mapentry.CacheData;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is a implementation of a very fast AGE_EXPIRY list, the size of the buckets are a fixed size, so we don't have to deal
 * with growing and shrinking of the buckets the Cache will tend to have items in it that are most often used and will
 * throw out the Least Used Items. The size of the cache should be a primary number. This helps to optimize the
 * distribution of keys to avoid collisions. This class implements the Map interface so can be used interchangable with
 * HashMap's etc.
 */
public class MRUCache<K, V> implements Map<K, V>, Cloneable {

    // the initial size is 23 (prime) but you should change this to
    // fit your needs
    private static final int DEFAULTSIZE = 23;
    private AtomicReference<CacheData<K, V>> buckets[] = null;
    private CacheDataFactory<K, V> factory = null;
    private AbstractSet<K> keySet;
    private AbstractCollection<V> valuesCollection = null;
    private CacheDelegate cacheDelegate = null;

    /**
     * @return Returns the Factory associated to this cache
     */
    protected CacheDataFactory<K, V> getFactory() {
        return factory;
    }

    /**
     * Copies all the mappings in the specified map to this map. These mappings will replace all mappings that this map
     * had for any of the keys currently in the given map.
     *
     * @param map the map to copy mappings from.
     * @throws NullPointerException if {@code map} is {@code null}.
     */
    public MRUCache(Map<? extends K, ? extends V> map, CacheDataFactory<K, V> fact) {
        this(map.size(), fact);
        putAllImpl(map);
    }

    /**
     * Create a new buckets the size defaults to 23
     *
     * @param fact the specific factory is provided
     */
    public MRUCache(CacheDataFactory<K, V> fact) {
        this(DEFAULTSIZE, fact);
    }

    /**
     * Contructor initialize the buckets to the specified size
     *
     * @param size the number of buckets entries that the buckets can maintain, should be a prime number
     * @param fact the factory to use with this cache
     */
    public MRUCache(int size, CacheDataFactory<K, V> fact) {
        factory = fact;
        buckets = new AtomicReference[nextPrime(size)];
        for (int i = 0, tot = buckets.length; i < tot; i++) {
            buckets[i] = new AtomicReference<CacheData<K, V>>(null);
        }
    }

    /**
     * this cache works best using a prime number as the size of the elements,
     * it helps give a better disribution of keys; helps avoid collisions....
     * this calculates the next higher prime number from the cache size provided
     *
     * @param n
     * @return
     */
    public static int nextPrime(int n) {
        boolean isPrime = false;
        int start = 2; // start at 2 and omit your if statement

        while (!isPrime) {
            // always incrememnt n at the beginning to check a new number
            n += 1;
            // redefine max boundary here
            int m = (int) Math.ceil(Math.sqrt(n));

            isPrime = true;
            // increment i by 1, not 2 (you're skipping numbers...)
            for (int i = start; i <= m; i++) {
                if (n % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        return n;
    }

    public void setCacheDelegate(CacheDelegate cacheDelegate) {
        this.cacheDelegate = cacheDelegate;
    }

    /**
     * check to see if the key is expired
     *
     * @param key
     * @return boolean true if the key is expired else false; returns false if key is null also
     */
    public boolean isExpired(Object key) {
        if (key == null) {
            return false;
        }

        CacheData<K, V> cacheData = getEntry(key);
        if (cacheData != null) {
            // see if the key passed in matches this one
            if (cacheData.validateKey(key, cacheDelegate) == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Looks up an object and returns the value object found or null if the key is not found
     *
     * @param key the value to look for
     * @return returns the Value for the Key provided or null if a match is not found
     */
    public V get(Object key) {
        if (cacheDelegate != null) {
            cacheDelegate.keyLookup(key);
        }

        if (key == null) {
            return null;
        }

        V value;
        CacheData<K, V> cacheData = getEntry(key);
        if (cacheData != null) {
            // see if the key passed in matches this one
            if ((value = cacheData.validateKey(key, cacheDelegate)) != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * return the position of the key by doing the calculation
     * guarantted to fall within the bounds of the array
     *
     * @param key
     * @return
     */
    final int getKeyPosition(final Object key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    /**
     * returns the CachData pair for the specified key
     *
     * @param key
     * @return a CacheData pair is returned or null if no match
     */
    final CacheData<K, V> getEntry(final Object key) {
        // validateKey the key in the buckets

        // synchronization is not a problem here
        // if someone changes or resets a key/value right before this
        // it will be properly handled, either it won't find a validateKey in the if or the
        // obj being returned will be null, that is fine and expected for a no-validateKey
        // then the client will just validateKey whatever data he was looking for in the buckets
        return getCacheData(getKeyPosition(key));
    }

    /**
     * clear resets any buckets entries to null, the size of the cache remains fixed and constant
     */
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
            buckets[i] = new AtomicReference<CacheData<K, V>>(null);
        }
    }

    /**
     * remove a reference to the given key
     *
     * @param key the key to validateKey and remove
     * @return returns the previous value at this key position in the cache
     */
    public V remove(final Object key) {
        if (key == null) {
            return null;
        }

        CacheData<K, V> cacheData = removeEntry(key);
        if (cacheData != null) {
            return cacheData.getValue();
        }
        return null;
    }

    /**
     * return the data at pos
     *
     * @param pos
     * @return
     */
    final CacheData<K, V> getCacheData(int pos) {
        return buckets[pos].get();
    }

    /**
     * set cachedata in a atomicreference
     *
     * @param pos
     * @param data
     */
    final void setCacheData(int pos, final CacheData<K, V> data) {
        buckets[pos].set(data);
    }

    /**
     * sets a entry to null, does not shrink the cache size
     *
     * @param key
     * @return
     */
    final CacheData<K, V> removeEntry(final Object key) {
        // find the place to put it and stuff it in, overwriting what was
        // previously there
        // synchronization is not needed here, if this value changes to null
        // either before or after a client looks at it, it's not a problem
        int pos = getKeyPosition(key);
        CacheData<K, V> cacheData = getCacheData(pos);
        setCacheData(pos, null);
        return cacheData;
    }

    /**
     * Updates the buckets with the key and value provided
     *
     * @param key   the key value used to validateKey this value
     * @param value the value object associated with the given key
     */
    public V put(final K key, final V value) {
        if (key == null) {
            return null;
        }

        // find the place to put it and stuff it in, overwriting what was
        // previously there
        int pos = getKeyPosition(key);
        CacheData<K, V> cacheData = getCacheData(pos);
        if (cacheData != null) {
            cacheData.reset();
            cacheData.setKey(key);
            cacheData.setValue(value);
        } else {
            setCacheData(pos, factory.createPair(key, value));
        }

        return (cacheData == null) ? null : cacheData.getValue();
    }

    /**
     * returns the set of keys
     *
     * @return Set the set of keys for this buckets
     */
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new AbstractSet<K>() {

                @Override
                public boolean contains(Object object) {
                    return containsKey((K) object);
                }

                @Override
                public int size() {
                    return MRUCache.this.size();
                }

                @Override
                public void clear() {
                    MRUCache.this.clear();
                }

                @Override
                public boolean remove(Object key) {
                    CacheData<K, V> cacheData = MRUCache.this.removeEntry(key);
                    return cacheData != null;
                }

                @Override
                public Iterator<K> iterator() {
                    return new KeyIterator<K, V>(MRUCache.this);
                }
            };
        }
        return keySet;
    }

    static class CacheEntrySet<KT, VT> extends AbstractSet<Map.Entry<KT, VT>> {

        private final MRUCache<KT, VT> associatedMap;

        public CacheEntrySet(MRUCache<KT, VT> hm) {
            associatedMap = hm;
        }

        MRUCache<KT, VT> cache() {
            return associatedMap;
        }

        @Override
        public int size() {
            return associatedMap.size();
        }

        @Override
        public void clear() {
            associatedMap.clear();
        }

        @Override
        public boolean remove(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry<?, ?> oEntry = (Map.Entry<?, ?>) object;
                CacheData<KT, VT> cacheData = associatedMap.getEntry(oEntry.getKey());
                if (valuesEq(cacheData, oEntry)) {
                    associatedMap.removeEntry(cacheData);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean contains(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry<?, ?> oEntry = (Map.Entry<?, ?>) object;
                CacheData<KT, VT> cacheData = associatedMap.getEntry(oEntry.getKey());
                return valuesEq(cacheData, oEntry);
            }
            return false;
        }

        private static boolean valuesEq(CacheData cacheData, Map.Entry<?, ?> oEntry) {
            return (cacheData != null)
                    && ((cacheData.getValue() == null)
                    ? (oEntry.getValue() == null)
                    : (areEqualValues(cacheData.getValue(), oEntry.getValue())));
        }

        @Override
        public Iterator<Map.Entry<KT, VT>> iterator() {
            return new EntryIterator<KT, VT>(associatedMap);
        }
    }

    static boolean areEqualKeys(Object key1, Object key2) {
        return (key1 == key2) || key1.equals(key2);
    }

    public static boolean areEqualValues(Object value1, Object value2) {
        return (value1 == value2) || value1.equals(value2);
    }

    /**
     * returns the set of keys
     *
     * @return Set the set of keys for this buckets
     */
    public Set<Map.Entry<K, V>> entrySet() {
        return new CacheEntrySet<K, V>(this);
    }

    /**
     * anything in the cache counters will be reset starting now
     */
    public void reset() {
        for (int i = 0, tot = buckets.length; i < tot; i++) {
            CacheData<K, V> pair = getCacheData(i);
            if (pair != null) {
                pair.reset();
            }
        }
    }

    private static class AbstractMapIterator<K, V> {

        int position = 0;
        CacheData<K, V> futureEntry;
        CacheData<K, V> currentEntry;
        CacheData<K, V> prevEntry;
        private final MRUCache<K, V> associatedMap;

        AbstractMapIterator(MRUCache<K, V> hm) {
            associatedMap = hm;
            futureEntry = null;
        }

        public boolean hasNext() {
            if (futureEntry != null) {
                return true;
            }
            while (position < associatedMap.buckets.length) {
                CacheData<K, V> pair = associatedMap.buckets[position].get();
                if (pair == null || ((CacheData<K, V>) pair).validateKey() == null) {
                    position++;
                } else {
                    return true;
                }
            }
            return false;
        }

        final void makeNext() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (futureEntry == null) {
                currentEntry = associatedMap.buckets[position++].get();
                futureEntry = currentEntry.getNext();
                prevEntry = null;
            } else {
                if (currentEntry != null) {
                    prevEntry = currentEntry;
                }
                currentEntry = futureEntry;
                futureEntry = futureEntry.getNext();
            }
        }

        public final void remove() {
            if (currentEntry == null) {
                throw new IllegalStateException();
            }
            if (prevEntry == null) {
                associatedMap.remove(currentEntry.getKey());
            } else {
                prevEntry.setNext(currentEntry.getNext());
            }
            currentEntry = null;
        }
    }

    private static class EntryIterator<K, V> extends AbstractMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {

        EntryIterator(MRUCache<K, V> map) {
            super(map);
        }

        public Map.Entry<K, V> next() {
            makeNext();
            return currentEntry;
        }
    }

    private static class KeyIterator<K, V> extends AbstractMapIterator<K, V> implements Iterator<K> {

        KeyIterator(MRUCache<K, V> map) {
            super(map);
        }

        public K next() {
            makeNext();
            return currentEntry.getKey();
        }
    }

    private static class ValueIterator<K, V> extends AbstractMapIterator<K, V> implements Iterator<V> {

        ValueIterator(MRUCache<K, V> map) {
            super(map);
        }

        public V next() {
            makeNext();
            return currentEntry.getValue();
        }
    }

    /**
     * @return returns a collection of all the values in this cache
     */
    public Collection<V> values() {
        // We don't bother overriding many of the optional methods, as doing so
        // wouldn't provide any significant performance advantage.
        //return values;
        if (valuesCollection == null) {
            valuesCollection = new AbstractCollection<V>() {

                @Override
                public boolean contains(Object object) {
                    return containsValue((V) object);
                }

                @Override
                public int size() {
                    return MRUCache.this.size();
                }

                @Override
                public void clear() {
                    MRUCache.this.clear();
                }

                @Override
                public Iterator<V> iterator() {
                    return new ValueIterator<K, V>(MRUCache.this);
                }
            };
        }
        return valuesCollection;
    }

    /**
     * Copies all the mappings in the specified map to this map. These mappings will replace all mappings that this map
     * had for any of the keys currently in the given map.
     *
     * @param map the map to copy mappings from.
     * @throws NullPointerException if {@code map} is {@code null}.
     */
    public void putAll(Map<? extends K, ? extends V> map) {
        if (!map.isEmpty()) {
            putAllImpl(map);
        }
    }

    private void putAllImpl(Map<? extends K, ? extends V> m) {
        final Map<K, V> addMap = (Map<K, V>) m;
        for (Map.Entry<K, V> e : addMap.entrySet()) {
            // Optimize in case the Entry is one of our own.
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * scan through the cache to see if the specified value exists or not
     *
     * @param value
     * @return true if the value is contained in the cache
     */
    public boolean containsValue(Object value) {
        for (Map.Entry<K, V> pair : entrySet()) {
            // Optimize in case the Entry is one of our own.
            if (pair.getValue().equals(value) && ((CacheData<K, V>) pair).validateKey() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * scan through the cache to see if the specified key exists or not
     *
     * @param key
     * @return true if the key is contained in the cache
     */
    public boolean containsKey(Object key) {
        if (cacheDelegate != null) {
            cacheDelegate.keyLookup(key);
        }

        if (key == null) {
            return false;
        }

        CacheData<K, V> cacheData = getEntry(key);
        if (cacheData != null) {
            // see if the key passed in matches this one
            if ((cacheData.validateKey(key, cacheDelegate)) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * determines if the cache is empty, that is if all the keys are valid
     *
     * @return boolean
     */
    public boolean isEmpty() {
        for (int i = 0, tot = buckets.length; i < tot; i++) {
            CacheData<K, V> entry = getCacheData(i);
            // if we find a key that is valid it's not empty
            if (entry != null && entry.validateKey() != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the # of active items in the cache, which is the active size
     */
    public int size() {
        int valid = 0;
        for (int i = 0, tot = buckets.length; i < tot; i++) {
            CacheData<K, V> entry = getCacheData(i);
            if (entry != null && entry.validateKey() != null) {
                valid++;
            }
        }
        return valid;
    }

    /**
     * an alias for size(), which returns the active # of elements in the cache,
     * if you want the total size call getTotalSize
     *
     * @return
     */
    public int getActiveSize() {
        return size();
    }

    /**
     * @return the total possible size
     */
    public int getTotalSize() {
        return buckets.length;
    }

    /**
     * Returns a shallow clone of this HashMap. The Map itself is cloned, but its contents are not. This is O(n).
     *
     * @return the clone
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        MRUCache<K, V> copy = null;
        try {
            copy = (MRUCache<K, V>) super.clone();
        } catch (CloneNotSupportedException x) {
            // This is impossible.
        }
        int next = 0;
        for (Map.Entry<K, V> pair : entrySet()) {
            // Optimize in case the Entry is one of our own.
            if (pair != null) {
                copy.buckets[next++].set(factory.createPair(pair.getKey(), pair.getValue()));
            }
        }
        return copy;
    }
}
