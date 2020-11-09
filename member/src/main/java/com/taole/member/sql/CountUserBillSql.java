package com.taole.member.sql;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.RptSalesByGoodsCondition;
import com.taole.member.condition.UserBillCondition;

/*
 * 查询顾客某一商品购买次数
 */
public class CountUserBillSql {

	public static String countUserBillSql(UserBillCondition condition){
		String sql = "select count(*)"
				+ "from user_bill u, goods_bill_detail g where u.shop_bill_id = g.shop_bill_id ";
				sql = addCondition(condition, sql);
		return sql;
	}
	private static String addCondition(UserBillCondition condition, String sql) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getGoodsId())) {
			conditionSql = conditionSql + " and g.goods_id ='" + condition.getGoodsId() + "'";
		}

		if (StringUtils.isNotBlank(condition.getOperatorShopId())) {
			conditionSql = conditionSql + " and g.shop_id ='" + condition.getOperatorShopId() + "'";
		}
		if (StringUtils.isNotBlank(condition.getUserId())) {
			conditionSql = conditionSql + " and u.user_id ='" + condition.getUserId() + "'";
		}
		if (StringUtils.isNotBlank(conditionSql)) {
			if (sql.indexOf("where") > 0) {// 存在where关键字了
				sql = sql + conditionSql;
			} else {
				sql = sql + " where " + conditionSql.substring(5, conditionSql.length());
			}
		}
		return sql;
}
	
	public static void main(String[] args) {
		System.out.println(countUserBillSql(new UserBillCondition()));
	}
	}
