package com.taole.framework.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taole.framework.util.StringUtils;

public class RequestLoggerFilter implements Filter {

	private final Log logger = LogFactory.getLog(RequestLoggerFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			
			chain.doFilter(request, response);
			HttpServletRequest req = (HttpServletRequest)request;

			Enumeration<?> enum1 = req.getParameterNames();
			String params = "";
			while (enum1.hasMoreElements()) {
				String key = (String)enum1.nextElement();
				String value = req.getParameter(key);
				params = params + key + ":" + value + ", ";
			}

			String url = req.getRequestURI();
			logger.info(getIpAddress(req)+":request url:" + url);
			if(!StringUtils.isEmpty(params))
				logger.info("request paramete:" + params.substring(0, params.lastIndexOf(",")));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	  }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
