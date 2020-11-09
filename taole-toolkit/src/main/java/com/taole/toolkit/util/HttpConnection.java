package com.taole.toolkit.util;

import com.taole.framework.rest.client.JSONWithHeaderResponseHandler;
import com.taole.framework.rest.client.RestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpConnection {
    private static Logger logger = LoggerFactory.getLogger(HttpConnection.class);

    /**
     * @param url
     * @param postContent
     * @return object[0]:JSONObject类型的返回结果 ；object[1]:Map responseHeader的Map
     */
    public Object[] connect(String url, String postContent) {
        RestClient client = new RestClient();
        Object[] objects = null;
        try {
            client.request(url).post();
            if (StringUtils.isNotBlank(postContent)) {
                client.withJsonData(new JSONObject(postContent));
            }
            objects = client.handleWith(new JSONWithHeaderResponseHandler()).getResult(Object[].class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return objects;
    }

    /**
     * @param url
     * @param postContent
     * @return object[0]:JSONObject类型的返回结果 ；object[1]:Map responseHeader的Map
     */
    public Object[] connectWithJson(String url, JSONObject postContent) {
        RestClient client = new RestClient();
        Object[] objects = null;
        try {
            client.request(url).post();
            client.withJsonData(postContent);
            objects = client.handleWith(new JSONWithHeaderResponseHandler()).getResult(Object[].class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return objects;
    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     * @param params json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     * @throws Exception
     */
    public static String connectWithJsonForHttps(String strURL, String params) throws Exception {

        MyX509TrustManager xtm = new MyX509TrustManager();
        MyHostnameVerifier hnv = new MyHostnameVerifier();
        SSLContext sslContext = SSLContext.getInstance("TLS"); // 或SSL
        X509TrustManager[] xtmArray = new X509TrustManager[]{xtm};
        sslContext.init(null, xtmArray, new java.security.SecureRandom());
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);

        URL url = new URL(strURL);// 创建连接
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST"); // 设置请求方式
        connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
        connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
        out.append(params);
        out.flush();
        out.close();
        // 读取响应
        int length = (int) connection.getContentLength();// 获取长度
        InputStream is = connection.getInputStream();
        if (length != -1) {
            byte[] data = new byte[length];
            byte[] temp = new byte[512];
            int readLen = 0;
            int destPos = 0;
            while ((readLen = is.read(temp)) > 0) {
                System.arraycopy(temp, 0, data, destPos, readLen);
                destPos += readLen;
            }
            String result = new String(data, "UTF-8"); // utf-8编码
            return result;
        }

        return "error"; // 自定义错误信息
    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     * @param params json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    public static String post(String strURL, String params) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

    /**
     * 发送Http Get请求
     *
     * @throws IOException
     */
    public static String connByGet(String strURL, Map<String, String> headerMap) throws IOException {
        URL url = new URL(strURL);// 创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (headerMap != null) {
            for (Map.Entry<String, String> header : headerMap.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("GET"); // 设置请求方式
        conn.connect();
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); // utf-8编码
        out.flush();
        out.close();
        // 读取响应
        int length = (int) conn.getContentLength();// 获取长度
        InputStream is = conn.getInputStream();
        if (length != -1) {
            byte[] data = new byte[length];
            byte[] temp = new byte[512];
            int readLen = 0;
            int destPos = 0;
            while ((readLen = is.read(temp)) > 0) {
                System.arraycopy(temp, 0, data, destPos, readLen);
                destPos += readLen;
            }
            String result = new String(data, "UTF-8"); // utf-8编码
            return result;
        } else {
            return null;
        }
    }

    /**
     * 发送Http Get请求
     *
     * @throws IOException
     */
    public static String connByGet(String strURL) throws IOException {
        URL url = new URL(strURL);// 创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("GET"); // 设置请求方式
        conn.connect();
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); // utf-8编码
        out.flush();
        out.close();
        // 读取响应
        int length = (int) conn.getContentLength();// 获取长度
        InputStream is = conn.getInputStream();
        if (length != -1) {
            byte[] data = new byte[length];
            byte[] temp = new byte[512];
            int readLen = 0;
            int destPos = 0;
            while ((readLen = is.read(temp)) > 0) {
                System.arraycopy(temp, 0, data, destPos, readLen);
                destPos += readLen;
            }
            String result = new String(data, "UTF-8"); // utf-8编码
            return result;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        // String url =
        // "http://mis.wodaibao/portal/service/rest/us.Group/collection/query";
        String url = "http://mis.wodaibao/portal/service/rest/us.Group/collection/query";
        try {

            HttpConnection hc = new HttpConnection();
            System.out.println(HttpConnection.connByGet(url));
            Object[] objs = hc.connect(url, null);
            if (objs[0] != null) {
                JSONObject jo = (JSONObject) objs[0];
                System.out.println(jo.toString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送get请求
     *
     * @param reqUrl 路径
     * @return
     * @throws IOException
     */
    public static JSONObject httpGet(String reqUrl) {
        try {
            URL url = new URL(reqUrl);// 创建连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("GET"); // 设置请求方式
            conn.connect();
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); // utf-8编码
            out.flush();
            out.close();
            // 读取响应
            int length = (int) conn.getContentLength();// 获取长度
            InputStream is = conn.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                if (StringUtils.isBlank(result))
                    return null;
                JSONObject resultObject = new JSONObject(result);
                return resultObject;
            } else {
                return null;
            }
        } catch (Exception ex) {
        }
        return null;
    }
}
