/*
 * @(#)JspHelper.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.taole.framework.util.PageRequest;
import com.taole.framework.util.ServletRequestParameterHelper;
import com.taole.framework.util.ServletUtils;
import com.taole.framework.util.StringUtils;

/**
 * Class: JspHelper Remark: TODO
 * 
 * @author: 
 */
public class JspHelper {

	PageContext pageContext = null;

	public JspHelper(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public JspHelper(PageContext pageContext, String requestEncoding)
			throws UnsupportedEncodingException {
		this(pageContext);
		pageContext.getRequest().setCharacterEncoding(requestEncoding);
	}

	protected ServletRequestParameterHelper getServletRequestParameterHelper() {
		return new ServletRequestParameterHelper(pageContext.getRequest());
	}

	protected BeanFactory getBeanFactory() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	}

	public ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	}

	public Object getBean(String name) {
		return getBeanFactory().getBean(name);
	}
	
	public <T> T getBean(String name, Class<T> requiredType) {
		return getBeanFactory().getBean(name, requiredType);
	}
	
	public <T> T getBean(Class<T> requiredType) {
		return getBeanFactory().getBean(requiredType);
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	public String getContextPath() {
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		return req.getContextPath();
	}

	public String getRemoteAddr() {
		return ServletUtils.getRemoteAddr((HttpServletRequest) pageContext
				.getRequest());
	}

	public String getServerName() {
		return pageContext.getRequest().getServerName();
	}

	public int getServerPort() {
		return pageContext.getRequest().getServerPort();
	}

	public String getScheme() {
		return pageContext.getRequest().getScheme();
	}

	public String getFullContextPath() {
		return ServletUtils.getFullContextPath((HttpServletRequest) pageContext
				.getRequest());
	}

	/**
	 * 获得请求参数
	 * 
	 * @param paraName
	 * @return
	 */
	public Collection<String> getRequestParameterNames() {
		return getServletRequestParameterHelper().getRequestParameterNames();
	}
	
	/**
	 * 获得请求参数
	 * 
	 * @param paraName
	 * @return
	 */
	public String getRequestParameter(String paraName) {
		return getServletRequestParameterHelper().getRequestParameter(paraName);
	}

	/**
	 * 获得请求参数,如没有发现该参数则返回缺省值
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 */
	public String getRequestParameter(String paraName, String defVal) {
		return getServletRequestParameterHelper().getRequestParameter(paraName, defVal);
	}

	/**
	 * 返回字符串参数
	 * 
	 * @see #getRequestParameter(String)
	 */
	public String getRequestStringParameter(String paraName) {
		return getServletRequestParameterHelper().getRequestStringParameter(paraName);
	}
	
	public String[] getRequestStringParameters(String paraName) {
		return getServletRequestParameterHelper().getRequestStringParameters(paraName);
	}

	public List<String> getRequestStringParameterList(String paraName) {
		return getServletRequestParameterHelper().getRequestStringParameterList(paraName);
	}
	
	public List<String> getRequestStringParameterListIgnoreBlank(String paraName) {
		return getServletRequestParameterHelper().getRequestStringParameterListIgnoreBlank(paraName);
	}

	/**
	 * 返回字符串类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public String getRequestStringParameter(String paraName, String defVal) {
		return getServletRequestParameterHelper().getRequestStringParameter(paraName, defVal);
	}

	/**
	 * 返回int类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public int getRequestIntParameter(String paraName, int defVal) {
		return getServletRequestParameterHelper().getRequestIntParameter(paraName, defVal);
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public boolean getRequestBooleanParameter(String paraName, boolean defVal) {
		return getServletRequestParameterHelper().getRequestBooleanParameter(paraName, defVal);
	}
	
	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public boolean getRequestBooleanParameter(String paraName, String[] trueVals) {
		return getServletRequestParameterHelper().getRequestBooleanParameter(paraName, trueVals);
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName) {
		return getServletRequestParameterHelper().getRequestDateParameter(paraName);
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param format
	 *            日期格式
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName, String format) {
		return getServletRequestParameterHelper().getRequestDateParameter(paraName, format);
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName, Date defVal) {
		return getServletRequestParameterHelper().getRequestDateParameter(paraName, defVal);
	}

	/**
	 * 返回boolean类型的参数
	 * 
	 * @param paraName
	 *            参数名
	 * @param format
	 *            日期格式
	 * @param defVal
	 *            缺省值
	 * @return
	 * @see #getRequestParameter(String, String)
	 */
	public Date getRequestDateParameter(String paraName, String format,
			Date defVal) {
		return getServletRequestParameterHelper().getRequestDateParameter(paraName, format, defVal);
	}
	
	/**
	 * 枚举类型
	 * @param paraName
	 * @param enumType
	 * @return
	 */
	public <T extends Enum<T>> T getRequestEnumParameter(String paraName, Class<T> enumType) {
		return getServletRequestParameterHelper().getRequestEnumParameter(paraName, enumType);
	}

	/**
	 * 返回请求中是否包含某个参数
	 * 
	 * @param paraName
	 * @return
	 */
	public boolean containsRequestParameter(String paraName) {
		return getServletRequestParameterHelper().containsRequestParameter(paraName);
	}

	/**
	 * 返回JspWriter out对象
	 * 
	 * @return
	 */
	public JspWriter getOut() {
		return pageContext.getOut();
	}

	/**
	 * 输出文本内容(进行过html编码)
	 * 
	 * @param txt
	 * @throws IOException
	 */
	public void printText(String txt) throws IOException {
		getOut().print(HTMLEncoder.encodeNodeValue(txt));
	}

	/**
	 * 输出文本内容并回车换行(进行过html编码)
	 * 
	 * @param txt
	 * @throws IOException
	 */
	public void printlnText(String txt) throws IOException {
		getOut().println(
				HTMLEncoder.encodeText2HTML(HTMLEncoder.encodeNodeValue(txt)));
	}

	/**
	 * 输出标签属性内容(进行过attribute编码)
	 * 
	 * @param txt
	 * @throws IOException
	 */
	public void printTagAttribute(String txt) throws IOException {
		getOut().print(HTMLEncoder.encodeAttribute(txt));
	}

	/**
	 * @param object
	 */
	public void printNonnull(Object object) {
		try {
			if (object != null) {
				getOut().print(object);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param object
	 */
	public Object notNull(Object object) {
		if (object != null) {
			return object;
		} else {
			return "";
		}
	}

	/**
	 * @param object
	 */
	public String notEmpty(String input, String defaultVal) {
		return StringUtils.isEmpty(input) ? defaultVal : input;
	}

	/**
	 * 动态包涵某个url的内容
	 * 
	 * @param url
	 * @throws ServletException
	 * @throws IOException
	 */
	public void include(String url) throws ServletException, IOException {
		pageContext.include(url, true);
	}

	/**
	 * 得到一个cookie根据指定的cookie名称
	 * 
	 * @param name
	 * @return
	 */
	public Object getCookie(String name) {
		Cookie[] cookies = ((HttpServletRequest) pageContext.getRequest())
				.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * 设置一个cookie根据指定的cookie名称
	 * 
	 * @param name
	 * @return
	 */
	public void setCookie(Cookie cookie) {
		((HttpServletResponse) pageContext.getResponse()).addCookie(cookie);
	}

	public void printComment(String comment) throws IOException {
		getOut().print("<!-- " + comment + " -->");
	}

	/**
	 * 得到一个cookie的值根据指定的cookie名称
	 * 
	 * @param name
	 * @return
	 */
	public String getCookieValue(String name) {
		Cookie cookie = (Cookie) getCookie(name);
		return (cookie == null) ? null : cookie.getValue();
	}

	/**
	 * 设置一个cookie的值根据指定的cookie名称和值
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public void setCookieValue(String name, String value) {
		setCookie(new Cookie(name, value));
	}

	/**
	 * 设置一个cookie的值根据指定的cookie名称和值,域名,路径,最大寿命
	 * 
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 * @param maxAge
	 */
	public void setCookieValue(String name, String value, String domain,
			String path, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (domain != null) {
			cookie.setDomain(domain);
		}
		if (path != null) {
			cookie.setPath(path);
		}
		if (maxAge >= 0) {
			cookie.setMaxAge(maxAge);
		}
		setCookie(cookie);
	}

	public PageRequest getPageRequest(String pageNoParamName, int pageSize) {
		return getServletRequestParameterHelper().getPageRequest(pageNoParamName, pageSize);
	}

	public PageRequest getPageRequest(int pageSize) {
		return getServletRequestParameterHelper().getPageRequest( pageSize);
	}

	public PageRequest getPageRequestObject() {
		return getServletRequestParameterHelper().getPageRequestObject();
	}


}
