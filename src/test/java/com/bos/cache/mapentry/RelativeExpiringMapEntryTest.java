/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bos.cache.mapentry;


import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.factory.impl.RelativeExpiringFactory;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author i0360b6
 */
public class RelativeExpiringMapEntryTest {
    RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
    Date startupdate = new Date(mx.getStartTime());
    //long startuptime = startupdate.getTime();
    long startuptime = new Date().getTime();

    public RelativeExpiringMapEntryTest() {
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
     * Test of getFactory method, of class RelativeExpiringMapEntry.
     */
    @Test
    public void testGetFactory() {
        System.out.println("getFactory");
        RelativeExpiringFactory nf = new RelativeExpiringFactory(startuptime,1000);
        RelativeExpiringMapEntry instance = new RelativeExpiringMapEntry("Key","Value",nf);
        CacheDataFactory expResult = nf;
        CacheDataFactory result = instance.getFactory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class RelativeExpiringMapEntry.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        RelativeExpiringFactory nf = new RelativeExpiringFactory(startuptime,500);
        RelativeExpiringMapEntry instance = new RelativeExpiringMapEntry("Key","Value",nf);
        sleep(900);
        assertNull(instance.validateKey());
        instance.reset();
        assertNotNull(instance.validateKey());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class RelativeExpiringMapEntry.
     */
    @Test
    public void testValidateKey_GenericType() {
        System.out.println("validateKey_GenericType");
        Object key = "Key";
        RelativeExpiringFactory nf = new RelativeExpiringFactory(startuptime,1000);
        RelativeExpiringMapEntry instance = new RelativeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        Object result = instance.validateKey(key,null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of validateKey method, of class RelativeExpiringMapEntry.
     */
    @Test
    public void testValidateKey_0args() {
        System.out.println("validateKey_0args");
        RelativeExpiringFactory nf = new RelativeExpiringFactory(startuptime,1000);
        RelativeExpiringMapEntry instance = new RelativeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        Object result = instance.validateKey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    String format(long l){
        Date d = new Date(l);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss.SSS Z");
        return sdf.format(d);
    }
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch(InterruptedException ie) {}
    }

    /**
     * Test of validateKey method, of class RelativeExpiringMapEntry.
     */
    @Test
    public void testValidateExpiredKey_0args() {
        System.out.println("now: " + format((new Date()).getTime()));
        System.out.println("validateExpiredKey_0args " + format(startuptime));
        RelativeExpiringFactory nf = new RelativeExpiringFactory(startuptime,15000);
        RelativeExpiringMapEntry instance = new RelativeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        sleep(10000);
        Object result1 = instance.validateKey();
        assertNotNull(result1);
        assertEquals(expResult,result1);
        sleep(4999);
        Object result = instance.validateKey();
        assertNull(result); 
    }


    /**
     * Test of validateKey method, of class RelativeExpiringMapEntry.
     */
    @Test
    public void testValidateExpiredKey_GenericType() {
        System.out.println("now: " + format((new Date()).getTime()));
        System.out.println("validateExpiredKey_GenericType " +format(startuptime));
        Object key = "Key";
        RelativeExpiringFactory nf = new RelativeExpiringFactory(startuptime,1500);
        RelativeExpiringMapEntry instance = new RelativeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        sleep(900);
        Object result1 = instance.validateKey(key,null);
        assertNotNull(result1);
        assertEquals(expResult,result1);
        sleep(1900);
        Object result = instance.validateKey(key,null);
        assertNull(result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
    /**
     * Test of setValue method, of class RelativeExpiringMapEntry.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        Object value = "Value";
        RelativeExpiringFactory nf = new RelativeExpiringFactory(startuptime,500);
        RelativeExpiringMapEntry instance = new RelativeExpiringMapEntry("Key","Value",nf);
        Object expResult = "Value";
        Object result = instance.setValue(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
