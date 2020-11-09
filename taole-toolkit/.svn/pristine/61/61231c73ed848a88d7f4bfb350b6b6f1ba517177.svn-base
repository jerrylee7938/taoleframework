package com.taole.toolkit.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 过滤字符串数组中的空格
     *
     * @param strs
     * @return
     */
    public static String[] filterBlank(String[] strs) {
        List<String> strList = new ArrayList<String>();
        for (String str : strs) {
            if (StringUtils.isNotBlank(str)) {
                strList.add(str);
            }
        }

        if (strList.size() > 0) {
            String[] newStrs = new String[strList.size()];
            for (int i = 0; i < strList.size(); i++) {
                newStrs[i] = strList.get(i);
            }
            return newStrs;
        } else {
            return null;
        }
    }

    /**
     * 过滤字符串数组中的空格及指定字符串
     *
     * @param strs       要进行过滤的字符串
     * @param filterStrs 数组中不能包括的字符串
     * @return
     */
    public static String[] filter(String[] strs, String[] filterStrs) {
        // 先将非法字符串整理到map中，作为后面流程的字典
        Map<String, String> filterMap = new HashMap<String, String>();
        for (String fltStr : filterStrs) {
            filterMap.put(fltStr, fltStr);
        }

        // 开始过滤，将满足条件的合法字符串放入strList
        List<String> strList = new ArrayList<String>();
        for (String str : strs) {
            if (StringUtils.isNotBlank(str) && filterMap.get(str) == null) {
                strList.add(str);
            }
        }

        // 将收集到的合法list转化为字符串数组
        if (strList.size() > 0) {
            String[] newStrs = new String[strList.size()];
            for (int i = 0; i < strList.size(); i++) {
                newStrs[i] = strList.get(i);
            }
            return newStrs;
        } else {
            return null;
        }
    }

    /**
     * 批量替换字符串关键字
     */
    public static String replaceByMap(String str, Map<String, String> replaceMap) {
        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }

    /**
     * 判断字符串是否是纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            // System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果字符串数组只有一个，以分割符方式形成新数组，该方法用于对请求参数值的兼容性处理
     * 有的是id=1&id=2&id=3；有的是id=1,2,3
     *
     * @param strs
     * @return
     */
    public static String[] paramsStrs(String[] strs, String separator) {
        if (strs != null && strs.length == 1) {
            String str = strs[0];
            if (str.indexOf(separator) > 0) {
                strs = str.split(separator);
            }
        }
        return strs;
    }

    public static String replaceBlank(String str) {

        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }

        return dest;

    }

    public static void main(String[] args) {
        String str = "1";
        String[] strs = new String[]{"1", "2"};
        String[] newStrs = paramsStrs(strs, ",");
        for (String newStr : newStrs) {
            System.out.println(newStr);
        }
        System.out.println(replaceBlank("just do it!"));
    }
}
