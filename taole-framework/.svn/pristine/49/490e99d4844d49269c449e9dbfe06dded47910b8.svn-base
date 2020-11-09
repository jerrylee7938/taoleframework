package com.taole.framework.cache;

import java.util.Collection;

import com.taole.cache.memcached.MemApi;

public class DangaMemcachedImpl implements CacheFactory {
    private MemApi client = MemApi.getInstance();

    @SuppressWarnings("unchecked")
    public Cache<Object, Object> getCache(String name) {
        if (client.acquire(name+"_lock")) {
            try {
                DangaMemcachedCache<Object, Object> cache = (DangaMemcachedCache<Object, Object>) client.get(name);
                if (cache == null) {
                    cache = new DangaMemcachedCache<Object, Object>(name, client);
                    client.set(name, cache);
                }
                return cache;
            } finally {
                client.release(name);
            }
        } else {
            return getCache(name);
        }
    }

    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType) {
        if (client.acquire(name+"_lock")) {
            try {
                DangaMemcachedCache<K, V> cache = (DangaMemcachedCache<K, V>) client.get(name);
                if (cache == null) {
                    cache = new DangaMemcachedCache<K, V>(name, client);
                    client.set(name, cache);
                }
                return cache;
            } finally {
                client.release(name);
            }
        } else {
            return getCache(name, keyType, valueType);
        }
    }

    public Collection<Cache<?, ?>> allCaches() {
        return null;
    }
}
