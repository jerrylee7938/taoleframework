package com.taole.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "userAttr")
@Table(name = "USER_ATTR")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class UserAttr implements DomainObject, Cloneable {

	private static final long serialVersionUID = -6231930216848596724L;

	@Id
	@PrimaryKey
	@Column(name = "ATTR_ID")
	private String id;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "ATTR_NAME")
	private String attrName;
	
	@Column(name = "ATTR_VALUE")
	private String attrValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
}
