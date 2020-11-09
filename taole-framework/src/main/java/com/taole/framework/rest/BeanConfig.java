package com.taole.framework.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.taole.framework.bean.ProjectBeanNameGenerator;
import com.taole.framework.rest.parser.ParameterParserRegistry;
import com.taole.framework.rest.processor.ResponseProcessorRegistry;
import com.taole.framework.rest.support.DefaultFetcherFactory;

//@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
@Configuration("fw.restBeanConfig")
@ComponentScan(value = { "com.taole.framework.rest.sysimpl", "com.homolo.framework.rest.parser", "com.homolo.framework.rest.processor" }, nameGenerator = ProjectBeanNameGenerator.class)
public class BeanConfig {

	/**
	 * WebData Handler Configuration
	 * 
	 * @return
	 */
	@Bean
	public RestServiceRegistry restfulServiceRegistry() {
		return new RestServiceRegistry();
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public RestUrlRuler restfulUrlRuler() {
		return new RestUrlRuler();
	}

	@Bean
	public DefaultFetcherFactory defaultFetcherFactory() {
		return new DefaultFetcherFactory();
	}

	@Bean
	public Configurator configurator() {
		return new Configurator();
	}

	@Bean
	public ParameterParserRegistry parameterParserRegistry() {
		return new ParameterParserRegistry();
	}

	@Bean
	public ResponseProcessorRegistry responseProcessorRegistry() {
		return new ResponseProcessorRegistry();
	}

}
