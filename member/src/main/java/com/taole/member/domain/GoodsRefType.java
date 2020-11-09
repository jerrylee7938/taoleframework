package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "goodsRefType")
@Table(name = "GOODS_REF_TYPE")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class GoodsRefType implements DomainObject,Cloneable{

	private static final long serialVersionUID = -3209624489471549567L;

	/**
	 * ID
	 */
	@Id
	@PrimaryKey
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	/**
	 * 商品ID
	 */
	@Column(name = "GOODS_ID")
	private String goodsId;
	
	/**
	 * 商品类型ID
	 */
	@Column(name = "TYPE_ID")
	private String typeId;
	
	/**
	 *创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	/**
	 *创建时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
