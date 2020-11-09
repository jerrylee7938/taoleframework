package com.taole.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.mapping.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.rest.ResultSet.Row;

public class SerializableJSONTransformer {

	private SerializableJSONTransformer() {
	}

	private static final Logger logger = LoggerFactory.getLogger(SerializableJSONTransformer.class);
	
	public static final String JSON_DATE_FORMAT = "yyyy-MM-dd' 'HH:mm:ss";

	public static final String ENTITY_NAME_FIELD = "entityName";
	
	public static final String ENTITY_TYPE_FIELD = "javaClass";

	public static String[] SUPPORT_DATEFORMATS = { JSON_DATE_FORMAT, "yyyy-MM-dd' 'HH:mm:ss", "yyyy-MM-dd' 'HH:mm", "yyyy-MM-dd" };
	
	@SuppressWarnings("unchecked")
	public static <T> T transformJso2Pojo(JSONObject o, Class<T> clz) {
		if (clz != null) {
			return clz.cast(doTransformJso2Pojo(o, clz));
		} else {
			return (T) doTransformJso2Pojo(o, clz);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T transformJso2Pojo(JSONArray array, Class<T> clz) {
		if (clz != null) {
			return clz.cast(doTransformJso2Pojo(array, clz));
		} else {
			return (T) doTransformJso2Pojo(array, clz);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object doTransformJso2Pojo(Object o, Class<?> type) {

		try {
			if (o == null) {
				return null;
			} else if (o instanceof JSONArray) {
				Collection<Object> objs = null;
				if (type != null && type == java.util.Set.class) {
					objs = new HashSet<Object>();
				} else {
					objs = new ArrayList<Object>();
				}
				JSONArray items = (JSONArray) o;
				for (int i = 0, l = items.size(); i < l; i++) {
					objs.add(doTransformJso2Pojo(items.get(i), type));
				}
				return objs;
			} else if (o instanceof JSONObject) {
				JSONObject json = (JSONObject) o;
				if (type != null && !(json.containsKey(ENTITY_NAME_FIELD) || json.containsKey(ENTITY_TYPE_FIELD))) {
					return setValue(type, json, type);
				} else if (json.containsKey(ENTITY_NAME_FIELD) || json.containsKey(ENTITY_TYPE_FIELD)) {

					Class<?> entityClass = null;
					if (json.containsKey(ENTITY_TYPE_FIELD)) {
						entityClass = Class.forName(json.getString(ENTITY_TYPE_FIELD));
					} else {
						entityClass = getEntityType(json.getString(ENTITY_NAME_FIELD));
					}
					return setValue(type, json, entityClass);
				} else {
					Map<String, Object> map = setMapValue(type, json);
					return map;
				}
			}
			if (type == null) {
				return o;
			} else if (type == Date.class && String.class.isInstance(o)) {
				String dateStr = String.valueOf(o);
				if (StringUtils.isBlank(dateStr)) {
					return null;
				}
				return DateUtils.parseDate(dateStr.trim(), SUPPORT_DATEFORMATS);
			} else if ((type == Double.class || type == double.class)) {
				return Double.parseDouble(String.valueOf(o));
			} else if (type == Integer.class || type == int.class) {
				return Integer.parseInt(String.valueOf(o));
			} else if (type == BigDecimal.class) {
				return NumberUtils.createBigDecimal(String.valueOf(o));
			} else if (type == BigInteger.class) {
				return NumberUtils.createBigInteger(String.valueOf(o));
			} else if (type.isEnum()) {
				return Enum.valueOf((Class<Enum>) type, o.toString());
			} else {
				return o;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param type
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	private static Map<String, Object> setMapValue(Class<?> type, JSONObject json) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> jsonMap = (Map<String, Object>) json.clone();
		Iterator<?> iter = jsonMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object oval = json.get(key);
			Object nval = doTransformJso2Pojo(oval, type);
			map.put(key, nval);
		}
		return map;
	}

	/**
	 * @param type
	 * @param json
	 * @param entityClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 */
	private static Object setValue(Class<?> type, JSONObject json, Class<?> entityClass) throws InstantiationException, IllegalAccessException, JSONException {
		Object bean;
		if (entityClass == java.util.Map.class) {
			if (json.containsKey("map")) {
				json = json.getJSONObject("map");
			}
			if (type == java.util.Map.class) {
				bean = setMapValue(type, json);
				return bean;
			}
			bean = type.newInstance();
		} else {
			bean = entityClass.newInstance();
		}
		List<String> normProps = BeanUtils.getFullInstanceDeclaredEntityFieldNamesWithout(bean.getClass(), Enum.class);
		BeanMap beanMap = BeanMap.create(bean);
		for (String prop : normProps) {
			if (json.containsKey(prop)) {
				Object oval = json.get(prop);
				Class<?> propertyType = BeanUtils.getPropertyType(entityClass, prop);
				if (propertyType != null && (propertyType == List.class || propertyType == Set.class || propertyType == Collection.class)
						&& oval instanceof JSONArray) {
					try {
						Field field = BeanUtils.getClassField(bean.getClass(), prop);
						ParameterizedType pType = (ParameterizedType) field.getGenericType();
						Object actualTypeArg = pType.getActualTypeArguments()[0];
						// ???? check it TODO
						if (actualTypeArg instanceof Class) {
							propertyType = (Class<?>) pType.getActualTypeArguments()[0];	
						} else {
							ParameterizedType cType = (ParameterizedType) bean.getClass().getGenericSuperclass();
							propertyType =  (Class<?>) cType.getActualTypeArguments()[0];
						}
					} catch (Exception e) {
						logger.error(String.format("json[%s] prop[%s] value[%s] propertyType[%s]", json.toString(), 
								prop, String.valueOf(oval), String.valueOf(propertyType)), e);
					}
				}
				Object nval = doTransformJso2Pojo(oval, propertyType);
				try {
					beanMap.put(prop, nval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		List<Field> enumFields = BeanUtils.getFullInstanceDeclaredEntityFields(bean.getClass(), Enum.class);
		for (Field field : enumFields) {
			String prop = field.getName();
			if (json.containsKey(prop)) {
				if (isNull(json, prop)) {
					beanMap.put(prop, null);
				} else if (json.get(prop) != null && org.apache.commons.lang3.StringUtils.isNotBlank(json.getString(prop))) {
					String oval = json.getString(prop);
					@SuppressWarnings({ "rawtypes", "unchecked" })
					Class<Enum> enumType = (Class<Enum>) field.getType();
					try {
						@SuppressWarnings("unchecked")
						Enum<?> nval = Enum.valueOf(enumType, oval);
						beanMap.put(prop, nval);
					} catch (IllegalArgumentException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return bean;
	}
	
	public static <T> T transformPojo2Jso(Object o, Class<T> clz) throws JSONException {
		Object obj = transformPojo2Jso(o);
		if (obj == null) {
			return null;
		}
		return clz.cast(obj);
	}
	
	public static Object transformPojo2Jso(Object o) throws JSONException {
		return transformPojo2Jso(o, new ArrayList<String>());
	}
	
	public static Object transformPojo2Jso(Object o, List<String> excludeFields) throws JSONException {
		if (o == null) {
			return null;
		} else if (o instanceof Date) {
			return DateFormatUtils.format((Date) o, JSON_DATE_FORMAT);
		} else if (BeanUtils.isBasicInstance(o)) {
			return o;
		} else if (o instanceof Enum<?>) {
			return ((Enum<?>) o).name();
		}
		if (o instanceof JSONObject || o instanceof JSONArray || o instanceof com.alibaba.fastjson.JSONObject) {
			return o;
		} else if (o instanceof Collection<?>) {
			JSONArray jsonlist = new JSONArray();
			for (Object item : (Collection<?>) o) {
				jsonlist.add(transformPojo2Jso(item));
			}
			return jsonlist;
		} else if (o.getClass().isArray()) {
			JSONArray jsonlist = new JSONArray();
			for (Object item : (Object[]) o) {
				jsonlist.add(transformPojo2Jso(item));
			}
			return jsonlist;
		} else if (o instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) o;
			JSONObject json = new JSONObject();
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				if (entry.getKey() instanceof String) {
					json.put((String) entry.getKey(), transformPojo2Jso(entry.getValue()));
				}
			}
			return json;
		} else if (o instanceof ResultSet) {
			JSONArray items = new JSONArray();
			ResultSet rs = (ResultSet) o;
			for (Row row : rs.getRows()) {
				JSONObject item = new JSONObject();
				for (String name : row.getFields()) {
					Object obj = row.getValue(name);
					item.put(name, convertRow2Json(obj));
				}
				items.add(item);
			}
			return items;
		}
		
		JSONObject json = new JSONObject();
		if (o instanceof DomainObject) {
			json.put(ENTITY_NAME_FIELD, getEntityName(o));
		} else {
			json.put(ENTITY_TYPE_FIELD, BeanUtils.getClass(o).getName());
		}

		List<String> props = BeanUtils.getFullInstanceDeclaredEntityFieldNames(o.getClass());
		BeanMap beanMap = BeanMap.create(o);
		for (String prop : props) {
			if (excludeFields != null && excludeFields.contains(prop)) {
				continue;
			}
			Object oval = beanMap.get(prop);
			Object nval = transformPojo2Jso(oval);
			json.put(prop, nval == null ? "" : nval);
		}
		return json;
	}

	public static Object convertRow2Json(Object obj) {
		if (obj instanceof Row) {
			Row row = (Row) obj;
			JSONObject json = new JSONObject();
			for (String name : row.getFields()) {
				Object value = row.getValue(name);
				try {
					json.put(name, transformPojo2Jso(value));
				} catch (JSONException e) {
				}
			}
			return json;
		} else {
			try {
				return transformPojo2Jso(obj);
			} catch (JSONException e) {
				return null;
			}
		}
	}

	public static String getEntityName(Object object) {
		return DomainObjectUtils.getEntityName(object.getClass());
	}

	public static Class<?> getEntityType(String entityName) {
		Class<?> type = DomainObjectUtils.getEntityType(entityName);
		try {
			return type == null ? Class.forName(entityName) : type;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return Object.class;
		}
	}

	private static boolean isNull(JSONObject obj, String key) {
		if(obj.containsKey(key)){
			Object value = obj.get(key);
			if(value == null || value == "" || value == "null")
				return false;
		}
		return true;
	}
}
