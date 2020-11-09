package com.taole.framework.cache;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.taole.cache.redis.redisClient.RedisAPI;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.SerializeUtil;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;


public class RedisCache<K, V> implements Cache<K, V> {
    private String name;

    public RedisCache(String name) {
        this.name = name;
    }

    public String getCacheKey(Object key) {
        return "___cache.entity." + name + "." + String.valueOf(key);
    }

    @SuppressWarnings("unchecked")
    protected Map<K, Long> getCacheMap() {
        String cacheName = getCacheKey("all.map");
        Map<K, Long> map = (Map<K, Long>) SerializeUtil.unSerialize(RedisAPI.getByte(cacheName));
     /*   Map<K, Long> map = null;
        String v = RedisAPI.get(cacheName);
        if (StringUtils.isNotEmpty(v))
            try {
                map = new HashMap<>();
                JSONObject jsonObject = new JSONObject(v);
                JSONArray jsonArray = (JSONArray) jsonObject.get(cacheName);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j1 = jsonArray.getJSONObject(i);
                    Iterator<String> iterator = j1.keys();
                    if (iterator.hasNext()) {
                        map.put((K) iterator.next(), Long.parseLong(j1.get(iterator.next()).toString()));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        if (map == null || map.size() == 0) {
            map = Maps.newConcurrentMap();
            RedisAPI.set(cacheName, SerializeUtil.serialize(map));
        }
        return map;
    }

    protected void saveCacheMap(Map<K, Long> map) {
       /* if (map != null && map.size() > 0) {
            try {
                Set<K> keys = map.keySet();
                JSONObject res = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (K k : keys) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) k, map.get(k));
                    jsonArray.put(jsonObject);
                }
                res.put(getCacheKey("all.map"), jsonArray.toString());
                RedisAPI.set(getCacheKey("all.map"), res.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
        RedisAPI.set(getCacheKey("all.map"), SerializeUtil.serialize(map));
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        V value = null;
        try {
           /* String content = RedisAPI.get(key.toString());
            if (org.apache.commons.lang3.StringUtils.isBlank(content)) return null;
            JSONObject jsonObject = new JSONObject(content);
            Object o = JSONTransformer.transformPojo2Jso(jsonObject);
            value = (V) o;*/
            value = (V) SerializeUtil.unSerialize(RedisAPI.getByte(getCacheKey(key)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void put(K key, V value) {
      /*  JSONObject jsonObject = new JSONObject(value);
        JSONArray jsonArray;
        if (value instanceof ArrayList) {
            jsonArray = new JSONArray();
            ArrayList list = (ArrayList) value;
            if (null != list && list.size() > 0) {
                for (Object object : list) {
                    JSONObject jsonObject1 = new JSONObject(object);
                    jsonArray.put(jsonObject1);
                }
            }
            RedisAPI.set(key.toString(), jsonArray.toString());
        } else {
            RedisAPI.set(key.toString(), jsonObject.toString());
        }*/
        RedisAPI.set(getCacheKey(key), SerializeUtil.serialize(value));
        Map<K, Long> map = getCacheMap();
        map.put(key, System.currentTimeMillis());
        saveCacheMap(map);
    }

    public void clear() {
        Map<K, Long> map = getCacheMap();
        for (K key : map.keySet()) {
            RedisAPI.del(getCacheKey(key));
        }
        map.clear();
        saveCacheMap(map);
    }

    public void remove(K key) {
        RedisAPI.del(getCacheKey(key));
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
            for (String key : keys) {
                list.add((V) SerializeUtil.unSerialize(RedisAPI.getByte(key)));
            }
        }
        return list;
    }

}
