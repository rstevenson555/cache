/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bos.cache.test;

import com.bos.cache.Cache;
import com.bos.cache.CacheFactory;
import com.bos.cache.factory.impl.AgeExpiringFactory;
import com.bos.cache.factory.impl.NonExpiringFactory;
import com.bos.cache.impl.MRUCache;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i0360b6
 */
public class CacheTest {
    static MRUCache<Integer,Double> globalcache = new MRUCache<Integer,Double>(250000, new AgeExpiringFactory(120000));

    public static void main(String []args) {
        
        CacheFactory factory = CacheFactory.newInstance();
        Cache fullcache = factory.createMRUCache(23);
        Cache cache = factory.createIdleExpiringMRUCache(23,15000);
        Cache cache2 = factory.createAgeExpiringMRUCache(23,15000);
        
        cache.put("bob", "stevenson");        
        cache.put("bobs","gordon");

        cache2.put("bob","smith");
        cache2.put("bobs2","junker");
        
        Object value1 = cache.get("bobs");
        System.out.println("value1 " + value1);

        Object value2 = cache2.get("bob");
        System.out.println("value2: " + value2);
        try {
            Thread.sleep(3000);
            cache.get("bob");
            cache2.get("bob");
            Thread.sleep(13000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object value = null;
        
        value = cache.get("bob");
        cache.put("fred","jones");
        cache.put("tommy","nevins");
        value2 = cache2.get("bob");
        
        System.out.println("idle value " + value);
        System.out.println("birth value2 " + value2);

        for (Object var : cache.values()) {
            System.out.println((String)var);
        }

        for (Object e: cache.keySet() ) {
            System.out.println(e);
        }

        System.out.println("dump");
        for(Object e:cache.entrySet()) {
            Map.Entry entry = (Map.Entry)e;
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }

        Object v = cache.remove("fred");
        System.out.println("old v: " + v);

        System.out.println("dump");
        for(Object e:cache.entrySet()) {
            Map.Entry entry = (Map.Entry)e;
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }

        System.out.println("contains key: bob: " + cache.containsKey("bob"));
        System.out.println("contains key: bobb: " + cache.containsKey("bobb"));
        System.out.println("contains value: nevins: " + cache.containsValue("nevins"));
        System.out.println("contains value: nevinss: " + cache.containsValue("nevinss"));

        fullcache.putAll(cache);
        System.out.println("fullcache: " + fullcache.values());
        System.out.println("fullcache keys: " + fullcache.keySet());


        MRUCache<Integer,Double> cachev = new MRUCache<Integer,Double>(3, new NonExpiringFactory());
        cachev.put(new Integer(2),new Double(3.3));
        cachev.put(new Integer(3),new Double(4.4));
        cachev.put(new Integer(4),new Double(5.4));
        cachev.put(new Integer(5),new Double(6.4));

        System.out.println(cachev.get(new Integer(2)));

        Double k = cachev.get(new Integer(3));
        System.out.println("cachev values: " + cachev.values());
        System.out.println("cachev keys: " + cachev.keySet());

        cachev.put(new Integer(2),new Double(3.3));
        System.out.println("cachev keys: " + cachev.keySet());


        // the data in this cache will only live 60 seconds; and it's only big enough
        // to hold 3 items
        MRUCache<Integer,Double> cacheaging = new MRUCache<Integer,Double>(23, new AgeExpiringFactory(5000));
        cacheaging.put(new Integer(5),new Double(6.4));
        cacheaging.put(new Integer(2),new Double(3.3));
        cacheaging.put(new Integer(3),new Double(4.4));
        cacheaging.put(new Integer(4),new Double(5.4));

        System.out.println(cacheaging.get(new Integer(2)));

        Double k1 = cacheaging.get(new Integer(3));
        System.out.println("cacheaging values: " + cacheaging.values());
        System.out.println("cacheaging keys: " + cacheaging.keySet());

       try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
        }


        //cacheaging.put(new Integer(2),new Double(3.3));
        System.out.println("cachev keys: " + cacheaging.keySet());
        System.out.println("cachev values: " + cacheaging.values());

        System.out.println("contains 4.4 " + cacheaging.containsValue(new Double(4.4)));
        System.out.println("containers 5 " + cacheaging.containsKey(new Integer(5)));

        System.out.println("contains 4.5 " + cacheaging.containsValue(new Double(4.5)));
        System.out.println("containers 9 " + cacheaging.containsKey(new Integer(9)));

        Pumper p = new Pumper(globalcache,0);
        p.run();

        Pumper p1 = new Pumper(globalcache,5);
        p1.start();

        System.out.println("running dumper");
        Dumper d = new Dumper(globalcache);
        d.start();

        try {
            synchronized(p1) {
                p1.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("pumper finished");
        try {
            synchronized(d) {
            d.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("dumper finished");

    }

    static class Dumper extends Thread {
       private MRUCache dumper = null;
        Dumper(MRUCache cache)
        {
            dumper = cache;
        }
        public void run()
        {
            long hitCount = 0;
            long attempts = 0;
           for(int i =0;i<1000000;i++) {
               long k = Math.round(Math.random()*1000000);
               //System.out.println("random key "+k);
               attempts++;
               Double d = (Double)dumper.get(new Integer((int)k));
               if ( d!=null)
                   hitCount++;

               if ( attempts % 100 ==0 )
                   System.out.println(( (float)hitCount / (float)attempts) * 100.);
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
        } 
    }
    static class Pumper extends Thread {
        private MRUCache pumper = null;
        int delay = 0;
        Pumper(MRUCache cache,int delay)
        {
            pumper = cache;
            this.delay = delay;
        }
        public void run()
        {
           for(int i =0;i<1000000;i++) {
               pumper.put(new Integer(i),new Double(i % 20));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
        }
    }
}
