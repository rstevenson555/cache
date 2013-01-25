package com.bos.cache.data;

import com.bos.cache.datavalue.Value;

public abstract class CacheData<K, V> implements java.util.Map.Entry<K, V>, Comparable {

    private K key = null;
    private Value<V> value = null;

    public CacheData(K key, Value<V> value) {
        this.key = key;
        this.value = value;
    }

    abstract public V validateKey(K key);
    abstract public V validateKey();

    public K getKey() {
        return key;
    }

    public Value<V> getContainerValue() {
        return value;
    }

    public V getValue() {
        return value.getValue();
    }

    /*public void setValue(Value value) {
        this.value = value;
    } */
    public V setValue(Value<V> value) {
        this.value = value;
        return ((Value<V>) getContainerValue()).getValue();
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        CacheData<K, V> other = (CacheData<K, V>) o;
        return (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey()))
                && (getContainerValue().getValue() == null
                ? other.getContainerValue().getValue() == null : getContainerValue().getValue().equals(other.getContainerValue().getValue()));
    }

    public int compareTo(Object o) {
        CacheData<K, V> other = (CacheData<K, V>) o;
        return ((Comparable) getKey()).compareTo(other.getKey());
    }

    /**
     * the hashCode is a xor of the key hashcode with the value hashcode
     **/
    @Override
    public int hashCode() {
        return (getKey() == null ? 0 : getKey().hashCode())
                ^ (getContainerValue().getValue() == null ? 0 : getContainerValue().getValue().hashCode());
    }
}
