package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.ShopStoreSetCondition;

public class ShopStoreSetSql {

	public static String getGoodsDetail(ShopStoreSetCondition shopStoreSetCondition) {
		System.out.println(shopStoreSetCondition.getShopId());
		String sql = "select sss.object_id,sss.object_type,sss.shop_id,sss.object_id,gi.goods_id,gi.goods_code,gi.name,gi.description,gi.unit, ";
		sql = sql+"(select balance from goods_bill_detail gbd where goods_id = gi.goods_id and shop_id = '"+shopStoreSetCondition.getShopId()+"' order by create_time desc limit 1)";
		sql = sql + "from shop_store_set sss ";
		sql = sql + "join goods_info gi ON sss.object_id=gi.goods_id ";
		sql = sql + "where 1=1 ";
		sql = addCondition(sql, shopStoreSetCondition);
		sql = sql + "order by sss.goods_set_id desc";
		return sql;
		
	}
	public static void main(String[] args) {
		System.out.println(getGoodsDetail(new ShopStoreSetCondition()));
	}
	public static String addCondition(String sql, ShopStoreSetCondition shopStoreSetCondition) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(shopStoreSetCondition.getObjectType())) {
			conditionSql = conditionSql + " and gi.catalog_id ='" + shopStoreSetCondition.getObjectType() + "' ";
		}
		
		if (StringUtils.isNotBlank(shopStoreSetCondition.getName())) {
			conditionSql = conditionSql + " and gi.name like '%" + shopStoreSetCondition.getName() + "%' ";
		}
		
		if (StringUtils.isNotBlank(shopStoreSetCondition.getShopId())) {
			conditionSql = conditionSql + " and sss.shop_id = '" + shopStoreSetCondition.getShopId() + "' ";
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
