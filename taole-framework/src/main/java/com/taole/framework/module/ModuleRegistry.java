package com.taole.framework.module;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ModuleRegistry {
	
	@Autowired
	ApplicationContext applicationContext;
	
	private Map<ModuleConfig, Object> table = new HashMap<ModuleConfig, Object>();
	
	@PostConstruct
	void autoFindAndRegister() {
		Map<String, Object> beanMap = searchModuleConfig(applicationContext);
		for (Object bean: beanMap.values()) {
			ModuleConfig mc = bean.getClass().getAnnotation(ModuleConfig.class);
			table.put(mc, bean);
		}
	}
	
	Map<String, Object> searchModuleConfig(ApplicationContext appCtx) {
		Map<String, Object> beanMap = appCtx.getBeansWithAnnotation(ModuleConfig.class);
		if (appCtx.getParent() != null) {
			beanMap.putAll(searchModuleConfig(appCtx.getParent()));
		}
		return beanMap;
	}
	
}
