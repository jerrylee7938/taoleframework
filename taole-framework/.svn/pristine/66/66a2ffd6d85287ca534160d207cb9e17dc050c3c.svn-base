package com.taole.framework.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import com.taole.framework.module.ModuleConfig;

public class ProjectBeanNameGenerator extends AnnotationBeanNameGenerator {

	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanName = super.generateBeanName(definition, registry);
		String projName = getClassProjectName(definition.getBeanClassName());
		if (projName != null && !beanName.startsWith(projName + ".")) {
			return projName + "." + beanName;
		} else {
			return beanName;
		}
	}

	public static Map<String, String> projectNameMap = new HashMap<String, String>();

	public static String getClassProjectName(String className) {
		if (className == null || !className.contains(".")) {
			return null;
		}
		String packageName = className.replaceAll("\\.[^\\.]+$", "");
		String configName = packageName + "." + "ProjectConfig";
		if (projectNameMap.containsKey(configName)) {
			return projectNameMap.get(configName);
		}
		try {
			Class<?> configClz = Class.forName(configName);
			ModuleConfig mc = configClz.getAnnotation(ModuleConfig.class);
			String projName = null;
			if (mc != null) {
				projName = mc.name();
			} else {
				projName = (String) configClz.getField("NAME").get(null);
			}
			projectNameMap.put(configName, projName);
			return projName;
		} catch (Exception e) {
			return getClassProjectName(packageName);
		}
	}

}
