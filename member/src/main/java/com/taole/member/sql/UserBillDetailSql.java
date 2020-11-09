package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

public class UserBillDetailSql {

	public static String getUserBillDetail(UserBillCondition condition) {
		String sql = "select ub.user_bill_id,ub.user_bill_no,ub.shop_bill_id,ub.create_time,ub.swipe_amount,ub.consume_money,ub.action_type_id,ub.pay_type,ub.operator,ub.user_id,ub.operator_shop_id,u.id,u.`name` userName,mc.user_name,ub.user_card_id ";
		sql = sql + "from user_bill ub ";
		sql = sql + "LEFT JOIN member_card mc ON ub.user_card_id = mc.user_card_id ";
		sql = sql + "LEFT JOIN `user` u ON ub.user_id = u.id ";
		
		sql = addCondition(sql, condition);
		sql = sql + "order by ub.create_time desc";
		return sql;
		
	}
	public static void main(String[] args) {
		UserBillCondition aBillCondition = new UserBillCondition();
		
		System.out.println(getUserBillDetail(aBillCondition));
	}
	public static String addCondition(String sql, UserBillCondition condition) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getOperatorShopId())) {
			conditionSql = conditionSql + " and ub.operator_shop_id ='" + condition.getOperatorShopId() + "' ";
		}
		if (condition.getOperatorShopIds() != null) {
			String[] operatorShopIds = condition.getOperatorShopIds();
			StringBuffer sb = new StringBuffer();
			for (String operatorShopId : operatorShopIds) {
				sb.append("'");
				sb.append(operatorShopId);
				sb.append("'");
				sb.append(",");
			}
			if(sb.length()>1){
				sb.deleteCharAt(sb.length()-1);
				conditionSql = conditionSql + " and ub.operator_shop_id in(" + sb + ") ";
			}
		}
		if (StringUtils.isNotBlank(condition.getCardNo())) {
			conditionSql = conditionSql + " and mc.card_no = '" + condition.getCardNo() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getUserId())) {
			conditionSql = conditionSql + " and ub.user_id ='" + condition.getUserId() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getPayType())) {
			conditionSql = conditionSql + " and ub.pay_type ='" + condition.getPayType() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getActionTypeId())) {
			conditionSql = conditionSql + " and ub.action_type_id ='" + condition.getActionTypeId() + "' ";
		}
		if (condition.getStartTime() !=  null){
			conditionSql = conditionSql + " and ub.create_time >='" + DateUtil.DateToString(condition.getStartTime(), DateStyle.YYYY_MM_DD_HH_MM_SS)+ "' ";
		}
		if (condition.getEndTime() !=  null){
			conditionSql = conditionSql + " and ub.create_time <='" + DateUtil.DateToString(condition.getEndTime(), DateStyle.YYYY_MM_DD_HH_MM_SS)+ "' ";
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
