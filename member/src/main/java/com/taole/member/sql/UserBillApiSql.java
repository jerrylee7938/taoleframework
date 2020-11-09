package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.UserBillCondition;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

public class UserBillApiSql {

	public static String userBillPayCount(UserBillCondition condition){
		String sql = "select SUM(ub.consume_money) from user_bill ub ";
		
		sql = addCondition1(condition, sql);
		return sql;
	}
	
	private static String addCondition1(UserBillCondition condition, String sql) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getUserId())) {
			conditionSql = conditionSql + " and ub.user_id ='" + condition.getUserId() + "'";
		}

		if (StringUtils.isNotBlank(condition.getActionTypeId())) {
			conditionSql = conditionSql + " and ub.action_type_id ='" + condition.getActionTypeId() + "'";
		}

		if (StringUtils.isNotBlank(condition.getNeActionTypeId())) {
			conditionSql = conditionSql + " and ub.action_type_id !='" + condition.getNeActionTypeId() + "'";
		}

		if (condition.getStartTime() != null){
			conditionSql = conditionSql + " and ub.operate_time >= '"+DateUtil.DateToString(condition.getStartTime(), DateStyle.YYYY_MM_DD_HH_MM_SS)+"'";
		}
		
		if (condition.getEndTime() != null){
			conditionSql = conditionSql + " and ub.operate_time <= '"+DateUtil.DateToString(condition.getEndTime(), DateStyle.YYYY_MM_DD_HH_MM_SS)+"'";
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
