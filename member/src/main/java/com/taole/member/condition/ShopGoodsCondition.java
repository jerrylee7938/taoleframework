package com.taole.member.condition;

public class ShopGoodsCondition {

	//店铺id
	private String shopId;
	//商品分类id
	private String catalogId;
	//店铺商品类型
	private String type;
	//首页分类id
	private String homeCatalogId;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHomeCatalogId() {
		return homeCatalogId;
	}

	public void setHomeCatalogId(String homeCatalogId) {
		this.homeCatalogId = homeCatalogId;
	}
}
