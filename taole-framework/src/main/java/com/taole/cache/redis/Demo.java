package com.taole.cache.redis;

import com.taole.cache.ExcuteType;

public class Demo {

    @TaoleCache(tp = ExcuteType.LOCK)
    TaoleCacheApi<String> lock;

    @TaoleCache(tp = ExcuteType.HOT)
    TaoleCacheApi<String> hotApi;

    //若业务节点授权配置中三种类型都配，这里默认不指定，按照注入优先级注入使用类型
    @TaoleCache
    TaoleCacheApi<String> coldApi;

    public void biz(String[] args) {
        hotApi.del("loan-123");
        lock.get("");//运行报错
    }
    public void biz2(String[] args) {
        //配置中如果指定了数据类型为hot，cold，lock
        hotApi.lock("loan-123", 1000);
    }

}
