package com.taole.toolkit.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.taole.framework.bean.ProjectBeanNameGenerator;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.hibernate.BaseHibernateDaoSupport;
import com.taole.framework.util.EnvironmentHelper;
import com.taole.toolkit.filesystem.domain.File;
import com.taole.toolkit.filesystem.manager.FileManager;
import com.taole.toolkit.filesystem.manager.FileSystemManager;

@Configuration("tk.filesystem.BeanConfig")
@ComponentScan(basePackages = { "com.taole.toolkit.filesystem.dao", "com.homolo.toolkit.filesystem.manager"
		,"com.taole.toolkit.filesystem.service.rest","com.taole.toolkit.manager"},nameGenerator=ProjectBeanNameGenerator.class)
@PropertySource(name="tk.filesystem.env",value="classpath:com/taole/toolkit/filesystem/config.properties")
public class BeanConfig {

	@Autowired
	Environment environment;

	@Bean(name = "filesystem.maxFileUploadSize")
	Integer maxFileUploadSize() {
		return new EnvironmentHelper(environment).getProperty("filesystem.maxSize", Integer.class);
	}

	@Bean(name = "filesystem.root")
	Object filesystemRoot() {
		JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
		factory.setJndiName(new EnvironmentHelper(environment).getProperty("filesystem.rootJndi"));
		factory.setResourceRef(true);
		return factory;
	}
	
	@Bean(name = "filesystem.storage.dir")
	String storageDirectory() {
		return new EnvironmentHelper(environment).getProperty("filesystem.storeDir");
	}

	@Bean(name = "filesystem.temp.dir")
	String tempDirectory() {
		return new EnvironmentHelper(environment).getProperty("filesystem.tempDir");
	}
	
	@Bean(name = "tk.fileDao")
	DomainObjectDao<File> generateFileDao() {
		return new BaseHibernateDaoSupport<File>(File.class);
	}

	@Bean(name = "tk.fileSystemManager")
	public FileSystemManager generateFileSystemManager() {
		return new FileSystemManager();
	}
	
	@Bean(name = "tk.fileManager")
	public FileManager generateFileManager() {
		return new FileManager();
	}

	@Bean
	public FileSystemPath generateFileSystemPath() {
		return new FileSystemPath();
	}
	
	
}
