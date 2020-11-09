package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

/**
 * 验证码。
 * 
 */
@Entity(name = ProjectConfig.PREFIX + "smsVerify")
@Table(name = "SMS_VERIFY")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SmsVerify implements DomainObject, Cloneable {

	private static final long serialVersionUID = 7330307907906647908L;

	/**
	 * 短信验证码ID。
	 */
	@Id
	@PrimaryKey
	@Column(name = "ID", unique = true, nullable = false)
	private String id;
	/**
	 * 验证码。(6)
	 */
	@Column(name = "VERIFY")
	private String verify;
	/**
	 * 手机号。(11)
	 */
	@Column(name = "TELEPHONE")
	private String telephone;
	/**
	 * 国家代码。(11)
	 */
	@Column(name = "COUNTRY")
	private Integer country;
	/**
	 * 验证码类型：1-注册；2-找回密码；3-验证身份。(11)
	 */
	@Column(name = "VERIFY_TYPE")
	private Integer verifyType;
	/**
	 * 短信模板ID 。(11)
	 */
	@Column(name = "TPL_ID")
	private Integer tplId;
	/**
	 * 是否使用：0-未使用；1-已使用。(11)
	 */
	@Column(name = "IS_USED")
	private Integer isUsed;
	/**
	 * 是否发送：0-未发送；1-已发送；2-发送失败。(11)
	 */
	@Column(name = "IS_SEND")
	private Integer isSend;
	/**
	 * 过期时间。
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRED_TIME")
	private Date expiredTime;
	/**
	 * 创建时间。
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME")
	private Date createdTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
	}

	public Integer getTplId() {
		return tplId;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
