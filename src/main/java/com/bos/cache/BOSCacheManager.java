package com.bos.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Created by 1328975 on 4/26/16.
 */
public class BOSCacheManager extends AbstractCacheManager {
    private final Collection<BOSCache> internalCaches;

    public BOSCacheManager(final Collection<BOSCache> internalCaches) {
        this.internalCaches = internalCaches;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Assert.notNull(internalCaches, "A collection caches is required and cannot be empty");
        return internalCaches;
    }
}
