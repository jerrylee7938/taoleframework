package com.taole.cache.memcached;

import com.taole.framework.lock.ResourceLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by ChengJ on 2017/5/2.
 */
public class MemApi extends MemWrapper4Super {
    private static final Logger logger = LoggerFactory.getLogger(MemApi.class);
    private static final Date DEFAULT_LOCK_TIME_OUT = new Date(System.currentTimeMillis() + 6000);

    private MemApi() {
        super();
    }

    private static MemApi memApi;

    public synchronized static MemApi getInstance() {
        if (null == memApi) memApi = new MemApi();
        return memApi;
    }

    /**
     * @param key   记录的主键
     * @param value 记录的内容
     * @return boolean 操作结果
     * @category 插入新记录.
     * @category 前提：记录的Key在缓存中不存在
     */
    public boolean add(String key, Object value) {
        if (!enUsed) {
            return false;
        } else {
            return memClient.add(key, value);
        }
    }

    /**
     * @param key        记录的主键
     * @param value      记录的内容
     * @param expiryDate 超时日期
     * @return boolean 操作结果
     * @category 插入新记录并设置超时日期
     * @category 前提：记录的Key在缓存中不存在
     */
    public boolean add(String key, Object value, Date expiryDate) {
        if (!enUsed) {
            return false;
        } else {
            return memClient.add(key, value, expiryDate);
        }
    }

    /**
     * @param key        记录的主键
     * @param value      记录的内容
     * @param expiryDays 超时天数
     * @return boolean 操作结果
     * @category 插入新记录并设置超时天数
     * @category 前提：记录的Key在缓存中不存在
     */
    public boolean add(String key, Object value, int expiryDays) {
        if (!enUsed) {
            return false;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, expiryDays); //增加天数
            return memClient.add(key, value, calendar.getTime());
        }
    }

    /**
     * @param key   记录的主键
     * @param value 记录的内容
     * @return boolean 操作结果
     * @category 插入新记录或更新已有记录
     * @category 解释：记录的Key在缓存中不存在则插入；否则更新
     */
    public boolean set(String key, Object value) {
        if (!enUsed) {
            return false;
        } else {
            return memClient.set(key, value);
        }
    }

    /**
     * @param key        记录的主键
     * @param value      记录的内容
     * @param expiryDate 超时日期
     * @return boolean 操作结果
     * @category 插入新记录或更新已有记录，并设置超时日期
     * @category 解释：记录的Key在缓存中不存在则插入；否则更新
     */
    public boolean set(String key, Object value, Date expiryDate) {
        if (!enUsed) {
            return false;
        } else {
            return memClient.set(key, value, expiryDate);
        }
    }

    /**
     * @param key   记录的主键
     * @param value 记录的内容
     * @return boolean 操作结果
     * @category 插入新记录或更新已有记录，并设置超时天数
     * @category 解释：记录的Key在缓存中不存在则插入；否则更新
     */
    public boolean set(String key, Object value, int expiryDays) {
        if (!enUsed) {
            return false;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, expiryDays); //增加天数
            return memClient.set(key, value, calendar.getTime());
        }
    }

    /**
     * @param key   记录的主键
     * @param value 记录的内容
     * @return boolean 操作结果
     * @category 更新已有记录
     * @category 前提：记录的Key在缓存中已经存在
     */
    public boolean replace(String key, Object value) {
        if (!enUsed) {
            return false;
        } else {
            return memClient.replace(key, value);
        }
    }

    /**
     * @param key        记录的主键
     * @param value      记录的内容
     * @param expiryDate 超时日期
     * @return boolean 操作结果
     * @category 更新已有记录，并设置超时日期
     * @category 前提：该值在缓存中已经存在
     */
    public boolean replace(String key, Object value, Date expiryDate) {
        if (!enUsed) {
            return false;
        } else {
            return memClient.replace(key, value, expiryDate);
        }
    }

    /**
     * @param key        记录的主键
     * @param value      记录的内容
     * @param expiryDays 超时天数
     * @return boolean 操作结果
     * @category 更新已有记录，并设置超时天数
     * @category 前提：该值在缓存中已经存在
     */
    public boolean replace(String key, Object value, int expiryDays) {
        if (!enUsed) {
            return false;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, expiryDays); //增加天数
            return memClient.replace(key, value, calendar.getTime());
        }
    }

    /**
     * @param key 记录的主键
     * @return 记录的内容
     * @category 返回单条记录
     */
    public Object get(String key) {
        if (!enUsed) {
            return null;
        } else {
            return memClient.get(key);
        }
    }

    /**
     * @param keys 记录的主键数组
     * @return Map<String, Object> 多条记录的内容
     * @category 返回多条记录
     */
    public Map<String, Object> get(String[] keys) {
        if (!enUsed) {
            return null;
        } else {
            return memClient.getMulti(keys);
        }
    }

    /**
     * @param key 记录的主键
     * @return 操作结果
     * @category 删除记录
     * @category 执行该方法之后，使用stats的统计结果会同步更新
     */
    public boolean delete(String key) {
        if (!enUsed) {
            return false;
        } else {
            return memClient.delete(key);
        }
    }

    /**
     * 获取key集合value
     *
     * @param args
     * @return
     */
    public Object[] getMultiArray(String[] args) {
        if (!enUsed) {
            return null;
        } else {
            return memClient.getMultiArray(args);
        }
    }

    public boolean acquire(String lockKey) {
        return acquire(lockKey, DEFAULT_LOCK_TIME_OUT);
    }

    public boolean release(String lockKey) {
        return memClient.delete(lockKey);
    }

    public boolean acquire(String lockKey, int expireMsecs) {
        return acquire(lockKey, new Date(System.currentTimeMillis() + expireMsecs));
    }

    public boolean acquire(String lockKey, Date expireMsecs) {
        try {
            ResourceLock.accquireWrite(lockKey);
            return add(lockKey, String.valueOf(expireMsecs.getTime()), expireMsecs);
        } catch (Exception e) {
            logger.error("lock key=" + lockKey + "errorMsg" + e.getMessage());
        } finally {
            ResourceLock.releaseWrite(lockKey);
        }
        return false;
    }


    /**
     * 使用示例
     */
    public static void main(String[] args) {
        MemServerGovern govern = MemServerGovern.getInstance();
        govern.flushAll();
       /* MemApi cache1 = MemApi.getInstance();
        HashMap user = new HashMap();
        user.put("userName", "程君");
        user.put("age", "26");
        user.put("sex", "男");
        cache1.set("chegnjun", user);
        Object o = cache1.get("chegnjun");*/
       // System.out.println(o);
    }

    public static void main1(String[] args) {

        //初始化memcached操作类对象
        MemApi cache = MemApi.getInstance();
        MemServerGovern govern = MemServerGovern.getInstance();

        //插入新记录
        System.out.println("开始插入新记录（add）：\r\n===================================");
        System.out.println("keyTest01:" + cache.add("keyTest01", "keyTest01Content"));
        System.out.println("keyTest02:" + cache.add("keyTest02", "keyTest02Content"));
        System.out.println("插入新记录操作完成\r\n===================================");

        //读取单条记录
        System.out.println("读取单条记录（get）：\r\n===================================");
        System.out.println("keyTest01:" + cache.get("keyTest01"));
        System.out.println("keyTest02:" + cache.get("keyTest02"));
        System.out.println("读取单条记录操作完成\r\n===================================");

        //读取多条记录
        System.out.println("读取多条记录（add）：\r\n===================================");
        System.out.println("keyTest01、keyTest02:" + cache.get(new String[]{"keyTest01", "keyTest02"}));
        System.out.println("读取多条记录操作完成\r\n===================================");

        //修改记录值
        System.out.println("修改记录值（replace）：\r\n===================================");
        System.out.println("keyTest01:" + cache.get("keyTest01"));
        System.out.println("keyTest01:" + cache.replace("keyTest01", "keyTest01ContentReplace!"));
        System.out.println("keyTest01:" + cache.get("keyTest01"));
        System.out.println("修改记录值操作完成\r\n===================================");

        //添加或修改记录
        System.out.println("添加或修改记录（set）：\r\n===================================");
        System.out.println("keyTest03:" + cache.set("keyTest03", "keyTest03Content"));
        System.out.println("keyTest03:" + cache.get("keyTest03"));
        System.out.println("keyTest03:" + cache.set("keyTest03", "keyTest03ContentReplace!"));
        System.out.println("keyTest03:" + cache.get("keyTest03"));
        System.out.println("添加或修改记录操作完成\r\n===================================");

        //删除记录
        System.out.println("删除记录（delete）：\r\n===================================");
        System.out.println("keyTest01:" + cache.delete("keyTest01"));
        System.out.println("keyTest02:" + cache.delete("keyTest02"));
        System.out.println("keyTest03:" + cache.get("keyTest03"));
        System.out.println("keyTest03:" + cache.delete("keyTest03"));
        System.out.println("keyTest03:" + cache.get("keyTest03"));
        System.out.println("修改记录值操作完成\r\n===================================");

        //打印当前的服务器参数及统计信息
        System.out.println("服务器参数及统计信息（stats）：\r\n===================================");
        Map statsList = govern.stats();
        for (Object server : statsList.keySet().toArray()) {
            System.out.println("-------------------------\r\n服务器：" + server + " : \r\n-------------------------");
            LinkedHashMap serverStats = (LinkedHashMap) statsList.get(server);
            for (Object statKey : serverStats.keySet().toArray()) {
                System.out.println(statKey + " : " + serverStats.get(statKey));
            }
        }
    }
}
