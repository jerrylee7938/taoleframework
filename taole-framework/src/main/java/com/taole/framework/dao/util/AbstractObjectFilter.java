package com.taole.framework.dao.util;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObjectFilter<T> implements ObjectFilter<T> {

	public List<T> doFilter(List<T> list) {
		List<T> result = new ArrayList<T>();
		for (T obj: list) {
			if (accept(obj)) {
				result.add(obj);
			}
		}
		return result;
	}

}
