package com.taole.framework.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class DynamicBeanFactory {

	@Autowired
	private ApplicationContext applicationContext;

	public synchronized Object registerBean(String className) {
		try {
			return registerBean(Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public synchronized <T> T registerBean(String className, Class<T> clazz) {
		return clazz.cast(registerBean(className));
	}

	public synchronized <T> T getBean(String beanId, Class<T> clazz) {
		if (!applicationContext.containsLocalBean(beanId)) {
			registerDynamicBean(beanId, clazz);
		}
		return applicationContext.getBean(beanId, clazz);
	}

	public synchronized <T> T registerBean(Class<T> clazz) {
		String beanName = parseBeanName(clazz.getName());
		if (!applicationContext.containsLocalBean(beanName)) {
			registerDynamicBean(beanName, clazz);
		}
		// if (!applicationContext.getBeansOfType(clazz).isEmpty()) {
		// return applicationContext.getBeansOfType(clazz).entrySet().iterator().next().getValue();
		// }
		return applicationContext.getBean(beanName, clazz);
	}

	private synchronized void registerDynamicBean(String beanName, Class<?> clazz) {
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
	}

	private static String parseBeanName(String className) {
		return "dynamic." + className;
	}

}
