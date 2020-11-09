package com.taole.framework.manager;

import com.taole.framework.bean.DomainObject;

public interface DomainCRUDWrapper<T extends DomainObject> {

	public abstract T get(String id);
	public abstract String create(T entity);
	public abstract void update(T entity);
	public abstract void delete(T entity);
	public boolean supports(Class<?> type);


}
