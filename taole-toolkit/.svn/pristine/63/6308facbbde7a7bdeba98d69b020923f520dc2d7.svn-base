package com.taole.toolkit.config.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.toolkit.ProjectConfig;


@Entity(name = ProjectConfig.PREFIX + "config")
@Table(name = "SYS_CONFIG")
public class Config implements DomainObject {

	private static final long serialVersionUID = -874445456847181364L;

	@PrimaryKey
	private String id;
	
	private String key;
	
	private String value;

	@Id
	@Column(name = "VC2_CONFIGID", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "VC2_KEY", length = 150)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VC2_VALUE", length = 2048)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
