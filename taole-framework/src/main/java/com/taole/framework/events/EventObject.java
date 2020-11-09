/*
 * @(#)EventObject.java 0.1 2009-10-10
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.events;

import java.util.HashMap;
import java.util.Map;


public class EventObject {

	private EventTarget target;
	private String type;
	private Object source;
	private boolean preventDefault = false;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public Object getSource() {
		return source;
	}

	public void setTarget(EventTarget target) {
		this.target = target;
	}

	public EventTarget getTarget() {
		return target;
	}

	public String getType() {
		return type;
	}
	
	public void preventInvoke() {
		preventDefault = true;
	}
	
	public boolean isPreventDefault() {
		return preventDefault;
	}

	public EventObject(String type, Object source) {
		this.type = type;
		this.source = source;
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}
	
	public <T> T getAttribute(String name, Class<T> clz) {
		return clz.cast(attributes.get(name));
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}
	
	public final static class Type {
		public final static String BEFORECREATE = "beforecreate";
		public final static String BEFOREUPDATE = "beforeupdate";
		public final static String BEFOREDELETE = "beforedelete";
		public final static String CREATED = "created";
		public final static String UPDATED = "updated";
		public final static String DELETED = "deleted";
		public final static String CHANGED = "changed";
	}
	
	public final static class Attribute {
		public final static String ACTOR_USERID = "actor-userid";
		public final static String ACTOR_USERNAME = "actor-username";
		public final static String ACTOR_NICKNAME = "actor-nickname";
		public final static String ACTOR_PERSONID = "actor-personid";
		public final static String ACTOR_DOMAINID = "actor-domainid";
		public final static String ACTOR_IP = "actor-ip";
		public final static String ARGUMENTS = "arguments";
		public final static String RESULT = "result";
		public final static String ARGUMENTS_SNAPSHOTS = "arguments-snapshots";
		public final static String SOURCE = "source";
		
	}

}
