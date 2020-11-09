package com.taole.framework.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AnnotationUtils {

	public static Method[] getAnnotationMethods(Class<?> type, Class<? extends Annotation> annotationType) {
		List<Method> list = new ArrayList<Method>();
		for (Method method : type.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotationType)) {
				list.add(method);
			}
		}
		return list.toArray(new Method[0]);
	}

	public static Method[] getAnnotationMethodsWithSupers(Class<?> type, Class<? extends Annotation> annotationType) {
		List<Method> list = new ArrayList<Method>();
		for (Method method : type.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotationType)) {
				list.add(method);
			}
		}
		Class<?> superClass = type.getSuperclass();
		if (superClass != null) {
			for (Method m : getAnnotationMethodsWithSupers(superClass, annotationType)) {
				list.add(m);
			}
		}
		return list.toArray(new Method[0]);
	}

	public static Field[] getAnnotationDeclaredFields(Class<?> type, Class<? extends Annotation> annotationType) {
		List<Field> list = new ArrayList<Field>();
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotationType)) {
				list.add(field);
			}
		}
		return list.toArray(new Field[0]);
	}

	public static Field[] getAnnotationDeclaredFieldsWithSupers(Class<?> type, Class<? extends Annotation> annotationType) {
		List<Field> list = new ArrayList<Field>();
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotationType)) {
				list.add(field);
			}
		}
		Class<?> superClass = type.getSuperclass();
		if (superClass != null) {
			list.addAll(Arrays.asList(getAnnotationDeclaredFieldsWithSupers(superClass, annotationType)));
		}
		return list.toArray(new Field[0]);
	}

	public static Field[] getAnnotationStaticDeclaredFields(Class<?> type, Class<? extends Annotation> annotationType) {
		List<Field> list = new ArrayList<Field>();
		for (Field field : type.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(annotationType)) {
				list.add(field);
			}
		}
		return list.toArray(new Field[0]);
	}

	public static String[] getAnnotationDeclaredFieldNames(Class<?> type, Class<? extends Annotation> annotationType) {
		List<String> list = new ArrayList<String>();
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotationType)) {
				list.add(field.getName());
			}
		}
		return list.toArray(new String[0]);
	}

	public static String[] getAnnotationStaticDeclaredFieldNames(Class<?> type, Class<? extends Annotation> annotationType) {
		List<String> list = new ArrayList<String>();
		for (Field field : type.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(annotationType)) {
				list.add(field.getName());
			}
		}
		return list.toArray(new String[0]);
	}

	public static <T extends Annotation> Map<String, T> getFieldAnnotationsWithSupers(Class<?> type, Class<T> annotationType) {
		Map<String, T> map = new HashMap<String, T>();
		for (Field field : type.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			T annotation = field.getAnnotation(annotationType);
			if (annotation != null) {
				map.put(field.getName(), annotation);
			}
		}
		Class<?> superClass = type.getSuperclass();
		if (superClass != null) {
			Map<String, T> parentMap = getFieldAnnotationsWithSupers(superClass, annotationType);
			parentMap.putAll(map);
			return parentMap;
		} else {
			return map;
		}
	}

	public static Object getAnnotationAttribute(Annotation annotation, String attributeName) {
		try {
			Method method = annotation.getClass().getDeclaredMethod(attributeName, new Class[0]);
			return method.invoke(annotation);
		} catch (Exception ex) {
			return null;
		}
	}

	public final static String[] getFullInstanceDeclaredEntityFieldNames(Class<?> type) {
		List<String> list = new ArrayList<String>();
		Class<?> superClass = type.getSuperclass();
		if (superClass != null) {
			for (String name : getFullInstanceDeclaredEntityFieldNames(superClass)) {
				list.add(name);
			}
		}
		for (Field field : type.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(NonEntityField.class)) {
				list.add(field.getName());
			}
		}
		return list.toArray(new String[0]);
	}

	private static Map<Class<?>, Field> primaryKeyMap = new HashMap<Class<?>, Field>();

	public final static Field getPrimaryKeyField(Class<?> type) {
		if (type == Object.class) {
			return null;
		}
		if (primaryKeyMap.containsKey(type)) {
			return primaryKeyMap.get(type);
		}
		Field pkField = null;
		for (Field field : type.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(PrimaryKey.class)) {
				pkField = field;
				break;
			}
		}
		if (pkField == null && type.getSuperclass() != null && type.getSuperclass() != Object.class) {
			pkField = getPrimaryKeyField(type.getSuperclass());
		}
		primaryKeyMap.put(type, pkField);
		return pkField;
	}

	public final static String getPrimaryKeyName(Class<?> type) {
		Field pkField = getPrimaryKeyField(type);
		return pkField == null ? null : pkField.getName();
	}

	private static Map<Class<?>, String> enumValueFieldMap = new HashMap<Class<?>, String>();

	public synchronized final static String getEnumValueField(Class<?> type) {
		String field = enumValueFieldMap.get(type);
		if (field == null) {
			field = "value";
			EnumValueField anno = type.getAnnotation(EnumValueField.class);
			if (anno != null) {
				field = anno.value();
			}
			enumValueFieldMap.put(type, field);
		}
		return field;
	}

	public static <A extends Annotation> A getClassAnnotation(Class<?> type, Class<A> annotationClass) {
		A a = type.getAnnotation(annotationClass);
		if (a == null && type.getSuperclass() != null && type.getSuperclass() != Object.class) {
			return getClassAnnotation(type.getSuperclass(), annotationClass);
		} else {
			return a;
		}
	}

}
