package com.taole.framework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.taole.framework.util.EnvironmentHelper;

public class Configurator {

	@Autowired
	Environment environment;

	private EnvironmentHelper getEnvironmentHelper() {
		return new EnvironmentHelper(environment);
	}

	public String getViewName(String module, String action) {
		return getEnvironmentHelper().getProperty("views." + module + "." + action);
	}

	public String getRequestName() {
		RestContext ctx = RestSessionFactory.getCurrentContext();
		String envKey = "requests." + ctx.getModule() + "." + ctx.getAction();
		return getEnvironmentHelper().getProperty(envKey);
	}

	public String getResponseName() {
		RestContext ctx = RestSessionFactory.getCurrentContext();
		String envKey = "responses." + ctx.getModule() + "." + ctx.getAction();
		return getEnvironmentHelper().getProperty(envKey);
	}

}
