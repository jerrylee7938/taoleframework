package com.taole.framework.rest.parser;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.taole.framework.rest.RequestParameters;

public class FormRequestParameters implements RequestParameters {

	private Map<String, Object[]> map;

	private Map<String, Object[]> extraMap = new HashMap<String, Object[]>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FormRequestParameters(Map map) {
		this.map = map;
	}

	public Object getDelegateObject() {
		return map;
	}

	private Map<String, Object[]> getFinalMap() {
		Map<String, Object[]> finalMap = new HashMap<String, Object[]>();
		finalMap.putAll(map);
		finalMap.putAll(extraMap);
		return finalMap;
	}

	public Object getParameter(String name) {
		Object[] values = getFinalMap().get(name);
		return values != null && values.length > 0 ? values[0] : null;
	}

	@Override
	public String[] getParameterNames() {
		return (String[]) getFinalMap().keySet().toArray(new String[0]);
	}

	public Object[] getParameterValues(String name) {
		return getFinalMap().get(name);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getParameter(String name, Class<T> requiredType) {
		if (requiredType.isArray()) {
			return (T) getFinalMap().get(name);
		} else if (requiredType == List.class) {
			Object[] values = getParameterValues(name);
			if (values == null) {
				return null;
			}
			return (T) Arrays.asList(values);
		}
		Object val = getParameter(name);
		if (val == null) {
			return null;
		}
		if (requiredType == String.class) {
			val = val.toString();
		} else if (requiredType != String.class && val instanceof String) {
			String s = (String) val;
			if (StringUtils.isBlank(s)) {
				return null;
			}
			if (requiredType == Integer.class) {
				val = Integer.parseInt(s, 10);
			} else if (requiredType == Long.class) {
				val = Long.parseLong(s, 10);
			} else if (requiredType == Float.class) {
				val = Float.parseFloat(s);
			} else if (requiredType == Double.class) {
				val = Double.parseDouble(s);
			} else if (requiredType == Boolean.class) {
				val = Boolean.parseBoolean(s);
			} else if (requiredType.isEnum()) {
				return requiredType.cast(Enum.valueOf((Class<Enum>) requiredType, val.toString()));
			} else if (requiredType == Date.class) {
				try {
					if (StringUtils.isBlank(s)) {
						return null;
					}
					if (NumberUtils.isNumber(s)) {
						val = new Date(NumberUtils.toLong(s));
					}
					val = DateUtils.parseDate(s, "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日");
				} catch (ParseException e) {
					throw new RuntimeException(String.format("convert string:%s to date error", s), e);
				}
			}
		}
		return (T) val;
	}

	public void setParameter(String name, Object value) {
		if (value == null) {
			extraMap.put(name, new Object[0]);
		} else if (value.getClass().isArray()) {
			extraMap.put(name, (Object[]) value);
		} else {
			extraMap.put(name, new Object[] { value });
		}
	}

	public Object clone() {
		FormRequestParameters other = new FormRequestParameters(new HashMap<String, Object[]>(map));
		other.extraMap = new HashMap<String, Object[]>(extraMap);
		return other;
	}
}
