package com.taole.cache.redis.redisClient;

import com.taole.framework.util.conf4res.ResourceUtil;
import com.taole.framework.util.conf4res.conf.RedisCachedConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class RedisAPI {

    private static JedisPool pool = null;

    private static String ERROR_MEG = "0";

    /**
     * 构建redis连接池
     *
     * @return JedisPool
     */
    public static JedisPool getPool() {
        if (pool == null) {
            ResourceUtil<RedisCachedConfig> configResourceUtil = new ResourceUtil<>();
            RedisCachedConfig.Config _config = configResourceUtil.getT4True(configResourceUtil.getRedisCachedConfig()).getConfig();
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(Integer.valueOf(_config.getMaxActive()));
            config.setMaxIdle(Integer.valueOf(_config.getMaxIdle()));
            config.setMaxWait(Long.parseLong(_config.getMaxWait()));
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, _config.getHost(), Integer.valueOf(_config.getPort()), 30 * 60 * 1000, _config.getPassword());
        }
        return pool;
    }

    /**
     * 返还到连接池
     *
     * @param pool
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }

    /**
     * 赋值数据
     * set String
     *
     * @param key
     * @param value return OK表示成功，0表示失败
     */
    public static String set(String key, String value) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ERROR_MEG;
    }

    public static String set(String key, byte[] value) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.set(key.getBytes(), value);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ERROR_MEG;
    }

    /**
     * 赋值数据包含过期时间
     * set String
     *
     * @param key
     * @param seconds 过期时间秒
     * @param value   return OK表示成功，0表示失败
     */
    public static String setEx(String key, int seconds, String value) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.setex(key, seconds, value);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ERROR_MEG;
    }

    /**
     * 赋值setNx
     *
     * @param key
     * @param value
     * @return 1表示赋值成功，0表示失败(key已存在)
     */
    public static String setNx(String key, String value) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.setnx(key, value).toString();
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ERROR_MEG;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    public static byte[] getByte(String key) {
        byte[] value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key.getBytes());
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 赋值新数据并返回旧数据
     *
     * @param key
     * @param newValue
     * @return
     */
    public static String getSet(String key, String newValue) {
        String value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.getSet(key, newValue);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }

    /**
     * 赋值list数据
     * set list
     *
     * @param key
     * @param list
     */
    public static void setList(String key, List<String> list) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            for (int i = 0; i < list.size(); i++) {
                jedis.lpush(key, (String) list.get(i));
            }
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
    }

    /**
     * 根据key获取list
     *
     * @param key
     * @return
     */
    public static List<String> getList(String key) {
        List<String> list = new ArrayList<String>();
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return list;
    }

    /**
     * 删除所有返回1表示成功，0表示失败
     *
     * @return
     */
    public static String delAll() {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.flushDB();
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ERROR_MEG;
    }


    /**
     * 根据key删除数据
     *
     * @param key
     * @return 1表示成功，0表示失败
     */
    public static String del(String key) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.del(key).toString();
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ERROR_MEG;
    }

    /**
     * 查询key剩余多长时间过期，返回-1表示key不存在或未设置过期时间
     *
     * @param key
     * @return
     */
    public static String ttl(String key) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.ttl(key).toString();
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return ERROR_MEG;
    }

    public static void main(String[] args) {
//		System.out.println(RedisAPI.setEx("name", 60, "zhqianlong"));
      //System.out.println(RedisAPI.set("__tk.Dictionary__","123"));
      //System.out.println(RedisAPI.del("_lock_BgRegister18866668888"));
     // System.out.println(RedisAPI.setEx("18710297640",10,"123"));
        System.out.println(  RedisAPI.get("13596937329"));;

    }
}
