/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bos.cache.impl;


/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
import com.bos.cache.CacheDelegate;
import com.bos.cache.factory.impl.CacheDataFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Cache is an implementation of Map. All optional operations (adding and removing) are supported. Keys and values can
 * be any objects.
 */
public class ExpiringHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>,
        Cloneable, Serializable {

    private static final long serialVersionUID = 362498820763181265L;
    private CacheDelegate cacheDelegate = null;

    public void setCacheDelegate(CacheDelegate dele) {
        cacheDelegate = dele;
    }
    /*
     * Actual count of entries
     */
    private transient int elementCount;
    /*
     * The internal data structure to hold Entries
     */
    private transient CacheData<K, V>[] elementData;
    /*
     * modification count, to keep track of structural modifications between the Cache and the iterator
     */
    private transient int modCount = 0;
    /*
     * default size that an Cache created using the default constructor would have.
     */
    private static final int DEFAULT_SIZE = 16;
    /*
     * maximum ratio of (stored elements)/(storage size) which does not lead to rehash
     */
    private final float loadFactor;
    /*
     * maximum number of elements that can be put in this map before having to rehash
     */
    private int threshold;
    private CacheDataFactory<K, V> factory = null;

    /**
     * @return Returns the Factory associated to this cache
     */
    protected CacheDataFactory<K, V> getFactory() {
        return factory;
    }

    private static class AbstractMapIterator<K, V> {

        private int position = 0;
        int expectedModCount;
        CacheData<K, V> futureEntry;
        CacheData<K, V> currentEntry;
        CacheData<K, V> prevEntry;
        final ExpiringHashMap<K, V> associatedMap;

        AbstractMapIterator(ExpiringHashMap<K, V> hm) {
            associatedMap = hm;
            expectedModCount = hm.modCount;
            futureEntry = null;
        }

        public boolean hasNext() {
            if (futureEntry != null) {
                return true;
            }
            while (position < associatedMap.elementData.length) {
                //if (associatedMap.elementData[position] == null) {
                CacheData<K, V> pair = associatedMap.elementData[position];
                if (pair == null || ((CacheData<K, V>) pair).validateKey() == null) {
                    position++;
                } else {
                    return true;
                }
            }
            return false;
        }

        final void checkConcurrentMod() throws ConcurrentModificationException {
            if (expectedModCount != associatedMap.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        final void makeNext() {
            checkConcurrentMod();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (futureEntry == null) {
                currentEntry = associatedMap.elementData[position++];
                futureEntry = currentEntry.next;
                prevEntry = null;
            } else {
                if (currentEntry != null) {
                    prevEntry = currentEntry;
                }
                currentEntry = futureEntry;
                futureEntry = futureEntry.next;
            }
        }

        public final void remove() {
            checkConcurrentMod();
            if (currentEntry == null) {
                throw new IllegalStateException();
            }
            if (prevEntry == null) {
                int index = currentEntry.origKeyHash & (associatedMap.elementData.length - 1);
                associatedMap.elementData[index] = associatedMap.elementData[index].next;
            } else {
                prevEntry.next = currentEntry.next;
            }
            currentEntry = null;
            expectedModCount++;
            associatedMap.modCount++;
            associatedMap.elementCount--;

        }
    }

    private static class EntryIterator<K, V> extends AbstractMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {

        EntryIterator(ExpiringHashMap<K, V> map) {
            super(map);
        }

        public Map.Entry<K, V> next() {
            makeNext();
            return currentEntry;
        }
    }

    private static class KeyIterator<K, V> extends AbstractMapIterator<K, V> implements Iterator<K> {

        KeyIterator(ExpiringHashMap<K, V> map) {
            super(map);
        }

        public K next() {
            makeNext();
            return currentEntry.key;
        }
    }

    private static class ValueIterator<K, V> extends AbstractMapIterator<K, V> implements Iterator<V> {

        ValueIterator(ExpiringHashMap<K, V> map) {
            super(map);
        }

        public V next() {
            makeNext();
            return currentEntry.value;
        }
    }

    static class CacheEntrySet<KT, VT> extends AbstractSet<Map.Entry<KT, VT>> {

        private final ExpiringHashMap<KT, VT> associatedMap;

        public CacheEntrySet(ExpiringHashMap<KT, VT> hm) {
            associatedMap = hm;
        }

        ExpiringHashMap<KT, VT> cache() {
            return associatedMap;
        }

        @Override
        public int size() {
            return associatedMap.size();//associatedMap.elementCount;
        }

        @Override
        public void clear() {
            associatedMap.clear();
        }

        @Override
        public boolean remove(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry<?, ?> oEntry = (Map.Entry<?, ?>) object;
                CacheData<KT, VT> cd = associatedMap.getEntry(oEntry.getKey());
                if (valuesEq(cd, oEntry)) {
                    associatedMap.removeEntry(cd);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean contains(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry<?, ?> oEntry = (Map.Entry<?, ?>) object;
                CacheData<KT, VT> cd = associatedMap.getEntry(oEntry.getKey());
                return valuesEq(cd, oEntry);
            }
            return false;
        }

        private static boolean valuesEq(CacheData cd, Map.Entry<?, ?> oEntry) {
            return (cd != null)
                    && ((cd.value == null)
                    ? (oEntry.getValue() == null)
                    : (areEqualValues(cd.value, oEntry.getValue())));
        }

        @Override
        public Iterator<Map.Entry<KT, VT>> iterator() {
            return new EntryIterator<KT, VT>(associatedMap);
        }
    }

    /**
     * Create a new element array
     *
     * @param s
     * @return Reference to the element array
     */
    @SuppressWarnings("unchecked")
    CacheData<K, V>[] newElementArray(int s) {
        return new CacheData[s];
    }

    /**
     * Constructs a new empty {@code Cache} instance.
     */
    public ExpiringHashMap(CacheDataFactory<K, V> fact) {
        this(DEFAULT_SIZE, fact);
    }

    /**
     * Constructs a new {@code Cache} instance with the specified capacity.
     *
     * @param capacity the initial capacity of this hash map.
     * @throws IllegalArgumentException when the capacity is less than zero.
     */
    public ExpiringHashMap(int capacity, CacheDataFactory<K, V> fact) {
        this(capacity, 0.75f, fact);  // default load factor of 0.75
    }

    /**
     * Calculates the capacity of storage required for storing given number of elements
     *
     * @param x number of elements
     * @return storage size
     */
    private static final int calculateCapacity(int x) {
        if (x >= 1 << 30) {
            return 1 << 30;
        }
        if (x == 0) {
            return 16;
        }
        x = x - 1;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return x + 1;
    }

    /**
     * Constructs a new {@code Cache} instance with the specified capacity and load factor.
     *
     * @param capacity the initial capacity of this hash map.
     * @param loadFactor the initial load factor.
     * @throws IllegalArgumentException when the capacity is less than zero or the load factor is less or equal to zero.
     */
    private ExpiringHashMap(int capacity, float loadFactor, CacheDataFactory<K, V> fact) {
        factory = fact;
        if (capacity >= 0 && loadFactor > 0) {
            capacity = calculateCapacity(capacity);
            elementCount = 0;
            elementData = newElementArray(capacity);
            this.loadFactor = loadFactor;
            computeThreshold();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a new {@code Cache} instance containing the mappings from the specified map.
     *
     * @param map the mappings to add.
     */
    public ExpiringHashMap(Map<? extends K, ? extends V> map, CacheDataFactory<K, V> fact) {
        this(calculateCapacity(map.size()), fact);
        putAllImpl(map);
    }

    /**
     * Removes all mappings from this hash map, leaving it empty.
     *
     * @see #isEmpty
     * @see #size
     */
    @Override
    public void clear() {
        if (elementCount > 0) {
            elementCount = 0;
            Arrays.fill(elementData, null);
            modCount++;
        }
    }

    /**
     * Returns a shallow copy of this map.
     *
     * @return a shallow copy of this map.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object clone() {
        try {
            ExpiringHashMap<K, V> map = (ExpiringHashMap<K, V>) super.clone();
            map.elementCount = 0;
            map.elementData = newElementArray(elementData.length);
            map.putAll(this);

            return map;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Computes the threshold for rehashing
     */
    private void computeThreshold() {
        threshold = (int) (elementData.length * loadFactor);
    }

    /**
     * Returns whether this map contains the specified key.
     *
     * @param key the key to search for.
     * @return {@code true} if this map contains the specified key,
     *         {@code false} otherwise.
     */
    @Override
    public boolean containsKey(Object key) {
        if (cacheDelegate != null) {
            cacheDelegate.keyLookup();
        }
        CacheData<K, V> m = getEntry(key);
        // see if the key passed in matches this one
        if (m != null) {
            if (m.validateKey((K) key, cacheDelegate) != null) {
                return true;

            }
        } else {
            
        }
        return false;
    }

    /**
     * Returns whether this map contains the specified value.
     *
     * @param value the value to search for.
     * @return {@code true} if this map contains the specified value,
     *         {@code false} otherwise.
     */
    @Override
    public boolean containsValue(Object value) {
        if (value != null) {
            for (int i = 0; i < elementData.length; i++) {
                CacheData<K, V> cd = elementData[i];
                while (cd != null) {
                    if (areEqualValues(value, cd.value) && cd.validateKey() != null) {
                        return true;
                    }
                    cd = cd.next;
                }
            }
        } /*
         * else { for (int i = 0; i < elementData.length; i++) { CacheData<K, V> CacheData = elementData[i]; while
         * (CacheData != null) { if (CacheData.value == null) { return true; } CacheData = CacheData.next; } } }
         */
        return false;
    }

    /**
     * Returns a set containing all of the mappings in this map. Each mapping is an instance of {@link Map.Entry}. As
     * the set is backed by this map, changes in one will be reflected in the other.
     *
     * @return a set of the mappings.
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new CacheEntrySet<K, V>(this);
    }

    /**
     * Returns the value of the mapping with the specified key.
     *
     * @param key the key.
     * @return the value of the mapping with the specified key, or {@code null} if no mapping for the specified key is
     * found.
     */
    @Override
    public V get(Object key) {
        if (cacheDelegate != null) {
            cacheDelegate.keyLookup();
        }
        if (key == null) {
            return null;
        }
        CacheData<K, V> m = getEntry(key);
        V value;
        if (m != null) {
            // see if the key passed in matches this one
            if ((value = m.validateKey((K) key, cacheDelegate)) != null) {
                return value;
            }
        } else {
            
        }
        return null;
    }

    final CacheData<K, V> getEntry(Object key) {
        CacheData<K, V> m;
        if (key == null) {
            m = findNullKeyEntry();
        } else {
            int hash = computeHashCode(key);
            int index = hash & (elementData.length - 1);
            m = findNonNullKeyEntry(key, index, hash);
        }
        return m;
    }

    final CacheData<K, V> findNonNullKeyEntry(Object key, int index, int keyHash) {
        CacheData<K, V> m = elementData[index];
        while (m != null
                && (m.origKeyHash != keyHash || !areEqualKeys(key, m.key))) {
            m = m.next;
        }
        return m;
    }

    final CacheData<K, V> findNullKeyEntry() {
        CacheData<K, V> m = elementData[0];
        while (m != null && m.key != null) {
            m = m.next;
        }
        return m;
    }

    /**
     * Returns whether this map is empty.
     *
     * @return {@code true} if this map has no elements, {@code false} otherwise.
     * @see #size()
     */
    @Override
    public boolean isEmpty() {
        return elementCount == 0;
    }
    private AbstractSet<K> keySet = null;

    /**
     * Returns a set of the keys contained in this map. The set is backed by this map so changes to one are reflected by
     * the other. The set does not support adding.
     *
     * @return a set of the keys.
     */
    @Override
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new AbstractSet<K>() {

                @Override
                public boolean contains(Object object) {
                    return containsKey((K) object);
                }

                @Override
                public int size() {
                    return ExpiringHashMap.this.size();
                }

                @Override
                public void clear() {
                    ExpiringHashMap.this.clear();
                }

                @Override
                public boolean remove(Object key) {
                    CacheData<K, V> cd = ExpiringHashMap.this.removeEntry(key);
                    return cd != null;
                }

                @Override
                public Iterator<K> iterator() {
                    return new KeyIterator<K, V>(ExpiringHashMap.this);
                }
            };
        }
        return keySet;
    }

    /**
     * Maps the specified key to the specified value.
     *
     * @param key the key.
     * @param value the value.
     * @return the value of any previous mapping with the specified key or
     *         {@code null} if there was no such mapping.
     */
    @Override
    public V put(K key, V value) {
        return putImpl(key, value);
    }

    V putImpl(K key, V value) {
        CacheData<K, V> cd;
        if (key == null) {
            /*
             * CacheData = findNullKeyEntry(); if (CacheData == null) { modCount++; if (++elementCount > threshold) {
             * rehash(); } CacheData = createHashedEntry(null, 0, 0); }
             */
            return null;
        } else {
            int hash = computeHashCode(key);
            int index = hash & (elementData.length - 1);
            cd = findNonNullKeyEntry(key, index, hash);
            if (cd == null) {
                modCount++;
                if (++elementCount > threshold) {
                    rehash();
                    index = hash & (elementData.length - 1);
                }
                cd = createHashedEntry(key, index, hash);
            } else {
                // it did find a matching element we need to replace it's contents
            }
        }

        V result = cd.value;
        // replace the value
        cd.value = value;
        cd.reset();  // reset all timestamps, because we are replacing the value
        // so we need to reset the expirations
        return result;
    }

    CacheData<K, V> createEntry(K key, int index, V value) {
        //CacheData<K, V> entry = new CacheData<K, V>(key, value);
        CacheData<K, V> entry = factory.createPair(key, value);
        entry.next = elementData[index];
        elementData[index] = entry;
        return entry;
    }

    CacheData<K, V> createHashedEntry(K key, int index, int hash) {
        //CacheData<K, V> entry = new CacheData<K, V>(key, hash);
        CacheData<K, V> entry = factory.createPair(key, hash);
        entry.next = elementData[index];
        elementData[index] = entry;
        return entry;
    }

    /**
     * Copies all the mappings in the specified map to this map. These mappings will replace all mappings that this map
     * had for any of the keys currently in the given map.
     *
     * @param map the map to copy mappings from.
     * @throws NullPointerException if {@code map} is {@code null}.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (!map.isEmpty()) {
            putAllImpl(map);
        }
    }

    private void putAllImpl(Map<? extends K, ? extends V> map) {
        int capacity = elementCount + map.size();
        if (capacity > threshold) {
            rehash(capacity);
        }
        for (Map.Entry<? extends K, ? extends V> CacheData : map.entrySet()) {
            putImpl(CacheData.getKey(), CacheData.getValue());
        }
    }

    void rehash(int capacity) {
        int length = calculateCapacity((capacity == 0 ? 1 : capacity << 1));

        CacheData<K, V>[] newData = newElementArray(length);
        for (int i = 0; i < elementData.length; i++) {
            CacheData<K, V> cd = elementData[i];
            while (cd != null) {
                int index = cd.origKeyHash & (length - 1);
                CacheData<K, V> next = cd.next;
                cd.next = newData[index];
                newData[index] = cd;
                cd = next;
            }
        }
        elementData = newData;
        computeThreshold();
    }

    void rehash() {
        rehash(elementData.length);
    }

    /**
     * Removes the mapping with the specified key from this map.
     *
     * @param key the key of the mapping to remove.
     * @return the value of the removed mapping or {@code null} if no mapping for the specified key was found.
     */
    @Override
    public V remove(Object key) {
        CacheData<K, V> cd = removeEntry(key);
        if (cd != null) {
            return cd.value;
        }
        return null;
    }

    /*
     * Remove the given CacheData from the Cache. Assumes that the CacheData is in the map.
     */
    final void removeEntry(CacheData<K, V> cd) {
        int index = cd.origKeyHash & (elementData.length - 1);
        CacheData<K, V> m = elementData[index];
        if (m.equals(cd)) {
            elementData[index] = cd.next;
        } else {
            while (m.next != cd) {
                m = m.next;
            }
            m.next = cd.next;

        }
        modCount++;
        elementCount--;
    }

    final CacheData<K, V> removeEntry(Object key) {
        int index = 0;
        CacheData<K, V> cd;
        CacheData<K, V> last = null;
        if (key != null) {
            int hash = computeHashCode(key);
            index = hash & (elementData.length - 1);
            cd = elementData[index];
            while (cd != null && !(cd.origKeyHash == hash && areEqualKeys(key, cd.key))) {
                last = cd;
                cd = cd.next;
            }
        } else {
            cd = elementData[0];
            while (cd != null && cd.key != null) {
                last = cd;
                cd = cd.next;
            }
        }
        if (cd == null) {
            return null;
        }
        if (last == null) {
            elementData[index] = cd.next;
        } else {
            last.next = cd.next;
        }
        modCount++;
        elementCount--;
        return cd;
    }

    /**
     * Returns the number of elements in this map.
     *
     * @return the number of elements in this map.
     */
    /*
     * @Override public int size() { return elementCount; }
     */
    @Override
    public int size() {
        int valid = 0;
        for (CacheData<K, V> entry : elementData) {
            if (entry != null && entry.validateKey() != null) {
                valid++;
            }
        }
        return valid;
    }
    private AbstractCollection<V> valuesCollection = null;

    /**
     * Returns a collection of the values contained in this map. The collection is backed by this map so changes to one
     * are reflected by the other. The collection supports remove, removeAll, retainAll and clear operations, and it
     * does not support add or addAll operations. <p> This method returns a collection which is the subclass of
     * AbstractCollection. The iterator method of this subclass returns a "wrapper object" over the iterator of map's
     * entrySet(). The {@code size} method wraps the map's size method and the {@code contains} method wraps the map's
     * containsValue method. <p> The collection is created when this method is called for the first time and returned in
     * response to all subsequent calls. This method may return different collections when multiple concurrent calls
     * occur, since no synchronization is performed.
     *
     * @return a collection of the values contained in this map.
     */
    @Override
    public Collection<V> values() {
        if (valuesCollection == null) {
            valuesCollection = new AbstractCollection<V>() {

                @Override
                public boolean contains(Object object) {
                    return containsValue((V) object);
                }

                @Override
                public int size() {
                    return ExpiringHashMap.this.size();
                }

                @Override
                public void clear() {
                    ExpiringHashMap.this.clear();
                }

                @Override
                public Iterator<V> iterator() {
                    return new ValueIterator<K, V>(ExpiringHashMap.this);
                }
            };
        }
        return valuesCollection;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(elementData.length);
        stream.writeInt(elementCount);
        Iterator<?> iterator = entrySet().iterator();
        while (iterator.hasNext()) {
            CacheData<?, ?> cd = (CacheData<?, ?>) iterator.next();
            stream.writeObject(cd.key);
            stream.writeObject(cd.value);
            //cd = cd.next;
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream) throws IOException,
            ClassNotFoundException {
        stream.defaultReadObject();
        int length = stream.readInt();
        elementData = newElementArray(length);
        elementCount = stream.readInt();
        for (int i = elementCount; --i >= 0;) {
            K key = (K) stream.readObject();
            int index = (null == key) ? 0 : (computeHashCode(key) & (length - 1));
            createEntry(key, index, (V) stream.readObject());
        }
    }

    /*
     * Contract-related functionality
     */
    static int computeHashCode(Object key) {
        return key.hashCode();
    }

    private static boolean areEqualKeys(Object key1, Object key2) {
        return (key1 == key2) || key1.equals(key2);
    }

    static boolean areEqualValues(Object value1, Object value2) {
        return (value1 == value2) || value1.equals(value2);
    }
}
