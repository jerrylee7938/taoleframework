/*
 * @(#)ProtocalHandler.java 0.1 May 20, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.protocol;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.bean.DomainObject;
import com.taole.framework.manager.DomainCRUDWrapper;
import com.taole.framework.manager.DomainEngineFactory;
import com.taole.framework.util.DomainObjectUtils;

public class EntityProtocolHandler extends AbstractProtocolHandler<Object> {

	Log logger = LogFactory.getLog(getClass());

	public static final String PROTOCOL = "entity";

	static final String URI_PREFIX = PROTOCOL + "://" + "local";

	@Autowired
	DomainEngineFactory domainEngineFactory;

	public Object getObject(String uri) {
		String path = uri.substring(URI_PREFIX.length());
		return findEntityProtocolParser(path).getEntity(path);
	}

	public String getURI(Object entity) {
		return URI_PREFIX + findEntityProtocolParser(entity).getPath(entity);
	}

	public boolean supports(Class<?> type) {
		return false;
	}

	public String getProtocol() {
		return PROTOCOL;
	}

	public EntityProtocolParser findEntityProtocolParser(String path) {
		searchAndRegisterParsers();
		for (EntityProtocolParser parser : parsers) {
			if (parser.supports(path)) {
				return parser;
			}
		}
		return null;
	}

	public EntityProtocolParser findEntityProtocolParser(Object entity) {
		searchAndRegisterParsers();
		for (EntityProtocolParser parser : parsers) {
			if (parser.supports(entity)) {
				return parser;
			}
		}
		return null;
	}

	private Set<EntityProtocolParser> parsers = new HashSet<EntityProtocolParser>();

	public void register(EntityProtocolParser parser) {
		parsers.add(parser);
	}

	public void unregister(EntityProtocolParser parser) {
		parsers.remove(parser);
	}

	private boolean searched = false;
	
	private void searchAndRegisterParsers () {
		if (searched) {
			return;
		}
		Map<Class<? extends DomainObject>, DomainCRUDWrapper<? extends DomainObject>> map = domainEngineFactory.getCRUDcrudWarpperMap();
		for (Class<? extends DomainObject> type: map.keySet()) {
			EntityProtocolParser parser = generateEntityProtocolParser(map.get(type), type);
			register(parser);
		}
		searched = true;
	}

	public EntityProtocolParser generateEntityProtocolParser(final DomainCRUDWrapper<? extends DomainObject> wrapper, final Class<? extends DomainObject> type) {

		return new EntityProtocolParser() {

			String rootPath = null;

			String getRootPath() {
				if (rootPath == null) {
					rootPath = "/" + DomainObjectUtils.getEntityName(type) + "/";
				}
				return rootPath;
			}

			private Object getEntityById(String id) {
				return wrapper.get(id);
			}

			public boolean supports(Object entity) {
				return type.isInstance(entity);
			}

			public boolean supports(String path) {
				return path.startsWith(getRootPath());
			}

			public String getPath(Object entity) {
				return getRootPath() + DomainObjectUtils.getPrimaryKeyValue(entity);
			}

			public Object getEntity(String path) {
				return getEntityById(path.substring(getRootPath().length()));
			}

		};
	}

	EntityProtocolParser defaultParser = new EntityProtocolParser() {

		public boolean supports(String path) {
			return true;
		}

		public boolean supports(Object entity) {
			return entity instanceof DomainObject;
		}

		public String getPath(Object entity) {
			return "/" + entity.getClass().getName() + "/" + entity;
		}

		public Object getEntity(String path) {
			return null;
		}
	};
	
	
	public static interface EntityProtocolParser {

		public boolean supports(Object entity);

		public boolean supports(String path);

		public String getPath(Object entity);

		public Object getEntity(String path);

	}


}
