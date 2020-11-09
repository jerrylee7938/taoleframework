package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "cardInfo")
@Table(name = "CARD_INFO")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class CardInfo implements DomainObject, Cloneable {

	private static final long serialVersionUID = -5778075075297897762L;

	/**
	 * 商品卡ID
	 */
	@Id
	@PrimaryKey
	@Column(name = "CARD_ID", unique = true, nullable = false)
	private String cardId;
	/**
	 * 卡名称
	 */
	@Column(name = "CARD_NAME")
	private String cardName;
	/**
	 * 卡code
	 */
	@Column(name = "CODE")
	private String code;
	/**
	 * 卡分类
	 */
	@Column(name = "CARD_TYPE")
	private String cardType;
	/**
	 * 计费类型（次卡/周期卡）sysdict
	 */
	@Column(name = "CHARGE_TYPE")
	private String chargeType;
	/**
	 * 有效周期(单位：天)
	 */
	@Column(name = "PERIOD_OF_VALIDITY")
	private Integer periodOfValidity;
	/**
	 * 有效次数
	 */
	@Column(name = "TOTAL_NUM")
	private Integer totalNum;
	/**
	 * 售价
	 */
	@Column(name = "MONEY")
	private Double money;
	/**
	 * 上架时间
	 */
	@Column(name = "BEGIN_TIME")
	private Date beginTime;
	/**
	 * 下架时间
	 */
	@Column(name = "END_TIME")
	private Date endTime;
	/**
	 * 状态(启用/停用)
	 */
	@Column(name = "STATUS")
	private String status;
	/**
	 * 线上是否显示1显示 0不显示
	 */
	@Column(name = "ONLINE_SHOW")
	private String onlineShow;
	/**
	 * 显示是否可以充值 1可以 0不可以
	 */
	@Column(name = "ONLINE_RECHARGE")
	private String onlineRecharge;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;
	/**
	 * 卡图片
	 */
	@Column(name = "CARD_IMAGE")
	private String cardImage;
	
	/**
	 * 背面图
	 */
	@Column(name = "IMAGE_BACK")
	private String imageBack;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}



	public Integer getPeriodOfValidity() {
		return periodOfValidity;
	}

	public void setPeriodOfValidity(Integer periodOfValidity) {
		this.periodOfValidity = periodOfValidity;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCardImage() {
		return cardImage;
	}

	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	public String getOnlineShow() {
		return onlineShow;
	}

	public void setOnlineShow(String onlineShow) {
		this.onlineShow = onlineShow;
	}

	public String getOnlineRecharge() {
		return onlineRecharge;
	}

	public void setOnlineRecharge(String onlineRecharge) {
		this.onlineRecharge = onlineRecharge;
	}

	public String getImageBack() {
		return imageBack;
	}

	public void setImageBack(String imageBack) {
		this.imageBack = imageBack;
	}
}
