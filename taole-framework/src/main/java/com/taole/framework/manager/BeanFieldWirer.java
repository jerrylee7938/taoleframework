package com.taole.framework.manager;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.annotation.AnnotationUtils;
import com.taole.framework.annotation.FieldType;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.bean.DynamicBeanFactory;
import com.taole.framework.rest.ResultSet.Fetcher;
import com.taole.framework.rest.ResultSet.Row;
import com.taole.framework.util.DomainObjectUtils;
import com.taole.framework.util.MessageUtils;
import com.taole.framework.util.Translator;
import com.opensymphony.util.BeanUtils;

/**
 * 将字段原值翻译成对象
 * 
 * @author wch
 * 
 */
public class BeanFieldWirer {

	@Autowired
	private DomainEngineFactory domainEngineFactory;

	@Autowired
	private DynamicBeanFactory dynamicBeanFactory;

	/**
	 * 获得对象属性的引用值
	 * 
	 * @param entity
	 * @param fieldName
	 * @return
	 */
	public Object getFieldValue(Object entity, String fieldName) {
		Field field = null;
		try {
			field = entity.getClass().getField(fieldName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getFieldValue(entity, field);
	}

	public Object getFieldValue(Object entity, Field field) {
		Object orgValue = BeanUtils.getValue(entity, field.getName());
		if (field != null) {
			FieldType ft = field.getAnnotation(FieldType.class);
			if (ft != null) {
				Class<?> type = ft.type();
				if (DomainObject.class.isAssignableFrom(type) && String.class.isInstance(orgValue)) {
					@SuppressWarnings("unchecked")
					DomainCRUDWrapper<? extends DomainObject> w = domainEngineFactory.getDomainCRUDWrapper((Class<? extends DomainObject>) type);
					if (w != null) {
						return w.get(orgValue.toString());
					}
				} else if (Translator.class.isAssignableFrom(type)) {
					@SuppressWarnings("unchecked")
					Translator<Object, Object> t = (Translator<Object, Object>) dynamicBeanFactory.registerBean(type);
					if (t != null) {
						return t.translate(orgValue);
					}
				}
			}
		}
		return orgValue;
	}

	public String getFieldDisplay(Object entity, Field field) {
		Object value = getFieldValue(entity, field);
		return getDisplay(value, field.getAnnotation(FieldType.class));
	}

	private Fetcher<Map<String, String>> fetcher;

	public synchronized Fetcher<Map<String, String>> getFetcher() {
		if (fetcher == null) {
			fetcher = new Fetcher<Map<String, String>>() {
				@Override
				public Map<String, String> fetch(Row row, Object propName) {
					return getDisplayMap(row.getOriginal());
				}
			};
		}
		return fetcher;
	}

	public Map<String, String> getDisplayMap(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = AnnotationUtils.getAnnotationDeclaredFields(obj.getClass(), FieldType.class);
		for (Field field : fields) {
			FieldType ft = field.getAnnotation(FieldType.class);
			Object val = getFieldValue(obj, field);
			if (val != null) {
				map.put(field.getName(), getDisplay(val, ft));
			}
		}
		return map;
	}

	public Map<String, Object> getRelationMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = AnnotationUtils.getAnnotationDeclaredFields(obj.getClass(), FieldType.class);
		for (Field field : fields) {
			Object val = getFieldValue(obj, field);
			map.put(field.getName(), val);
		}
		return map;
	}

	public Object getParentObject(Object obj) {
		Field[] fields = AnnotationUtils.getAnnotationDeclaredFields(obj.getClass(), FieldType.class);
		for (Field field : fields) {
			FieldType ft = field.getAnnotation(FieldType.class);
			if (ft.parent()) {
				return getFieldValue(obj, field);
			}
		}
		return null;
	}

	private String getDisplay(Object value, FieldType ft) {
		if (value == null) {
			return null;
		} else if (DomainObject.class.isInstance(value)) {
			return DomainObjectUtils.getObjectName((DomainObject) value);
		} else if (Date.class.isInstance(value)) {
			String format = ft != null ? ft.format() : "";
			if (format.isEmpty()) {
				format = "yyyy-MM-dd HH:mm:ss";
			}
			return new SimpleDateFormat(format).format((Date) value);
		} else if (Enum.class.isInstance(value)) {
			return MessageUtils.getLocaleMessage((Enum<?>) value);
		} else {
			return value.toString();
		}
	}

}
