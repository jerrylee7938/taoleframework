package com.taole.member.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.Constants.DimensName;
import com.taole.member.condition.RptSalesByGoodsCondition;
import com.taole.member.domain.RptSalesByGoods;
import com.taole.member.domain.bo.SalesStatResult;
import com.taole.member.sql.CommonSql;
import com.taole.member.sql.RptSalesByGoodsSql;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;
import com.taole.toolkit.util.PageUtil;


@DomainEngine(types = RptSalesByGoods.class)
@Transactional(readOnly = true)
public class RptSalesByGoodsManager {
	
	@Autowired
	private DictionaryManager dictionaryManager;

	@Resource(name = ProjectConfig.PREFIX + "rptSalesByGoodsDao")
	DomainObjectDao<RptSalesByGoods> rptSalesByGoodsDao;

	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createRptSalesByGoods(RptSalesByGoods rptSalesByGoods) {
		if (StringUtils.isBlank(rptSalesByGoods.getStatId()))
			rptSalesByGoods.setStatId(UUID.generateUUID());

		rptSalesByGoods.setCreateTime(new Date());
		return rptSalesByGoodsDao.createObject(rptSalesByGoods);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateRptSalesByGoods(RptSalesByGoods rptSalesByGoods) {
		rptSalesByGoodsDao.updateObject(rptSalesByGoods);
	}

	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteRptSalesByGoods(RptSalesByGoods rptSalesByGoods) {
		rptSalesByGoodsDao.deleteObject(rptSalesByGoods);
	}

	@DomainEngine.R
	public RptSalesByGoods getRptSalesByGoods(String id) {
		return rptSalesByGoodsDao.loadObject(id);
	}

	public List<RptSalesByGoods> list(RptSalesByGoodsCondition condition) {
		return rptSalesByGoodsDao.listByCondition(condition);
	}

	public List<RptSalesByGoods> list(RptSalesByGoodsCondition condition, Sorter sorter, int limit) {
		return rptSalesByGoodsDao.listByCondition(condition, sorter, limit);
	}

	public PaginationSupport<RptSalesByGoods> search(RptSalesByGoodsCondition condition, Range range, Sorter sroter) {
		return rptSalesByGoodsDao.search(condition, range, sroter);
	}

	public int count(RptSalesByGoodsCondition condition) {
		return rptSalesByGoodsDao.countByCondition(condition);
	}

	public RptSalesByGoods findByCondition(RptSalesByGoodsCondition condition) {
		List<RptSalesByGoods> list = rptSalesByGoodsDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 按多条件查询二维码列表
	 */
	public List<SalesStatResult> queryByMonth(final RptSalesByGoodsCondition condition, final Integer start, final Integer pageSize) throws Exception {
		List<?> querylist = ((HibernateDaoSupport) rptSalesByGoodsDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = RptSalesByGoodsSql.getSqlGoodsSaleByMonth(condition);
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
			resultBo.setObjectId((String) objs[1]);//商品id
			resultBo.setStatMoney((Double)objs[2]);//统计金额
			resultBo.setStatAmount(((BigDecimal)objs[3]).intValue());//统计数量
			if (objs[4]!=null) {
				String statDateStr = (String)objs[4];//统计月份
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
				resultBo.setStatDate(formatter.parse(statDateStr));
			}
			resultBo.setObjectName((String)objs[5]);//商品名称
			resultBo.setObjectUnit((String)objs[7]);//商品单位
			resultBo.setShopName((String)objs[8]);//店面名称
			
			list.add(resultBo);
		}
		return list;
	}

	/**
	 * 按多条件查询二维码的记录总数
	 */
	public Integer queryByMonthCount(final RptSalesByGoodsCondition condition) throws Exception {
		List<?> list = ((HibernateDaoSupport) rptSalesByGoodsDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = RptSalesByGoodsSql.getSqlGoodsSaleByMonth(condition);
				sql = CommonSql.count(sql);
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				return sqlQuery.list();
			}
		});

		int count = Integer.valueOf(list.get(0).toString());
		return count;
	}
	
	/**
	 * 调用商品日报表统计存储过程
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public List statSalesByGoods(final RptSalesByGoodsCondition condition, final Integer start, final Integer pageSize) throws Exception {
		List<?> statList = ((HibernateDaoSupport) rptSalesByGoodsDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				Connection conn = session.connection();   
				CallableStatement call = conn.prepareCall("{Call stat_sales_by_goods()}");  
				call.execute();
				return null;
			}
		});
		return statList;
	}
	
	public JSONObject queryByDay(String shopId, String statDate) {
		try {
			// 封装条件
			RptSalesByGoodsCondition condition = new RptSalesByGoodsCondition();
			condition.setShopId(shopId);
			if (StringUtils.isNotBlank(statDate)) {
				condition.setStatDate(DateUtil.StringToDate(statDate, DateStyle.YYYY_MM_DD));
			}

			// 开始查询
			List<RptSalesByGoods> list = list(condition);

			// 统一化查询结果
			JSONArray itemJa = new JSONArray();
			for (RptSalesByGoods rptSalesByGoods : list) {
				JSONObject itemJo = new JSONObject();
				itemJo.put("name", rptSalesByGoods.getGoodsName());
				itemJo.put("money", rptSalesByGoods.getMoney());
				itemJa.add(itemJo);
			}
			JSONObject resultJo = new JSONObject();
			resultJo.put("name", "按商品明细分");
			resultJo.put("items", itemJa);
			return resultJo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public JSONObject queryByMonth(String shopId, String statDate) {
		try {

			// 封装条件
			RptSalesByGoodsCondition condition = new RptSalesByGoodsCondition();
			condition.setShopId(shopId);
			if (StringUtils.isNotBlank(statDate)) {
				Date firstDay = DateUtil.getFirstDayOfMonth(DateUtil.StringToDate(statDate));
				Date lastDay = DateUtil.getLastDayOfMonth(DateUtil.StringToDate(statDate));
				condition.setFirstDay(firstDay);
				condition.setLastDay(lastDay);
			}
			// 开始查询
			List<RptSalesByGoods> list = list(condition);

			// 整理查询结果
			JSONArray itemJa = new JSONArray();
			Double sumMoney = 0D;
			for (RptSalesByGoods rptSalesByGoods : list) {
				JSONObject itemJo = new JSONObject();
				sumMoney += rptSalesByGoods.getMoney();
				itemJo.put("stateMoney", sumMoney);
				itemJo.put("name", rptSalesByGoods.getGoodsName());
				
				itemJa.add(itemJo);
			}
			JSONObject resultJo = new JSONObject();
			resultJo.put("name", "按商品明细分");
			resultJo.put("items", itemJa);
			return resultJo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
