package com.taole.framework.cache;

import com.taole.framework.util.conf4res.ResourceUtil;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class EhCacheFactoryImpl implements CacheFactory {

    private static CacheManager cacheManager;

    public EhCacheFactoryImpl() {
        if (cacheManager == null)
            cacheManager = CacheManager.newInstance(ResourceUtil.getEhcachePath());
    }

    public Cache<Object, Object> getCache(String name) {
        net.sf.ehcache.Cache ehcache = null;
        try {
            ehcache = cacheManager.getCache(name);
        } finally {
            return new EhCacheImpl<Object, Object>(ehcache);
        }
    }

    public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType) {
        net.sf.ehcache.Cache ehcache = null;
        if (!cacheManager.cacheExists(name)) {
            try {
                cacheManager.addCache(name);
            } catch (Exception e) {

            }
        }
        try {
            ehcache = cacheManager.getCache(name);
        } finally {
            return new EhCacheImpl<K, V>(ehcache);
        }

    }

    public List<Cache<?, ?>> allCaches() {
        List<Cache<?, ?>> list = new ArrayList<Cache<?, ?>>();
        for (String name : cacheManager.getCacheNames()) {
            if (!cacheManager.cacheExists(name)) {
                continue;
            }
            net.sf.ehcache.Cache ehcache = cacheManager.getCache(name);
            list.add(new EhCacheImpl<Object, Object>(ehcache));
        }
        return list;
    }
}
