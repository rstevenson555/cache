/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bos.cache;

/**
 *
 * @author i0360b6
 */
public interface CacheDelegate {

    public void keyLookup();
    public void keyMatched();
    public void keyExpired();
        
}
