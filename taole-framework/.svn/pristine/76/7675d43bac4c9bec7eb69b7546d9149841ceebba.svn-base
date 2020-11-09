package com.taole.framework.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.taole.cache.memcached.MemApi;


public class DangaMemcachedCache<K, V> implements Cache<K, V> {
    private String name;

    public DangaMemcachedCache(String name, MemApi client) {
        this.name = name;
    }

    public MemApi getClient() {
        return MemApi.getInstance();
    }

    /**
     * 转换成memcached保存的key
     *
     * @param key
     * @return
     */
    public String getCacheKey(Object key) {
        return "___cache.entity." + name + "." + String.valueOf(key);
    }

    @SuppressWarnings("unchecked")
    protected Map<K, Long> getCacheMap() {
        String cacheName = getCacheKey("all.map");
        Map<K, Long> map = (Map<K, Long>) getClient().get(cacheName);
        if (map == null) {
            map = Maps.newConcurrentMap();
            getClient().set(cacheName, map);
        }
        return map;
    }

    protected void saveCacheMap(Map<K, Long> map) {
        getClient().replace(getCacheKey("all.map"), map);
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        return (V) getClient().get(getCacheKey(key));
    }

    public void put(K key, V value) {
        getClient().set(getCacheKey(key), value);
        Map<K, Long> map = getCacheMap();
        map.put(key, System.currentTimeMillis());
        saveCacheMap(map);
    }

    public void clear() {
        Map<K, Long> map = getCacheMap();
        for (K key : map.keySet()) {
            getClient().delete(getCacheKey(key));
        }
        map.clear();
        saveCacheMap(map);
    }

    public void remove(K key) {
        getClient().delete(getCacheKey(key));
        Map<K, Long> map = getCacheMap();
        map.remove(key);
        saveCacheMap(map);
    }

    public int getSize() {
        return getCacheMap().size();
    }

    public boolean isIn(K key) {
        return getCacheMap().containsKey(String.valueOf(key));
    }

    public Collection<K> keys() {
        return getCacheMap().keySet();
    }

    @SuppressWarnings("unchecked")
    public Collection<V> values() {
        List<V> list = new ArrayList<V>();
        Map<K, Long> map = getCacheMap();
        String[] keys = map.keySet().toArray(new String[map.size()]);
        for (int i = 0; i < keys.length; i++) {
            keys[i] = getCacheKey(keys[i]);
        }
        if (keys != null && keys.length > 0) {
            Object[] objArray = getClient().getMultiArray(keys);
            if (objArray != null && objArray.length > 0) {
                for (Object object : objArray) {
                    list.add((V) object);
                }
            }
        }
        return list;
    }

}
