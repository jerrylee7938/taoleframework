package com.taole.framework.util;

import static java.lang.String.format;

import java.util.Map;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.Assert;

public class EnvironmentHelper {

	public static String WEB_PROPERTYSOURCE_NAME = "web.env";

	private StandardEnvironment environment;

	public EnvironmentHelper(Environment environment) {
		this.environment = (StandardEnvironment) environment;
	}

	protected Object getWebEnvProperty(String key) {
		Assert.isTrue(getPropertySources().contains(WEB_PROPERTYSOURCE_NAME), String.format("PropertySource named [%s] does not exist", WEB_PROPERTYSOURCE_NAME));
		PropertySource<?> ps = getPropertySources().get(WEB_PROPERTYSOURCE_NAME);
		if (ps != null) {
			Object value = ps.getProperty(key);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getPropertySources()
	 */
	public MutablePropertySources getPropertySources() {
		return environment.getPropertySources();
	}

	/**
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getSystemEnvironment()
	 */
	public Map<String, Object> getSystemEnvironment() {
		return environment.getSystemEnvironment();
	}

	/**
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getSystemProperties()
	 */
	public Map<String, Object> getSystemProperties() {
		return environment.getSystemProperties();
	}

	/**
	 * @param key
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getProperty(java.lang.String)
	 */
	public String getProperty(String key) {
		return getProperty(key, String.class);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getProperty(java.lang.String, java.lang.String)
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		return value == null ? defaultValue : value;
	}

	/**
	 * @param key
	 * @param targetType
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getProperty(java.lang.String, java.lang.Class)
	 */
	public <T> T getProperty(String key, Class<T> targetType) {
		Object value = getWebEnvProperty(key);
		if (value != null) {
			Class<?> valueType = value.getClass();
			ConversionService conversionService = environment.getConversionService();
			if (!conversionService.canConvert(valueType, targetType)) {
				throw new IllegalArgumentException(format("Cannot convert value [%s] from source type [%s] to target type [%s]", value,
						valueType.getSimpleName(), targetType.getSimpleName()));
			}
			return conversionService.convert(value, targetType);
		}
		return environment.getProperty(key, targetType);
	}

	/**
	 * @param key
	 * @param targetType
	 * @param defaultValue
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getProperty(java.lang.String, java.lang.Class, java.lang.Object)
	 */
	public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		T value = getProperty(key, targetType);
		return value == null ? defaultValue : value;
	}

	/**
	 * @param key
	 * @param targetType
	 * @return
	 * @see org.springframework.core.env.AbstractEnvironment#getPropertyAsClass(java.lang.String, java.lang.Class)
	 */
	public <T> Class<T> getPropertyAsClass(String key, Class<T> targetType) {
		return environment.getPropertyAsClass(key, targetType);
	}

}
