/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bos.cache.mapentry;

import com.bos.cache.factory.impl.AgeExpiringFactory;
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
public class AgeExpiringMapEntryTest {

    public AgeExpiringMapEntryTest() {
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
     * Test of getFactory method, of class AgeExpiringMapEntry.
     */
    @Test
    public void testGetFactory() {
        System.out.println("getFactory");
        AgeExpiringFactory nf = new AgeExpiringFactory(1000);
        AgeExpiringMapEntry instance = new AgeExpiringMapEntry("Key","Value",nf);
        CacheDataFactory expResult = nf;
        CacheDataFactory result = instance.getFactory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class AgeExpiringMapEntry.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        AgeExpiringFactory nf = new AgeExpiringFactory(500);
        AgeExpiringMapEntry instance = new AgeExpiringMapEntry("Key","Value",nf);
        sleep(900);
        assertNull(instance.validateKey());
        instance.reset();
        assertNotNull(instance.validateKey());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class AgeExpiringMapEntry.
     */
    @Test
    public void testValidateKey_GenericType() {
        System.out.println("validateKey_GenericType");
        Object key = "Key";
        AgeExpiringFactory nf = new AgeExpiringFactory(1000);
        AgeExpiringMapEntry instance = new AgeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        Object result = instance.validateKey(key,null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class AgeExpiringMapEntry.
     */
    @Test
    public void testValidateKey_0args() {
        System.out.println("validateKey_0args");
        AgeExpiringFactory nf = new AgeExpiringFactory(1000);
        AgeExpiringMapEntry instance = new AgeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        Object result = instance.validateKey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class AgeExpiringMapEntry.
     */
    @Test
    public void testValidateExpiredKey_0args() {
        System.out.println("validateExpiredKey_0args");
        AgeExpiringFactory nf = new AgeExpiringFactory(500);
        AgeExpiringMapEntry instance = new AgeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        sleep(200);
        Object result1 = instance.validateKey();
        assertNotNull(result1);
        assertEquals(expResult,result1);
        sleep(900);
        Object result = instance.validateKey();
        assertNull(result);
    }
    
    /**
     * Test of validateKey method, of class AgeExpiringMapEntry.
     */
    @Test
    public void testValidateExpiredKey_GenericType() {
        System.out.println("validateExpiredKey_GenericType");
        Object key = "Key";
        AgeExpiringFactory nf = new AgeExpiringFactory(500);
        AgeExpiringMapEntry instance = new AgeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        sleep(200);
        Object result1 = instance.validateKey(key,null);
        assertNotNull(result1);
        assertEquals(expResult,result1);
        sleep(900);
        Object result = instance.validateKey(key,null);
        assertNull(result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch(InterruptedException ie) {}
    }
    /**
     * Test of setValue method, of class AgeExpiringMapEntry.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        Object value = "Value";
        AgeExpiringMapEntry instance = new AgeExpiringMapEntry("Key","Value",null);
        Object expResult = "Value";
        Object result = instance.setValue(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}