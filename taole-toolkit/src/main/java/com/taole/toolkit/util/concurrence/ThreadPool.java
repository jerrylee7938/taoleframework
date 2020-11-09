package com.taole.toolkit.util.concurrence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.taole.toolkit.util.biz.ConvertUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author ChengJ
 * @version 1.0
 * @Date 2016/12/2
 * @desc 工作队列管理中心
 */
public class ThreadPool extends ThreadGroup {
    private Log LOGGER = LogFactory.getLog(getClass());
    private boolean IS_CLOSED = false;
    private LinkedList<Runnable> WORK_QUERY;// 工作队列
    private static int THREAD_POLL_ID;// 表示线程池ID
    private int THREAD_ID;// 表示工作线程ID

    public ThreadPool(int poolSize) {
        super("ThreadPool-" + (THREAD_POLL_ID++));
        setDaemon(true);
        WORK_QUERY = new LinkedList<Runnable>();// 创建并启动工作线程
        for (int i = 0; i < poolSize; i++) {
            new WorkThread().start();
        }
        LOGGER.debug("线程池初始化完毕！");
    }

    /**
     * 向工作队列中加入一个新任务，由工作线程去执行
     *
     * @param task
     */
    public void execute(Runnable task) {
        synchronized (this) {
            if (IS_CLOSED) {
                throw new IllegalStateException();
            }
            if (task != null) {
                WORK_QUERY.add(task);
                LOGGER.debug(" 工作线程开始开始执行！");
                notify();//
            }
        }

    }

    /**
     * 从工作队列中取出一个任务，工作线程会调用此方法
     *
     * @return
     * @throws InterruptedException
     */
    protected synchronized Runnable getTask() throws InterruptedException {
        while (WORK_QUERY.size() == 0) {
            if (IS_CLOSED)
                return null;
            LOGGER.debug("等待任务中！...");
            wait();
        }
        return WORK_QUERY.removeFirst();
    }

    /**
     * 关闭线程池
     */
    public synchronized void close() {
        if (!IS_CLOSED) {
            IS_CLOSED = true;
            WORK_QUERY.clear();
            interrupt();
        }

    }

    /**
     * 等待工作线程把所有任务执行完
     */
    public void join() {
        synchronized (this) {
            IS_CLOSED = true;
            LOGGER.debug("唤醒还在getTask()方法中等待任务的工作线程");
            notifyAll();
        }

        Thread[] thread = new Thread[activeCount()];
        int count = enumerate(thread);// 获得当前所有或者的工作线程
        for (int i = 0; i < count; i++) {// 等待所有的线程运行起来
            try {
                thread[i].join();// 等待所有工作线程运行结束
            } catch (InterruptedException e) {
            }

        }

    }

    /**
     * 执行工作线程
     */
    private class WorkThread extends Thread {
        public WorkThread() {
            super(ThreadPool.this, "WorkThread-" + (THREAD_ID++));
        }

        public void run() {
            while (!isInterrupted()) {// 判断线程是否被中断
                Runnable task = null;
                try {
                    task = getTask();
                } catch (InterruptedException e) {
                }
                if (task == null)
                    return;// 如果getTask()返回Null或者线程执行getTask()被中断，则结束此线程
                try {
                    task.run();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            runnableList.add(new Runnable() {
                @Override
                public void run() {
                    System.out.println(123);
                }

                ;
            });
        }
        EchoThreadManager excuteTer = EchoThreadManager.getInstance();
        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runnable[] runnables = new Runnable[10];
        for (int i = 0; i < runnableList.size(); i++)
            runnables[i] = runnableList.get(i);
        excuteTer.joinTask(runnables);
    }

}