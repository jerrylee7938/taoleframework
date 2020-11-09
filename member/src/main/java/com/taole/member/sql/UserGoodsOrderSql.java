package com.taole.member.sql;

public class UserGoodsOrderSql {

	public static String allGoodsNameSql(){
		
		String sql = "select GROUP_CONCAT(gi.`name`) from goods_bill_detail gbd, goods_info gi where gbd.goods_id = gi.goods_id and gbd.shop_bill_id = :shopBillId";
	
		return sql;
	}
}
