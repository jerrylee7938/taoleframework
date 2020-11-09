package com.taole.toolkit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.taole.framework.events.EventTarget;
import com.taole.framework.module.ModuleConfig;

@ModuleConfig(name = ProjectConfig.NAME, domainPackages = {
		"com.taole.toolkit.config.domain",
		"com.taole.toolkit.dict.domain",
		"com.taole.toolkit.filesystem.domain"})
@Import({
	com.taole.toolkit.dict.BeanConfig.class, 
	com.taole.toolkit.config.BeanConfig.class,
	com.taole.toolkit.cache.BeanConfig.class,
	com.taole.toolkit.filesystem.BeanConfig.class
})
@Configuration("tk.projectConfig")
public class ProjectConfig {

	public static final String NAME = "tk";
	
	public static final String PREFIX = NAME + ".";

	@Bean(name = "tk.eventTarget")
	public EventTarget generateEventTarget() {
		return new EventTarget();
	}

}
