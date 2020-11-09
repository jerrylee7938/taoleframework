package com.taole.toolkit.util.concurrence;

import java.io.Serializable;

/**
 * 线程总线管理器
 *
 * @author ChengJ-Portal
 */
public interface ThreadPoolManager extends Serializable {
    int POOL_SIZE = 4;// 单个Cpu时线程池中抢占资源数目

    ThreadPool getThreadPool();

    void joinTask(Runnable task);

    void joinTask(Runnable... task);
}
