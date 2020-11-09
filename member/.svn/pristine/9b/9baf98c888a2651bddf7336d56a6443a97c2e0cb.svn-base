package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.UserBillApplyCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.member.condition.UserCondition;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

public class UserSql {

	//查询充值列表sql
	public static String getUser4Operator(UserCondition condition) {
		String sql = "select distinct mc.user_name,u.telphone,u.create_time,u.id ";
		sql = sql + "from `user` u ";
		sql = sql + "join member_card mc ON u.id = mc.user_id ";
		sql = sql + "where 1=1 ";
		sql = addCondition(sql, condition);
		sql = sql + "order by u.create_time desc";
		return sql;
		
	}
	public static void main(String[] args) {
		System.out.println(getUser4Operator(new UserCondition()));
	}
	public static String addCondition(String sql, UserCondition condition) {
		String conditionSql = "";

		if (StringUtils.isNotBlank(condition.getName())) {
			conditionSql = conditionSql + " and u.name ='" + condition.getName() + "' ";
		}
		if (condition.getIds() != null) {
			String[] ids = condition.getIds();
			StringBuffer sb = new StringBuffer();
			for (String id : ids) {
				sb.append("'");
				sb.append(id);
				sb.append("'");
				sb.append(",");
			}
			if(sb.length()>1){
				sb.deleteCharAt(sb.length()-1);
				conditionSql = conditionSql + " and u.id in(" + sb + ") ";
			}
		}
		if (StringUtils.isNotBlank(condition.getTelphone())) {
			conditionSql = conditionSql + " and u.telphone ='" + condition.getTelphone() + "' ";
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
