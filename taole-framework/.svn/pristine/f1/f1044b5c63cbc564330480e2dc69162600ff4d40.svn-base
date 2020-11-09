/**
 * Project:HEAFramework 2.0
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.web;

import java.io.FileNotFoundException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import org.springframework.util.Assert;
import org.springframework.util.Log4jConfigurer;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.WebUtils;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: Log4jWebConfigurer.java 9640 2017-05-07 11:49:36Z chengjun $
 * @since Jul 27, 2010 3:03:16 PM
 */
public class Log4jWebConfigurer {

    private Log4jWebConfigurer() {
    }

    /**
     * Parameter specifying the location of the log4j config file
     */
    public static final String CONFIG_LOCATION_PARAM = "log4jConfigLocation";

    /**
     * Parameter specifying the refresh interval for checking the log4j config file
     */
    public static final String REFRESH_INTERVAL_PARAM = "log4jRefreshInterval";

    /**
     * Parameter specifying the jndi name for logfile path
     */
    public static final String LOG4J_LOGFILE_PATH_JNDI_PARAM = "log4j.logfile.path.jndi";

    /**
     * Web app log key parameter at the setup context level
     * (i.e. a context-param in <code>web.xml</code>): "webAppLogKey".
     */
    public static final String WEB_APP_LOG_KEY_PARAM = "webAppLogKey";
    /**
     * Web app  key parameter at the setup context level
     * (i.e. a context-param in <code>web.xml</code>): "webAppRootKey".
     */
    public static final String WEB_APP_ROOT_KEY_PARAM = "webAppRootKey";

    /**
     * Default web app log key: "webapp.log"
     */
    public static final String DEFAULT_WEB_LOG_ROOT_KEY = "webapp.log";

    public static final String DEFAULT_LOG4J_LOGFILE_PATH_JNDI = "java:comp/env/logfile/path";

    public static final String LOGGER_LEVEL_JNDI = "java:comp/env/logger/level";

    /**
     * Default logger level is :"info"
     */
    public static final String DEFAULT_LOGGER_LEVEL = "info";

    /**
     * The logger system key: "logger.level"
     */
    public static final String LOGGER_LEVEL_KEY = "logger.level";

    public static final String DEFAULT_CONFIG_LOCATION = "classpath:log4jconf.properties";

    /**
     * Initialize log4j, including setting the web app root system property.
     *
     * @param servletContext the current ServletContext
     */
    public static void initLogging(ServletContext servletContext) {

        String jndiName = obtainJndiName(servletContext);
        if (jndiName != null) {
            // Expose the web app root system property.
            setWebAppLogSystemProperty(servletContext);

            // Only perform custom log4j initialization in case of a config file.
            String location = obtainConfigLocation(servletContext);
            if (location != null) {
                // Perform actual log4j initialization; else rely on log4j's default initialization.
                try {
                    // Return a URL (e.g. "classpath:" or "file:") as-is;
                    // consider a plain file path as relative to the web application root directory.
                    if (!ResourceUtils.isUrl(location)) {
                        // Resolve system property placeholders before resolving real path.
                        location = SystemPropertyUtils.resolvePlaceholders(location);
                        location = WebUtils.getRealPath(servletContext, location);
                    }

                    // Write log message to server log.
                    servletContext.log("Initializing log4j from [" + location + "]");

                    // Check whether refresh interval was specified.
                    long refreshInterval = obtainRefreshInterval(servletContext);
                    if (refreshInterval > 0) {
                        Log4jConfigurer.initLogging(location, refreshInterval);
                    } else {
                        // Initialize without refresh check, i.e. without log4j's watchdog thread.
                        Log4jConfigurer.initLogging(location);
                    }
                } catch (FileNotFoundException ex) {
                    throw new IllegalArgumentException("Invalid 'log4jConfigLocation' parameter: " + ex.getMessage());
                }
            }
        } else {
            servletContext.log("please config " + LOG4J_LOGFILE_PATH_JNDI_PARAM + " in contextParam");
        }
    }

    /**
     * Shut down log4j, properly releasing all file locks and resetting the web app root system property.
     *
     * @param servletContext the current ServletContext
     * @see WebUtils#removeWebAppRootSystemProperty
     */
    public static void shutdownLogging(ServletContext servletContext) {
        servletContext.log("Shutting down log4j");
        try {
            Log4jConfigurer.shutdownLogging();
        } finally {
            // Remove the web app log system property.
            removeWebAppLogSystemProperty(servletContext);
        }
    }


    public static void setWebAppLogSystemProperty(ServletContext servletContext) throws IllegalStateException {
        Assert.notNull(servletContext, "ServletContext must not be null");
        String key = obtainWebAppLogKey(servletContext);
        String rootKey = obtainWebAppRootKey(servletContext);
        String oldValue = System.getProperty(key);
        String logfilePath = obtainLogfilePath(servletContext);
        String webRootPath = obtainWebRootPath(servletContext);
        if (oldValue != null && !StringUtils.pathEquals(oldValue, logfilePath)) {
            throw new IllegalStateException(
                    "Web app root system property already set to different value: '" +
                            key + "' = [" + oldValue + "] instead of [" + logfilePath + "] - " +
                            "Choose unique values for the 'webAppLogKey' context-param in your web.xml files!");
        }
        System.setProperty(key, logfilePath);
        System.setProperty(rootKey, webRootPath);
        System.setProperty(LOGGER_LEVEL_KEY, obtainLoggerLevel(servletContext));
        servletContext.log("Set web app log system property: '" + key + "' = [" + logfilePath + "]");

    }

    protected static String obtainLoggerLevel(ServletContext servletContext) {
        try {
            InitialContext ctx = new InitialContext();
            String level = (String) ctx.lookup(LOGGER_LEVEL_JNDI);
            if (!StringUtils.hasText(level)) {
                level = DEFAULT_LOGGER_LEVEL;
            }
            return level;
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static String obtainLogfilePath(ServletContext servletContext) {
        try {
            InitialContext ctx = new InitialContext();
            String jndiName = obtainJndiName(servletContext);
            return (String) ctx.lookup(jndiName);
        } catch (NamingException e) {
            e.printStackTrace();
            servletContext.log("not found jndi:" + LOG4J_LOGFILE_PATH_JNDI_PARAM, e);
            return null;
        }

    }

    protected static String obtainWebRootPath(ServletContext servletContext) {
        return servletContext.getRealPath("/");
    }

    protected static String obtainJndiName(ServletContext servletContext) {
        String jndiName = servletContext.getInitParameter(LOG4J_LOGFILE_PATH_JNDI_PARAM);
        return jndiName != null ? jndiName : DEFAULT_LOG4J_LOGFILE_PATH_JNDI;
    }

    protected static String obtainConfigLocation(ServletContext servletContext) {
        String location = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        return location != null ? location : DEFAULT_CONFIG_LOCATION;
    }

    protected static long obtainRefreshInterval(ServletContext servletContext) {
        String intervalString = servletContext.getInitParameter(REFRESH_INTERVAL_PARAM);
        if (intervalString != null) {
            try {
                return Long.parseLong(intervalString);
            } catch (NumberFormatException ex) {
            }
        }
        return 0l;
    }

    protected static String obtainWebAppLogKey(ServletContext servletContext) {
        String webAppLogKey = servletContext.getInitParameter(WEB_APP_LOG_KEY_PARAM);
        return webAppLogKey != null ? webAppLogKey : DEFAULT_WEB_LOG_ROOT_KEY;
    }

    protected static String obtainWebAppRootKey(ServletContext obtainWebAppLogKey) {
        return WEB_APP_ROOT_KEY_PARAM;
    }

    public static void removeWebAppLogSystemProperty(ServletContext servletContext) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        String key = obtainWebAppLogKey(servletContext);
        System.getProperties().remove(key);
        System.getProperties().remove(obtainWebAppRootKey(servletContext));
    }
}
