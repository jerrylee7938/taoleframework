package com.taole.portal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.taole.framework.events.EventTarget;
import com.taole.framework.bean.ProjectBeanNameGenerator;
import com.taole.framework.module.ModuleConfig;

@ModuleConfig(name = ProjectConfig.NAME, domainPackages = { "com.taole.portal.domain"})
@ComponentScan(basePackages = { "com.taole.portal" }, nameGenerator = ProjectBeanNameGenerator.class)
@PropertySource(name = "portal.env", value = { "classpath:com/taole/portal/project.properties" })
public class ProjectConfig {

	public static final String NAME = "taole-portal-service";
	
	public static final String PREFIX = NAME + ".";
	
	@Bean(name = PREFIX + "eventTarget")
	public EventTarget generateEventTarget() {
		return new EventTarget();
	}
}
