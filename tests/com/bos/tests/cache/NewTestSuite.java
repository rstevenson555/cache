/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bos.tests.cache;

import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.impl.MRUCache;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author i0360b6
 */
public class NewTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInkAndToner() throws Exception {
        MRUCache<String,HashMap> instance = new MRUCache<String,HashMap>(1,new AgeExpiringFactory<String,HashMap>(60*1000));

        HashMap<String,String> cache = new HashMap<String,String>();
        cache.put("Ink","Ink");
        cache.put("printer ink","printer ink");
        cache.put("toner cartridges","toner cartridges");
        cache.put("printer cartridges","printer cartridges");
        cache.put("HP toner","HP toner");
        cache.put("Xerox refill","Xerox refill");
        cache.put("toner ink","toner ink");
        cache.put("printer refill","printer refill");
        cache.put("printer refills","printer refills");
        cache.put("toner cartridge","toner cartridge");
        cache.put("printer cartridge","printer cartridge");
        cache.put("toner refill","toner refill");
        cache.put("toner refills","toner refills");
        cache.put("guybrown reman","guybrown reman");
        cache.put("guy brown remanufactured","guy brown remanufactured");

        //instance.putAll(cache);
        instance.put("cache", cache);

        //String val = instance.get("printer refill");
        HashMap map = instance.get("cache");
        String val = (String)map.get("printer refill");
        System.out.println("val: " + val);
        System.out.println("val: " + cache.size());
        System.out.println("val: " + instance.size());

        //assert(val.equals("printer refill"));

    }

}