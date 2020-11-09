package com.taole.cache.memcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.taole.framework.util.conf4res.ResourceUtil;
import com.taole.framework.util.conf4res.conf.MemCachedConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.telnet.TelnetClient;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ChengJ
 * @category memcached内存数据库操作类
 */
public abstract class MemWrapper4Super implements MemWrapper {
    private Log log = LogFactory.getLog(getClass());
    protected static boolean enUsed = true;
    private static boolean IS_LOADED = false;
    protected MemCachedClient memClient;
    private Lock lock = null;
    private static final String MemCachedConfigFile_NAME = "MemCachedConfig.xml";
    protected static String[] serverListArr = new String[1];
    protected static Integer[] weightListArr = new Integer[1];
    protected static Map<String, String> serverConfig;

    protected MemWrapper4Super() {
        lock = new ReentrantLock();
        try {
            lock.lock();
            if (!IS_LOADED) {
                init();
            }
            memClient = new MemCachedClient();
        } finally {
            lock.unlock();
        }
    }

    private void init() {
        initConfig();
        if (enUsed) {
            SockIOPool pool = SockIOPool.getInstance();
            pool.setServers(serverListArr);
            pool.setWeights(weightListArr);
            pool.setInitConn(Integer.parseInt(serverConfig.get("initConn").toString()));
            pool.setMinConn(Integer.parseInt(serverConfig.get("minConn").toString()));
            pool.setMaxConn(Integer.parseInt(serverConfig.get("maxConn").toString()));
            pool.setMaxIdle(Long.parseLong(serverConfig.get("maxIdle").toString()));
            pool.setMaintSleep(Long.parseLong(serverConfig.get("maintSleep").toString()));
            pool.setNagle(false);
            pool.setSocketTO(Integer.parseInt(serverConfig.get("socketTO").toString()));
            pool.setSocketConnectTO(Integer.parseInt(serverConfig.get("socketConnTO").toString()));
            pool.initialize();
        }
        IS_LOADED = true;
    }
    public void initConfig() {
        serverListArr[0] = "127.0.0.1:11210";
        weightListArr[0] = 1;
        //初始化默认配置
        serverConfig = new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;
            {
                put("initConn", "5"); //设置初始连接数
                put("minConn", "5"); //设置最小连接数
                put("maxConn", "250"); //设置最大连接数
                put("maxIdle", "21600000"); //设置连接最大空闲时间（6小时）
                put("maintSleep", "30"); //设置主线程的睡眠时间（30秒）
                put("socketTO", "10000"); //读取操作的超时限制（10秒）
                put("socketConnTO", "0"); //连接操作的超时限制（不限制）
                put("compressEnable", "true"); //是否启用自动压缩（启用）
            }
        };

        //获取配置数据
        ResourceUtil<MemCachedConfig> resourceUtil = new ResourceUtil<MemCachedConfig>();
        HashMap<String, MemCachedConfig> t = resourceUtil.getMemCachedConfig();
        try {
            MemCachedConfig config = resourceUtil.getT4True(t);
            if (null != config) {
                enUsed = true;
                //检测服务器是否可以成功连接,装载可用server节点
                List<MemCachedConfig.Server> serverUsed = new ArrayList<MemCachedConfig.Server>();
                TelnetClient telnet = new TelnetClient();
                telnet.setConnectTimeout(2000);
                for (MemCachedConfig.Server serverTmp : config.getServers()) {
                    try {
                        telnet.connect(serverTmp.getHost(), Integer.parseInt(serverTmp.getPort()));
                        telnet.disconnect();
                        serverUsed.add(serverTmp);
                    } catch (Exception e) {
                       // log.debug(serverTmp.getHost() + ":" + serverTmp.getPort() + "连接丢失...");
                    }
                }
                int serverCount = serverUsed.size();
                if (serverCount == 0) {
                    enUsed = false;
                    return;
                }
                //初始化服务器地址及端口号权重等数据
                serverListArr = new String[serverCount];
                weightListArr = new Integer[serverCount];
                for (int ind = 0; ind < serverCount; ind++) {
                    serverListArr[ind] = serverUsed.get(ind).getHost() + ":" + serverUsed.get(ind).getPort();
                    weightListArr[ind] = Integer.parseInt(serverUsed.get(ind).getWeight());
                }
                //替换默认配置数据
                Object[] serverConfigArr = serverConfig.keySet().toArray();
                for (Object cfgItem : serverConfigArr) {
                    MemCachedConfig.Config c = config.getConfig();
                    Field[] fields = c.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.getName().equals(cfgItem.toString())) {
                            serverConfig.put(cfgItem.toString(), field.get(c).toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void initConfig4Govern() {
    }

    public static boolean used() {
        return enUsed;
    }

}
