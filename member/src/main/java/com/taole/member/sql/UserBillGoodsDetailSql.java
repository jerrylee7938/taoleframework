package com.taole.member.sql;

import org.apache.commons.lang3.StringUtils;

import com.taole.member.Constants.ShopBillType;
import com.taole.member.Constants.TransactionType;
import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.UserBillCondition;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

public class UserBillGoodsDetailSql {

	public static String getUserBillGoodsDetail(UserBillCondition condition) {
		String sql = "select ub.user_bill_no,gbd.create_time,si.`name` as 'shopName',u.`name` as 'userName',mc.card_no,gi.`name` as goodsName,gi.sale_money,gbd.amount,ub.consume_money,ub.pay_type,ub.operator,ub.user_id ";
		sql = sql + "from goods_bill_detail gbd ";
		sql = sql + "left join goods_bill gb ON gbd.shop_bill_id = gb.shop_bill_id ";
		sql = sql + "left join user_bill ub on gb.shop_bill_id = ub.shop_bill_id ";
		sql = sql + "left join goods_info gi on gbd.goods_id = gi.goods_id ";
		sql = sql + "left join shop_info si on gbd.shop_id = si.shop_id ";
		sql = sql + "left join `user` u on ub.user_id = u.id ";
		sql = sql + "left join member_card mc on ub.user_card_id = mc.user_card_id ";
		sql = sql + "where 1=1 AND gb.shop_bill_type = '"+ShopBillType.SALEOUT+"'and ub.action_type_id = '"+TransactionType.XF+"'";
		sql = addCondition(sql, condition);
		sql = sql + "order by ub.create_time desc";
		return sql;
		
	}
	public static void main(String[] args) {
		UserBillCondition aBillCondition = new UserBillCondition();
		
		String[] operatorShopIds = new String[]{"q","2"};
		aBillCondition.setOperatorShopIds(operatorShopIds);
		System.out.println(getUserBillGoodsDetail(aBillCondition));
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
			conditionSql = conditionSql + " and mc.card_no like '%" + condition.getCardNo() + "%' ";
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
