/*
 * @(#)FileSystemManager.java 0.1 Dec 14, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.filesystem.manager;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.util.MimeUtils;
import com.taole.framework.util.UUID;
import com.taole.toolkit.ProjectConfig;
import com.taole.toolkit.filesystem.FileSystemPath;
import com.taole.toolkit.filesystem.condition.FileCondition;
import com.taole.toolkit.filesystem.domain.File;

@DomainEngine(types = File.class)
@Transactional(readOnly = true)
public class FileSystemManager {

	private final Logger logger = LoggerFactory.getLogger(FileSystemManager.class);

	@Autowired
	private FileSystemPath fileSystemPath;

	@Resource(name = ProjectConfig.NAME + ".fileDao")
	private DomainObjectDao<File> fileDao;

	@DomainEngine.C
	@Transactional
	public void createFile(File file) {
		if (file.getCreateDate() == null) {
			file.setCreateDate(new Date());
		}
		if (file.getUpdateDate() == null) {
			file.setUpdateDate(new Date());
		}
		fileDao.createObject(file);
	}

	@DomainEngine.R
	public File getFile(String fileId) {
		return fileDao.loadObject(fileId);
	}

	public File getFile(String path, String name) {
		FileCondition condition = new FileCondition();
		condition.setPath(path);
		condition.setName(name);
		List<File> files = fileDao.listByCondition(condition);
		if (files == null || files.isEmpty()) {
			return null;
		} else if (files.size() > 1) {
			logger.error(String.format("there are[%s] found with path:%s and name:%s", files.size(), path, name));
		}
		return files.get(0);
	}

	public List<File> list(String path) {
		FileCondition condition = new FileCondition();
		condition.setPath(path);
		return fileDao.listByCondition(condition);
	}

	public List<File> list(FileCondition condition) {
		return fileDao.listByCondition(condition);
	}

	public List<File> list(FileCondition condition, Sorter sorter) {
		return fileDao.listByCondition(condition, sorter);
	}

	public List<File> listByOwner(String owner) {
		FileCondition fileCondition = new FileCondition();
		fileCondition.setOwner(owner);
		return list(fileCondition);
	}

	@Transactional
	public void deleteFile(String fileId) {
		deleteFile(getFile(fileId));
	}

	@DomainEngine.D
	@Transactional
	public void deleteFile(File file) {
		fileDao.deleteObject(file);
		String realFilePath = fileSystemPath.getRealPath(file.getPath());
		java.io.File f = new java.io.File(realFilePath);
		if (f.exists()) {
			f.delete();
		}
	}

	@DomainEngine.U
	@Transactional
	public void updateFile(File file) {
		file.setUpdateDate(new Date());
		fileDao.updateObject(file);
	}

	@Transactional
	public void updateFile(File file, Date modifyDate) {
		if (modifyDate == null) {
			modifyDate = new Date();
		}
		file.setUpdateDate(modifyDate);
		fileDao.updateObject(file);
	}

	public File importFile(String source, String owner) {
		return importFile(new java.io.File(source), owner);
	}

	public File importFile(java.io.File source, String owner) {
		File file = new File();
		file.setId(UUID.generateUUID());
		String fileName = FilenameUtils.getName(source.getName());
		String ext = FilenameUtils.getExtension(fileName);
		if (StringUtils.isNotBlank(ext)) {
			ext = "." + ext;
		}
		file.setName(fileName);
		file.setSize(source.length());
		file.setOwner(owner);
		file.setMimeType(MimeUtils.getMimeType(fileName));
		String dateString = DateFormatUtils.format(new Date(), "yyyy-MM");
		String dirPath = fileSystemPath.getStorageDir() + "/" + dateString + "/";
		file.setPath(dirPath + file.getId() + ext);
		java.io.File dir = new java.io.File(fileSystemPath.getRealPath(dirPath));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		java.io.File destinationFile = new java.io.File(fileSystemPath.getRealPath(file.getPath()));
		try {
			FileUtils.copyFile(source, destinationFile);
			createFile(file);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
