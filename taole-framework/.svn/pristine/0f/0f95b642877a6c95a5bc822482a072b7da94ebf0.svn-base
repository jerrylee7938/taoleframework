package com.taole.cache.redis.redisLock;


import com.taole.cache.redis.redisClient.RedisAPI;
import redis.clients.jedis.JedisPool;

/**
 * @author piaohailin
 * @date   2014-3-13
*/
public class SimpleLockTest {

    /**
     * @param args
     * @author piaohailin
     * @date   2014-3-13
    */
    public static void main(String[] args) {
        final JedisPool pool = RedisAPI.getPool();
        SimpleLock.setPool(pool);//只需要初始化一次
        
        String key = "test";
        SimpleLock lock = new SimpleLock(key,3000,300000);
        lock.wrap(new Runnable() {
            @Override
            public void run() {
                //此处代码是锁上的
            	System.out.println("ddddddddddddddddddd");
            }
        });
    }

}
