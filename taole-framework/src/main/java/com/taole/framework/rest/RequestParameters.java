package com.taole.framework.rest;

public interface RequestParameters extends Cloneable {

	public Object getParameter(String name);

	public <T> T getParameter(String name, Class<T> requiredType);

	public Object[] getParameterValues(String name);

	public String[] getParameterNames();

	public Object getDelegateObject();
	
	public void setParameter(String name, Object value);
	
	public Object clone();

}
