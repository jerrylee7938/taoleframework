package com.taole.member.sql;

import com.taole.member.condition.ShopStoreSetCondition;

public class UserBillSql {

	/**
	 * 获取一张卡的已消费次数sql
	 */
	public static String getConsumeNumForCard(String userCardId) {
		String sql = "select sum(swipe_amount) from user_bill ub where action_type_id = 'XF' and user_card_id = '"
				+ userCardId+"'";
		return sql;
	}
}
