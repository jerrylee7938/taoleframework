package com.taole.toolkit.util.biz;

import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.toolkit.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.Assert;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpParam {
    static final String EVN = "UTF-8";
    private Object target;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Range range;
    private Sorter sorter;
    private JSONObject paramJsonMap;
    private ServletContext webContext;

    public HttpParam(Object target) {
        Assert.notNull(target);
        this.target = target;
        try {
            init();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ServletContext getWebContext() {
        return webContext;
    }

    public void setWebContext(ServletContext webContext) {
        this.webContext = webContext;
    }

    public static HttpParam getHM(HttpServletRequest request) {
        return new HttpParam(request);
    }

    public Object getTarget() {
        return target;
    }

    public Range getRange() {
        return range;
    }

    public HttpParam setRange(Range range) {
        this.range = range;
        return this;
    }

    public Sorter getSorter() {
        return sorter;
    }

    public HttpParam setSorter(Sorter sorter) {
        this.sorter = sorter;
        return this;
    }

    public JSONObject getParamJsonMap() {
        return paramJsonMap;
    }

    public void setParamJsonMap(JSONObject paramJsonMap) {
        this.paramJsonMap = paramJsonMap;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpParam setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    public HttpParam setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    private void init() throws UnsupportedEncodingException, JSONException {
        if (target instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) target;
            request.setCharacterEncoding(EVN);
            this.webContext = request.getServletContext();
            paramJsonMap = new JSONObject();
            Map paramMap = request.getParameterMap();
            Set<String> keys = paramMap.keySet();
            for (String key : keys)
                paramJsonMap.put(key, request.getParameter(key));
            setRequest(request);
        }
    }

    public String getParameter(String name) {
        String result = null;
        try {
            if (target instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) target;
                result = request.getParameter(name);
                if (StringUtils.isBlank(result)) {
                    if (paramJsonMap.has(name))
                        result = paramJsonMap.getString(name);
                }
            } else if (target instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) target;
                try {
                    if (jsonObject.has(name))
                        result = jsonObject.getString(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Map getParameterMap() {
        Map result = null;
        if (target instanceof HttpServletRequest) {
            try {
                HttpServletRequest request = (HttpServletRequest) target;
                result = request.getParameterMap();
                if (null == result) {
                    result = new HashMap();
                    Iterator<String> iterator = paramJsonMap.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        result.put(key, paramJsonMap.has(key) ? paramJsonMap.getString(key) : null);
                    }
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else if (target instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) target;
            result = new HashMap();
            try {
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    result.put(key, jsonObject.has(key) ? jsonObject.getString(key) : null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public HttpParam setCharacterEncoding(String env) throws UnsupportedEncodingException {
        if (target instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) target;
            request.setCharacterEncoding(env);
        }
        return this;
    }

    public HttpServletRequest getRequest() {
        try {
            request = (HttpServletRequest) target;
        } catch (Exception e) {
            return request;
        }
        return request;
    }

    public JSONObject getJsonObject() {
        JSONObject jsonObject = (JSONObject) target;
        return jsonObject;
    }

    public String getUrlParam() {
        StringBuilder builder = new StringBuilder();
        Map<String, String> map = this.getParameterMap();
        for (Map.Entry<String, String> entry : map.entrySet())
            builder.append(entry.getKey()).append("=").append(getParameter(entry.getKey())).append("&");
        builder.append("timeexpire=" + System.currentTimeMillis());
        return builder.toString();
    }
}
