package com.bos.cache.datavalue;

/**
 * this is a inner class defined to tie a data element
 * along to a time, so that we can check the data's age
 * and expire it when it get's too old
 **/
public class AgedValue<D> extends Value<D> {

    private long idleTime;
    private long birthTime;

    /**
     * holds on to a data element, and associates
     * a birth time and last update time with this object
     **/
    public AgedValue(D uData) {
        super(uData);
        birthTime = System.currentTimeMillis();
        touch();
    }

    /**
     *
     * returns how long this data has been idle
     **/
    public final long getIdleTime() {
        return System.currentTimeMillis() - idleTime;
    }
    
    /**
     * returns when this object was
     * born or (created)
     **/
    public final long getBirthTime()
    {
        return birthTime;        
    }

    /**
     * updates the timestamp for the last time this data
     * element was touched
     **/
    void touch() {
        idleTime = System.currentTimeMillis();
    }

    /**
     * touches the last update time
     * and returns the data
     **/
    @Override
    public D getValue() {
        return getValue(true);
    }

    /**
     * modifies the last touched timetime
     * and returns the data assosicated
     **/
    public D getValue(boolean touch) {
        if (touch == true) {
            touch();
        }
        return super.getValue();
    }
}
