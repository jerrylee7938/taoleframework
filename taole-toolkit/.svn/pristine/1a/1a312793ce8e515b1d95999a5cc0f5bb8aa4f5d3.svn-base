package com.taole.toolkit.util.concurrence;

/**
 * 线程回调工具
 *
 * @param <T>
 */
public abstract class GeneralThread<T> implements Runnable {
    public abstract void callBack(T target);

    private T bundle;

    private GeneralThread() {
        super();
    }

    public GeneralThread(T bundle) {
        this();
        this.bundle = bundle;
    }

    @Override
    public void run() {
        callBack(bundle);
    }
}
