package com.taole.framework.rest;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.DomainObjectUtils;

public class DataModel {

	private String entityName;
	private String javaClass;
	private Map<String, String> fields = new HashMap<String, String>();

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public DataModel addFields(String... names) {
		for (String name : names) {
			addField(name, "string");
		}
		return this;
	}

	public DataModel addField(String name) {
		return addField(name, "string");
	}

	public DataModel addField(String name, String type) {
		getFields().put(name, type);
		return this;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		try {
			json.put("javaClass", entityName);
			json.put("entityName", entityName);
			JSONArray array = new JSONArray();
			json.put("fields", array);
			for (String name : fields.keySet()) {
				try {
					JSONObject field = new JSONObject();
					field.put("name", name);
					field.put("type", fields.get(name));
					array.put(field);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			return null;
		}
		return json;
	}

	public static DataModel getDataModel(Class<?> type) {
		DataModel dm = new DataModel();
		dm.setJavaClass(type.getName());
		dm.setEntityName(DomainObjectUtils.getEntityName(type));
		try {
			BeanInfo info = Introspector.getBeanInfo(type);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			Set<String> props = new HashSet<String>(Arrays.asList(BeanUtils.getFullInstanceDeclaredFieldNames(type)));
			for (int i = 0; i < properties.length; i++) {
				String name = properties[i].getName();
				if (props.contains(name)) {
					dm.getFields().put(name, getTypeName(properties[i].getPropertyType()));
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		dm.getFields().put("$displays", "object");
		return dm;
	}

	public static DataModel getDataModelWithoutFields(Class<?> type) {
		DataModel dm = new DataModel();
		dm.setJavaClass(type.getName());
		dm.setEntityName(DomainObjectUtils.getEntityName(type));
		return dm;
	}

	private static String getTypeName(Class<?> type) {
		if (type == Object.class) {
			return "object";
		} else if (type == java.lang.String.class) {
			return "string";
		} else if (type == Integer.class || type == int.class) {
			return "int";
		} else if (type == Long.class || type == long.class) {
			return "long";
		} else if (type == Float.class || type == float.class) {
			return "float";
		} else if (type == Double.class || type == double.class) {
			return "double";
		} else if (type.isAssignableFrom(Number.class)) {
			return "number";
		} else if (type.isAssignableFrom(java.util.Date.class)) {
			return "date";
		} else if (type == Boolean.class || type == boolean.class) {
			return "boolean";
		} else if (type.isAssignableFrom(Enum.class)) {
			return "string";
		} else {
			return "object";
		}
	}
}
