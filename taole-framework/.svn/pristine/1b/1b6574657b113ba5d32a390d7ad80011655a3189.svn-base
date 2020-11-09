package com.taole.framework.rest.parser;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taole.framework.rest.RequestParameters;

public class JSONObjectRequestParameters implements RequestParameters {

	private final Logger logger = LoggerFactory.getLogger(JSONObjectRequestParameters.class);

	private JSONObject jsonObject;

	public JSONObjectRequestParameters(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JSONObject getDelegateObject() {
		return jsonObject;
	}

	@Override
	public Object getParameter(String name) {
		try {
			return jsonObject.get(name);
		} catch (JSONException e) {
			return null;
		}
	}

	public String[] getParameterNames() {
		JSONArray ja = jsonObject.names();
		String[] names = new String[ja.length()];
		for (int i = 0, l = ja.length(); i < l; i++) {
			try {
				names[i] = ja.getString(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return names;
	}

	/**
	 * 根据参数名取参数值数组，如果是JsonArray取里面的值。 {@inheritDoc}
	 */
	public Object[] getParameterValues(String name) {
		Object obj = getParameter(name);
		if (obj == null) {
			return null;
		}
		if (obj instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) obj;
			Object[] objs = new Object[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					objs[i] = jsonArray.get(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return objs;
		} else {
			return new Object[] { obj };
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getParameter(String name, Class<T> requiredType) {
		if (jsonObject.isNull(name)) { // 如果对应属性为 空直接返回 null.
			return null;
		}
		if (requiredType == Date.class) {
			Object value = getParameter(name);
			if (Long.class.isInstance(value) || long.class.isInstance(value)) {
				return (T) new Date(Long.class.cast(value));
			} else if (String.class.isInstance(value)) {
				try {
					return (T) DateUtils.parseDate(String.class.cast(value), "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy年MM月dd日");
				} catch (ParseException e) {
					logger.error("parse string to date error", e);
				}
			}
		} else if (requiredType.isArray() && JSONArray.class.isInstance(getParameter(name))) {
			JSONArray jsonArray = JSONArray.class.cast(getParameter(name));
			Class<?> componentType = requiredType.getComponentType();
			if (jsonArray != null && jsonArray.length() > 0) {
				Object[] a = (Object[]) Array.newInstance(componentType, jsonArray.length());
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						Object v = jsonArray.get(i);
						if (JSONObject.NULL == v) {
							a[i] = null;
						} else {
							a[i] = componentType.cast(v);
						}
					} catch (JSONException e) {
						logger.error("get json object from jsonArray error", e);
					}
				}
				return (T) a;
			} else {
				return (T) Array.newInstance(componentType, 0);
			}
		} else if (List.class == requiredType && JSONArray.class.isInstance(getParameter(name))) {
			JSONArray jsonArray = JSONArray.class.cast(getParameter(name));
			List<T> objList = new ArrayList<T>();
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					objList.add((T) jsonArray.get(i));
				} catch (JSONException e) {
					logger.error("get json object from jsonArray error", e);
				}
			}
			return (T) objList;
		} else if (requiredType.isEnum()) {
			return (T) Enum.valueOf((Class<Enum>) requiredType, (String) getParameter(name));
		}
		return (T) getParameter(name);
	}

	public void setParameter(String name, Object value) {
		try {
			jsonObject.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Object clone() {
		JSONObject json = new JSONObject();
		try {
			json = new JSONObject(jsonObject.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONObjectRequestParameters(json);
	}
}
