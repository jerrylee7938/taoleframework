package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.UserBillApplyCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

public class UserBillApplySql {

	//查询充值列表sql
	public static String getUserBillApply(UserBillApplyCondition condition) {
		String sql = "select uba.trans_type,uba.money,uba.pay_type,uba.create_time,uba.status,mc.card_no,ci.card_name,u.`name` as userName,si.`name` as shopName,uba.user_card_id,uba.charge_no,uba.apply_id,uba.amount,ci.card_type,uba.description ";
		sql = sql + "from user_bill_apply uba ";
		sql = sql + "left join shop_info si ON uba.shop_id = si.shop_id ";
		sql = sql + "left join member_card mc ON uba.user_card_id = mc.user_card_id ";
		sql = sql + "left join `user` u ON mc.user_id = u.id ";
		sql = sql + "left join card_info ci ON mc.card_id = ci.card_id ";
		sql = sql + "where 1=1 ";
		sql = addCondition(sql, condition);
		sql = sql + "order by uba.create_time desc";
		return sql;
		
	}
	public static void main(String[] args) {
		System.out.println(getUserBillApply(new UserBillApplyCondition()));
	}
	public static String addCondition(String sql, UserBillApplyCondition condition) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getShopId())) {
			conditionSql = conditionSql + " and uba.shop_id ='" + condition.getShopId() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getCardNo())) {
			conditionSql = conditionSql + " and mc.card_no like '%" + condition.getCardNo() + "%' ";
		}
		if (StringUtils.isNotBlank(condition.getStatus())) {
			conditionSql = conditionSql + " and uba.status ='" + condition.getStatus() + "' ";
		}
		if (StringUtils.isNotBlank(condition.getTransType())) {
			conditionSql = conditionSql + " and uba.trans_type ='" + condition.getTransType() + "' ";
		}
		if (condition.getStartTime() !=  null){
			conditionSql = conditionSql + " and uba.create_time >='" + DateUtil.DateToString(condition.getStartTime(), DateStyle.YYYY_MM_DD)+ "' ";
		}
		if (condition.getEndTime() !=  null){
			conditionSql = conditionSql + " and uba.create_time <='" + DateUtil.DateToString(condition.getEndTime(), DateStyle.YYYY_MM_DD)+ "' ";
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
