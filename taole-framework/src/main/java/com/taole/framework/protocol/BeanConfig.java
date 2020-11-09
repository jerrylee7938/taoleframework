package com.taole.framework.protocol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("fw.protocolBeanConfig")
public class BeanConfig {

	@Bean
	public ProtocolRegistry protocolRegistry() {
		return new ProtocolRegistry();
	}

	@Bean
	public BeanProtocolHandler beanProtocolHandler() {
		return new BeanProtocolHandler();
	}

	@Bean
	public EntityProtocolHandler entityProtocolHandler() {
		return new EntityProtocolHandler();
	}

}
