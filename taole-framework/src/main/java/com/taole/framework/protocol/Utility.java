/*
 * @(#)ProtocalHandler.java 0.1 May 20, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.protocol;

import com.taole.framework.bean.DomainObject;
import com.taole.framework.util.DomainObjectUtils;

public abstract class Utility {
	
	public static String getEntityUri(Class<? extends DomainObject> type, String id) {
		return getEntityUriPrefix(type) + id;
	}
	
	public static String getEntityUriPrefix(Class<? extends DomainObject> type) {
		return DomainObjectUtils.getEntityName(type) + ":";
	}
	
	public static String getEntityIdByUri(String uri) {
		if (uri == null) {
			return uri;
		}
		int offset = uri.indexOf(":");
		return (offset != -1) ? uri.substring(offset + 1) : uri;
	}


}
