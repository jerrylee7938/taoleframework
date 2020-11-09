/*
 * @(#)File.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.toolkit.filesystem.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.taole.framework.annotation.NamePosition;
import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.bean.ExtendableObject;
import com.taole.toolkit.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "file")
@Table(name = "SYS_FILE")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class File extends ExtendableObject implements DomainObject {

	private static final long serialVersionUID = -386868583036905049L;

	// 文件编号
	@PrimaryKey
	private String id;
	
	// 文件地址
	private String path;

	// 文件名
	@NamePosition
	private String name;

	// 文件类型
	private String mimeType;

	// 文件大小
	private long size;

	// 创建日期
	private Date createDate;

	// 更新日期
	private Date updateDate;

	private String owner;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAT_CREATEDATE", updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Id
	@Column(name = "VC2_FILECODE", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "VC2_NAME", length = 200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NUM_SIZE")
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Column(name = "VC2_MIMETYPE", length = 50)
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAT_UPDATEDATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "VC2_PATH", length = 220)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "VC2_OWNER", length = 150)
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
