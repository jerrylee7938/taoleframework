/*
 * @(#)ProtocolAnalyzer.java 0.1 May 20, 2009
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.cglib.proxy.Factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.taole.framework.bean.DomainObject;
import com.taole.framework.manager.DomainCRUDWrapper;
import com.taole.framework.manager.DomainEngineFactory;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.DomainObjectUtils;

public class ProtocolRegistry {

	private static final Log log = LogFactory.getLog(ProtocolRegistry.class);
	private Map<String, ProtocolHandler<?>> table = new HashMap<String, ProtocolHandler<?>>();
	private Map<Class<?>, ProtocolHandler<?>> cache = new HashMap<Class<?>, ProtocolHandler<?>>();

	@Autowired
	private DomainEngineFactory domainEngineFactory;

	@SuppressWarnings("unchecked")
	public String getURI(Object object) {
		if (object == null) {
			return null;
		}
		ProtocolHandler<?> handler = getProtocolHandler(BeanUtils.getClass(object));
		if (handler != null) {
			return ((ProtocolHandler<Object>) handler).getURI(object);
		} else {
			return "object:" + object.toString();
		}
	}

	public Object getObject(String uri) {
		if (uri == null) {
			return null;
		}
		String protocol = parseProtocol(uri);
		if (protocol == null) {
			return null;
		}
		ProtocolHandler<?> handler = getProtocolHandler(protocol);
		if (handler != null) {
			return handler.getObject(uri);
		}
		return null;
	}

	public <T> T getObject(String uri, Class<T> clz) {
		Object obj = getObject(uri);
		if (obj == null) {
			return null;
		}
		return clz.cast(obj);
	}

	public void register(ProtocolHandler<?> handler) {
		log.debug("register protocol:" + handler.getProtocol());
		table.put(handler.getProtocol(), handler);
	}

	public ProtocolHandler<?> getProtocolHandler(Class<?> type) {
		if (type == null) {
			return null;
		}
		ProtocolHandler<?> handler = cache.get(type);
		if (handler != null) {
			return handler;
		}
		searchAndRegisterProtocolHandler();
		Iterator<ProtocolHandler<?>> iterator = table.values().iterator();
		while (iterator.hasNext()) {
			handler = iterator.next();
			if (handler.supports(type)) {
				cache.put(type, handler);
				return handler;
			}
		}
		return null;
	}

	public ProtocolHandler<?> getProtocolHandler(String protocol) {
		searchAndRegisterProtocolHandler();
		return table.get(protocol);
	}

	public String parseProtocol(String uri) {
		int offset = uri.indexOf(":");
		if (offset != -1) {
			return uri.substring(0, offset);
		} else {
			return null;
		}
	}

	public ProtocolHandler<?> getProtocolHandler(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Factory) {
			return getProtocolHandler(object.getClass().getSuperclass());
		} else {
			return getProtocolHandler(object.getClass());
		}
	}

	private boolean searched = false;

	private void searchAndRegisterProtocolHandler() {
		if (searched) {
			return;
		}
		Map<Class<? extends DomainObject>, DomainCRUDWrapper<? extends DomainObject>> map = domainEngineFactory.getCRUDcrudWarpperMap();
		for (Class<? extends DomainObject> type : map.keySet()) {
			DomainCRUDWrapper<? extends DomainObject> wrapper = map.get(type);
			ProtocolHandler<?> handler = generateDomainProtocolHandler(wrapper, type);
			register(handler);
		}
		searched = true;
	}

	@SuppressWarnings({ "rawtypes" })
	private ProtocolHandler<?> generateDomainProtocolHandler(final DomainCRUDWrapper<? extends DomainObject> wrapper, final Class<? extends DomainObject> type) {

		return new ProtocolHandler() {
			String protocol = null;

			public String getProtocol() {
				if (protocol == null) {
					protocol = DomainObjectUtils.getEntityName(type);
				}
				return protocol;
			}

			private Object getEntityById(String id) {
				return wrapper.get(id);
			}

			public Object getObject(String uri) {
				String id = uri.substring(getProtocol().length() + 1);
				return getEntityById(id);
			}

			public String getURI(Object object) {
				return getProtocol() + ":" + DomainObjectUtils.getPrimaryKeyValue(object);
			}

			public boolean supports(Class clazz) {
				return type == clazz;
			}

		};
	}

}
