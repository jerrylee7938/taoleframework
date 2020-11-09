package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "account")
@Table(name = "ACCOUNT")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Account implements DomainObject,Cloneable{

	private static final long serialVersionUID = 3057294320570451098L;

	/**
	 * 账户ID
	 */
	@Id
	@PrimaryKey
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false)
	private String accountId;

	/**
	 * 用户ID
	 */
	@Column(name = "USER_ID")
	private String userId;
	
	/**
	 * 登录账号
	 */
	@Column(name = "ACCOUNT")
	private String account;
	
	/**
	 * 登录密码
	 */
	@Column(name = "PASSWORD")
	private String password;
	
	/**
	 * salt值
	 */
	@Column(name = "SALT")
	private String salt;
	
	/**
	 * 角色 1 会员
	 */
	@Column(name = "ROLE")
	private Integer role;
	
	/**
	 *支付密码
	 */
	@Column(name = "PAY_SECRET")
	private String paySecret;
	
	/**
	 *状态
	 */
	@Column(name = "STATUS")
	private String status;
	
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
	
	/**
	 *是否删除 0 未删除 1 已删除
	 */
	@Column(name = "IS_DELETE")
	private Integer isDelete;
	
	/**
	 *业务线
	 */
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getPaySecret() {
		return paySecret;
	}

	public void setPaySecret(String paySecret) {
		this.paySecret = paySecret;
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
}
