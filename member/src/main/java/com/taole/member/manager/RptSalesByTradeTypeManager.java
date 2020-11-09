package com.taole.member.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.events.EventMethod;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.RptSalesByGoodsCondition;
import com.taole.member.condition.RptSalesByPayTypeCondition;
import com.taole.member.condition.RptSalesByTradeTypeCondition;
import com.taole.member.condition.ShopStoreSetCondition;
import com.taole.member.domain.RptSalesByGoods;
import com.taole.member.domain.RptSalesByTradeType;
import com.taole.member.domain.bo.SalesStatResult;
import com.taole.member.sql.CommonSql;
import com.taole.member.sql.RptSalesByPayTypeSql;
import com.taole.member.sql.RptSalesByTradeTypeSql;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

@DomainEngine(types = RptSalesByTradeType.class)
@Transactional(readOnly = true)
public class RptSalesByTradeTypeManager {
	
	@Resource(name = ProjectConfig.PREFIX + "rptSalesByTradeTypeDao")
	DomainObjectDao<RptSalesByTradeType> rptSalesByTradeTypeDao;

	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createRptSalesByTradeType(RptSalesByTradeType rptSalesByTradeType) {
		if (StringUtils.isBlank(rptSalesByTradeType.getStatId()))
			rptSalesByTradeType.setStatId(UUID.generateUUID());

		rptSalesByTradeType.setCreateTime(new Date());
		return rptSalesByTradeTypeDao.createObject(rptSalesByTradeType);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateRptSalesByTradeType(RptSalesByTradeType rptSalesByTradeType) {
		rptSalesByTradeTypeDao.updateObject(rptSalesByTradeType);
	}

	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteRptSalesByTradeType(RptSalesByTradeType rptSalesByTradeType) {
		rptSalesByTradeTypeDao.deleteObject(rptSalesByTradeType);
	}

	@DomainEngine.R
	public RptSalesByTradeType getRptSalesByTradeType(String id) {
		return rptSalesByTradeTypeDao.loadObject(id);
	}

	public List<RptSalesByTradeType> list(RptSalesByTradeTypeCondition condition) {
		return rptSalesByTradeTypeDao.listByCondition(condition);
	}

	public List<RptSalesByTradeType> list(RptSalesByTradeTypeCondition condition, Sorter sorter, int limit) {
		return rptSalesByTradeTypeDao.listByCondition(condition, sorter, limit);
	}

	public PaginationSupport<RptSalesByTradeType> search(RptSalesByTradeTypeCondition condition, Range range, Sorter sroter) {
		return rptSalesByTradeTypeDao.search(condition, range, sroter);
	}

	public int count(RptSalesByTradeTypeCondition condition) {
		return rptSalesByTradeTypeDao.countByCondition(condition);
	}

	public RptSalesByTradeType findByCondition(RptSalesByTradeTypeCondition condition) {
		List<RptSalesByTradeType> list = rptSalesByTradeTypeDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 按多条件查询二维码列表
	 */
	public List<SalesStatResult> queryByMonth(final RptSalesByTradeTypeCondition condition, final Integer start, final Integer pageSize) throws Exception {
		List<?> querylist = ((HibernateDaoSupport) rptSalesByTradeTypeDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = RptSalesByTradeTypeSql.getSqlGoodsSaleByMonth(condition);
				sql = sql + " order by t1.statDate desc ";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				if (start != null) {
					sqlQuery.setFirstResult(start);
					sqlQuery.setMaxResults(pageSize);
				}
				List<?> list = sqlQuery.list();
				return list;
			}
		});

		List<SalesStatResult> list = new ArrayList<SalesStatResult>();
		for (int i = 0; i < querylist.size(); i++) {
			Object[] objs = (Object[]) querylist.get(i);
			SalesStatResult resultBo = new SalesStatResult();
			resultBo.setShopId((String) objs[0]);//店面id
			//resultBo.setObjectId((String) objs[1]);//商品id
			resultBo.setStatMoney((Double)objs[4]);//统计金额
			if(objs[5] == null){
				resultBo.setStatAmount(0);
			}else{
				
				resultBo.setStatAmount(Integer.parseInt(objs[5].toString()));//统计数量
			}
			if (objs[6]!=null) {	
				String statDateStr = (String)objs[6];//统计月份
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
				resultBo.setStatDate(formatter.parse(statDateStr));
			}
			resultBo.setObjectName((String)objs[3]);//商品名称
			//resultBo.setObjectUnit((String)objs[7]);//商品单位
			resultBo.setShopName((String)objs[1]);//店面名称
			
			list.add(resultBo);
		}
		return list;
	}
	/**
	 * 按多条件查询二维码的记录总数
	 */
	public Integer queryByMonthCount(final RptSalesByTradeTypeCondition condition) throws Exception {
		List<?> list = ((HibernateDaoSupport) rptSalesByTradeTypeDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = RptSalesByTradeTypeSql.getSqlGoodsSaleByMonth(condition);
				sql = CommonSql.count(sql);
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				return sqlQuery.list();
			}
		});

		int count = Integer.valueOf(list.get(0).toString());
		return count;
	}
	/**
	 * 调用按交易类型统计销售额日报表的存储过程
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 */
	@Transactional(readOnly = false)
	public List statSalesByBillType(final RptSalesByTradeTypeCondition condition, final int start, final int limit) {
		List<?> statList = ((HibernateDaoSupport) rptSalesByTradeTypeDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				Connection conn = session.connection();   
				CallableStatement call = conn.prepareCall("{Call stat_sales_by_trade_type()}");  
				call.execute();
				return null;
			}
		});
		return statList;
	}
	
	public JSONObject queryByDay(String shopId, String statDate) {
		try {
			// 封装条件
			RptSalesByTradeTypeCondition condition = new RptSalesByTradeTypeCondition();
			condition.setShopId(shopId);
			if (StringUtils.isNotBlank(statDate)) {
				condition.setStatDate(DateUtil.StringToDate(statDate, DateStyle.YYYY_MM_DD));
			}

			// 开始查询
			List<RptSalesByTradeType> list = list(condition);

			// 统一化查询结果
			JSONArray itemJa = new JSONArray();
			for (RptSalesByTradeType rptSalesByTradeType : list) {
				JSONObject itemJo = new JSONObject();
				itemJo.put("name", rptSalesByTradeType.getBillTypeName());
				itemJo.put("money", rptSalesByTradeType.getMoney());
				itemJa.add(itemJo);
			}
			JSONObject resultJo = new JSONObject();
			resultJo.put("name", "按交易类型分");
			resultJo.put("items", itemJa);
			return resultJo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
