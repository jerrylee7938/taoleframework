/**
 * Project:HEAFramework 3.0
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.template;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 这里读文件系统配置，如果指定的filesystemRoot 和 templateDirs就添加一个FileTemplateLoader.
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since May 22, 2011 12:04:38 PM
 * @version $Id: FreeMarkerConfigurer.java 9640 2017-05-07 11:49:36Z chengjun $
 */
public class FreeMarkerConfigurer extends org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer {

	private final Log log = LogFactory.getLog(FreeMarkerConfigurer.class);

	private String filesystemRoot;

	private List<String> templateDirs;

	/**
	 * @param templateDirs the templateDirs to set
	 */
	public void setTemplateDirs(List<String> templateDirs) {
		this.templateDirs = templateDirs;
	}

	public void setFilesystemRoot(String filesystemRoot) {
		this.filesystemRoot = filesystemRoot;
	}


	/** {@inheritDoc}
	 * @see org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		List<TemplateLoader> tpls = new ArrayList<TemplateLoader>();
		if (templateDirs != null) {
			for (String str: templateDirs) {
				TemplateLoader loader = parseTemplateDir(str);
				if (loader != null) {
					tpls.add(loader);
				}
			}
		}
		setPostTemplateLoaders((TemplateLoader[]) tpls.toArray(new TemplateLoader[0]));
		super.afterPropertiesSet();
		this.getConfiguration().setLocalizedLookup(false);
		this.getConfiguration().setClassicCompatible(true);
	}

	private TemplateLoader parseTemplateDir(String dirPath) throws IOException {
		if (!StringUtils.isNotBlank(dirPath)) {
			return null;
		} else if (dirPath.startsWith("classpath:")) {
			return new ClassTemplateLoader(this.getClass(), dirPath.substring(9));
		} else if (StringUtils.isNotBlank(dirPath)) {
			if (StringUtils.isNotBlank(filesystemRoot)) {
				dirPath = StringUtils.replaceOnce(dirPath, "%{filesystem-root}", filesystemRoot);
			}
			File dirFile = new File(dirPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			log.info("add a new FileTemplateLoader from directory:" + dirPath);
			return new FileTemplateLoader(dirFile);
		} else  {
			return null;
		}
	}

}
