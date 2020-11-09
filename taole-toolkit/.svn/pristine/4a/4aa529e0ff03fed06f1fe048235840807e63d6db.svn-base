package com.taole.toolkit.dict;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.taole.framework.bean.ProjectBeanNameGenerator;
import com.taole.framework.dao.NodeDao;
import com.taole.framework.dao.hibernate.HibernateNodeDao;
import com.taole.framework.events.EventTarget;
import com.taole.toolkit.ProjectConfig;
import com.taole.toolkit.dict.domain.Dictionary;

@Configurable(value = "tk.dict.BeanConfig")
@ComponentScan(value = { "com.taole.toolkit.dict" }, nameGenerator = ProjectBeanNameGenerator.class)
@PropertySource(name="tk.dict.env",value="classpath:com/taole/toolkit/dict/config.properties")
public class BeanConfig {

	final static String PREFIX = ProjectConfig.NAME;
	
	public final static String EVENT_TARGET_NAME = "tk.dict.eventTarget";

	@Bean(name = EVENT_TARGET_NAME)
	EventTarget generateEventTarget() {
		return new EventTarget();
	}

	@Bean(name = PREFIX + ".dictionaryDao")
	NodeDao<Dictionary> generateDictionaryDao() {
		return new HibernateNodeDao<Dictionary>(Dictionary.class);
	}

}
