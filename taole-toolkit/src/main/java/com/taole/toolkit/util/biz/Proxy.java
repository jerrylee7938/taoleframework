package com.taole.toolkit.util.biz;

import java.lang.reflect.Method;

/**
 * 动态执行代理对象
 */
public class Proxy {
    private Object target;
    private String methodName;
    private Method method;
    private Object[] param;
    private Class[] paramType;
    private String biz;
    private String bizObject;


    public Object getTarget() {
        return target;
    }

    public Proxy setTarget(Object target) {
        this.target = target;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getMethod() {
        return method;
    }

    public Proxy setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Proxy setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Object[] getParam() {
        return param;
    }

    public Proxy setParam(Object[] param) {
        this.param = param;
        return this;
    }

    public Class[] getParamType() {
        return paramType;
    }

    public Proxy setParamType(Class[] paramType) {
        this.paramType = paramType;
        return this;
    }

    public String getBiz() {
        return biz;
    }

    public String getBizObject() {
        return bizObject;
    }

    public Proxy setBizObject(String bizObject) {
        this.bizObject = bizObject;
        return this;
    }

    public Proxy setBiz(String biz) {
        this.biz = biz;
        return this;
    }

    public static Proxy getProxy(Object target, String methodName, Object... param) {
        Proxy proxy = new Proxy();
        if (null == target) return null;
        proxy.setTarget(target);
        proxy.setParam(param);
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                proxy.setMethod(method);
                proxy.setParamType(method.getParameterTypes());
                break;
            }
        }
        return proxy;
    }

    public static Proxy getProxy(Object target, Method method, Object... param) {
        Proxy proxy = new Proxy();
        if (null == target) return null;
        proxy.setTarget(target);
        proxy.setParam(param);
        proxy.setMethod(method);
        return proxy;
    }

    public Object excute() throws Exception {
        Object cl = this.getTarget();
        Object[] param = this.getParam();
        Method target = null;
        if (null != this.getMethod()) target = this.getMethod();
        else
            target = cl.getClass().getMethod(this.getMethodName(), this.getParamType());
        if (!target.isAccessible())
            target.setAccessible(true);
        return target.invoke(cl, param);
    }

}