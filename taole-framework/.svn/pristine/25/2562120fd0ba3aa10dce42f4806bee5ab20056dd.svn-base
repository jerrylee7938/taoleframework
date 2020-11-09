package com.taole.framework.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import com.taole.framework.util.DomainObjectUtils;
import com.taole.framework.util.StringUtils;

public class RestServiceRegistry {

	@Autowired
	protected ApplicationContext applicationContext;

	private Map<String, Object> map = null;

	private Map<String, Object> initMap() {
		map = new HashMap<String, Object>();
		Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RestService.class);
		if (applicationContext.getParent() != null) {
			beanMap.putAll(applicationContext.getParent().getBeansWithAnnotation(RestService.class));
		}
		for (Object obj : beanMap.values()) {
			map.put(getServiceName(obj), obj);
		}
		return map;
	}

	private String getServiceName(Object obj) {
		RestService annotation = AnnotationUtils.findAnnotation(obj.getClass(), RestService.class);
		if (StringUtils.isEmpty(annotation.name()) && BaseDomainObjectServiceSupport.class.isInstance(obj)) {
			BaseDomainObjectServiceSupport<?> oss = (BaseDomainObjectServiceSupport<?>) obj;
			return DomainObjectUtils.getEntityName(oss.getEntityType());
		}
		return annotation.name();
	}

	public Object getRestService(String name) {
		if (map == null) {
			return initMap().get(name);
		}
		Object service = map.get(name);
		if (service == null) {
			Map<String, Object> map = applicationContext.getBeansWithAnnotation(RestService.class);
			if (applicationContext.getParent() != null) {
				map.putAll(applicationContext.getParent().getBeansWithAnnotation(RestService.class));
			}
			for (Object obj : map.values()) {
				String svcName = getServiceName(obj);
				if (StringUtils.compare(svcName, name)) {
					map.put(svcName, service = obj);
					break;
				}
			}
		}
		return service;
	}

}
