package com.taole.cache.redis.redisLock;


import com.taole.cache.redis.redisClient.RedisAPI;
import com.taole.framework.util.MacUtil;
import com.taole.framework.util.ThreadLocalFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Redis distributed lock implementation.
 *
 * @author Alois Belaska <alois.belaska@gmail.com>
 */
public class JedisLock {

    /**
     * Lock key path.
     */
    String lockKey;
    /**
     * 重入锁key后缀
     */
    static final String lockKey4ReeNt = "_REENT_";
    /**
     * 是否重入锁
     */
    boolean isReent;
    /**
     * mac地址
     */
    String macAd;

    /**
     * 线程id
     */
    long pid;


    /**
     * Lock expiration in miliseconds.
     */
    int expireMsecs = 60 * 1000; // 锁超时，防止线程在入锁以后，无限的执行等待
    /**
     * LexpireMsecs4Self in miliseconds.
     */
    long expireMsecs4Self = 0; // 自循锁超时，防止线程在入锁以后，无限的执行等待
    /**
     * defSleep in miliseconds.
     */
    int defSleep = 100;//默认自循锁循环周期等待毫秒数


    /**
     * Acquire timeout in miliseconds.
     */
    int timeoutMsecs = 30 * 1000; // 锁等待，防止线程饥饿

    boolean locked = false;

    /**
     * Detailed constructor with default acquire timeout 10000 msecs and lock
     * expiration of 60000 msecs.
     *
     * @param lockKey lock key (ex. account:1, ...)
     */
    public JedisLock(String lockKey) {
        this(lockKey, 0, true);
    }

    public JedisLock(String lockKey, boolean isRet) {
        this(lockKey, 0, isRet);
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public JedisLock(String lockKey, String macAd, long pid, int expireMsecs) {
        this.lockKey = lockKey;
        this.macAd = macAd;
        this.pid = pid;
        this.isReent = true;
        this.expireMsecs = expireMsecs;
    }

    /**
     * 使用默认重入锁
     *
     * @param lockKey
     * @param expireMsecs
     * @param defalutReetn
     */
    public JedisLock(String lockKey, int expireMsecs, boolean defalutReetn) {
        this.lockKey = lockKey;
        this.isReent = defalutReetn;
        if (expireMsecs != 0)
            this.expireMsecs = expireMsecs;
        if (defalutReetn) {
            this.pid = Thread.currentThread().getId();
            this.macAd = MacUtil.getLocalMac().replaceAll("-", "").replace(":", "");
        }
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     *
     * @param lockKey lock key (ex. account:1, ...)
     */
    public JedisLock(String lockKey, int expireMsecs) {
        this(lockKey, expireMsecs, true);
    }


    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }

    /**
     * Acquire lock.
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean acquire() throws InterruptedException {

        long expires = System.currentTimeMillis() + expireMsecs + 1;
        String expiresStr = String.valueOf(expires); // 锁到期时间
        //返回1说明该线程获得了锁，并设置过期时间，可以继续执行；返回0说明锁已经被其他线程获取了，不能继续执行
        if (RedisAPI.setNx(lockKey, expiresStr).equals("1")) {
            // lock acquired
            reentLock();
            locked = true;
            return true;

        }
        String currentValueStr = RedisAPI.get(lockKey);// redis里的时间
        String reentV = RedisAPI.get(getReentK());
        if (StringUtils.isNotBlank(reentV) && !"null0".equals(reentV))
            if (reentV.equals(getReentV())) return true;
        //是否过期
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            String oldValueStr = RedisAPI.getSet(lockKey, expiresStr);
            // 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
            if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                reentLock();
                locked = true;
                return true;
            }
        }
        return false;
    }

    public boolean getAcquire() {
        return this.getAcquire(expireMsecs, defSleep);
    }

    /**
     * 上重入锁
     *
     * @return
     */
    public boolean reentLock() {
        if (isReent) {
            if (RedisAPI.set(getReentK(), getReentV()).equals("OK"))
                return true;
        }
        return false;
    }

    public boolean getAcquire(long expireMsecs) {
        return getAcquire(expireMsecs, defSleep);
    }

    public boolean getAcquire(long expireMsecs, int sleep) {
        try {
            if (expireMsecs4Self == 0)
                expireMsecs4Self = System.currentTimeMillis() + expireMsecs + 1;
            int i = 1;
            do {
                if (this.acquire()) return true;
                Thread.currentThread().sleep(sleep);
                if (System.currentTimeMillis() >= expireMsecs4Self) return false;
            } while (true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Acqurired lock release.
     */
    public synchronized void release() {
        if (locked) {
            if (isReent)
                RedisAPI.del(lockKey + lockKey4ReeNt);
            RedisAPI.del(lockKey);
            locked = false;
        }

    }

    /**
     * 获取分布式中重入锁V
     *
     * @return
     */
    private String getReentV() {
        if (!isReent) return null;
        return macAd + pid;
    }

    /**
     * 获取分布式中重入锁K
     *
     * @return
     */
    private String getReentK() {
        return lockKey + lockKey4ReeNt;
    }

    public static void main(String[] args) {
        String mac = "";
        try {
            Process p = new ProcessBuilder("ifconfig").start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                Pattern pat = Pattern.compile("\\b\\w+:\\w+:\\w+:\\w+:\\w+:\\w+\\b");
                Matcher mat = pat.matcher(line);
                if (mat.find()) {
                    mac = mat.group(0);
                }
            }
            br.close();
        } catch (IOException e) {
        }
        System.out.println("本机MAC地址为:\n" + mac);
    }

}
