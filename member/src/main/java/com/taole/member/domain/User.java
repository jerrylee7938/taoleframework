package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;
@Entity(name = ProjectConfig.PREFIX + "user")
@Table(name = "USER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class User implements DomainObject,Cloneable{

	private static final long serialVersionUID = -4870046409562065953L;

	/**
	 * 用户ID
	 */
	@Id
	@PrimaryKey
	@Column(name = "ID", unique = true, nullable = false)
	private String id;
	
	/**
	 * 账户ID
	 */
	@Column(name = "ACCOUNT_ID")
	private String accountId;
	
	/**
	 * 手机号
	 */
	@Column(name = "TELPHONE")
	private String telphone;

	/**
	 * 真实姓名
	 */
	@Column(name = "NAME")
	private String name;
	
	/**
	 * 头像地址
	 */
	@Column(name = "AVATAR")
	private String avatar;
	
	/**
	 * 性别 0 未知 1 男 2 女
	 */
	@Column(name = "GENDER")
	private Integer gender;
	
	/**
	 * 出生日期
	 */
	@Column(name = "BIRTHDAY")
	private Date birthday;
	
	/**
	 * 省份代码
	 */
	@Column(name = "PROVINCE")
	private String province;
	
	/**
	 * 城市代码
	 */
	@Column(name = "CITY")
	private String city;
	
	/**
	 * 住址
	 */
	@Column(name = "ADDRESS")
	private String address;
	
	/**
	 * 证件类型 1: 身份证; 2: 护照; 3:军官证
	 */
	@Column(name = "CREDENTIAL_TYPE")
	private Integer credentialType;
	
	/**
	 * 证件号
	 */
	@Column(name = "CREDENTIAL_NO")
	private String credentialNo;
	
	/**
	 * 是否认证 0 未认证 1 已认证
	 */
	@Column(name = "IS_VERIFIED")
	private Integer isVerified;
	
	/**
	 * 认证时间
	 */
	@Column(name = "VERIFIED_TIME")
	private Date verifiedTime;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date UpdateTime;
	
	/**
	 * 是否删除 0 未删除 1 已删除
	 */
	@Column(name = "IS_DELETE")
	private Integer isDelete;
	
	/**
	 * 是否显示 0 不显示  1 显示
	 */
	@Column(name = "IS_DISPLAY")
	private Integer isDisplay;
	
	/**
	 * 职称ID
	 */
	@Column(name = "RANK")
	private String rank;
	
	/**
	 * 排序 数字越大越前
	 */
	@Column(name = "LISTORDER")
	private Integer listorder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}

	public String getCredentialNo() {
		return credentialNo;
	}

	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}

	public Integer getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

	public Date getVerifiedTime() {
		return verifiedTime;
	}

	public void setVerifiedTime(Date verifiedTime) {
		this.verifiedTime = verifiedTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(Date updateTime) {
		UpdateTime = updateTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Integer getListorder() {
		return listorder;
	}

	public void setListorder(Integer listorder) {
		this.listorder = listorder;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
}
