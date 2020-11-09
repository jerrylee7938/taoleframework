package com.taole.toolkit.util;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.taole.framework.util.ServletUtils;

@SuppressWarnings("ALL")
public class PathUtil {

	public static String getRiskControlDir(HttpServletRequest request, String id) {
		String rootDirPath = "/etc/httpd/riskControl/";
		String riskControlPath = rootDirPath + DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD) + "/" + id + "/";
		File file = new File(riskControlPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return riskControlPath;
	}

	public static String getRiskControlDownLoadDir(HttpServletRequest request, String id, String downLoadUrl) {
		return downLoadUrl + "riskControl/" + DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD) + "/" + id + "/";
	}

	/**
	 * 得到一个容器内的应用根url，用于寻找业务服务
	 */
	public static String getServiceRootUrl(HttpServletRequest request, String appName) {
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ appName;
		return strUrl;
	}

	/**
	 * 获取用户的ip地址
	 * 
	 * @param request
	 * @return
	 */
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
}
