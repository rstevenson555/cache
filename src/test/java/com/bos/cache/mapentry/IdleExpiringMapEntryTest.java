/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.IdleExpiringFactory;
import com.bos.cache.factory.impl.CacheDataFactory;
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
public class IdleExpiringMapEntryTest {

    public IdleExpiringMapEntryTest() {
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
     * Test of getFactory method, of class IdleExpiringMapEntry.
     */
    @Test
    public void testGetFactory() {
        System.out.println("getFactory");
        IdleExpiringFactory nf = new IdleExpiringFactory(1000);
        IdleExpiringMapEntry instance = new IdleExpiringMapEntry("Key","Value",nf);
        CacheDataFactory expResult = nf;
        CacheDataFactory result = instance.getFactory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

     /**
     * Test of reset method, of class IdleExpiringMapEntry.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        IdleExpiringFactory nf = new IdleExpiringFactory(500);
        IdleExpiringMapEntry instance = new IdleExpiringMapEntry("Key","Value",nf);
        sleep(900);
        assertNull(instance.validateKey());
        instance.reset();
        assertNotNull(instance.validateKey());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class IdleExpiringMapEntry.
     */
    @Test
    public void testValidateKey_GenericType() {
        System.out.println("validateKey_GenericType");
        Object key = "Key";
        IdleExpiringFactory nf = new IdleExpiringFactory(1000);
        IdleExpiringMapEntry instance = new IdleExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        Object result = instance.validateKey(key,null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class IdleExpiringMapEntry.
     */
    @Test
    public void testValidateKey_0args() {
        System.out.println("validateKey_0args");
        IdleExpiringFactory nf = new IdleExpiringFactory(1000);
        IdleExpiringMapEntry instance = new IdleExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        Object result = instance.validateKey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class IdleExpiringMapEntry.
     */
    @Test
    public void testValidateExpiredKey_0args() {
        System.out.println("validateExpiredKey_0args");
        IdleExpiringFactory nf = new IdleExpiringFactory(1000);
        IdleExpiringMapEntry instance = new IdleExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        sleep(200);
        Object result1 = instance.validateKey();
        assertNotNull(result1);
        assertEquals(expResult,result1);
        sleep(600);
        Object result = instance.validateKey();
        assertNotNull(result);
        assertEquals(expResult,result);
        sleep(600);
        result = instance.validateKey();
        assertNotNull(result);
        assertEquals(expResult,result);
        sleep(600);
        result = instance.validateKey();
        assertNotNull(result);
        assertEquals(expResult,result);
    }

    /**
     * Test of validateKey method, of class IdleExpiringMapEntry.
     */
    @Test
    public void testValidateExpiredKey_GenericType() {
        Object key = "Key";
        System.out.println("validateExpiredKey_GenericType");
        IdleExpiringFactory nf = new IdleExpiringFactory(1000);
        IdleExpiringMapEntry instance = new IdleExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        sleep(200);
        Object result1 = instance.validateKey();
        assertNotNull(result1);
        assertEquals(expResult,result1);
        sleep(600);
        Object result = instance.validateKey();
        assertNotNull(result);
        assertEquals(expResult,result);
        sleep(600);
        result = instance.validateKey();
        assertNotNull(result);
        assertEquals(expResult,result);
        sleep(600);
        result = instance.validateKey();
        assertNotNull(result);
        assertEquals(expResult,result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch(InterruptedException ie) {}
    }
    /**
     * Test of setValue method, of class IdleExpiringMapEntry.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        Object value = "Value";
        IdleExpiringMapEntry instance = new IdleExpiringMapEntry("Key","Value",null);
        Object expResult = "Value";
        Object result = instance.setValue(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}