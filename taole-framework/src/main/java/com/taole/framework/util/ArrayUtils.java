package com.taole.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

public abstract class ArrayUtils {

	public static <T> void sort(List<T> list, PropertyFetcher<T> fetcher, final boolean isAsc) {

		final Map<T, Object> map = new HashMap<T, Object>();
		for (T obj : list) {
			map.put(obj, fetcher.getProperty(obj));
		}
		Collections.sort(list, new Comparator<T>() {
			public int compare(T o1, T o2) {
				Object v1 = map.get(o1);
				Object v2 = map.get(o2);
				int i = 0;
				if (v1 == null && v2 == null) {
					i = 0;
				} else if (v1 == null && v2 != null) {
					i = -1;
				} else if (v1 != null && v2 == null) {
					i = 1;
				} else if (v1 instanceof Date && v2 instanceof Date) {
					i = ((Date) v1).compareTo((Date) v2);
				} else if (v1 instanceof Number && v2 instanceof Number) {
					i = (int) (((Number) v1).doubleValue() - ((Number) v2).doubleValue());
				} else {
					i = v1.toString().compareTo(v2.toString());
				}
				return (isAsc ? 1 : -1) * i;
			}
		});
	}

	public static <T> void sort(List<T> list, final String property, final boolean isAsc) {
		sort(list, new BasedNamePropertyFetcher<T>(property), isAsc);
	}

	public static <T> void moveOffset(List<T> list, T object, int offset, final OrderWrapper<T> wrapper) {

		if (!list.contains(object)) {
			// 如果list中部包换object，则无法排序
			return;
		}
		Map<T, Integer> oldIndexMap = new HashMap<T, Integer>();
		for (T entity : list) {
			oldIndexMap.put(entity, wrapper.getOrder(entity));
		}
		Comparator<T> typeComparator = new Comparator<T>() {
			public int compare(T o1, T o2) {
				return wrapper.getOrder(o1) - wrapper.getOrder(o2);
			}
		};
		Collections.sort(list, typeComparator);
		for (int i = 0; i < list.size(); i++) {
			wrapper.setOrder(list.get(i), i * 2);
		}
		if (offset >= 9) {
			wrapper.setOrder(object, Integer.MAX_VALUE);
		} else if (offset <= -9) {
			wrapper.setOrder(object, Integer.MIN_VALUE);
		} else if (offset >= 1) {
			wrapper.setOrder(object, wrapper.getOrder(object) + 1 + offset * 2);
		} else if (offset <= -1) {
			wrapper.setOrder(object, wrapper.getOrder(object) - 1 + offset * 2);
		}
		Collections.sort(list, typeComparator);
		for (int i = 0; i < list.size(); i++) {
			T entity = list.get(i);
			int newIndex = i;
			int oldIndex = oldIndexMap.get(entity).intValue();
			wrapper.setOrder(entity, newIndex);
			if (newIndex != oldIndex) {
				wrapper.update(entity);
			}
		}
	}

	public static <T> void moveIndex(List<T> list, T object, int index, final OrderWrapper<T> wrapper) {
		if (!list.contains(object)) {
			// 如果list中部包换object，则无法排序
			return;
		}
		Map<T, Integer> oldIndexMap = new HashMap<T, Integer>();
		for (T entity : list) {
			oldIndexMap.put(entity, wrapper.getOrder(entity));
		}
		Comparator<T> typeComparator = new Comparator<T>() {
			public int compare(T o1, T o2) {
				return wrapper.getOrder(o1) - wrapper.getOrder(o2);
			}
		};
		Collections.sort(list, typeComparator);
		list.remove(object);
		list.add(index, object);
		for (int i = 0; i < list.size(); i++) {
			T entity = list.get(i);
			int newIndex = i;
			int oldIndex = oldIndexMap.get(entity).intValue();
			wrapper.setOrder(entity, newIndex);
			if (newIndex != oldIndex) {
				wrapper.update(entity);
			}
		}
	}

	public static List<?> asList(Iterator<?> iter) {
		List<Object> list = new ArrayList<Object>();
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		return list;
	}
	
	public static <T> List<T> asList(Iterator<?> iter, Class<T> t) {
		List<T> list = new ArrayList<T>();
		while (iter.hasNext()) {
			list.add(t.cast(iter.next()));
		}
		return list;
	}

	public static interface PropertyFetcher<T> {
		public Object getProperty(T obj);
	}

	public static class BasedNamePropertyFetcher<T> implements PropertyFetcher<T> {
		String property;
		boolean simple;
		String[] ps;

		public BasedNamePropertyFetcher(String property) {
			this.property = property;
			simple = !property.contains(".");
			if (!simple) {
				ps = property.split(".");
			}
		}

		public Object getProperty(T obj) {
			if (simple) {
				return BeanMap.create(obj).get(property);
			} else {
				Object o = obj;
				for (String p : ps) {
					o = BeanMap.create(o).get(p);
					if (o == null) {
						return null;
					}
				}
				return o;
			}

		}
	}

	public static interface OrderWrapper<T> {

		public int getOrder(T obj);

		public void setOrder(T obj, int index);

		public void update(T obj);
	}

}
