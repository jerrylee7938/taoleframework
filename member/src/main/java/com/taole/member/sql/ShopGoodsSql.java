package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.ShopGoodsCondition;

public class ShopGoodsSql {

	public static String shopGoodsList(ShopGoodsCondition condition){
		
		String sql = "select gi.goods_id,gi.`name`,gi.sale_money,gi.image,gi.description,gi.original_cost,gi.maxnumber "
				+ "from shop_store_set sss, goods_info gi "
				+ "where sss.object_id = gi.goods_id and gi.online_buy = '1' ";
		
		sql = addCondition(sql, condition);
		
		return sql;
	}
	public static void main(String[] args) {
		System.out.println(shopGoodsList(new ShopGoodsCondition()));
	}
	public static String shopGoodsForHomeList(ShopGoodsCondition condition){
		
		String sql = "select gi.goods_id,gi.`name`,gi.sale_money,gi.image,gi.description "
				+ "from shop_store_set sss, goods_info gi, goods_ref_type grt "
				+ "where sss.object_id = gi.goods_id and gi.goods_id = grt.goods_id and gi.online_buy = '1' ";
		
		sql = addCondition(sql, condition);
		
		return sql;
	}
	
	public static String shopTicketList(ShopGoodsCondition condition){
		
		String sql = "select gi.goods_id,gi.`name`,gi.sale_money,gi.image,gi.description,gi.image_back,gi.original_cost,gi.maxnumber "
				+ "from shop_store_set sss, goods_info gi "
				+ "where sss.object_id = gi.goods_id and gi.catalog_id = 'MPL' and gi.online_buy = '1' ";
		
		sql = addCondition(sql, condition);
		
		return sql;
	}
	
	public static String addCondition(String sql, ShopGoodsCondition condition) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getShopId())) {
			conditionSql = conditionSql + " and sss.shop_id ='" + condition.getShopId() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getCatalogId())) {
			conditionSql = conditionSql + " and gi.catalog_id ='" + condition.getCatalogId() + "' ";
		}
		
		if (StringUtils.isNotBlank(condition.getType())) {
			conditionSql = conditionSql + " and sss.object_type = '" + condition.getType() + "' ";
		}
		
		if (StringUtils.isNotBlank(condition.getHomeCatalogId())) {
			conditionSql = conditionSql + " and grt.type_id = '" + condition.getHomeCatalogId() + "' ";
		}
		
		if (StringUtils.isNotBlank(conditionSql)) {
			if (sql.indexOf("where") <= 0) {// 没有where关键字
				sql = sql + " where " + conditionSql.substring(4, conditionSql.length());
			} else {
				sql = sql + conditionSql;
			}
		}
		return sql;
	}
}
