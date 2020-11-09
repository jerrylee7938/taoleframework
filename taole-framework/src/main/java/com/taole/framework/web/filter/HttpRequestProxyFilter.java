/*
 * @(#)HttpRequestProxyFilter.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.web.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taole.framework.util.IOUtils;

public class HttpRequestProxyFilter implements Filter {

    String target = null;

    String directory = null;

    int interval = 1000;

    ServletContext context = null;

    /*
     * (non-Javadoc)
     * 
     * @see javax.setup.Filter#init(javax.setup.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        target = config.getInitParameter("target");
        directory = config.getInitParameter("directory");
        interval = Integer.parseInt(config.getInitParameter("interval"));
        context = config.getServletContext();
    }

    /**
     * @param request
     * @return
     */
    private String getTargetUrl(HttpServletRequest request) {
        return target + request.getServletPath() + "?"
                + request.getQueryString();
    }

    /**
     * @param request
     * @return
     */
    private String getTargetFilePath(HttpServletRequest request) {
        String result = "";
        result = (directory == null) ? context.getRealPath(request
                .getServletPath()) : (directory + "/" + request
                .getServletPath());
        return result + "#" + request.getQueryString();
    }

    /**
     * 判断当前文件是否最新，不存在 return false, 时间过期 return false
     * 
     * @param file
     * @return
     */
    private boolean isNewFile(File file) {
        File parent = new File(file.getParent());
        if (!parent.exists()) {
            parent.mkdirs();
            return false;
        }
        return (System.currentTimeMillis() - file.lastModified() <= interval);
    }

    /**
     * @param request
     * @throws Exception
     */
    private File createFile(HttpServletRequest request) throws Exception {
        File file = new File(getTargetFilePath(request));
        if (isNewFile(file)) {
            return file;
        }
        String url = getTargetUrl(request);
        HttpURLConnection conn = (HttpURLConnection) new URL(url)
                .openConnection();
        IOUtils.copyAndCloseIOStream(new FileOutputStream(file), conn
                .getInputStream());
        conn.disconnect();
        return file;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.setup.Filter#doFilter(javax.setup.ServletRequest,
     *      javax.setup.ServletResponse, javax.setup.FilterChain)
     */
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        File file = null;
        try {
            file = createFile(request);
            IOUtils.copyAndCloseIOStream(response.getOutputStream(),
                    new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO
        // arg2.doFilter(arg0, arg1);
        // String url = request.getServletPath() + "#" +
        // request.getQueryString();
        // context.getRequestDispatcher(url).forward(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.setup.Filter#destroy()
     */
    public void destroy() {
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
