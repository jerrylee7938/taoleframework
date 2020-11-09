package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "goodsBill")
@Table(name = "GOODS_BILL")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class GoodsBill implements DomainObject, Cloneable {

	private static final long serialVersionUID = 1293879779444040756L;
	/**
	 * 单据id
	 */
	@Id
	@PrimaryKey
	@Column(name = "SHOP_BILL_ID", unique = true, nullable = false)
	private String shopBillId;
	
	/**
	 * 单据编号（若是销售单，与流水单号一致）
	 */
	@Column(name = "SHOP_BILL_NO")
	private String shopBillNo;
	/**
	 * 单据类型(进货单/销售单/调拨入库/调拨出库)
	 */
	@Column(name = "SHOP_BILL_TYPE")
	private String shopBillType;
	/**
	 * 进出类型（进出）
	 */
	@Column(name = "IN_OUT_TYPE")
	private String inOutType;
	/**
	 * 操作人ID
	 */
	@Column(name = "OPERATOR")
	private String operator;
	/**
	 * 店面ID
	 */
	@Column(name = "SHOP_ID")
	private String shopId;
	/**
	 * 操作时间
	 */
	@Column(name = "OPERATOR_TIME")
	private Date operatorTime;
	/**
	 * 单据备注
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	/**
	 * 状态
	 */
	@Column(name = "STATUS")
	private String status;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	public String getShopBillId() {
		return shopBillId;
	}
	public void setShopBillId(String shopBillId) {
		this.shopBillId = shopBillId;
	}
	public String getShopBillNo() {
		return shopBillNo;
	}
	public void setShopBillNo(String shopBillNo) {
		this.shopBillNo = shopBillNo;
	}
	public String getShopBillType() {
		return shopBillType;
	}
	public void setShopBillType(String shopBillType) {
		this.shopBillType = shopBillType;
	}
	public String getInOutType() {
		return inOutType;
	}
	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Date getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
