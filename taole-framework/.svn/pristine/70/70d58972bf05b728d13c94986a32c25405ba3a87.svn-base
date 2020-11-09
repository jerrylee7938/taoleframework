package com.taole.cache.redis.redisLock;

import com.taole.cache.redis.redisClient.RedisAPI;
import com.taole.framework.util.MacUtil;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JedisLockTest {
    private static ExecutorService executor = Executors.newFixedThreadPool(100);
    static final String key = "investId";

    public static void main(String[] args) {
        // System.out.println(MacUtil.getLocalMac());
        //rotat(0);
        //run();
        lodingLock();
    }

    public static void run() {
        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        JedisLock lock = new JedisLock(key, MacUtil.getLocalMac().replaceAll("-", "").replace(":", ""), Thread.currentThread().getId(), 1000 * 60);
                        if (lock.acquire()) {
                            try {
                                //Thread.sleep(1000);
                                System.out.println("获得锁:" + Thread.currentThread().getId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                lock.release();
                            }
                        } else {
                            System.out.println("没有获得锁:" + Thread.currentThread().getId());
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            });
        }
    }

    public static void rotat(int index) {
        if (index < 11000) {

           /* try {
                //  JedisLock lock = new JedisLock(key, MacUtil.getLocalMac(InetAddress.getLocalHost()), Thread.currentThread().getId(), 1000 * 60);
                JedisLock lock = new JedisLock(key, 1000 * 60);
                if (lock.acquire()) {//如果锁上了
                    try {
                        System.out.println("获得锁:" + Thread.currentThread().getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.release();//则解锁
                    }
                } else {
                    System.out.println("没有获得锁:" + Thread.currentThread().getId());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }*/

            System.out.println(index);
            ++index;
            rotat(index);
        }
    }

    public static void lodingLock() {
        final JedisLock lock = new JedisLock("kkkk", false);
        try {
            lock.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RedisAPI.del("kkkk");
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = lock.getAcquire();
                System.out.println(b);
            }
        }).start();

    }
}