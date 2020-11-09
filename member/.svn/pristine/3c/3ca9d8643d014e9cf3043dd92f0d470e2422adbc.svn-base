package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Table(name = "ORDER_SERVICE")
@Entity(name = ProjectConfig.PREFIX + "orderService")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class OrderService implements DomainObject, Cloneable {
	private static final long serialVersionUID = 2006719370697386113L;

	/**
    * id
    */
	@Id
	@PrimaryKey
	@Column(name = "ID")
    private String id;

    /**
    * 订单id
    */
	@Column(name = "ORDER_ID")
    private String orderId;

    /**
    * 服务名称
    */
	@Column(name = "SERVICE_NAME")
    private String serviceName;

    /**
    * 服务id
    */
	@Column(name = "SERVICE_ID")
    private String serviceId;

    /**
    * 图片地址
    */
	@Column(name = "IMAGE")
    private String image;

    /**
    * 服务价格
    */
	@Column(name = "MONEY")
    private Double money;
	
	/**
	 * 数量
	 */
	@Column(name = "NUM")
	private Integer num;

    /**
    * 服务描述
    */
	@Column(name = "DESCRIPTION")
    private String description;

    /**
    * create_time
    */
	@Column(name = "CREATE_TIME")
    private Date createTime;

    /**
    * update_time
    */
	@Column(name = "UPDATE_TIME")
    private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
