package com.taole.member.sql;


public class MemberCardSql {

	public static String userMemberCardSql(){
		String sql = "select mc.user_card_id, mc.card_no, mc.deadline, mc.`status`, mc.card_image,"
				+ " ci.card_name, ci.card_id, ci.card_type, ci.total_num,"
				+ " mc.user_name, mc.user_avatar, mc.user_birthday, mc.user_gender, mc.operator_shop_id, si.`name` "
				+ "from member_card mc, card_info ci, shop_info si where mc.card_id = ci.card_id and mc.operator_shop_id = si.shop_id "
				+ "and mc.user_id = :userId "
				+ "and mc.status not in ('0', '2', '6') ";
		return sql;
	}
	
	public static String userMemberCardForRechargeSql(){
		String sql = "select mc.user_card_id, mc.card_no, mc.deadline,ci.card_name,mc.card_image,ci.card_type,mc.card_id "
				+ "from member_card mc, card_info ci where mc.card_id = ci.card_id "
				+ "and mc.user_id = :userId "
				+ "and mc.`status` not in ('0', '2', '6') ";
		return sql;
	}
}
