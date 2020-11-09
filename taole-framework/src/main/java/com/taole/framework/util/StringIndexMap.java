/*
 * @(#)StringIndexMap.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.util.HashMap;
import java.util.Map;


/**
 * Class:	FastStringMap
 * Remark:	TODO
 * @author: 
 */
public class StringIndexMap {
    
    // ~ Instance fields ====================================================================================

    private Map<String, Map<String, Object>> mapPool = new HashMap<String, Map<String, Object>>();

    private int level = 2;
    
    // ~ Constructors =======================================================================================

    public StringIndexMap() {
    }

    public StringIndexMap(int level) {
        this.level = level;
    }
 
    // ~ Methods ============================================================================================

    /**
     * @param name
     * @return
     */
    private synchronized Map<String, Object> getMap(final String name) {
        if (mapPool.containsKey(name)) {
            return mapPool.get(name);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            mapPool.put(name, map);
            return map;
        }
    }

    /**
     * @param key
     * @return
     */
    private String getMapName(String key) {
        if (key == null || key.length() < 6 || key.length() < level) {
            return "";
        } else {
            return key.substring(0, level);
        }
    }

    /**
     * @param key
     * @param value
     */
    public synchronized Object put(String key, Object value) {
        Map<String, Object> map = getMap(getMapName(key));
        return map.put(key, value);
    }

    /**
     * @param key
     * @return
     */
    public synchronized Object get(String key) {
        Map<String, Object> map = getMap(getMapName(key));
        return map.get(key);
    }

    /**
     * @param key
     * @return
     */
    public synchronized Object remove(String key) {
        Map<String, Object> map = getMap(getMapName(key));
        return map.remove(key);
    }

    /**
     * 清除Map
     */
    public synchronized void clear() {
        mapPool.clear();
    }

    /**
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        Map<String, Object> map = getMap(getMapName(key));
        return map.containsKey(key);
    }

    /**
     * @param value
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    public boolean containsValue(Object value) {
        Object[] maps = mapPool.values().toArray();
        for (int i = 0; i < maps.length; i++) {
            if (((Map) maps[i]).containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return
     */
    @SuppressWarnings("rawtypes")
    public int size() {
        Object[] maps = mapPool.values().toArray();
        int count = 0;
        for (int i = 0; i < maps.length; i++) {
            count += ((Map) maps[i]).size();
        }
        return count;
    }


}

