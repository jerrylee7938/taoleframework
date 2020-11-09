package com.taole.cache.memcached;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by ChengJ on 2017/5/2.
 */
public class MemServerGovern extends MemWrapper4Super {
    private static MemServerGovern govern;
    private static HashMap<GOVERN, String> statsItems;

    public enum GOVERN {
        PID, VERSION, POINTER_SIZE, TIME, UPDATE, CONNECTION_STRUCTURES, TOTAL_CONNECTIONS, CURR_CONNECTIONS, LIMIT_MAXBYTE, BYTES,
        BYTES_WRITEN, BYTES_READ, TOTAL_ITEMS, CURR_ITEMS, CMD_ITEMS, CMD_GET, GET_HITS, GET_MISSES, CMD_SET
    }

    private MemServerGovern() {
        super();
        initConfig4Govern();
    }

    public static MemServerGovern getInstance() {
        if (govern == null) govern = new MemServerGovern();
        return govern;
    }

    public void initConfig4Govern() {
        super.initConfig4Govern();
        statsItems = new HashMap<GOVERN, String>() {
            {
                put(GOVERN.PID, "MemCached服务进程ID");
                put(GOVERN.VERSION, "MemCached服务版本");
                put(GOVERN.POINTER_SIZE, "MemCached服务器架构");
                put(GOVERN.TIME, "服务器当前时间");
                put(GOVERN.UPDATE, "服务器本次启动以来，总共运行时间");
                put(GOVERN.CONNECTION_STRUCTURES, "服务器分配的连接结构数");
                put(GOVERN.TOTAL_CONNECTIONS, "服务器本次启动以来，累计响应连接总次数");
                put(GOVERN.CURR_CONNECTIONS, "当前打开的连接数");
                put(GOVERN.LIMIT_MAXBYTE, "允许服务支配的最大内存容量");
                put(GOVERN.BYTES, "当前已使用的内存容量");
                put(GOVERN.BYTES_WRITEN, "服务器本次启动以来，写入的数据量");
                put(GOVERN.BYTES_READ, "服务器本次启动以来，读取的数据量");
                put(GOVERN.TOTAL_ITEMS, "服务器本次启动以来，曾存储的Item总个数");
                put(GOVERN.CURR_ITEMS, "当前存储的Item个数");
                put(GOVERN.CMD_GET, "服务器本次启动以来，执行Get命令总次数");
                put(GOVERN.GET_HITS, "服务器本次启动以来，Get操作的命中次数");
                put(GOVERN.GET_MISSES, "服务器本次启动以来，Get操作的未命中次数");
                put(GOVERN.CMD_SET, "服务器本次启动以来，执行Set命令总次数");
            }
        };
    }

    /**
     * @return 操作结果
     * @category 清空全部缓存数据。*慎用！！
     * @category 执行该方法之后，使用stats的统计结果不会马上发生变化，每get一个不存在的item之后，该item的值才会被动清空
     */
    public boolean flushAll() {
        if (!enUsed) {
            return false;
        } else {
            return memClient.flushAll();
        }
    }

    /**
     * @return 数组（服务器地址:端口）
     * @category 返回可用的服务器列表
     */
    public String[] servers() {
        if (!enUsed) return null;
        return serverListArr;
    }

    /**
     * 返回所有缓存服务器当前的运行状态
     *
     * @return
     */
    public Map<String, LinkedHashMap<String, String>> stats() {
        if (!enUsed) return null;
        Map<String, LinkedHashMap<String, String>> retMap = new HashMap<String, LinkedHashMap<String, String>>();
        for (String server : serverListArr) {
            LinkedHashMap<String, String> serverStats = this.stats(server);
            retMap.put(server, serverStats);
        }
        return retMap;
    }

    /**
     * @param server 服务器地址:端口
     *               <p>
     *               优化： 参数名称中文显示
     *               优化： 毫秒数转换为小时
     *               优化： 字节数转换为MB或KB
     *               优化： UNIX时间戳转换为标准时间
     *               优化： 参数显示顺序更加直观
     * @return LinkedHashMap<String, String> 可对Map进行有序遍历
     * @category 返回指定服务器当前的运行状态
     */
    public LinkedHashMap<String, String> stats(String server) {
        if (!enUsed) return null;
        LinkedHashMap<String, String> retMap = new LinkedHashMap<String, String>();
        Map<String, Map<String, String>> statsList = memClient.stats(new String[]{server});
        //System.out.println(memClient.stats().toString());
        DecimalFormat format = new DecimalFormat("0.0");
        for (Object serverTitle : statsList.keySet().toArray()) {
            Map<String, String> serverStats = (Map<String, String>) statsList.get(serverTitle);
            retMap.put(statsItems.get(GOVERN.PID), serverStats.get("pid").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.VERSION), serverStats.get("version").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.POINTER_SIZE), serverStats.get("pointer_size").replaceAll("\\r\\n", "") + "位");
            retMap.put(statsItems.get(GOVERN.TIME), new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(serverStats.get("time").replaceAll("\\r\\n", "")) * 1000)).toString());
            retMap.put(statsItems.get(GOVERN.UPDATE), format.format(Double.parseDouble(serverStats.get("uptime").replaceAll("\\r\\n", "")) / (60 * 60)) + "小时");
            retMap.put(statsItems.get(GOVERN.CONNECTION_STRUCTURES), serverStats.get("connection_structures").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.TOTAL_CONNECTIONS), serverStats.get("total_connections").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.CURR_CONNECTIONS), serverStats.get("curr_connections").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.LIMIT_MAXBYTE), format.format(Double.parseDouble(serverStats.get("limit_maxbytes").replaceAll("\\r\\n", "")) / (1024 * 1024)) + "MB");
            retMap.put(statsItems.get(GOVERN.BYTES), format.format(Double.parseDouble(serverStats.get("bytes").replaceAll("\\r\\n", "")) / (1024 * 1024)) + "MB");
            retMap.put(statsItems.get(GOVERN.BYTES_WRITEN), format.format(Double.parseDouble(serverStats.get("bytes_written").replaceAll("\\r\\n", "")) / (1024)) + "KB");
            retMap.put(statsItems.get(GOVERN.BYTES_READ), format.format(Double.parseDouble(serverStats.get("bytes_read").replaceAll("\\r\\n", "")) / (1024)) + "KB");
            retMap.put(statsItems.get(GOVERN.TOTAL_ITEMS), serverStats.get("total_items").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.CURR_ITEMS), serverStats.get("curr_items").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.CMD_GET), serverStats.get("cmd_get").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.GET_HITS), serverStats.get("get_hits").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.GET_MISSES), serverStats.get("get_misses").replaceAll("\\r\\n", ""));
            retMap.put(statsItems.get(GOVERN.CMD_SET), serverStats.get("cmd_set").replaceAll("\\r\\n", ""));
        }
        return retMap;
    }

    /**
     * @param server  服务器地址:端口
     * @param slab    SlabId
     * @param counter 最多显示items条数
     * @return
     * @category 返回指定服务器及Slab中当前使用的item列表
     */
    public LinkedList<String> items(String server, int slab, int counter) {
        if (!enUsed) return null;
        LinkedList<String> ret = new LinkedList<String>();
        Map<String, String> itemsKey = memClient.statsCacheDump(new String[]{server}, slab, counter).get(server);
        for (Object key : itemsKey.keySet().toArray()) {
            ret.add(key.toString());
        }
        return ret;
    }

    /**
     * @param server 服务器地址:端口
     * @return
     * @category 返回指定服务器当前使用的SlabsID列表
     */
    public LinkedList<Integer> slabs(String server) {
        if (!enUsed) return null;
        LinkedList<Integer> slabsId = new LinkedList<Integer>();
        Map<String, String> itemsMap = memClient.statsItems(new String[]{server}).get(server);
        Object[] itemsArr = itemsMap.keySet().toArray();
        for (int i = 0, len = itemsArr.length; i < len; i += 2) {
            slabsId.add(Integer.parseInt(itemsArr[i].toString().split(":")[1]));
        }
        return slabsId;
    }

    /*
     * 上面的方法都是为了对memcached服务器进行监控及管理所用的，可能对服务器造成阻塞，所以除Debug以外，不推荐使用！
     * ****************************************************************************************************************
     */

}
