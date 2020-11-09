/**
 * Project:hello-hibernate
 * File:WebApplication.java 
 * Copyright 2004-2011 Homolo Co., Ltd. All rights reserved.
 */
package spring;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Homolo Generator
 * @since Mar 14, 2011 10:41:16 AM
 * @version $Id: WebApplication.java 21 2014-01-17 18:56:49Z yedf $
 */
public class WebApplication {

	private final Logger logger = LoggerFactory.getLogger(WebApplication.class);
	
	@PostConstruct
	public void init() {
		logger.info("Set web application default locale: zh_CN.");
		Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
	}
	
	
}
