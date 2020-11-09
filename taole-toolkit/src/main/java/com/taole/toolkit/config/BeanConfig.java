package com.taole.toolkit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taole.toolkit.config.dao.ConfigDao;
import com.taole.toolkit.config.dao.impl.ConfigDaoImpl;
import com.taole.toolkit.config.manager.ConfigManager;
import com.taole.toolkit.config.service.rest.ConfigService;

/**
 * 
 * @author Administrator
 * $Id: BeanConfig.java 16 2014-01-17 17:58:24Z yedf $
 */
@Configuration("tk.config.BeanConfig")
public class BeanConfig {
	
	@Bean(name="tk.configService")
	public ConfigService generateConfigService(){
		return new ConfigService();
	}
	@Bean(name="tk.configDao") 
	public ConfigDao generateConfigDao () {
		return new ConfigDaoImpl();
	}

	@Bean(name="tk.configManager") 
	public ConfigManager generateConfigManager() {
		return new ConfigManager();
	}

}
