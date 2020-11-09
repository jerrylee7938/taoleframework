/**
 * Project:HEAFramework 2.0
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.taole.framework.web.Log4jWebConfigurer;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 27, 2010 5:24:07 PM
 * @version $Id: Log4jConfigListener.java 5 2014-01-15 13:55:28Z yedf $
 */
public class Log4jConfigListener implements ServletContextListener {

	/** {@inheritDoc}
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Log4jWebConfigurer.shutdownLogging(sce.getServletContext());
	}

	/** {@inheritDoc}
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Log4jWebConfigurer.initLogging(sce.getServletContext());
	}

}
