/**
 * Project:taole-heaframework
 * File:CloneUtils.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Rory
 * @date Mar 24, 2012
 * @version $Id: CloneUtils.java 5 2014-01-15 13:55:28Z yedf $
 */
public final class CloneUtils {

	private CloneUtils() {
	}
	
	/**
	 * 克隆对象，如果对象不支持则返回 {@code null}
	 * @param obj
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static Object cloneQuietly(final Object obj) throws CloneNotSupportedException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Cloneable) {
            Class<?> clazz = obj.getClass ();
            Method m;
            try {
                m = clazz.getMethod("clone", (Class[]) null);
            } catch (NoSuchMethodException ex) {
                return null;
            }
            try {
                return m.invoke(obj, (Object []) null);
            } catch (InvocationTargetException ex) {
                return null;
            } catch (IllegalAccessException ex) {
            	return null;
            }
        } else {
        	return null;
        }
    }
}
