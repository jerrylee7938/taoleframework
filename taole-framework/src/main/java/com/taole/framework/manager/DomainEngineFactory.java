package com.taole.framework.manager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.taole.framework.annotation.AnnotationUtils;
import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.util.BeanUtils;

public class DomainEngineFactory {

	@Autowired
	private ApplicationContext applicationContext;

	Map<Class<? extends DomainObject>, DomainCRUDWrapper<? extends DomainObject>> crudWarpperMap = new HashMap<Class<? extends DomainObject>, DomainCRUDWrapper<? extends DomainObject>>();
	Map<Class<? extends DomainObject>, Object> domainEngineMap = new HashMap<Class<? extends DomainObject>, Object>();
	
	@SuppressWarnings("unchecked")
	public <T extends DomainObject> DomainCRUDWrapper<T> getDomainCRUDWrapper(Class<T> type) {
		searchAndRegister();
		return (DomainCRUDWrapper<T>) crudWarpperMap.get(type);
	}

	public Map<Class<? extends DomainObject>, DomainCRUDWrapper<? extends DomainObject>> getCRUDcrudWarpperMap() {
		searchAndRegister();
		return new HashMap<Class<? extends DomainObject>, DomainCRUDWrapper<? extends DomainObject>>(crudWarpperMap);
	}

	public boolean contains(Class<?> type) {
		searchAndRegister();
		return domainEngineMap.containsKey(type);	
	}
	
	public Object getDomainEngine(Class<?> type) {
		searchAndRegister();
		if (!domainEngineMap.containsKey(type)) {
			searchAndRegister();
		}
		return domainEngineMap.get(type);
	}
	
	boolean searched = false;
	
	private void searchAndRegister() {
		if (searched) {
			return;
		}
		Map<String, Object> map = applicationContext.getBeansWithAnnotation(DomainEngine.class);
		for (Object engine : map.values()) {
			DomainEngine de = AnnotationUtils.getClassAnnotation(BeanUtils.getClass(engine), DomainEngine.class);
			if (de == null) {
				continue;
			}
			Class<? extends DomainObject>[] types = de.types();
			for (Class<? extends DomainObject> type : types) {
				if (domainEngineMap.containsKey(type)) {
					continue;
				}
				domainEngineMap.put(type, engine);
				crudWarpperMap.put(type,buildeDomainCRUDWrapper(type, engine));
			}
		}
		searched = true;
	}

	public static <T extends DomainObject> DomainCRUDWrapper<T> buildeDomainCRUDWrapper(Class<T> type, Object bean) {
		return new BeanProxyDomainCRUDWrapper<T>(type, bean);
	}

	public static class BeanProxyDomainCRUDWrapper<T extends DomainObject> implements DomainCRUDWrapper<T> {

		private Class<T> type;
		private Object engine = null;
		private Method c = null;
		private Method r = null;
		private Method u = null;
		private Method d = null;

		protected BeanProxyDomainCRUDWrapper(Class<T> type, Object engine) {
			this.type = type;
			this.engine = engine;
		}

		private Method getC() {
			if (c == null) {
				Method[] methods = AnnotationUtils.getAnnotationMethodsWithSupers(engine.getClass(), DomainEngine.C.class);
				for (Method m : methods) {
					DomainEngine.C a = m.getAnnotation(DomainEngine.C.class);
					if (a.type() == type || a.type() == DomainObject.class) {
						c = m;
						break;
					}
				}
			}
			return c;
		}
		
		private Method getR() {
			if (r == null) {
				Method[] methods = AnnotationUtils.getAnnotationMethodsWithSupers(engine.getClass(), DomainEngine.R.class);
				for (Method m : methods) {
					DomainEngine.R a = m.getAnnotation(DomainEngine.R.class);
					if (a.type() == type || a.type() == DomainObject.class) {
						r = m;
						break;
					}
				}
			}
			return r;
		}
		
		private Method getU() {
			if (u == null) {
				Method[] methods = AnnotationUtils.getAnnotationMethodsWithSupers(engine.getClass(), DomainEngine.U.class);
				for (Method m : methods) {
					DomainEngine.U a = m.getAnnotation(DomainEngine.U.class);
					if (a.type() == type || a.type() == DomainObject.class) {
						u = m;
						break;
					}
				}
			}
			return u;
		}
		
		private Method getD() {
			if (d == null) {
				Method[] methods = AnnotationUtils.getAnnotationMethodsWithSupers(engine.getClass(), DomainEngine.D.class);
				for (Method m : methods) {
					DomainEngine.D a = m.getAnnotation(DomainEngine.D.class);
					if (a.type() == type || a.type() == DomainObject.class) {
						d = m;
						break;
					}
				}
			}
			return d;
		}

		@SuppressWarnings("unchecked")
		public T get(String id) {
			try {
				return (T) getR().invoke(engine, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public String create(T entity) {
			try {
				return (String) getC().invoke(engine, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public void update(T entity) {
			try {
				getU().invoke(engine, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void delete(T entity) {
			try {
				getD().invoke(engine, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public boolean supports(Class<?> type) {
			return this.type == type;
		}

	}

}
