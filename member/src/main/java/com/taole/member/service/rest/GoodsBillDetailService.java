package com.taole.member.service.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.framework.annotation.ReturnCodeInfo;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.UUID;
import com.taole.member.Constants.InOutType;
import com.taole.member.Constants.ShopBillType;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.domain.GoodsBill;
import com.taole.member.domain.GoodsBillDetail;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.param.GoodsBillDetail.GenerateShopBillCode;
import com.taole.member.domain.param.GoodsBillDetail.InStore;
import com.taole.member.domain.param.GoodsBillDetail.InStore.GoodsValue;
import com.taole.member.domain.param.GoodsBillDetail.OutStore;
import com.taole.member.domain.param.GoodsBillDetail.Query;
import com.taole.member.manager.GoodsBillDetailManager;
import com.taole.member.manager.GoodsBillManager;
import com.taole.member.manager.GoodsInfoManager;
import com.taole.member.manager.ShopInfoManager;
import com.taole.toolkit.util.ArrayListUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "出入库明细后台管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "GoodsBillDetail")
@Controller
@RestService(name = ProjectConfig.PREFIX + "GoodsBillDetail")
public class GoodsBillDetailService {
	private Logger logger = LoggerFactory.getLogger(GoodsBillDetailService.class);

	private final static String RETURN_CODE_URL = ProjectConfig.RETURN_CODE_PATH + "GoodsBillDetail_";

	@Autowired
	private GoodsBillDetailManager goodsBillDetailManager;
	@Autowired
	private GoodsBillManager goodsBillManager;
	@Autowired
	private GoodsInfoManager goodsInfoManager;
	@Autowired
	private ShopInfoManager shopInfoManager;

	/**
	 * 查询商品信息列表
	 * 
	 * @param request
	 * @param response
	 * @param start
	 * @param limit
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping(value = "/collection/query", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询出入库明细列表", httpMethod = "GET", response = Query.GoodsBillDetailServiceQueryResp.class, notes = "查询出入库明细列表")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	@ReturnCodeInfo(value = "query")
	public @ResponseBody Object query(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query.GoodsBillDetailServiceQueryReq queryCondition) {
		try {
			Range range = new Range(start, limit);

			GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
			if(null != queryCondition.getShopId()){
				condition.setShopId(queryCondition.getShopId());
			}
			if(null != queryCondition.getCatalogId()){
				condition.setCatalogId(queryCondition.getCatalogId());
			}
			if(null != queryCondition.getName()){
				condition.setGoodsName(queryCondition.getName());
			}
			if(null != queryCondition.getGoodsId()){
				condition.setGoodsId(queryCondition.getGoodsId());
			}
			JSONArray jsonArray = (JSONArray) goodsBillDetailManager.searchToJson(condition, start, limit);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("total", goodsBillDetailManager.searchGoodsDetailCount(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "查询出入库明细列表成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "查询出入库明细列表失败: " + e.getMessage());
		}
	}

	/**
	 * 新增商品编码
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/generateShopBillCode", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "生成入库单据编号", httpMethod = "GET", response = GenerateShopBillCode.GoodsBillDetailCreateGoodsCodeResp.class, notes = "生成入库单据编号")
	@ApiResponses(value = {
	@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "generateShopBillCode.html") })
	@ReturnCodeInfo(value = "generateShopBillCode")
	public @ResponseBody Object generateShopBillCode(HttpServletRequest request) {
		try {
			//String code = IdUtils.generateId();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
			String code = formatter.format(date);
			code = "RK"+code;
			/*GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
			condition.setShopBillId(code);
			GoodsBillDetail goodsBillDetail = goodsBillDetailManager.findByCondition(condition);
			if(goodsBillDetail != null){
				return new ServiceResult(ReturnResult.FAILURE, "单据编号已存在！", code);
			}*/
			return new ServiceResult(ReturnResult.SUCCESS, "生成单据编号成功", code);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "GoodsInfoService" + ".generateGoodsCode:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "生成单据编号失败: " + e.getMessage());
		}
	}

	/**
	 * 新增商品编码
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/generateShopBillOutCode", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "生成出库单据编号", httpMethod = "GET", response = GenerateShopBillCode.GoodsBillDetailCreateGoodsCodeResp.class, notes = "生成出库单据编号")
	@ApiResponses(value = {
			@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "generateShopBillCode.html") })
	@ReturnCodeInfo(value = "generateShopBillOutCode")
	public @ResponseBody Object generateShopBillOutCode(HttpServletRequest request) {
		try {
			//String code = IdUtils.generateId();
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
			String code = formatter.format(date);
			code = "CK"+code;
			/*GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
			condition.setShopBillId(code);
			GoodsBillDetail goodsBillDetail = goodsBillDetailManager.findByCondition(condition);
			if(goodsBillDetail != null){
				return new ServiceResult(ReturnResult.FAILURE, "单据编号已存在！", code);
			}*/
			return new ServiceResult(ReturnResult.SUCCESS, "生成单据编号成功", code);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "GoodsInfoService" + ".generateGoodsCode:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "生成单据编号失败: " + e.getMessage());
		}
	}

	/**
	 * 商品入库
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/inStore", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "商品入库", httpMethod = "POST", response = InStore.GoodsBillDetailServiceStoreResp.class, notes = "商品入库")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "inStore.html") })
	@ReturnCodeInfo(value = "inStore")
	public @ResponseBody Object inStore(HttpServletRequest request,
			@RequestBody InStore.GoodsBillDetailServiceInStoreReq req) {
		try {
			String shopBillId = UUID.generateUUID();
			
			GoodsBill goodsBill = new GoodsBill();
			goodsBill.setShopBillId(shopBillId);
			goodsBill.setShopBillNo(req.getShopBillNo());
			goodsBill.setOperator(req.getOperator());
			goodsBill.setShopId(req.getShopId());
			goodsBill.setShopBillType(ShopBillType.INSTORE);
			goodsBill.setInOutType(InOutType.IN);
			goodsBill.setOperatorTime(new Date());
			goodsBill.setDescription(req.getDescription());
			goodsBill.setStatus("");
			goodsBill.setCreateTime(new Date());
			goodsBillManager.createGoodsBill(goodsBill);
			
			
			List<GoodsValue> goodsValues = req.getGoodsValues();
			if (!ArrayListUtil.isEmpty(goodsValues)) {
				for (GoodsValue goodsValue : goodsValues) {
					GoodsBillDetail goodsBillDetail = new GoodsBillDetail();
					goodsBillDetail.setShopId(req.getShopId());
					GoodsInfo goodsInfo = new GoodsInfo();
					goodsInfo = goodsInfoManager.getGoodsInfo(goodsValue.getGoodsId());
					goodsBillDetail.setPrice(goodsInfo.getSaleMoney());
					goodsBillDetail.setShopBillId(shopBillId);

					goodsBillDetail.setGoodsId(goodsValue.getGoodsId());
					goodsBillDetail.setAmount(goodsValue.getAmount());
					
					goodsBillDetail.setUnit(goodsInfo.getUnit());
					GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
					condition.setShopId(req.getShopId());
					condition.setGoodsId(goodsValue.getGoodsId());
					List<GoodsBillDetail> list = goodsBillDetailManager.list(condition, new Sorter().desc("createTime"),1);
					/*if (!ArrayListUtil.isEmpty(list)) {
						GoodsBillDetail goodsBillDetail2 = list.get(0);
						goodsBillDetail.setBalance(goodsBillDetail2.getBalance() + goodsValue.getAmount());
					}*/
					if (!ArrayListUtil.isEmpty(list)) {
						GoodsBillDetail goodsBillDetail2 = list.get(0);
							goodsBillDetail.setBalance(goodsBillDetail2.getBalance() + goodsValue.getAmount());
					}else {
						goodsBillDetail.setBalance(goodsValue.getAmount());
						}
					goodsBillDetailManager.createGoodsBillDetail(goodsBillDetail);
				}
			}


			

			return new ServiceResult(ReturnResult.SUCCESS, "商品入库成功");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "商品入库失败: " + e.getMessage());
		}

	}

	/**
	 * 商品出库
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/outStore", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "商品出库", httpMethod = "POST", response = OutStore.GoodsBillDetailServiceOutStoreResp.class, notes = "商品出库")
	@ApiResponses(value = {
			@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "createGoodsCode.html") })
	@ReturnCodeInfo(value = "generateGoodsCode")
	public @ResponseBody Object outStore(HttpServletRequest request,
			@RequestBody OutStore.GoodsBillDetailServiceOutStoreReq req) {
		try {
			String shopBillId = UUID.generateUUID();
			
			GoodsBill goodsBill = new GoodsBill();
			goodsBill.setShopBillId(shopBillId);
			goodsBill.setShopBillNo(req.getShopBillNo());
			goodsBill.setOperator(req.getOperator());
			goodsBill.setShopId(req.getShopId());
			goodsBill.setShopBillType(ShopBillType.OUTSTORE);
			goodsBill.setInOutType(InOutType.OUT);
			goodsBill.setOperatorTime(new Date());
			goodsBill.setDescription(req.getDescription());
			goodsBill.setStatus("");
			goodsBill.setCreateTime(new Date());
			goodsBillManager.createGoodsBill(goodsBill);
			
			
			List<com.taole.member.domain.param.GoodsBillDetail.OutStore.GoodsValue> goodsValues = req.getGoodsValues();
			if (!ArrayListUtil.isEmpty(goodsValues)) {
				for (com.taole.member.domain.param.GoodsBillDetail.OutStore.GoodsValue goodsValue : goodsValues) {
					GoodsBillDetail goodsBillDetail = new GoodsBillDetail();
					goodsBillDetail.setShopId(req.getShopId());
					GoodsInfo goodsInfo = new GoodsInfo();
					goodsInfo = goodsInfoManager.getGoodsInfo(goodsValue.getGoodsId());
					goodsBillDetail.setPrice(goodsInfo.getSaleMoney());
					goodsBillDetail.setUnit(goodsInfo.getUnit());
					goodsBillDetail.setShopBillId(shopBillId);

					goodsBillDetail.setGoodsId(goodsValue.getGoodsId());
					goodsBillDetail.setAmount(goodsValue.getAmount());
					goodsBillDetail.setGoodsId(goodsValue.getGoodsId());
					GoodsBillDetailCondition condition = new GoodsBillDetailCondition();
					condition.setShopId(req.getShopId());
					condition.setGoodsId(goodsValue.getGoodsId());
					List<GoodsBillDetail> list = goodsBillDetailManager.list(condition, new Sorter().desc("createTime"),1);
					/*if (!ArrayListUtil.isEmpty(list)) {
						GoodsBillDetail goodsBillDetail2 = list.get(0);
							goodsBillDetail.setBalance(goodsBillDetail2.getBalance() + goodsValue.getAmount());
					}else {
						goodsBillDetail.setBalance(goodsValue.getAmount());
						}*/
					if (!ArrayListUtil.isEmpty(list)) {
						GoodsBillDetail goodsBillDetail2 = list.get(0);
						if(goodsBillDetail2.getBalance() == null){
							goodsBillDetail2.setBalance(0.00);
						}
						goodsBillDetail.setBalance(goodsBillDetail2.getBalance() - goodsValue.getAmount());
					}	
					goodsBillDetailManager.createGoodsBillDetail(goodsBillDetail);
				}
				}

			
			return new ServiceResult(ReturnResult.SUCCESS, "商品出库成功");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "商品出库失败: " + e.getMessage());
		}

	}
	
	
}
