package com.taole.framework.util;

import java.util.ArrayList;
import java.util.List;


public class ThreadLocalFactory {

	static List<ThreadLocal<?>> threadLocals = new ArrayList<ThreadLocal<?>>();
	
    public static ThreadLocal<?> createThreadLocal() {
    	return createThreadLocal(Object.class);
    }

    public static <T> ThreadLocal<T> createThreadLocal(Class<?> T) {
    	ThreadLocal<T> tl = new ThreadLocal<T>();
    	threadLocals.add(tl);
    	return tl;
    }

    public static void cleanAllThreadLocals () {
    	for (ThreadLocal<?> tl: threadLocals) {
    		tl.remove();
    	}
    }

}
