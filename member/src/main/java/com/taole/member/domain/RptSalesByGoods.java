package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "rptSalesByGoods")
@Table(name = "rpt_sales_by_goods")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class RptSalesByGoods implements DomainObject, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6234014943133489746L;

	/**
	 * 统计记录ID
	 */
	@Id
	@PrimaryKey
	@Column(name = "stat_id")
	private String statId;
	
	/**
	 * 商店ID
	 */
	@Column(name = "shop_id")
	private String shopId;
	
	/**
	 * 商店名称
	 */
	@Column(name = "shop_name")
	private String shopName;
	
	/**
	 * 统计日期
	 */
	@Column(name = "stat_date")
	private Date statDate;
	
	/**
	 * 商品ID
	 */
	@Column(name = "goods_id")
	private String goodsId;
	
	/**
	 * 商品名称
	 */
	@Column(name = "goods_name")
	private String goodsName;
	
	/**
	 * 商品类型ID
	 */
	@Column(name = "goods_type_id")
	private String goodsTypeId;
	
	/**
	 * 商品类型名称
	 */
	@Column(name = "goods_type_name")
	private String goodsTypeName;
	
	/**
	 * 统计金额
	 */
	@Column(name = "money")
	private Double money;
	
	/**
	 * 统计数量
	 */
	@Column(name = "amount")
	private Integer amount;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	public String getStatId() {
		return statId;
	}

	public void setStatId(String statId) {
		this.statId = statId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
