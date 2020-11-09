package com.taole.toolkit.util.biz;

import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.toolkit.dict.domain.Dictionary;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.ArrayListUtil;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 业务抽象工具类
 *
 * @param <T>
 * @param <V>
 */
@SuppressWarnings("ALL")
public abstract class ConvertUtil<T, V> {
    /*在vo处理逻辑是动态回调，显示继承制 静态代理*/
    public abstract void excute(V v);

    protected void excute(T t, V v) {
        System.out.println("...");
    }

    private static Log log = LogFactory.getLog(ConvertUtil.class);

    public static class StringUtils {
        /**
         * @param s 字符串
         * @return false:空 true:不为空
         */
        public static boolean isNotEmpty(String s) {
            return s == null || s.trim().length() == 0 || "".trim().equals(s.trim()) || "null".trim().equals(s.trim()) ? false : true;
        }

        public static boolean isEmpty(String s) {
            return s == null || s.trim().length() == 0 || "".trim().equals(s.trim()) || "null".trim().equals(s.trim()) ? true : false;
        }

        static String ISO = "ISO-8859-1";
        static String UTF = "UTF-8";

        // 转换编码：
        public static String encodeString(String value) {
            String retval = null;
            try {
                retval = new String(value.getBytes(UTF), ISO);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return retval;
        }

        /**
         * 解码
         *
         * @param value
         * @return
         */
        public static String decodeString(String value) {
            if (org.apache.commons.lang3.StringUtils.isBlank(value)) return null;
            String retval = null;
            try {
                retval = new String(value.getBytes(ISO), UTF);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return retval;
        }

        /**
         * 过滤Emoji字符
         *
         * @param str
         * @return
         */
        public static String removeEmojiUnicode(String str) {
            if (str == null) {
                return null;
            }
            str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
            return str;
        }

        /**
         * 格式化货币
         *
         * @param money
         * @return
         */
        public static String FormatMoney(Double money) {
            //   NumberFormat nf = new DecimalFormat("#,###.####");
            // NumberFormat nf = new DecimalFormat("$,###.###..");
            NumberFormat currencyFormatA = NumberFormat
                    .getCurrencyInstance(Locale.SIMPLIFIED_CHINESE);
            if (null == money) return null;
            String moneyD = currencyFormatA.format(money);
            return moneyD;
        }

        /**
         * 格式化stirng类型，money转化为double
         *
         * @param moneyStr
         * @return
         */
        public static Double FormatMoney2Double(String moneyStr) {
            Objects.requireNonNull(moneyStr, "格式化stirng类型，money转化为double,转化数据不能为空");
            Double money = Double.parseDouble(moneyStr.replaceAll(",", ""));
            return money;
        }


        /**
         * 乱码转化
         *
         * @param codeStr
         * @return
         */
        public static String convertCode(String codeStr) {
            if (StringUtils.isNotEmpty(codeStr)) {
                try {
                    codeStr = new String(codeStr.getBytes("ISO8859-1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return codeStr;
        }
    }

    /**
     * Spring上下文获取工具（仅限web服务环境）
     */
    public static class SpringUtils {
        public static ApplicationContext getAppContext(HttpServletRequest request) {
            return getAppContext(request.getServletContext());
        }

        public static ApplicationContext getAppContext(ServletContext webContext) {
            return WebApplicationContextUtils.getWebApplicationContext(webContext);
        }

        public static <T> T getBean(Class<T> tClass, HttpServletRequest request) {
            return getBean(tClass, getAppContext(request));
        }

        public static <T> T getBean(String beanName, Class<T> tClass, HttpServletRequest request) {
            return getBean(beanName, tClass, getAppContext(request));
        }

        public static <T> T getBean(Class<T> tClass, ServletContext webContext) {
            return getBean(tClass, getAppContext(webContext));
        }

        public static <T> T getBean(String beanName, Class<T> tClass, ServletContext webContext) {
            return getBean(beanName, tClass, getAppContext(webContext));
        }

        public static <T> T getBean(Class<T> tClass, ApplicationContext context) {
            return context.getBean(tClass);
        }

        public static <T> T getBean(String beanName, Class<T> tClass, ApplicationContext context) {
            return context.getBean(beanName, tClass);
        }

        public static <T> T getBean(String beanName, Class<T> tClass) {
            if (null == RestSessionFactory.getCurrentContext().getHttpServletRequest())
                throw new RuntimeException("请求线程入口必须是Taole->RestServer机制！");
            return getBean(beanName, tClass, getAppContext(RestSessionFactory.getCurrentContext().getHttpServletRequest()));
        }

        public static <T> T getBean(Class<T> tClass) {
            if (null == RestSessionFactory.getCurrentContext().getHttpServletRequest())
                throw new RuntimeException("请求线程入口必须是Taole->RestServer机制调入！");
            return getBean(tClass, getAppContext(RestSessionFactory.getCurrentContext().getHttpServletRequest()));
        }
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }


    public static class BeanUtil {
        public static class ClassVo {
            Class<?> calss;
            String calssPath;

            public ClassVo(Class<?> calss, String calssPath) {
                this.calss = calss;
                this.calssPath = calssPath;
            }

            public Class<?> getCalss() {
                return calss;
            }

            public void setCalss(Class<?> calss) {
                this.calss = calss;
            }

            public String getCalssPath() {
                return calssPath;
            }

            public void setCalssPath(String calssPath) {
                this.calssPath = calssPath;
            }
        }

        /**
         * 根据路径获取所有class（基于Spring）
         * com/abc
         *
         * @param url
         * @return
         */
        public static List<ClassVo> getClassPaths4Url(String url) {
            List<ClassVo> list = new ArrayList<>();
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
            Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents("com/abc");
            for (BeanDefinition beanDefinition : beanDefinitions) {
                System.out.println(beanDefinition.getBeanClassName()
                        + "\t" + beanDefinition.getResourceDescription()
                        + "\t" + beanDefinition.getClass());
                list.add(new ClassVo(beanDefinition.getClass(), beanDefinition.getBeanClassName()));
            }
            return list;
        }

        /**
         * 获取指定方法
         *
         * @param tatget
         * @param methodName
         * @param types
         * @return
         */
        public static Method getMethod(Object tatget, String methodName, Class... types) {
            try {
                Method method = tatget.getClass().getMethod(methodName, types);
                return method;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static <T> T C(Class<T> tClass) {
            return C(tClass, null);
        }

        public static <T> T C(Class<T> tClass, Object[] args, Class... argsTypes) {
            try {
                Constructor<T> constructor = tClass.getDeclaredConstructor(argsTypes);
                constructor.setAccessible(true);
                return constructor.newInstance(args);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * domain和vo直接转换（json操作）
     *
     * @param ps
     * @return
     */
    public JSONArray toJsonArrayUtil(PaginationSupport<T> ps, Object objectVo) {
        ArrayList<V> Vos = null;
        if (!ArrayListUtil.isEmpty(ps.getItems())) {
            Vos = new ArrayList();
            for (T t : ps.getItems()) {
                V v = null;
                try {
                    v = (V) objectVo.getClass().newInstance();
                    ConvertUtil.BeanToBean(t, v);
                    excute(v);
                    Vos.add(v);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        PaginationSupport<V> ps1 = new PaginationSupport<V>();
        ConvertUtil.BeanToBean(ps, ps1);
        ps1.setItems(Vos);
        JSONArray newArray = new JSONArray();
        pageIngConvert(ps1, newArray);
        return newArray;
    }

    /**
     * pageBean转化JSon
     *
     * @param ps
     * @param newArray
     */
    public static void pageIngConvert(PaginationSupport ps, JSONArray newArray) {
        ResultSet rs = new ResultSet(ps);
        JSONArray jsonArray = (JSONArray) JSONTransformer.convertRow2Json(rs);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = null;
            try {
                jo = (JSONObject) jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newArray.put(jo);
        }
    }

    /**
     * 字典工具
     */
    public static class NodeUtils {
        /**
         * 获取某字典下要获取的子节点对应的name
         *
         * @param codeMian    字典code
         * @param valueOfCode 字典下要获取的对应code的值
         * @return
         */
        public static String getNods(String codeMian, String valueOfCode, DictionaryManager dictionaryManager) {
            if (StringUtils.isNotEmpty(codeMian)) {
                Map<String, DictionaryManager.DictNode> stringDictNodeMap = dictionaryManager.getDictNods(codeMian);
                if (stringDictNodeMap != null) {
                    return (stringDictNodeMap.get(valueOfCode) != null ? stringDictNodeMap.get(valueOfCode).getName() : null);
                }
            }
            return null;
        }

        /**
         * 获取某字典下要获取的子节点对应的value
         *
         * @param codeMian    字典code
         * @param valueOfCode 字典下要获取的对应code的值
         * @return
         */
        public static String getNodeBy2Val(String codeMian, String valueOfCode, DictionaryManager dictionaryManager) {
            if (StringUtils.isNotEmpty(codeMian)) {
                Map<String, DictionaryManager.DictNode> stringDictNodeMap = dictionaryManager.getDictNods(codeMian);
                if (stringDictNodeMap != null) {
                    return (stringDictNodeMap.get(valueOfCode) != null ? stringDictNodeMap.get(valueOfCode).getValue() : null);
                }
            }
            return null;
        }

        /**
         * @param code
         * @param dictionaryManager
         * @return
         */
        public static String getNode(String code, DictionaryManager dictionaryManager) {
            Dictionary dictionary = dictionaryManager.getDictionary(code);
            if (null != dictionary) return dictionary.getValue();
            return null;
        }

    }

    /**
     * 检查手机格式是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        System.out.println(m.matches() + "---");

        return m.matches();

    }


    /**
     * 反射赋值
     *
     * @param mField
     * @param Bean
     * @param object
     * @throws IllegalAccessException
     */
    public static void setValue(Field mField, Object Bean, Object object) throws IllegalAccessException {
        String args = null;
        if (object != null) {
            args = object.toString();
            if (StringUtils.isEmpty(args)) return;
        }
        mField.setAccessible(true);
        String className = mField.getType().getSimpleName();
        switch (className) {
            case "Double":
                mField.set(Bean, args != null ? Double.parseDouble(args) : null);
                break;
            case "Integer":
                mField.set(Bean, args != null ? Integer.parseInt(args) : null);
                break;
            case "Float":
                mField.set(Bean, args != null ? Float.parseFloat(args) : null);
                break;
            case "Short":
                mField.set(Bean, args != null ? Short.parseShort(args) : null);
                break;
            case "Long":
                mField.set(Bean, args != null ? Long.parseLong(args) : null);
                break;
            case "String":
                mField.set(Bean, args != null ? args.toString() : null);
                break;
            case "Date":
                try {
                    mField.set(Bean, object);
                } catch (Exception ex) {
                    try {
                        mField.set(Bean, DateUtils.getFormatDate(args, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
                    } catch (Exception e2) {
                        mField.set(Bean, DateUtils.getFormattedString(object, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
                    }
                }
                break;
            default:
                mField.set(Bean, object);
                break;
        }
    }

    public static class DateUtils {
        /**
         * 将指定字符串转换成日期
         *
         * @param date        String 日期字符串
         * @param datePattern String 日期格式
         * @return Date
         */
        public static Date getFormatDate(String date, String datePattern) {
            SimpleDateFormat sd = new SimpleDateFormat(datePattern);
            if (StringUtils.isNotEmpty(date))
                try {
                    return sd.parse(date);
                } catch (ParseException e) {
                    return DateUtil.StringToDate(date);
                }
            return null;
        }

        /**
         * 将指定日期对象转换成格式化字符串
         *
         * @param date        Date XML日期对象
         * @param datePattern String 日期格式
         * @return String
         */
        public static String getFormattedString(Date date, String datePattern) {
            if (null == date || org.apache.commons.lang3.StringUtils.isBlank(datePattern)) return null;
            SimpleDateFormat sd = new SimpleDateFormat(datePattern);
            return sd.format(date);
        }

        public static String getFormattedString(Object date, String datePattern) {
            if (null == date || org.apache.commons.lang3.StringUtils.isBlank(datePattern)) return null;
            SimpleDateFormat sd = new SimpleDateFormat(datePattern);
            return sd.format(date);
        }

    }


    /**
     * domain和vo直接转换（json操作）
     * 作用于分页容器
     *
     * @param ps
     * @return
     */
    public JSONArray toJsonArrayUtil(PaginationSupport<T> ps, Class<V> mClass) {
        ArrayList<V> Vos = null;
        if (null != ps.getItems() && ps.getItems().size() > 0) {
            Vos = new ArrayList();
            for (T t : ps.getItems()) {
                V v = null;
                try {
                    v = (V) mClass.newInstance();
                    ConvertUtil.BeanToBean(t, v);
                    excute(v);
                    Vos.add(v);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        PaginationSupport<V> ps1 = new PaginationSupport<V>();
        ConvertUtil.BeanToBean(ps, ps1);
        ps1.setItems(Vos);
        ResultSet rs = new ResultSet(ps1);
        JSONArray jsonArray = (JSONArray) JSONTransformer.convertRow2Json(rs);
        {
            /*清理栈数据*/
            ps = null;
            ps1 = null;
            rs = null;
        }
        return jsonArray;

    }

    /**
     * domain和vo直接转换（json操作）
     * 作用于分页容器
     *
     * @param ps
     * @return
     */
    public JSONArray toJsonArrayUtilV2(PaginationSupport<T> ps, Class<V> mClass) {
        ArrayList<V> Vos = null;
        if (null != ps.getItems() && ps.getItems().size() > 0) {
            Vos = new ArrayList();
            for (T t : ps.getItems()) {
                V v = null;
                try {
                    v = (V) mClass.newInstance();
                    ConvertUtil.BeanToBean(t, v);
                    excute(t, v);
                    Vos.add(v);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        PaginationSupport<V> ps1 = new PaginationSupport<V>();
        ConvertUtil.BeanToBean(ps, ps1);
        ps1.setItems(Vos);
        ResultSet rs = new ResultSet(ps1);
        JSONArray jsonArray = (JSONArray) JSONTransformer.convertRow2Json(rs);
        {
            /*清理内存*/
            ps = null;
            ps1 = null;
            rs = null;
        }
        return jsonArray;

    }

    /**
     * * domain和vo直接转换（json操作）
     * 针对于 一对多、多对一关联查询 json解析死循环
     *
     * @param ps
     * @param classVo
     * @param filter  过滤属性(暂未实现)
     * @return
     */
    public List<V> toJsonArrayUtilStrGridList(PaginationSupport<T> ps, Class<V> classVo, String[] filter) {
        ArrayList<V> Vos = null;
        if (null != ps.getItems()) {
            Vos = new ArrayList();
            for (T t : ps.getItems()) {
                V v = null;
                try {
                    v = (V) classVo.newInstance();
                    ConvertUtil.BeanToBean(t, v);
                    excute(v);
                    Vos.add(v);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return Vos;
    }


    /**
     * json字符串参数转化数组（例如多选框）
     *
     * @param updateObj
     * @param args
     * @return
     */
    public ArrayList<T> jsonToArrayConvert(JSONObject updateObj, String args) {
        ArrayList<T> t = null;
        try {
            if (updateObj.has(args)) {
                Object customBusTypeVal = updateObj.get(args);
                if (customBusTypeVal instanceof JSONArray) {
                    JSONArray objects = (JSONArray) updateObj.get(args);
                    t = new ArrayList<T>();
                    for (int i = 0; i < objects.length(); i++) {
                        T value = (T) objects.get(i).toString();
                        t.add(value);
                    }
                } else {
                    t = new ArrayList<T>();
                    T value = (T) customBusTypeVal.toString();
                    t.add(value);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * form表单转pojo
     *
     * @param httpRequest
     * @param toBean      写入的bean
     * @param filter      过滤字段
     * @param isCreateId  是否自动创建id
     * @param idStr       id的field描述
     * @return 传入的Bean
     */
    public static Object FormToEntity(HttpServletRequest httpRequest, Object toBean, boolean isCreateId, String idStr, String... filter) {
        Enumeration enu = httpRequest.getParameterNames();
        Map<String, Object> filterMap = new HashMap();
        if (filter != null && filter.length > 0)
            for (String f : filter) {
                filterMap.put(f, f);
            }
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            if (filterMap.containsKey(paraName)) continue;
            try {
                Field mField = toBean.getClass().getDeclaredField(paraName);
                if ("\\".equals(java.io.File.separator) && !(httpRequest.getMethod().toLowerCase().equals("post"))) {
                    setValue(mField, toBean, StringUtils.convertCode(httpRequest.getParameter(paraName)));
                } else {
                    setValue(mField, toBean, httpRequest.getParameter(paraName));
                }

            } catch (NoSuchFieldException e) {
                ConvertUtil.log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        if (isCreateId && StringUtils.isNotEmpty(idStr)) {
            Field mField = null;
            try {
                mField = toBean.getClass().getDeclaredField(idStr);
                mField.setAccessible(true);
                mField.set(toBean, com.taole.framework.util.UUID.generateUUID());
            } catch (NoSuchFieldException e) {
                log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }

        }
        return toBean;
    }

    /**
     * form表单转pojo
     *
     * @param httpRequest
     * @param toBean      写入的bean
     * @param filter      过滤字段
     * @return 传入的Bean
     */
    public static Object FormToEntity(HttpServletRequest httpRequest, Object toBean, String... filter) {
        Enumeration enu = httpRequest.getParameterNames();
        Map<String, Object> filterMap = new HashMap();
        if (filter != null && filter.length > 0)
            for (String f : filter) {
                filterMap.put(f, f);
            }
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            if (filterMap.containsKey(paraName)) continue;
            try {
                Field mField = toBean.getClass().getDeclaredField(paraName);
                if ("\\".equals(java.io.File.separator) && !(httpRequest.getMethod().toLowerCase().equals("post"))) {
                    setValue(mField, toBean, StringUtils.convertCode(httpRequest.getParameter(paraName)));
                } else {
                    setValue(mField, toBean, httpRequest.getParameter(paraName));
                }
            } catch (NoSuchFieldException e) {
                ConvertUtil.log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }

        return toBean;
    }

    /**
     * form表单转pojo
     *
     * @param httpRequest
     * @param toBean      写入的bean
     * @return 传入的Bean
     */
    public static Object FormToEntity(HttpServletRequest httpRequest, Object toBean) {
        Enumeration enu = httpRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            try {
                Field mField = toBean.getClass().getDeclaredField(paraName);
                if ("\\".equals(java.io.File.separator) && !(httpRequest.getMethod().toLowerCase().equals("post"))) {
                    setValue(mField, toBean, StringUtils.convertCode(httpRequest.getParameter(paraName)));
                } else {
                    setValue(mField, toBean, httpRequest.getParameter(paraName));
                }
            } catch (NoSuchFieldException e) {
                ConvertUtil.log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }

        return toBean;
    }

    /**
     * @param updateObj  json对象
     * @param toBean     写入的bean
     * @param isCreateId
     * @param idStr
     * @param filter     过滤字段（即不需要写入的属性）
     * @return
     * @throws JSONException
     */
    public static Object JsonToEntity(JSONObject updateObj, Object toBean, boolean isCreateId, String idStr, String[] filter) {
        JSONArray jsonArrayNames = updateObj.names();
        Map<String, Object> filterMap = new HashMap();
        if (filter != null && filter.length > 0)
            for (String f : filter) {
                filterMap.put(f, f);
            }
        for (int i = 0; i < jsonArrayNames.length(); i++) {
            Object paraName = null;
            try {
                paraName = jsonArrayNames.get(i);
                if (filterMap.containsKey(paraName)) continue;
                Field mField = toBean.getClass().getDeclaredField(paraName.toString());
                Object objStr = updateObj.get(paraName.toString());
                setValue(mField, toBean, objStr);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                ConvertUtil.log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        if (isCreateId && StringUtils.isNotEmpty(idStr)) {
            Field mField = null;
            try {
                mField = toBean.getClass().getDeclaredField(idStr);
                mField.setAccessible(true);
                mField.set(toBean, com.taole.framework.util.UUID.generateUUID());
            } catch (NoSuchFieldException e) {
                log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }

        }
        return null;
    }


    public static Map<String, String> Json2Map(JSONObject updateObj) {
        Map<String, String> map = new HashMap<>();
        JSONArray jsonArrayNames = updateObj.names();
        for (int i = 0; i < jsonArrayNames.length(); i++) {
            try {
                String paraName = jsonArrayNames.getString(i);
                map.put(paraName, updateObj.getString(paraName));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * @param updateObj  json对象
     * @param toBean     写入的bean
     * @param isCreateId
     * @param idStr
     * @return
     * @throws JSONException
     */
    public static Object JsonToEntity(JSONObject updateObj, Object toBean, boolean isCreateId, String idStr) {
        JSONArray jsonArrayNames = updateObj.names();
        for (int i = 0; i < jsonArrayNames.length(); i++) {
            Object paraName = null;
            try {
                paraName = jsonArrayNames.get(i);
                Field mField = toBean.getClass().getDeclaredField(paraName.toString());
                Object objStr = updateObj.get(paraName.toString());
                setValue(mField, toBean, objStr);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                ConvertUtil.log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        if (isCreateId && StringUtils.isNotEmpty(idStr)) {
            Field mField = null;
            try {
                mField = toBean.getClass().getDeclaredField(idStr);
                mField.setAccessible(true);
                mField.set(toBean, com.taole.framework.util.UUID.generateUUID());
            } catch (NoSuchFieldException e) {
                log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }

        }
        return null;
    }

    /**
     * @param updateObj json对象
     * @param toBean    写入的bean
     * @param filter    过滤字段（即不需要写入的属性）
     * @return
     * @throws JSONException
     */
    public static Object JsonToEntity(JSONObject updateObj, Object toBean, String[] filter) {
        JSONArray jsonArrayNames = updateObj.names();
        Map<String, Object> filterMap = new HashMap();
        if (filter != null && filter.length > 0)
            for (String f : filter) {
                filterMap.put(f, f);
            }
        for (int i = 0; i < jsonArrayNames.length(); i++) {
            Object paraName = null;
            try {
                paraName = jsonArrayNames.get(i);
                if (filterMap.containsKey(paraName)) continue;
                Field mField = toBean.getClass().getDeclaredField(paraName.toString());
                Object objStr = updateObj.get(paraName.toString());
                setValue(mField, toBean, objStr);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                ConvertUtil.log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        return null;
    }

    /**
     * 实体bean转化json，不丢弃空字段
     *
     * @param bean
     * @return
     */
    public static JSONObject toJsonObject(Object bean) {
        Map<String, Object> map = new HashMap<>();
        Class klass = bean.getClass();
        Method[] methods = klass.getMethods();
        for (int i = 0; i < methods.length; i += 1) {
            try {
                Method method = methods[i];
                String name = method.getName();
                String key = "";
                if (name.startsWith("get")) {
                    key = name.substring(3);
                } else if (name.startsWith("is")) {
                    key = name.substring(2);
                }
                if (key.length() > 0 &&
                        Character.isUpperCase(key.charAt(0)) &&
                        method.getParameterTypes().length == 0) {
                    if (key.length() == 1) {
                        key = key.toLowerCase();
                    } else if (!Character.isUpperCase(key.charAt(1))) {
                        key = key.substring(0, 1).toLowerCase() +
                                key.substring(1);
                    }
                    map.put(key, method.invoke(bean, null));
                }
            } catch (Exception e) {
            }
        }
        return new JSONObject(map);
    }


    public static JSONArray listToJasonArray(List lists) {
        if (ArrayListUtil.isEmpty(lists)) return null;
        JSONArray jsonArray = new JSONArray();
        for (Object list : lists) {
            JSONObject jsonObject = new JSONObject(list);
            jsonObject.remove("class");
            jsonArray.put(jsonObject);

        }
        return jsonArray;
    }

    public static com.alibaba.fastjson.JSONArray listToAlJasonArray(List lists) {
        if (ArrayListUtil.isEmpty(lists)) return null;
        com.alibaba.fastjson.JSONArray jsonArray = new com.alibaba.fastjson.JSONArray();
        for (Object list : lists) {
            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) SerializableJSONTransformer.transformPojo2Jso(list);
            jsonObject.remove("class");
            jsonArray.add(jsonObject);

        }
        return jsonArray;
    }

    public static JSONArray listToJasonArray(List lists, String... filter) {
        if (ArrayListUtil.isEmpty(lists)) return null;
        JSONArray jsonArray = new JSONArray();
        for (Object list : lists) {
            JSONObject jsonObject = new JSONObject(list);
            for (String s : filter)
                jsonObject.remove(s);
            jsonObject.remove("class");
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }


    /**
     * @param updateObj json对象
     * @param toBean    写入的bean
     * @return
     * @throws JSONException
     */
    public static Object JsonToEntity(JSONObject updateObj, Object toBean) {
        JSONArray jsonArrayNames = updateObj.names();
        Map<String, Object> filterMap = new HashMap();
        if (null != jsonArrayNames)
            for (int i = 0; i < jsonArrayNames.length(); i++) {
                Object paraName = null;
                try {
                    paraName = jsonArrayNames.get(i);
                    Field mField = toBean.getClass().getDeclaredField(paraName.toString());
                    Object objStr = updateObj.get(paraName.toString());
                    setValue(mField, toBean, objStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    ConvertUtil.log.info(e.getMessage());
                } catch (IllegalAccessException e) {
                    log.info(e.getMessage());
                }
            }
        return null;
    }


    /**
     * 一个bean中所有属性值写入到另一个bean中
     * 在vo中自动运行相关查询
     *
     * @param soucesBean
     * @param desBean
     * @return
     */
    public static Object BeanToBean(Object soucesBean, Object desBean, String[] filter) {
        Map<String, Object> filterMap = new HashMap();
        if (filter != null && filter.length > 0)
            for (String f : filter) {
                filterMap.put(f, f);
            }
        if (soucesBean != null && desBean != null) {
            Field[] dfileds = desBean.getClass().getDeclaredFields();
            Field[] sfileds = soucesBean.getClass().getDeclaredFields();
            for (Field d : dfileds) {
                d.setAccessible(true);
                if (filterMap.containsKey(d.getName())) continue;
                for (Field s : sfileds) {
                    s.setAccessible(true);
                    if (d.getName().equals(s.getName())) {
                        Object object = null;
                        try {
                            object = s.get(soucesBean);
                            setValue(d, desBean, object);
                            break;
                        } catch (IllegalAccessException e) {
                            log.info(e.getMessage());
                        }
                    }
                }
            }

        }
        return desBean;
    }

    /**
     * 一个bean中所有属性值写入到另一个bean中
     *
     * @param soucesBean
     * @param desBean
     * @return
     */
    public static Object BeanToBean(Object soucesBean, Object desBean) {
        if (soucesBean != null && desBean != null) {
            Field[] dfileds = desBean.getClass().getDeclaredFields();
            Field[] sfileds = soucesBean.getClass().getDeclaredFields();
            for (Field d : dfileds) {
                d.setAccessible(true);
                for (Field s : sfileds) {
                    s.setAccessible(true);
                    if (d.getName().equals(s.getName())) {
                        Object object = null;
                        try {
                            object = s.get(soucesBean);
                            setValue(d, desBean, object);
                            break;
                        } catch (IllegalAccessException e) {
                            log.info(e.getMessage());
                        }
                    }
                }
            }

        }
        return desBean;
    }

    /**
     * 一个bean中所有属性值写入到另一个bean中
     * 使用vo查询
     *
     * @param soucesBean
     * @param desBean
     * @return
     */
    public V BeanToBean_Vo(T soucesBean, V desBean) {
        if (soucesBean != null && desBean != null) {
            excute(desBean);
            Field[] dfileds = desBean.getClass().getDeclaredFields();
            Field[] sfileds = soucesBean.getClass().getDeclaredFields();
            for (Field d : dfileds) {
                d.setAccessible(true);
                for (Field s : sfileds) {
                    s.setAccessible(true);
                    if (d.getName().equals(s.getName())) {
                        Object object = null;
                        try {
                            object = s.get(soucesBean);
                            setValue(d, desBean, object);
                            break;
                        } catch (IllegalAccessException e) {
                            log.info(e.getMessage());
                        }
                    }
                }
            }

        }
        return desBean;
    }

    /**
     * domain和vo直接转换（json操作）
     *
     * @param t
     * @return
     */
    public JSONObject toJsonObjectUtil(T t, Object objectVo) {
        V v = null;
        try {
            v = (V) objectVo.getClass().newInstance();
            ConvertUtil.BeanToBean(t, v);
            excute(v);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        jsonObject = new JSONObject(v);
        return jsonObject;
    }


    /**
     * 把一个bean中存在值得数据写入到另一个bena中，在执行更新操作是常用到，极为方便
     *
     * @param desBean
     * @param sourceBean
     * @return
     */
    public static Object CopyBeanByNeed(Object desBean, Object sourceBean) {
        Field desFileds[] = desBean.getClass().getDeclaredFields();
        Map<String, Object> needFieldData = new HashMap<String, Object>();
        /*需要写入数据field*/
        for (Field field : desFileds) {
            try {
                field.setAccessible(true);
                Object objectValue = field.get(desBean);
                if (objectValue != null) needFieldData.put(field.getName(), objectValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (String keyField : needFieldData.keySet()) {
            try {
                Field field = sourceBean.getClass().getDeclaredField(keyField);
                if (field != null) {
                    field.setAccessible(true);
                    setValue(field, sourceBean, needFieldData.get(keyField));
                }
            } catch (NoSuchFieldException e) {
                log.info(e.getMessage());
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        return sourceBean;
    }


    public static <V> List<V> convertListVo(List tList, V v) {
        Assert.notNull(tList);
        Assert.notNull(v);
        List<V> vList = new ArrayList<>();
        try {
            for (Object o : tList) {
                V v1 = (V) v.getClass().newInstance();
                vList.add((V) BeanToBean(o, v1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vList;
    }

    /**
     * 通用实体转换方法,将JPA返回的数组转化成对应的实体集合
     *
     * @param <T>
     * @param list
     * @param clazz 需要转化后的类型
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> castEntity2St(List<Object[]> list, Class<T> clazz) {
        List<T> returnList = new ArrayList<T>();
        if (ArrayListUtil.isEmpty(list)) return null;
        for (Object[] o : list) {
            Class[] c2 = new Class[o.length];
            //确定构造方法
            for (int i = 0; i < o.length; i++) {
                if (null != o[i]) c2[i] = o[i].getClass();
                else c2[i] = Object.class;
            }
            Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
            for (Constructor<T> c : constructors) {
                try {
                    returnList.add(c.newInstance(o));
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnList;
    }

    public static <T> LinkedList<T> castEntity2St4Linked(List<Object[]> list, Class<T> clazz) {
        LinkedList<T> returnList = new LinkedList<T>();
        if (ArrayListUtil.isEmpty(list)) return null;
        for (Object[] o : list) {
            Class[] c2 = new Class[o.length];
            for (int i = 0; i < o.length; i++) {
                if (null != o[i]) c2[i] = o[i].getClass();
                else c2[i] = Object.class;
            }
            Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
            for (Constructor<T> c : constructors) {
                try {
                    returnList.add(c.newInstance(o));
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnList;
    }

    /**
     * 通用实体转换方法,将JPA返回的数组转化成对应的实体集合
     *
     * @param <T>
     * @param list
     * @param clazz 需要转化后的类型
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<V> castEntity(List<Object[]> list, Class<V> clazz) {
        List<V> returnList = new ArrayList<V>();
        for (Object[] o : list) {
            Class[] c2 = new Class[o.length];
            //确定构造方法
            for (int i = 0; i < o.length; i++) {
                if (null != o[i]) c2[i] = o[i].getClass();
                else c2[i] = Object.class;
            }
            Constructor<V>[] constructors = (Constructor<V>[]) clazz.getConstructors();

            for (Constructor<V> c : constructors) {
                try {
                    V v = c.newInstance(o);
                    excute(v);
                    returnList.add(v);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
        return returnList;
    }

    /**
     * 方法链反向执行
     *
     * @param cl
     * @param chain
     * @param param
     * @return
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object stackTrace(Proxy proxy, int chain) {
        Object cl = proxy.getTarget();
        Object[] param = proxy.getParam();
        StackTraceElement[] s = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = s[chain];
        String methodName = stackTraceElement.getMethodName();
        Method target = null;
        try {
            if (null != proxy.getMethod()) target = proxy.getMethod();
            else
                target = cl.getClass().getMethod(proxy.getMethodName(), proxy.getParamType());
            if (!target.isAccessible())
                target.setAccessible(true);
            if (null == target) return "";
            return target.invoke(cl, param);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射执行
     *
     * @param cl
     * @param chain
     * @param param
     * @return
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object stackTrace(Proxy proxy) {
        log.info("执行了方法递归!");
        Object cl = proxy.getTarget();
        Object[] param = proxy.getParam();
        Method target = null;
        try {
            if (null != proxy.getMethod()) target = proxy.getMethod();
            else
                target = cl.getClass().getMethod(proxy.getMethodName(), proxy.getParamType());
            if (!target.isAccessible())
                target.setAccessible(true);
            if (null == target) return "";
            return target.invoke(cl, param);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date反射赋值，需要遵守后缀规则，原字段后补充“Str”
     * <p>
     * 例子：有 create（Date类型），createStr（String类型） createStr--》create（自动把String类型字段转化完成后给Date类型字段）
     *
     * @param object
     */
    public void dataFormat(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            int index = field.getName().indexOf("Str");
            if (index > 0) {
                String fieldName = field.getName().substring(0, index);
                try {
                    Field wField = object.getClass().getDeclaredField(fieldName);
                    if (null == wField) continue;
                    wField.setAccessible(true);
                    Object dataObject = field.get(object);
                    if (null == dataObject) continue;
                    wField.set(object, DateUtils.getFormatDate(dataObject.toString(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
                } catch (NoSuchFieldException e) {
                    log.info(e.getMessage());
                } catch (IllegalAccessException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }


}
