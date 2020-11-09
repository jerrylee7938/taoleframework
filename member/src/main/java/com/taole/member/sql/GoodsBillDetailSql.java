package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.GoodsBillDetailCondition;

public class GoodsBillDetailSql {

	public static String getGoodsBillDetail(GoodsBillDetailCondition condition) {
		String sql = "select gbd.shop_bill_id,gbd.goods_id,gbd.shop_id,gbd.amount,gbd.unit,gbd.balance,gbd.create_time,gb.shop_bill_no,gb.shop_bill_type,gb.operator,si.name as 'shopName' ,gi.name as 'goodsName' ,gi.goods_code,gi.catalog_id ";
		sql = sql + "from goods_bill_detail gbd ";
		sql = sql + "left join goods_bill gb ON gbd.shop_bill_id=gb.shop_bill_id ";
		sql = sql + "left join shop_info si ON gbd.shop_id = si.shop_id ";
		sql = sql + "left join goods_info gi ON gbd.goods_id = gi.goods_id ";
		sql = sql + "where 1=1 ";
		sql = addCondition(sql, condition);
		sql = sql + "order by gbd.create_time desc";
		return sql;
		
	}
	public static void main(String[] args) {
		System.out.println(getGoodsBillDetail(new GoodsBillDetailCondition()));
	}
	public static String addCondition(String sql, GoodsBillDetailCondition condition) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getShopId())) {
			conditionSql = conditionSql + " and gbd.shop_id ='" + condition.getShopId() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getGoodsId())) {
			conditionSql = conditionSql + " and gbd.goods_id ='" + condition.getGoodsId() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getCatalogId())) {
			conditionSql = conditionSql + " and gi.catalog_id ='" + condition.getCatalogId() + "' ";
		}
		
		if (StringUtils.isNotBlank(condition.getGoodsName())) {
			conditionSql = conditionSql + " and gi.name like '%" + condition.getGoodsName() + "%' ";
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
