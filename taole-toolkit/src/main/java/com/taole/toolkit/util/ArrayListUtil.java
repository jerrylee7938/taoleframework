package com.taole.toolkit.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 *
 * @param <E>
 * @author CJ
 */
public class ArrayListUtil<E> {
    /**
     * 集合去重
     *
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<E> removeDuplicateWithOrder(List<E> list) {
        Set<E> set = new HashSet<E>();
        List<E> newList = new ArrayList<E>();
        for (Iterator<E> iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add((E) element))
                newList.add((E) element);
        }
        list.clear();
        list.addAll(newList);
        System.out.println(" remove duplicate " + list);
        return newList;
    }

    /**
     * @param list   要排序的集合
     * @param method 要排序的实体的属性所对应的get方法
     * @param sort   desc 为倒叙
     * @author CJ
     */
    public void Sort(List<E> list, final String method, final String sort) {
        // 用内部类实现排序  
        Collections.sort(list, new Comparator<E>() {
            public int compare(E a, E b) {
                int ret = 0;
                try {
                    // 获取m1的方法名  
                    Method m1 = a.getClass().getMethod(method, null);
                    // 获取m2的方法名  
                    Method m2 = b.getClass().getMethod(method, null);
                    if (sort != null && "desc".equals(sort)) {
                        ret = m2.invoke(((E) b), null).toString().compareTo(m1.invoke(((E) a), null).toString());
                    } else {
                        // 正序排序  
                        ret = m1.invoke(((E) a), null).toString().compareTo(m2.invoke(((E) b), null).toString());
                    }
                } catch (NoSuchMethodException ne) {
                    System.out.println(ne);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return ret;
            }
        });
    }

    /**
     * 集合是否为空
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List list) {
        return list != null && list.size() > 0 ? false : true;
    }

    public static String[] insert(String[] o1, String o) {
        if (null == o1 || o1.length == 0) o1 = new String[]{};
        String[] o2 = new String[o1.length + 1];
        for (int i = 0; i < o1.length; i++) {
            o2[i] = o1[i];
        }
        o2[o1.length] = o;
        return o2;
    }

    public static String[] insert(String[] o1, String... val) {
        if (null != val) {
            for (String v : val) {
                o1 = insert(o1, v);
            }
        }
        return o1;
    }

    public static String[] del(String as[], String delKey) {
        String[] oldA = as;
        String[] newA = null;
        if (null != as) {
            for (int i = 0; i < as.length; i++) {
                if (as[i].equals(delKey)) {
                    oldA[i] = null;
                }
            }
            int sum = 1;
            List<String> arrList = new ArrayList<>();
            for (int j = 0; j < oldA.length; j++) {
                if (StringUtils.isBlank(oldA[j])) sum++;
                else arrList.add(oldA[j]);
            }
            newA = new String[sum];
            newA = arrList.toArray(newA);
            return newA;
        }
        return null;
    }


    public List<E> Array2List(E... a) {
        Assert.notNull(a);
        List<E> eList = new ArrayList<>();
        for (E e : a) {
            eList.add(e);
        }
        return eList;
    }

    public static List Array2List4Object(Object... a) {
        Assert.notNull(a);
        List<Object> eList = new ArrayList<>();
        for (Object e : a) {
            eList.add(e);
        }
        return eList;
    }

    public static void main(String[] args) {
        String[] chkParamNames = {"123", "cj"};
        chkParamNames = insert(chkParamNames, "pppp");
        System.out.println(chkParamNames);
        for (String chkParamName : chkParamNames) {
            System.out.println(chkParamName);
        }
    }

    /**
     * 数组中是否包含某个字符串
     *
     * @param array
     * @param key
     * @return
     */
    public static boolean isContain(String[] array, String key) {
        if (null != array && StringUtils.isNotBlank(key))
            for (String s : array)
                if (s.equals(key)) return true;
        return false;
    }
}
