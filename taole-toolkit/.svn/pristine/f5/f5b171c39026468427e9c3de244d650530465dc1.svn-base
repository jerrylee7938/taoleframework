package com.taole.toolkit.util;

import com.taole.framework.util.UUID;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdUtils {
    /**
     * 这里需要改造成分布式的
     *
     * @return
     */
    public static String generateId() {
        String str = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String gid = str.substring(3, str.length());
        return gid;
    }

    public static String generateIdNotSub() {
        String str = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return str;
    }

    public static String generateIdV2() {
        String str = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return str;
    }

    public static void main(String[] args) {
        System.out.println(generateIdV2());
    }
}
