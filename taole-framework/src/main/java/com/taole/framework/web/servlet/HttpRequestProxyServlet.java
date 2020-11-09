/*
 * @(#)HttpRequestProxyServlet.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taole.framework.util.IOUtils;

public class HttpRequestProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8116209009793461083L;

	private String directory; // 缓存目录

	private long defaultInterval = 60000l; //

	private long cacheTimes;

	private boolean defaultCache = true;

	private boolean cache;

	ServletContext context = null;

	public HttpRequestProxyServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
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
		return (System.currentTimeMillis() - file.lastModified() <= cacheTimes);
	}

	private String getTargetFilePath(HttpServletRequest request, String url) {
		String result = "";
		url = url.replaceAll(":", "");
		url = url.replace("?", "#");
		result = (directory == null) ? context.getRealPath(request
				.getServletPath()) : directory;
		result += "/" + url + ".bak";
		return result;
	}

	public void proxy(HttpServletRequest request, HttpServletResponse response,
			String url) throws FileNotFoundException, IOException {
		File file = new File(getTargetFilePath(request, url));

		if (cache) {
			// 缓存
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) new URL(url).openConnection();
				if (!isNewFile(file)) {
					conn.setRequestProperty("User-Agent", " Reader");
					IOUtils.copyAndCloseIOStream(new FileOutputStream(file),
							conn.getInputStream());
				}
				response.setContentType(conn.getContentType());
				conn.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IOUtils.copyAndCloseIOStream(response.getOutputStream(),
					new FileInputStream(file));
		} else {
			// 不缓存
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.setRequestProperty("User-Agent", " Reader");
			response.setContentType(conn.getContentType());
			IOUtils.copyAndCloseIOStream(response.getOutputStream(), conn
					.getInputStream());
			conn.disconnect();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!request.getParameterNames().hasMoreElements())
			return;
		String url = (String) request.getQueryString();
		cache = defaultCache;
		cacheTimes = defaultInterval;
		proxy(request, response, url);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = (String) request.getParameter("url");
		cache = Boolean
				.parseBoolean(request.getParameter("cache") == null ? "false"
						: request.getParameter("cache"));
		try {
			cacheTimes = Long.parseLong(request.getParameter("cacheTimes"));
		} catch (Exception e) {
			cacheTimes = defaultInterval;
		}
		proxy(request, response, url);
	}

	public void init(ServletConfig config) throws ServletException {
		context = config.getServletContext();
		try {
			defaultInterval = Long.parseLong(config
					.getInitParameter("interval"));
		} catch (Exception e) {
		}
		try {
			String cache = config.getInitParameter("cache");
			if (cache != null)
				defaultCache = Boolean.parseBoolean(cache);
		} catch (Exception e) {
		}
		directory = config.getInitParameter("cacheDir");
	}
}
