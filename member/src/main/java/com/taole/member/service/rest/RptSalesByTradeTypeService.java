package com.taole.member.service.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.member.Constants.DimensName;
import com.taole.member.Constants.TransactionType;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.RptSalesByTradeTypeCondition;
import com.taole.member.domain.RptSalesByTradeType;
import com.taole.member.domain.bo.SalesStatResult;
import com.taole.member.manager.RptSalesByTradeTypeManager;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.PageUtil;

@RestService(name = ProjectConfig.PREFIX + "rptSalesByGoods")
public class RptSalesByTradeTypeService {
	@Autowired
	private RptSalesByTradeTypeManager rptSalesByTradeTypeManager;
	@Autowired
	private DictionaryManager dictionaryManager;
	/**
	 * 店面商品销售额日报表
	 */
	@ActionMethod(response = "json")
	public Object queryByDay(HttpServletRequest request, HttpServletResponse response, Range range, Sorter sorter) {
		try {
			// 获取请求参数
			String shopId = request.getParameter("shopId");
			String billType = request.getParameter("billType");
			String statDate = request.getParameter("statDate");// yyyy-MM-dd
			String page = request.getParameter("page");
			String length = request.getParameter("length");
			String dimens = request.getParameter("dimens");	
			// 封装分页
			if (range == null) {
				range = PageUtil.getRangeAByReq(page, length);
			}

			// 封装排序
			sorter = new Sorter().desc("statDate");

			// 封装条件
			RptSalesByTradeTypeCondition condition = new RptSalesByTradeTypeCondition();
			condition.setBillType(billType);
			condition.setShopId(shopId);
			if (StringUtils.isNotBlank(statDate)) {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					condition.setStatDate(formatter.parse(statDate));
				} catch (Exception e) {
					return new ServiceResult(ReturnResult.FAILURE, "日报表统计日期参数错误：" + e.getMessage());
				}
			}

			// 开始查询
			PaginationSupport<RptSalesByTradeType> ps = rptSalesByTradeTypeManager.search(condition, range, sorter);
			List<RptSalesByTradeType> list = ps.getItems();

			// 统一化查询结果
			JSONArray itemJa = new JSONArray();
			for (RptSalesByTradeType rptSalesByTradeTypes : list) {
				SalesStatResult salesStatResult = new SalesStatResult();
				salesStatResult.setShopId(rptSalesByTradeTypes.getShopId());
				salesStatResult.setShopName(rptSalesByTradeTypes.getShopName());
				salesStatResult.setObjectId(rptSalesByTradeTypes.getBillType());
				salesStatResult.setObjectName(rptSalesByTradeTypes.getBillTypeName());
				salesStatResult.setStatMoney(rptSalesByTradeTypes.getMoney());
				salesStatResult.setStatAmount(rptSalesByTradeTypes.getAmount());

				JSONObject itemJo = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(salesStatResult);
				//整理返回的统计日期格式
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				itemJo.put("statDate", formatter.format(rptSalesByTradeTypes.getStatDate()));
				itemJo.put("dimensName", dictionaryManager.getDictName(DimensName.DICTIONARY_DIMENS_NAME_CODE_ID, dimens));
				itemJa.add(itemJo);
			}
			JSONObject resultJo = new JSONObject();
			resultJo.put("total", rptSalesByTradeTypeManager.count(condition));
			resultJo.put("items", itemJa);
			return resultJo;
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, e.getMessage());
		}
	}

	/**
	 * 店面商品销售额月报表
	 */
	@ActionMethod(response = "json")
	public Object queryByMonth(HttpServletRequest request, HttpServletResponse response, Range range, Sorter sorter) {
		try {
			// 获取请求参数
			String shopId = request.getParameter("shopId");
			String billType = request.getParameter("billType");
			String statDate = request.getParameter("statDate");// yyyy-MM
			String page = request.getParameter("page");
			String length = request.getParameter("length");
			String dimens = request.getParameter("dimens");	
			// 封装分页
			if (range == null) {
				range = PageUtil.getRangeAByReq(page, length);
			}

			// 封装排序
			sorter = new Sorter().desc("statDate");

			// 封装条件
			//RptSalesByGoodsCondition condition = new RptSalesByGoodsCondition();
			RptSalesByTradeTypeCondition condition = new RptSalesByTradeTypeCondition();
			condition.setBillType(billType);
			condition.setShopId(shopId);
			if (StringUtils.isNotBlank(statDate)) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
				condition.setStatDate(formatter.parse(statDate));
			} else {
				return new ServiceResult(ReturnResult.FAILURE, "请求参数统计月份不能为空！");
			}

			// 开始查询
			List<SalesStatResult> list = rptSalesByTradeTypeManager.queryByMonth(condition, range.getStart(),
					range.getLimit());

			// 整理查询结果
			JSONArray itemJa = new JSONArray();
			for (SalesStatResult salesStatResult : list) {
				Date statDatePm = salesStatResult.getStatDate();
				JSONObject itemJo = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(salesStatResult);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
				itemJo.put("statDate", formatter.format(statDatePm));
				itemJo.put("dimensName", dictionaryManager.getDictName(DimensName.DICTIONARY_DIMENS_NAME_CODE_ID, dimens));
				itemJo.put("billTypeName", dictionaryManager.getDictName(TransactionType.DICTIONARY_TRANSACTION_TYPE_CODE_ID, billType));
				itemJa.add(itemJo);
			}
			JSONObject resultJo = new JSONObject();
			resultJo.put("total", rptSalesByTradeTypeManager.queryByMonthCount(condition));
			resultJo.put("name", "按交易类型分");
			resultJo.put("items", itemJa);
			//return resultJo;
			return new ServiceResult(ReturnResult.SUCCESS, resultJo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, e.getMessage());
		}
	}

	/**
	 * 统计测试方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ActionMethod(response = "json")
	public Object stat(HttpServletRequest request, HttpServletResponse response) {
		try {
			rptSalesByTradeTypeManager.statSalesByBillType(null, 0, 10);
			return new ServiceResult(ReturnResult.SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult(ReturnResult.FAILURE, e.getMessage());
		}
	}
}
