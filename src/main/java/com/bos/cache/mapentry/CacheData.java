package com.bos.cache.mapentry;

import com.bos.cache.CacheDelegate;

import java.util.Map;

/**
 * CacheData implements the Map.Entry interface for storing the key/value pairs
 * it also defines the abstract validateKey method which is used in the derived classes
 * to determine if a key is expired or not based on the expiration strategy
 *
 * @param <K>
 * @param <V>
 * @author i0360b6
 */
public abstract class CacheData<K, V> implements Map.Entry<K, V>, Comparable<CacheData<K, V>> {

    volatile protected K key = null;
    volatile protected V value = null;
    final int origKeyHash;
    CacheData<K, V> next;

    protected CacheData(final K theKey, final int hash) {
        key = theKey;
        this.origKeyHash = hash;
    }

    /**
     * Constructor
     *
     * @param theKey
     * @param theValue
     */
    public CacheData(final K theKey, final V theValue) {
        key = theKey;
        value = theValue;
        origKeyHash = (theKey == null ? 0 : theKey.hashCode());
    }

    /**
     * Validate the key provided to see if it is still valid
     *
     * @param key
     * @return the value of null if no valid match is found
     */
    abstract public V validateKey(final Object key, final CacheDelegate delegate);

    /**
     * Validate the key for this object
     *
     * @return the value or null if no valid match is found
     */
    abstract public V validateKey();

    abstract public void reset();

    /**
     * @return returns the key for this object
     */
    public K getKey() {
        return key;
    }

    /**
     * @return returns the value for this object
     */
    public V getValue() {
        return value;
    }

    /**
     * stores the value of this pair
     *
     * @param theValue set the value for this key pair
     * @return returns the current value
     */
    public V setValue(V theValue) {
        reset();
        value = theValue;
        return value;
    }

    /**
     * stores the key of this pair
     *
     * @param key
     */
    public void setKey(K key) {
        reset();
        this.key = key;
    }

    public int getOrigKeyHash() {
        return origKeyHash;
    }
    
    public void setKeyValue(K key, V value) {
        reset();
        setKey(key);
        setValue(value);
    }

    public CacheData<K, V> getNext() {
        return next;
    }

    public void setNext(CacheData<K, V> next) {
        this.next = next;
    }

    /**
     * Compares the keys of this and the other object
     *
     * @param other
     * @return int value showing if the items compare 0 if match >0 if this
     * item comes after the compared item
     */
    public int compareTo(CacheData<K, V> other) {
        return ((Comparable) key).compareTo(other.key);
    }

    /**
     * Overrides equals for this object
     *
     * @param obj
     * @return boolean match value
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CacheData<K, V> other = (CacheData<K, V>) obj;
        if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    /**
     * a unique hashcode for this object
     *
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.key != null ? this.key.hashCode() : 0);
        hash = 17 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        CacheData<K, V> entry = (CacheData<K, V>) super.clone();
        if (next != null) {
            entry.next = (CacheData<K, V>) next.clone();
        }
        return entry;
    }
}
