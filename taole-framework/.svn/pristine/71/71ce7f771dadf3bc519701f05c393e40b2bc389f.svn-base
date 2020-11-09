package com.taole.framework.cache;

import com.taole.framework.util.conf4res.ResourceUtil;
import com.taole.framework.util.conf4res.conf.CachedConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public class CacheFactoryFactory {
    private interface CACHE {
        String E = "EhCache";
        String M = "MapCache";
        String Mm = "MemCache";
        String R = "RedisCache";
    }

    @Resource(name = "map.cacheFactory")
    private CacheFactory globalCacheFactory;
    @Resource(name = "e.cacheFactory")
    private CacheFactory eCacheFactory;
    @Resource(name = "mem.cacheFactory")
    private CacheFactory memCacheFactory;
    @Resource(name = "red.CacheFactory")
    private CacheFactory redisCacheFactory;
    private CacheFactory currentDomainCacheFactory;

    /**
     * 动态缓存工厂
     *
     * @return
     */
    public CacheFactory getCacheFactory() {
        return currentDomainCacheFactory;
    }

    @PostConstruct
    public void todoInitEvent() {
        ResourceUtil<CachedConfig> resourceUtil = new ResourceUtil<CachedConfig>();
        CachedConfig config = resourceUtil.getT4True(resourceUtil.getCachedConfig());
        CachedConfig.Cache cacheConf = config.getDomainConfigCache();

        switch (cacheConf.getName()) {
            case CACHE.E:
                currentDomainCacheFactory = eCacheFactory;
                break;
            case CACHE.M:
                currentDomainCacheFactory = globalCacheFactory;
                break;
            case CACHE.Mm:
                currentDomainCacheFactory = memCacheFactory;
                break;
            case CACHE.R:
                currentDomainCacheFactory = redisCacheFactory;
                break;
        }
    }
}
