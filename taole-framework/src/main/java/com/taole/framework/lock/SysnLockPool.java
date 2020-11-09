package com.taole.framework.lock;

import org.apache.commons.collections.map.LRUMap;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class SysnLockPool {
    private static final int POOLSIZE = 10000;
    private Object lockWite = new Object();
    private ConcurrentHashMap<String, SysnKeyLock> core = new ConcurrentHashMap();
    private LRUMap cache = new LRUMap(POOLSIZE);//采用淘汰缓存存放业务锁

    public SysnLockPool() {
    }


    public SysnKeyLock getLock(String resourceID) {
        SysnKeyLock lock=null;
        synchronized (lockWite) {
             lock = this.core.get(resourceID);
            if (lock == null) {
                lock = (SysnKeyLock) this.cache.get(resourceID);
                if (lock == null) {
                    lock = new SysnKeyLock(resourceID);
                } else {
                    this.cache.remove(resourceID);
                }
                this.core.put(resourceID, lock);
            }

        }
        return lock;
    }

    public synchronized void releaseLock(SysnKeyLock lock) {
        this.core.remove(lock.getKey());
        this.cache.put(lock.getKey(), lock);
        lock.unlock();
    }
}