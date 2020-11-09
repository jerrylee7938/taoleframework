package com.taole.member.domain.param.ShopInfoService;

import java.util.Date;

import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Create {
	@ApiModel(value = "新增店面信息请求参数")
	public static class ShopInfoServiceCreateReq {

		@ApiModelProperty(value = "店面名称", example = "国贸店")
		private String name;

		@ApiModelProperty(value = "省份", example = "310000")
		private String province;

		@ApiModelProperty(value = "城市", example = "310104")
		private String city;

		@ApiModelProperty(value = "地址", example = "朝阳区国贸")
		private String address;

		@ApiModelProperty(value = "联系电话", example = "13746262839")
		private String contactTel;

		@ApiModelProperty(value = "联系人", example = "张三")
		private String contactPerson;

		@ApiModelProperty(value = "状态(已开业1/未开业0)")
		private String status;

		@ApiModelProperty(value = "备注")
		private String description;

		@ApiModelProperty(value = "店铺LOGO")
		private String shopLogo;

		@ApiModelProperty(value = "店铺列表图片")
		private String shopListImage;

		@ApiModelProperty(value = "店铺详情图片(大图)")
		private String shopDescImage;

		@ApiModelProperty(value = "店铺经度")
		private Double lng;

		@ApiModelProperty(value = "店铺纬度")
		private Double lat;

		@ApiModelProperty(value = "店铺介绍")
		private String introduce;

		@ApiModelProperty(value = "排序字段")
		private Integer sort;

		@ApiModelProperty(value = "开店时间")
		private String openTime;

		@ApiModelProperty(value = "关店时间")
		private String closeTime;

		public String getOpenTime() {
			return openTime;
		}

		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}

		public String getCloseTime() {
			return closeTime;
		}

		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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

		public String getContactTel() {
			return contactTel;
		}

		public void setContactTel(String contactTel) {
			this.contactTel = contactTel;
		}

		public String getContactPerson() {
			return contactPerson;
		}

		public void setContactPerson(String contactPerson) {
			this.contactPerson = contactPerson;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getShopLogo() {
			return shopLogo;
		}

		public void setShopLogo(String shopLogo) {
			this.shopLogo = shopLogo;
		}

		public String getShopListImage() {
			return shopListImage;
		}

		public void setShopListImage(String shopListImage) {
			this.shopListImage = shopListImage;
		}

		public String getShopDescImage() {
			return shopDescImage;
		}

		public void setShopDescImage(String shopDescImage) {
			this.shopDescImage = shopDescImage;
		}

		public Double getLng() {
			return lng;
		}

		public void setLng(Double lng) {
			this.lng = lng;
		}

		public Double getLat() {
			return lat;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public String getIntroduce() {
			return introduce;
		}

		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

	}

	@ApiModel(value = "新增店面信息返回参数")
	public static class ShopInfoServiceCreateResp {
		@ApiModelProperty(value = "店面Id")
		private String shopId;

		@ApiModelProperty(value = "店面名称")
		private String name;

		@ApiModelProperty(value = "省份")
		private String province;

		@ApiModelProperty(value = "城市")
		private String city;

		@ApiModelProperty(value = "地址")
		private String address;

		@ApiModelProperty(value = "联系电话")
		private String contactTel;

		@ApiModelProperty(value = "联系人")
		private String contactPerson;

		@ApiModelProperty(value = "状态(已开业/未开业)")
		private String status;

		@ApiModelProperty(value = "备注")
		private String description;

		@ApiModelProperty(value = "创建时间")
		private String createTime;

		@ApiModelProperty(value = "修改时间")
		private String updateTime;

		@ApiModelProperty(value = "店铺LOGO")
		private String shopLogo;

		@ApiModelProperty(value = "店铺列表图片")
		private String shopListImage;

		@ApiModelProperty(value = "店铺详情图片(大图)")
		private String shopDescImage;

		@ApiModelProperty(value = "店铺经度")
		private Double lng;

		@ApiModelProperty(value = "店铺纬度")
		private Double lat;

		@ApiModelProperty(value = "店铺介绍")
		private String introduce;

		@ApiModelProperty(value = "排序字段")
		private Integer sort;

		@ApiModelProperty(value = "开店时间")
		private String openTime;

		@ApiModelProperty(value = "关店时间")
		private String closeTime;

		public String getOpenTime() {
			return openTime;
		}

		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}

		public String getCloseTime() {
			return closeTime;
		}

		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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

		public String getContactTel() {
			return contactTel;
		}

		public void setContactTel(String contactTel) {
			this.contactTel = contactTel;
		}

		public String getContactPerson() {
			return contactPerson;
		}

		public void setContactPerson(String contactPerson) {
			this.contactPerson = contactPerson;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

		public String getShopLogo() {
			return shopLogo;
		}

		public void setShopLogo(String shopLogo) {
			this.shopLogo = shopLogo;
		}

		public String getShopListImage() {
			return shopListImage;
		}

		public void setShopListImage(String shopListImage) {
			this.shopListImage = shopListImage;
		}

		public String getShopDescImage() {
			return shopDescImage;
		}

		public void setShopDescImage(String shopDescImage) {
			this.shopDescImage = shopDescImage;
		}

		public Double getLng() {
			return lng;
		}

		public void setLng(Double lng) {
			this.lng = lng;
		}

		public Double getLat() {
			return lat;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public String getIntroduce() {
			return introduce;
		}

		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

	}
}
