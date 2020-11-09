/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		Category.java
 * Module:			TODO
 * Class:			Category
 * Date:			2006-12-13
 * Author:			
 * Description:		TODO
 *
 *
 * ======================================================================
 * Change History Log
 * ----------------------------------------------------------------------
 * Mod. No.	| Date		| Name			| Reason			| Change Req.
 * ----------------------------------------------------------------------
 * 			| 2006-12-13	| 	| Created			|
 *
 * </pre>
 **/

package com.taole.toolkit.dict.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.taole.framework.bean.Node;
import com.taole.toolkit.ProjectConfig;

/**
 * Class: Category Remark: 分类
 * 
 * @author: 
 */
@Entity(name = ProjectConfig.PREFIX + "dictionary")
@Table(name = "SYS_DICT")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Dictionary extends Node<Dictionary> {

	private static final long serialVersionUID = 8978305707895153345L;

	private Boolean isDefault = false; // 是否默认
	private boolean system = false; //是否系统
	private String value;
	private String type;
	private String description;
	
	@Column(name = "NUM_DEFAULT")
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "VC2_VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	@Column(name = "VC2_TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "NUM_SYSTEM")
	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}
	
	@Column(name = "VC2_DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
