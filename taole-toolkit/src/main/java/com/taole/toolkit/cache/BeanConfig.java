package com.taole.toolkit.cache;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.taole.framework.bean.ProjectBeanNameGenerator;
import com.taole.framework.events.EventTarget;
import com.taole.toolkit.ProjectConfig;

@Configurable(value = "tk.cache.BeanConfig")
@ComponentScan(value = { "com.taole.toolkit.cache" }, nameGenerator = ProjectBeanNameGenerator.class)
public class BeanConfig {

	final static String PREFIX = ProjectConfig.NAME;
	
	public final static String EVENT_TARGET_NAME = "tk.cache.eventTarget";

	@Bean(name = EVENT_TARGET_NAME)
	EventTarget generateEventTarget() {
		return new EventTarget();
	}
}
