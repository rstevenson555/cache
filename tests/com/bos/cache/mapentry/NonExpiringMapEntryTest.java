/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.factory.impl.NonExpiringFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author i0360b6
 */
public class NonExpiringMapEntryTest {

    public NonExpiringMapEntryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getFactory method, of class NonExpiringMapEntry.
     */
    @Test
    public void testGetFactory() {
        System.out.println("getFactory");
        NonExpiringFactory nf = new NonExpiringFactory();
        NonExpiringMapEntry instance = new NonExpiringMapEntry("Key","Value",nf);
        CacheDataFactory expResult = nf;
        CacheDataFactory result = instance.getFactory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class NonExpiringMapEntry.
     */
    @Test
    public void testValidateKey_0args() {
        System.out.println("validateKey_0args");
        NonExpiringMapEntry instance = new NonExpiringMapEntry("Key","Value",null);
        Object expResult = "Value";
        Object result = instance.validateKey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class NonExpiringMapEntry.
     */
/*    @Test
    public void testReset() {
        System.out.println("reset");
        NonExpiringMapEntry instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of validateKey method, of class NonExpiringMapEntry.
     */
    @Test
    public void testValidateKey_GenericType() {
        System.out.println("validateKey_GenericType");
        Object key = "Key";
        NonExpiringMapEntry instance = new NonExpiringMapEntry("Key","Value",null);
        Object expResult = "Value";
        Object result = instance.validateKey(key,null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setValue method, of class NonExpiringMapEntry.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        Object value = "Value";
        NonExpiringMapEntry instance = new NonExpiringMapEntry("Key","Value",null);
        Object expResult = "Value";
        Object result = instance.setValue(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}