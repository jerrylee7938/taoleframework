package com.taole.member.sql;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.RptSalesByGoodsCondition;

public class RptSalesByGoodsSql {

	/**
	 * 按月份进行商品销售额汇总
	 * 
	 * @param condition
	 * @return
	 */
	public static String getSqlGoodsSaleByMonth(RptSalesByGoodsCondition condition) {
		String sql = "select t1.*,gi.name goodsName,gi.catalog_id,gi.unit,si.name shopName from ";
		sql = sql + "(select shop_id,goods_id,sum(money),sum(amount),DATE_FORMAT(stat_date,'%Y-%m') statDate from "
				+ "rpt_sales_by_goods group by shop_id,goods_id,DATE_FORMAT(stat_date,'%Y-%m')) t1 ";
		sql = sql + "LEFT JOIN goods_info gi on t1.goods_id = gi.goods_id ";
		sql = sql + "LEFT JOIN shop_info si on t1.shop_id = si.shop_id ";
		sql = addCondition(condition, sql);
		return sql;
	}
	public static void main(String[] args) {
		System.out.println(getSqlGoodsSaleByMonth(new RptSalesByGoodsCondition()));
	}
	private static String addCondition(RptSalesByGoodsCondition condition, String sql) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getGoodsId())) {
			conditionSql = conditionSql + " and t1.goods_id ='" + condition.getGoodsId() + "'";
		}

		if (StringUtils.isNotBlank(condition.getShopId())) {
			conditionSql = conditionSql + " and t1.shop_id ='" + condition.getShopId() + "'";
		}

		if (condition.getStatDate() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			String statDateStr = formatter.format(condition.getStatDate());
			conditionSql = conditionSql + " and statDate ='" + statDateStr + "'";
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
	
	/**
	 * 按天进行商品销售额汇总
	 * 
	 * @param condition
	 * @return
	 */
	public static String getSqlGoodsSaleByDay(RptSalesByGoodsCondition condition) {
		String sql = "select t1.*,gi.name goodsName,gi.catalog_id,gi.unit,si.name shopName from ";
		sql = sql + "(select shop_id,goods_id,sum(money),sum(amount),DATE_FORMAT(stat_date,'%Y-%m-%d') statDate from "
				+ "rpt_sales_by_goods group by shop_id,goods_id,DATE_FORMAT(stat_date,'%Y-%m-%d')) t1 ";
		sql = sql + "LEFT JOIN goods_info gi on t1.goods_id = gi.goods_id ";
		sql = sql + "LEFT JOIN shop_info si on t1.shop_id = si.shop_id ";
		sql = addCondition1(condition, sql);
		return sql;
	}

	private static String addCondition1(RptSalesByGoodsCondition condition, String sql) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getGoodsId())) {
			conditionSql = conditionSql + " and t1.goods_id ='" + condition.getGoodsId() + "'";
		}

		if (StringUtils.isNotBlank(condition.getShopId())) {
			conditionSql = conditionSql + " and t1.shop_id ='" + condition.getShopId() + "'";
		}

		if (condition.getStatDate() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String statDateStr = formatter.format(condition.getStatDate());
			conditionSql = conditionSql + " and statDate ='" + statDateStr + "'";
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
}
