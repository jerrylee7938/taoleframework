package com.taole.framework.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class MessageUtils {

	private static MessageSource defaultMessageSource;

	private static MessageSource customMessageSource;

	private static String[] resources;

	public static String[] getResources() {
		return resources;
	}

	/**
	 * @return the defaultMessageSource
	 */
	public static MessageSource getDefaultMessageSource() {
		return defaultMessageSource;
	}

	/**
	 * @param defaultMessageSource
	 *            the defaultMessageSource to set
	 */
	public static void initDefaultMessageResources(String[] resources) {
		MessageUtils.resources = resources;
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setBasenames(resources);
		MessageUtils.setDefaultMessageSource(ms);
		setDefaultMessageSource(ms);
	}

	/**
	 * @param defaultMessageSource
	 *            the defaultMessageSource to set
	 */
	public static void setDefaultMessageSource(MessageSource defaultMessageSource) {
		MessageUtils.defaultMessageSource = defaultMessageSource;
	}

	public static MessageSource getCustomMessageSource() {
		return customMessageSource;
	}

	public static void setCustomMessageSource(MessageSource customMessageSource) {
		MessageUtils.customMessageSource = customMessageSource;
	}

	public static String getLocaleMessage(String name) {
		return getLocaleMessage(name, null);
	}

	public static String getLocaleMessage(String name, String defaultMsg) {
		String message = null;
		if (customMessageSource != null) {
			message = customMessageSource.getMessage(name, null, null, null);
		}
		if (message == null && defaultMessageSource != null) {
			message = defaultMessageSource.getMessage(name, null, defaultMsg, Locale.getDefault());
		}
		return message;
	}

	public static String getLocaleMessage(Enum<?> obj, String defaultMsg) {
		return getLocaleMessage(obj.getClass().getName() + "." + obj.name(), defaultMsg);
	}

	public static String getLocaleMessage(Enum<?> obj) {
		return getLocaleMessage(obj, obj.name());
	}

}
