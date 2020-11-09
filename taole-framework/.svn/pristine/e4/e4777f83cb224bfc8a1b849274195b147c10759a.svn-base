package com.taole.framework.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONObject;

public class BeanUpdater {

	public static final String[] DATE_FORMATS = new String[] { "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy年MM月dd日" };

	@SuppressWarnings("unchecked")
	private static ThreadLocal<Map<Object, BeanChangeDescriptor>> tl = (ThreadLocal<Map<Object, BeanChangeDescriptor>>) ThreadLocalFactory.createThreadLocal();

	public static Map<Object, BeanChangeDescriptor> getChangeMap() {
		Map<Object, BeanChangeDescriptor> map = (Map<Object, BeanChangeDescriptor>) tl.get();
		if (map == null) {
			map = new HashMap<Object, BeanChangeDescriptor>();
			tl.set(map);
		}
		return map;
	}

	public static BeanChangeDescriptor getBeanChangeDescriptor(Object bean) {
		return getBeanChangeDescriptor(bean, false);
	}

	public static BeanChangeDescriptor getBeanChangeDescriptor(Object bean, boolean autoCreate) {
		Map<Object, BeanChangeDescriptor> map = getChangeMap();
		BeanChangeDescriptor info = map.get(bean);
		if (info == null && autoCreate) {
			map.put(bean, info = new BeanChangeDescriptor(bean));
		}
		return info;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateObject(Object bean, Map<String, ?> changeProps) {
		if (bean instanceof Map) {
			Map mapBean = (Map) bean;
			mapBean.putAll(changeProps);
			return;
		}
		BeanChangeDescriptor info = getBeanChangeDescriptor(bean, true);
		BeanMap beanMap = BeanMap.create(bean);
		for (String propName : changeProps.keySet()) {
			if (!beanMap.containsKey(propName)) {
				continue;
			}
			Object newVal = changeProps.get(propName);
			if (newVal == null) {
				continue;
			}
			Object oldVal = beanMap.get(propName);
			if (oldVal != null && newVal != null && oldVal instanceof Map && newVal instanceof Map) {
				Map map0 = (Map) oldVal;
				Map map1 = (Map) newVal;
				map0.putAll(map1);
			} else if (!BeanUtils.equalsIgnoreType(oldVal, newVal)) {
				updateBeanMap(beanMap, bean, propName, newVal, oldVal);
				info.addChangeEntry(propName, oldVal, newVal);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void updateBeanMap(BeanMap beanMap, Object bean, String propName, Object newVal, Object oldVal) {
		try {
			beanMap.put(propName, newVal);
		} catch (java.lang.ClassCastException e) {
			if (Enum.class.isAssignableFrom(beanMap.getPropertyType(propName)) && String.class.isInstance(newVal)) {
				try {
					newVal = Enum.valueOf(beanMap.getPropertyType(propName), (String) newVal);
				} catch (IllegalArgumentException e1) {
					newVal = null;
				}
				beanMap.put(propName, newVal);
			} else if (Date.class.isAssignableFrom(beanMap.getPropertyType(propName)) && String.class.isInstance(newVal)) {
				try {
					if (!StringUtils.isEmpty(String.valueOf(newVal))) {
						newVal = DateUtils.parseDate(String.valueOf(newVal), DATE_FORMATS);
					}
				} catch (Exception e2) {
					newVal = null;
				}
				beanMap.put(propName, newVal);
			} else if (Double.class.isAssignableFrom(beanMap.getPropertyType(propName)) && Integer.class.isInstance(newVal)) {
				double val = 0.00;
				try {
					if (!StringUtils.isEmpty(String.valueOf(newVal))) {
						val = (Integer)newVal;
						beanMap.put(propName, val);
					}
				} catch (Exception e2) {
					e.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
		}
	}

	private static void updateObjectBySpecProperties(Object bean, Object other, Set<String> updatePropSet) {
		BeanMap map0 = BeanMap.create(bean);
		BeanMap map1 = BeanMap.create(other);
		for (Object propName : map1.keySet()) {
			if (!map0.containsKey(propName) || (updatePropSet != null && !updatePropSet.contains(propName))) {
				continue;
			}
			Object newVal = map1.get(propName);
			map0.put(propName, newVal);
		}
	}

	public static void updateObject(Object bean, Object other) {
		updateObjectBySpecProperties(bean, other, null);
	}

	@SuppressWarnings({ "unchecked" })
	public static void updateObject(Object bean, JSONObject changeProps) {
		Object updateObj = JSONTransformer.transformJso2Pojo(changeProps, null);
		if (updateObj instanceof Map) {
			Map<String, ?> map = (Map<String, ?>) updateObj;
			updateObject(bean, map);
		} else if (bean.getClass().isInstance(updateObj)) {
			updateObjectBySpecProperties(bean, updateObj, new HashSet<String>(ArrayUtils.asList(changeProps.keys(), String.class)));
		}
	}

	public static void updateObject(Object bean, String propName, Object value) {
		BeanChangeDescriptor info = getBeanChangeDescriptor(bean, true);
		BeanMap beanMap = BeanMap.create(bean);
		Object oldVal = beanMap.get(propName);
		if (oldVal != value) {
			beanMap.put(propName, value);
			info.addChangeEntry(propName, oldVal, value);
		}
	}

	public static void addBeanChangEntry(Object bean, String propName, Object oldValue, Object newVal) {
		BeanChangeDescriptor info = getBeanChangeDescriptor(bean, true);
		info.addChangeEntry(propName, oldValue, newVal);
	}

	public static class BeanChangeDescriptor {

		private Object bean;

		private Map<String, ChangeEntry> changeMap = new HashMap<String, ChangeEntry>();

		public BeanChangeDescriptor(Object bean) {
			this.bean = bean;
		}

		public Object getBean() {
			return bean;
		}

		public void addChangeEntry(ChangeEntry entry) {
			changeMap.put(entry.getPropertyName(), entry);
		}

		public void addChangeEntry(String propName, Object oldValue, Object newVal) {
			addChangeEntry(new ChangeEntry(propName, MessageUtils.getLocaleMessage(bean.getClass().getName() + "." + propName, propName), oldValue, newVal));
		}

		public void removeChangeEntry(ChangeEntry entry) {
			changeMap.put(entry.getPropertyName(), entry);
		}

		public void clear() {
			changeMap.clear();
		}

		public List<ChangeEntry> getChangeEntries() {
			return new ArrayList<ChangeEntry>(changeMap.values());
		}

		public static class ChangeEntry {

			private String propertyName;
			private String propertyDescription;
			private Object oldValue;
			private Object newValue;

			public ChangeEntry(String propertyName, String propertyDescription, Object oldValue, Object newValue) {
				this.propertyName = propertyName;
				this.propertyDescription = propertyDescription;
				this.oldValue = oldValue;
				this.newValue = newValue;
			}

			public ChangeEntry(String propertyName, Object oldValue, Object newValue) {
				this(propertyName, null, oldValue, newValue);
			}

			public String getPropertyDescription() {
				return propertyDescription;
			}

			public String getPropertyName() {
				return propertyName;
			}

			public Object getOldPropertyValue() {
				return oldValue;
			}

			public Object getNewPropertyValue() {
				return newValue;
			}

		}

	}

}
