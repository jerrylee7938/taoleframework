package com.taole.toolkit.vo;

import com.taole.framework.util.UUID;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 轻量file模型供给前台使用
 */
public class RetFileSrc {
    private String diskPath;
    private String urlPath;

    public String getDiskPath() {
        return diskPath;
    }

    public RetFileSrc setDiskPath(String diskPath) {
        this.diskPath = diskPath;
        return this;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public RetFileSrc setUrlPath(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }

    public static void main(String[] args) {
        //  System.out.println(UUID.generateUUID());
    }



    private void e3(String s) {
        System.out.println(s);

    }
/*
    private void e2(String s1) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        System.out.println(s1);
        e3(s1);
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stack[2];
        String className = stackTraceElement.getClassName();
        stackTraceElement.
        Class cl = Class.forName(className);
        String methodName = stackTraceElement.getMethodName();
        Method[] methods = cl.getDeclaredMethods();
        Method target = null;
        Class[] paramTypes = null;
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                target = m;
                break;
            }
        }
        if (null == target) return;
        Class[] params = target.getParameterTypes();
        paramTypes = new Class[params.length];
        for (int j = 0; j < params.length; j++) {
            paramTypes[j] = Class.forName(params[j].getName());
        }
        boolean before = target.isAccessible();
        if (!before)
            target.setAccessible(true);
        Object invoke = target.invoke(cl, new String[]{"123"});
    }*/
}