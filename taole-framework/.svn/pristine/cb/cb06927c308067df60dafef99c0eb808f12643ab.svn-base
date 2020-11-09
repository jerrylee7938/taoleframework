package com.taole.framework.dao.util;

import org.apache.commons.collections.Predicate;

public abstract class PredicateCondition<T> implements Predicate, Condition {

	public abstract boolean accept(T entity);

	@SuppressWarnings("unchecked")
	public boolean evaluate(Object object) {
		if (object == null) {
			return false;
		}
		try {
			return accept((T) object);
		} catch (ClassCastException e) {
			return false;
		}
	}

}
