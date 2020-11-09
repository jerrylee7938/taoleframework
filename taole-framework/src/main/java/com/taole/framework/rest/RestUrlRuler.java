package com.taole.framework.rest;

import com.taole.framework.util.DomainObjectUtils;

public class RestUrlRuler {

	public static final String SERVICE_ROOT = "/service";
	public static final String REST_ROOT = "/rest";

	public static String getRestServiceRoot() {
		return SERVICE_ROOT + REST_ROOT;
	}

	public String getEntityUrl(Object entity, String action) {
		return getRestServiceRoot() + "/" + DomainObjectUtils.getEntityName(entity.getClass()) + "/" + DomainObjectUtils.getPrimaryKeyValue(entity) + "/"
				+ action;
	}

	public String getEntityUrl(Class<?> clz, String entityId, String action) {
		return getRestServiceRoot() + "/" + DomainObjectUtils.getEntityName(clz) + "/" + entityId + "/" + action;
	}

}
