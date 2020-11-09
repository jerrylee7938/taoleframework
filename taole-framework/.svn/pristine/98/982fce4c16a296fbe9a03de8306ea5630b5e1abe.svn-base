package com.taole.framework.cache;

import com.taole.cache.redis.redisClient.RedisAPI;
import com.taole.cache.redis.redisLock.JedisLock;
import com.taole.framework.util.SerializeUtil;

import java.util.Collection;

public class RedisCachedImpl implements CacheFactory {

    @SuppressWarnings("unchecked")
    public Cache<Object, Object> getCache(String name) {
        JedisLock lock = new JedisLock(name+"_lock", 6 * 1000);
        try {
            if (lock.acquire()) {
                RedisCache<Object, Object> cache = (RedisCache<Object, Object>) SerializeUtil.unSerialize(RedisAPI.getByte(name));
                if (cache == null) {
                    cache = new RedisCache<Object, Object>(name);
                    RedisAPI.set(name, SerializeUtil.serialize(cache));
                }
                lock.release();
                return cache;
            } else {
                return getCache(name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType) {
        JedisLock lock = new JedisLock(name+"_lock", 6 * 1000);
        try {
            if (lock.acquire()) {
                RedisCache<K, V> cache = (RedisCache<K, V>) SerializeUtil.unSerialize(RedisAPI.getByte(name));
                if (cache == null) {
                    cache = new RedisCache<K, V>(name);
                   String result= RedisAPI.set(name, SerializeUtil.serialize(cache));
                    System.out.println(result);
                }
                lock.release();
                return cache;
            } else {
                return getCache(name, keyType, valueType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Cache<?, ?>> allCaches() {
        return null;
    }
}
