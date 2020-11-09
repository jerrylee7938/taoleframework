package com.taole.member.manager;

import java.math.BigDecimal;
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
import com.taole.member.domain.RptSalesByGoods;
import com.taole.member.domain.RptSalesByPayType;
import com.taole.member.domain.bo.SalesStatResult;
import com.taole.member.sql.CommonSql;
import com.taole.member.sql.RptSalesByGoodsSql;
import com.taole.member.sql.RptSalesByPayTypeSql;
import com.taole.toolkit.util.DateStyle;
import com.taole.toolkit.util.DateUtil;

@DomainEngine(types = RptSalesByPayType.class)
@Transactional(readOnly = true)
public class RptSalesByPayTypeManager {

	@Resource(name = ProjectConfig.PREFIX + "rptSalesByPayTypeDao")
	DomainObjectDao<RptSalesByPayType> rptSalesByPayTypeDao;

	@DomainEngine.C
	@EventMethod
	@Transactional
	public String createRptSalesByPayType(RptSalesByPayType rptSalesByPayType) {
		if (StringUtils.isBlank(rptSalesByPayType.getStatId()))
			rptSalesByPayType.setStatId(UUID.generateUUID());

		rptSalesByPayType.setCreateTime(new Date());
		return rptSalesByPayTypeDao.createObject(rptSalesByPayType);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateRptSalesByPayType(RptSalesByPayType rptSalesByPayType) {
		rptSalesByPayTypeDao.updateObject(rptSalesByPayType);
	}

	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteRptSalesByPayType(RptSalesByPayType rptSalesByPayType) {
		rptSalesByPayTypeDao.deleteObject(rptSalesByPayType);
	}

	@DomainEngine.R
	public RptSalesByPayType getRptSalesByPayType(String id) {
		return rptSalesByPayTypeDao.loadObject(id);
	}

	public List<RptSalesByPayType> list(RptSalesByPayTypeCondition condition) {
		return rptSalesByPayTypeDao.listByCondition(condition);
	}

	public List<RptSalesByPayType> list(RptSalesByPayTypeCondition condition, Sorter sorter, int limit) {
		return rptSalesByPayTypeDao.listByCondition(condition, sorter, limit);
	}

	public PaginationSupport<RptSalesByPayType> search(RptSalesByPayTypeCondition condition, Range range, Sorter sroter) {
		return rptSalesByPayTypeDao.search(condition, range, sroter);
	}

	public int count(RptSalesByPayTypeCondition condition) {
		return rptSalesByPayTypeDao.countByCondition(condition);
	}

	public RptSalesByPayType findByCondition(RptSalesByPayTypeCondition condition) {
		List<RptSalesByPayType> list = rptSalesByPayTypeDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 * 按多条件查询二维码列表
	 */
	public List<SalesStatResult> queryByMonth(final RptSalesByPayTypeCondition condition, final Integer start, final Integer pageSize) throws Exception {
		List<?> querylist = ((HibernateDaoSupport) rptSalesByPayTypeDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = RptSalesByPayTypeSql.getSqlGoodsSaleByMonth(condition);
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
			resultBo.setStatAmount(Integer.parseInt(objs[5].toString()));//统计数量
			if (objs[5]!=null) {	
				String statDateStr = (String)objs[6];//统计月份
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
				resultBo.setStatDate(formatter.parse(statDateStr));
			}
			resultBo.setObjectName((String)objs[3]);//支付名称
			//resultBo.setObjectUnit((String)objs[7]);//商品单位
			resultBo.setShopName((String)objs[1]);//店面名称
			
			list.add(resultBo);
		}
		return list;
	}

	/**
	 * 按多条件查询二维码的记录总数
	 */
	public Integer queryByMonthCount(final RptSalesByPayTypeCondition condition) throws Exception {
		List<?> list = ((HibernateDaoSupport) rptSalesByPayTypeDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = RptSalesByPayTypeSql.getSqlGoodsSaleByMonth(condition);
				sql = CommonSql.count(sql);
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				return sqlQuery.list();
			}
		});

		int count = Integer.valueOf(list.get(0).toString());
		return count;
	}
	/**
	 * 调用按支付方式统计销售额日报表的存储过程
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 */
	@Transactional(readOnly = false)
	public List statSalesByPayType(final RptSalesByPayTypeCondition condition, final int start, final int limit) {
		List<?> statList = ((HibernateDaoSupport) rptSalesByPayTypeDao).getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doInHibernate(Session session) throws HibernateException, SQLException {
				Connection conn = session.connection();   
				CallableStatement call = conn.prepareCall("{Call stat_sales_by_pay_type()}");  
				call.execute();
				return null;
			}
		});
		return statList;
	}
	
	public JSONObject queryByDay(String shopId, String statDate) {
		try {
			// 封装条件
			RptSalesByPayTypeCondition condition = new RptSalesByPayTypeCondition();
			condition.setShopId(shopId);
			if (StringUtils.isNotBlank(statDate)) {
				condition.setStatDate(DateUtil.StringToDate(statDate, DateStyle.YYYY_MM_DD));
			}

			// 开始查询
			List<RptSalesByPayType> list = list(condition);

			// 统一化查询结果
			JSONArray itemJa = new JSONArray();
			for (RptSalesByPayType rptSalesByPayType : list) {
				JSONObject itemJo = new JSONObject();
				itemJo.put("name", rptSalesByPayType.getPayTypeName());
				itemJo.put("money", rptSalesByPayType.getMoney());
				itemJa.add(itemJo);
			}
			JSONObject resultJo = new JSONObject();
			resultJo.put("name", "按支付类型分");
			resultJo.put("items", itemJa);
			return resultJo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
