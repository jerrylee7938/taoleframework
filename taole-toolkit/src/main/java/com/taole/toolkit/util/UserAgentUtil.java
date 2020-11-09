package com.taole.toolkit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * request中的UserAgent处理解析工具类
 * @author Administrator
 *
 */
public class UserAgentUtil {
	/**
	 * android平台常量，调用方可使用进行判断
	 */
	public static String Android = "android";

	/**
	 * android平台常量，调用方可使用进行判断
	 */
	public static String IOS = "ios";

	/**
	 * pc平台常量，调用方可使用进行判断
	 */
	public static String PC = "pc";

	/**
	 * 微信平台常量，调用方可使用进行判断
	 */
	public static String WeChat = "wx";

	/**
	 * WAP平台常量，调用方可使用进行判断
	 */
	public static String Wap = "wap";

	private static String androidReg = "\\bandroid|Nexus\\b";
	private static String iosReg = "ip(hone|od|ad)";

	private static Pattern androidPat = Pattern.compile(androidReg, Pattern.CASE_INSENSITIVE);
	private static Pattern iosPat = Pattern.compile(iosReg, Pattern.CASE_INSENSITIVE);

	/**
	 * 根据useragent匹配是否是Android平台
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean likeAndroid(String userAgent) {
		if (null == userAgent) {
			userAgent = "";
		}
		// 匹配
		Matcher matcherAndroid = androidPat.matcher(userAgent);
		if (matcherAndroid.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据useragent匹配是否是ios平台
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean likeIOS(String userAgent) {
		if (null == userAgent) {
			userAgent = "";
		}
		// 匹配
		Matcher matcherIOS = iosPat.matcher(userAgent);
		if (matcherIOS.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据useragent匹配是否是微信平台
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean likeWeixin(String userAgent) {
		if (userAgent.indexOf("micromessenger") > 0) {// 是微信浏览器
			return true;
		} else {
			return false;
		}
	}

	public static String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		if (StringUtils.isBlank(userAgent)) {
			userAgent = "none";
		} else {
			// if (likeAndroid(userAgent)) {
			// userAgent = "android";
			// } else if (likeIOS(userAgent)) {
			// userAgent = "ios";
			// } else {
			if (userAgent.length() > 500) {
				userAgent = userAgent.substring(0, 500);
			}
			// }
		}
		return userAgent;
	}

	/**
	 * 根据UA判断是从哪个平台来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String getFromByUa(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		return getFromByUa(userAgent);
	}

	public static String getFromByUa(String userAgent) {
		if (StringUtils.isNotBlank(userAgent)) {
			if (likeWeixin(userAgent)) {
				return WeChat;// 来自微信
			} else if (likeAndroid(userAgent)) {
				if (userAgent.indexOf("mozilla") > 0) {
					return Wap;// 来自android平台的wap
				} else {
					return Android;// 来自android应用
				}
			} else if (likeIOS(userAgent)) {
				if (userAgent.indexOf("mozilla") > 0) {
					return Wap;// 来自IOS平台的wap
				} else {
					return IOS;// 来自ios应用
				}
			} else {
				return PC;
			}
		}
		return null;
	}
}
