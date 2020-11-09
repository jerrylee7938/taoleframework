package com.taole.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "cardNo")
@Table(name = "CARD_NO")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class CardNo implements DomainObject {
	private static final long serialVersionUID = -7528903579376713086L;
	
	@Id
	@PrimaryKey
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
