package com.taole.toolkit.util.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class LoggerWapper<LOG> {
    private Log debugLogger = null;
    private Log errorLogger = null;
    private Log infoLogger = null;
    private static Map<String, Object> INSS = new HashMap<>();

    public LoggerWapper() {
        super();
        loadLogger(((Class<LOG>) getClass()).getSimpleName());
    }


    protected static <T> T INS(Class<T> t, ServletContext webContext) {
        return ConvertUtil.SpringUtils.getBean(t, webContext);
    }


    public static <LOG> LOG INS(Class<LOG> clazz) {
        try {
            Constructor[] constructors = clazz.getDeclaredConstructors();
            INSS.put(clazz.getSimpleName(), (LOG) constructors[0].newInstance());
            return (LOG) INSS.get(clazz.getSimpleName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 装载系统使用的log
     */
    void loadLogger(String logName) {
        debugLogger = LogFactory.getFactory().getInstance(logName);
        infoLogger = LogFactory.getFactory().getInstance(logName);
        errorLogger = LogFactory.getFactory().getInstance(logName);
    }

    /**
     * @param msg: error级别的错误信息
     */
    public void error(Object msg) {
        errorLogger.error(msg);
    }

    /**
     * @param e: error级别的异常信息
     */
    public void error(Exception e) {
        errorLogger.error(getExceptionTrace(e));
    }

    /**
     * @param e:   error级别的异常信息
     * @param msg: error级别的错误信息
     */
    public void error(Exception e, Object msg) {
        errorLogger.error(msg + "\n" + getExceptionTrace(e));
    }

    /**
     * @param msg: debug级别的错误信息
     */
    public void debug(Object msg) {
        debugLogger.debug(msg);
    }

    /**
     * @param e: debug级别的异常信息
     */
    public void debug(Exception e) {
        debugLogger.debug(getExceptionTrace(e));
    }

    /**
     * @param e:   debug级别的异常信息
     * @param msg: debug级别的错误信息
     */
    public void debug(Exception e, Object msg) {
        debugLogger.debug(msg + "\n" + getExceptionTrace(e));
    }

    /**
     * @param msg: info级别的错误信息
     */
    public void infoLog(Object msg) {
        infoLogger.info("tId:" + Thread.currentThread().getId() + "," + msg);
    }

    /**
     * @param e: info级别的异常信息
     */
    public void infoError(Exception e) {
        infoLogger.info(getExceptionTrace(e));
    }

    /**
     * @param e:   debug级别的异常信息
     * @param msg: debug级别的错误信息
     */
    public void system(Exception e, Object msg) {
        infoLogger.info(msg + "\n" + getExceptionTrace(e));
    }

    /**
     * @param e: 异常信息输出
     */
    public void exOut(Exception e) {
        String print = getExceptionTrace(e);
        errorLogger.error(print);
    }

    /**
     * @param e: debug级别的异常信息
     */
    private String getExceptionTrace(Exception e) {
        String print = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintWriter wrt = new PrintWriter(bout);
        e.printStackTrace(wrt);
        wrt.close();
        print = bout.toString();
        return print;
    }
}
