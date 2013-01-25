package com.bos.cache.datavalue;

/**
 * this is a inner class defined to tie a data element
 * along to a time, so that we can check the data's age
 * and expire it when it get's too old
 **/
public class Value<D> {

    private D data;

    public Value(D uData) {
        data = uData;
    }

    public D getValue() {
        return data;
    }
}
