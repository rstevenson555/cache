/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bos.cache.impl;

import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.factory.impl.CacheDataFactory;
import com.bos.cache.factory.impl.TimeExpiringFactory;
import com.bos.cache.mapentry.AgeExpiringMapEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AgeExpiringMRUCacheTest {

    public AgeExpiringMRUCacheTest() {
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
     * Test of getFactory method, of class MRUCache.
     */
    @Test
    public void testGetFactory() {
        System.out.println("getFactory");
        MRUCache instance = new MRUCache<String,String>(new AgeExpiringFactory<String,String>(1000));
        CacheDataFactory expResult = null;
        CacheDataFactory result = instance.getFactory();
        //assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        assertNotNull("Factory is null",result);
    }

    /**
     * Test of size method, of class MRUCache.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        MRUCache instance = new MRUCache<String,String>(new AgeExpiringFactory<String,String>(1000));
        instance.put("Test", "value");
        int expResult = 1;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class MRUCache.
     */
    @Test
    public void testSizeAfterExpired() {
        System.out.println("sizeAfterExpired");
        MRUCache instance = new MRUCache<String,String>(new AgeExpiringFactory<String,String>(1000));
        instance.put("Test", "value");
        try {
            Thread.sleep(1050);
        }
        catch(InterruptedException ie) {}
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class MRUCache.
     */
    @Test
    public void testIsExpiredElement() {
        System.out.println("isExpiredElement");
        MRUCache instance = new MRUCache<String,String>(new AgeExpiringFactory<String,String>(1000));
        instance.put("Test", "value");
        try {
            Thread.sleep(1050);
        }
        catch(InterruptedException ie) {}
        int expResult = 0;
        int result = instance.size();
        assert( instance.isExpired("Test")==true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MRUCache.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Object key = "Key";
        MRUCache instance = new MRUCache<String,String>(new AgeExpiringFactory<String,String>(1000));
        instance.put("Key","Value");
        Object expResult = "Value";
        Object result = instance.get(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEntry method, of class MRUCache.
     */
    @Test
    public void testGetEntry() {
        System.out.println("getEntry");
        Object key = "Key";
        CacheDataFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key","Value");
        CacheData expResult = cf.createPair("Key", "Value");
        CacheData result = instance.getEntry(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class MRUCache.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        MRUCache instance = new MRUCache<String,String>(new AgeExpiringFactory<String,String>(1000));
        instance.put("Key", "Value");
        instance.clear();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class MRUCache.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Object key = "Key";
        MRUCache instance = new MRUCache<String,String>(new AgeExpiringFactory<String,String>(1000));
        instance.put("Key", "Value");
        Object expResult = "Value";
        Object result = instance.remove(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of removeEntry method, of class MRUCache.
     */
    @Test
    public void testRemoveEntry() {
        System.out.println("removeEntry");
        Object key = "Key";
        CacheDataFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key","Value");
        CacheData expResult = cf.createPair("Key", "Value");
        CacheData result = instance.removeEntry(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of put method, of class MRUCache.
     */
    @Test
    public void testPut() {
        System.out.println("put");
        Object key = "Key";
        Object value = "Value";
        CacheDataFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key","Value1");
        Object expResult = "Value1";
        Object result = instance.put(key, value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
/**
     * Test of put method, of class MRUCache.
     */
    @Test
    public void testPutAllExpired() {
        System.out.println("putAllExpired");
        Object key = "Key";
        Object value = "Value";
        CacheDataFactory<String,String> cf = new AgeExpiringFactory<String,String>(500);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key","Value1");
        sleep(900);
        instance.put("Key2","Value2");

        MRUCache instance2 = new MRUCache<String,String>(cf);
        instance2.putAll(instance);
        HashMap<String,String> expResults = new HashMap<String,String>();
        expResults.put("Key2","Value2");
        Object expResult = expResults;
        assertEquals(expResult, instance2);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of keySet method, of class MRUCache.
     */
    @Test
    public void testKeySet() {
        System.out.println("keySet");
        CacheDataFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key1","Value1");
        instance.put("Key2","Value1");
        HashSet set = new HashSet();
        set.add("Key1");
        set.add("Key2");
        Set expResult = set;
        Set result = instance.keySet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getKeys method, of class MRUCache.
     */
    @Test
    public void testKeySetAfterExpiration() {
        System.out.println("keySetAfterExpiration");
        CacheDataFactory<String,String> cf = new AgeExpiringFactory<String,String>(500);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key1","Value1");
        instance.put("Key2","Value2");
        sleep(900);
        instance.put("Key3","Value3");
        instance.put("Key4","Value4");
        Set expResult = new HashSet();
        expResult.add("Key3");
        expResult.add("Key4");
        Set result = instance.keySet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of areEqualKeys method, of class MRUCache.
     */
    @Test
    public void testAreEqualKeys() {
        System.out.println("areEqualKeys");
        Object key1 = "Key";
        Object key2 = "Key";
        boolean expResult = true;
        boolean result = MRUCache.areEqualKeys(key1, key2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of areEqualValues method, of class MRUCache.
     */
    @Test
    public void testAreEqualValues() {
        System.out.println("areEqualValues");
        Object value1 = "Value1";
        Object value2 = "Value1";
        boolean expResult = true;
        boolean result = MRUCache.areEqualValues(value1, value2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of entrySet method, of class MRUCache.
     */
    @Test
    public void testEntrySet() {
        System.out.println("entrySet");
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key1","Value1");
        instance.put("Key2","Value2");
        HashSet set = new HashSet();
        set.add(new AgeExpiringMapEntry("Key1","Value1",cf));
        set.add(new AgeExpiringMapEntry("Key2","Value2",cf));
        Set expResult = set;
        Set result = instance.entrySet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of entrySet method, of class MRUCache.
     */
    @Test
    public void testEntrySetAfterExpiration() {
        System.out.println("entrySetAfterExpiration");
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(500);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key1","Value1");
        instance.put("Key2","Value2");
        sleep(900);
        instance.put("Key3","Value1");
        instance.put("Key4","Value2");
        HashSet set = new HashSet();
        set.add(new AgeExpiringMapEntry("Key3","Value1",cf));
        set.add(new AgeExpiringMapEntry("Key4","Value2",cf));

        Set expResult = set;
        Set result = instance.entrySet();
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of entrySet method, of class MRUCache.
     */
    @Test
    public void testEntrySetIterator() {
        System.out.println("entrySetIterator");
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(500);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key1","Value1");
        instance.put("Key2","Value2");
        int i = 0;
        for(Iterator<CacheData<String,String>> iter =instance.entrySet().iterator();iter.hasNext();) {
            if (++i == 1) {
                CacheData cd = iter.next();
                iter.remove();
            } else {
                CacheData cd = iter.next();
            }
            //sleep(500);
        }
        sleep(900);
        instance.put("Key3","Value1");
        instance.put("Key4","Value2");
        HashSet set = new HashSet();
        set.add(new AgeExpiringMapEntry("Key3","Value1",cf));
        set.add(new AgeExpiringMapEntry("Key4","Value2",cf));

        Set expResult = set;
        Set result = instance.entrySet();
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of trigger method, of class MRUCache.
     */
   /* @Test
    public void testTrigger() {
        System.out.println("trigger");
        MRUCache instance = null;
        instance.trigger();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    } */

    /**
     * Test of reset method, of class MRUCache.
     */
    /*@Test
    public void testReset() {
        System.out.println("reset");
        MRUCache instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    } */

    /**
     * Test of values method, of class MRUCache.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key1","Value1");
        instance.put("Key2","Value2");
        Collection expResult = new ArrayList();
        expResult.add("Value1");
        expResult.add("Value2");
        Collection result = new ArrayList(instance.values());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of putAll method, of class MRUCache.
     */
    @Test
    public void testPutAll() {
        System.out.println("putAll");
        //Map<? extends K, ? extends V> m = null;
        HashMap<String,String> m = new HashMap<String,String>();
        m.put("Key1","Value1");
        m.put("Key2","Value2");
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.putAll(m);
        HashSet set = new HashSet();
        set.add(new AgeExpiringMapEntry("Key1","Value1",cf));
        set.add(new AgeExpiringMapEntry("Key2","Value2",cf));
        Set expResult = set;
        Set result = instance.entrySet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of containsValue method, of class MRUCache.
     */
    @Test
    public void testContainsValue() {
        System.out.println("containsValue");
        Object value = "Value";
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key","Value");
        boolean expResult = true;
        boolean result = instance.containsValue(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of containsKey method, of class MRUCache.
     */
    @Test
    public void testContainsKey() {
        System.out.println("containsKey");
        Object key = "Key";
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key", "Value");
        boolean expResult = true;
        boolean result = instance.containsKey(key);
        assertEquals(expResult, result);

        result = instance.containsKey("Key2");
        assertEquals(false, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch(InterruptedException ie) {}
    }

    /**
     * Test of containsKey method, of class MRUCache.
     */
    @Test
    public void testContainsKeyAfterExpiration() {
        System.out.println("containsKeyAfterExpiration");
        Object key = "Key";
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(500);
        MRUCache instance = new MRUCache<String,String>(cf);
        instance.put("Key", "Value");
        sleep(900);
        boolean expResult = false;
        boolean result = instance.containsKey(key);
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class MRUCache.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
        MRUCache instance = new MRUCache<String,String>(cf);
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of clone method, of class MRUCache.
     */
    @Test
    public void testClone() {
        try {
            System.out.println("clone");
            TimeExpiringFactory<String,String> cf = new AgeExpiringFactory<String,String>(1000);
            MRUCache instance = new MRUCache<String,String>(cf);
            instance.put("Key1", "Value1");
            instance.put("Key2", "Value2");
            Object expResult = instance.entrySet();
            MRUCache copy = (MRUCache)instance.clone();
            Object result = copy.entrySet();
            assertEquals(expResult, result);
            //fail("The test case is a prototype.");
            //fail("The test case is a prototype.");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AgeExpiringMRUCacheTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}