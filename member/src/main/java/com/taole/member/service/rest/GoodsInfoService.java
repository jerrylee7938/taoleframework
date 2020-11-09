package com.taole.member.service.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.GoodsBillDetailCondition;
import com.taole.member.condition.GoodsInfoCondition;
import com.taole.member.condition.GoodsRefTypeCondition;
import com.taole.member.domain.GoodsBillDetail;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.param.GoodsInfoService.Create;
import com.taole.member.domain.param.GoodsInfoService.GenerateGoodsCode;
import com.taole.member.domain.param.GoodsInfoService.Delete;
import com.taole.member.domain.param.GoodsInfoService.Query;
import com.taole.member.domain.param.GoodsInfoService.QueryForSale;
import com.taole.member.domain.param.GoodsInfoService.Update;
import com.taole.member.manager.GoodsBillDetailManager;
import com.taole.member.manager.GoodsInfoManager;
import com.taole.member.manager.GoodsRefTypeManager;
import com.taole.toolkit.util.DateUtil;
import com.taole.toolkit.util.IdUtils;
import com.taole.toolkit.util.biz.ConvertUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "商品信息后台管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "GoodsInfo")
@Controller
@RestService(name = ProjectConfig.PREFIX + "GoodsInfo")
public class GoodsInfoService {
	private Logger logger = LoggerFactory.getLogger(GoodsInfoService.class);

	private final static String RETURN_CODE_URL = ProjectConfig.RETURN_CODE_PATH + "GoodsInfo_";

	@Autowired
	private GoodsInfoManager goodsInfoManager;
	
	@Autowired
	private GoodsBillDetailManager goodsBillDetailManager;
	
	@Autowired
	private GoodsRefTypeManager goodsRefTypeManager;
	/**
	 * 新增商品信息
	 * w
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/create", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "新增商品信息", httpMethod = "POST", response = Create.GoodsInfoServiceCreateResp.class, notes = "新增商品信息")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "create.html") })
	@ReturnCodeInfo(value = "create")
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public @ResponseBody Object create(HttpServletRequest request,
			@RequestBody Create.GoodsInfoServiceCreateReq createReq) {
		try {
			GoodsInfo goodsInfo = new GoodsInfo();
			if(createReq.getName() == null){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "商品名称不能为空！");
			}
			GoodsInfoCondition condition = new GoodsInfoCondition();
			condition.setName(createReq.getName());
			GoodsInfo goodsInfo2 = goodsInfoManager.findByCondition(condition);
			if(goodsInfo2 != null){
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "商品名称不能重复！");
			}
			//ConvertUtil.BeanToBean(createReq, goodsInfo);
			goodsInfo.setGoodsCode(createReq.getGoodsCode());
			goodsInfo.setName(createReq.getName());
			goodsInfo.setCatalogId(createReq.getCatalogId());
			goodsInfo.setOriginalCost(createReq.getOriginalCost());
			goodsInfo.setSaleMoney(createReq.getSaleMoney());
			goodsInfo.setUnit(createReq.getUnit());
			String deadLine = createReq.getDeadline();
			String beginDate = createReq.getBeginDate();
			String endDate = createReq.getEndDate();
			goodsInfo.setDeadline(DateUtil.StringToDate(deadLine));
			goodsInfo.setBeginDate(DateUtil.StringToDate(beginDate));
			goodsInfo.setEndDate(DateUtil.StringToDate(endDate));
			goodsInfo.setDescription(createReq.getDescription());
			goodsInfo.setStatus(createReq.getStatus());
			goodsInfo.setImage(createReq.getImage());
			goodsInfo.setImageBack(createReq.getImageBack());
			goodsInfo.setOnlineBuy(createReq.getOnlineBuy());
			goodsInfo.setMaxnumber(createReq.getMaxnumber());
			
			

			/*int code = goodsInfoManager.getGoodsCode();
			int newCode= code + 1;
			goodsInfo.setGoodsCode(newCode);*/
			
			String goodsId = goodsInfoManager.createGoodsInfo(goodsInfo);
			JSONObject jsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsInfo);
			jsonObject.put("goodsId", goodsId);
			
			//添加首页分类功能
			goodsRefTypeManager.createGoodsRefTypes(createReq.getHomeTypes(), goodsId);
			
			return new ServiceResult(ReturnResult.SUCCESS, "新增商品信息成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "CardInfoService" + ".create:{}", e);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 删除商品基本信息
	 * 
	 * @param response
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/collection/delete", method = RequestMethod.DELETE, produces = "application/json;")
	@ApiOperation(value = "删除商品基本信息", httpMethod = "DELETE", response =Delete.GoodsInfoServiceDeleteResp .class, notes = "删除商品基本信息")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "delete.html") })
	@ReturnCodeInfo(value = "delete")
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public @ResponseBody Object delete(HttpServletResponse response,
			@ApiParam(value = "商品ID，多个以“,”分隔", name = "ids", required = true) @RequestParam String ids) {
		try {
			if (StringUtils.isEmpty(ids)) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "商品Id不能为空");
			}

			GoodsBillDetailCondition goodsBillDetailCondition  = new GoodsBillDetailCondition();
			goodsBillDetailCondition.setGoodsId(ids);
			GoodsBillDetail goodsBillDetail = goodsBillDetailManager.findByCondition(goodsBillDetailCondition);

			if(goodsBillDetail != null) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "该商品已被使用无法删除！");
			}
			String[] goodsIds = ids.split(",");
			for (String goodsId : goodsIds) {
				
				GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(goodsId);
				if (goodsInfo != null) {
					goodsRefTypeManager.deleteGoodsRefType(goodsInfo.getGoodsId());
					
					goodsInfoManager.deleteGoodsInfo(goodsInfo);
				}
			}
			return new ServiceResult(ReturnResult.SUCCESS, "删除商品信息成功");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "GoodsInfoService" + ".delete:{}", e);
			throw new RuntimeException("删除商品信息失败: " + e.getMessage());
		}
	}
	
	/**
	 * 修改商品信息
	 * 
	 * @param request
	 * @param categoryIds
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "修改商品信息", httpMethod = "POST", response = Update.GoodsInfoServiceUpdateResp.class, notes = "修改商品信息")
	@ApiResponses(value = {@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "updateCategory.html") })
	@ReturnCodeInfo(value = "update")
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public @ResponseBody Object update(HttpServletRequest request,
			@RequestBody Update.GoodsInfoServiceUpdateReq updateReq,
			@ApiParam(required = true, name = "id", value = "商品id") @PathVariable String id) {
		try {
			GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(id);
			if(goodsInfo == null)
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "商品信息不存在！");
			
			if (StringUtils.isEmpty(id)) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "商品id不能为空！");
			}
			ConvertUtil.BeanToBean(updateReq, goodsInfo);
//			BeanUpdater.updateObject(shopInfo, updateReq);
			goodsInfoManager.updateGoodsInfo(goodsInfo);
			
			//修改首页类型逻辑先删除所有再重新增加
			goodsRefTypeManager.deleteGoodsRefType(id);
			goodsRefTypeManager.createGoodsRefTypes(updateReq.getHomeTypes(), id);
			
			JSONObject jsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsInfo);
			return new ServiceResult(ReturnResult.SUCCESS, "修改商品信息成功",jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".update:{}", e);
			throw new RuntimeException("修改商品信息失败: " + e.getMessage());
		}
	}
	
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
	@ApiOperation(value = "查询商品信息列表", httpMethod = "GET", response = Query.GoodsInfoServiceQueryResp.class, notes = "查询商品信息列表")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	@ReturnCodeInfo(value = "query")
	public @ResponseBody Object query(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query.GoodsInfoServiceQueryReq queryCondition) {
		try {
			Range range = new Range(start, limit);

			GoodsInfoCondition condition = new GoodsInfoCondition();
			if(null != queryCondition.getName()){
				condition.setName(queryCondition.getName());
			}
			if(null != queryCondition.getCatalogId()){
				condition.setCatalogId(queryCondition.getCatalogId());;
			}
			if(null != queryCondition.getGoodsId()){
				condition.setGoodsId(queryCondition.getGoodsId());
			}
			
			JSONArray jsonArray = (JSONArray) goodsInfoManager.searchToJson(condition, range, new Sorter().desc("createTime"));

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("total", goodsInfoManager.count(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "查询商品信息列表成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "查询商品信息列表失败: " + e.getMessage());
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/{id}/retrieve", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "获取商品信息详情", httpMethod = "GET", response = Query.GoodsInfoServiceQueryResp.class, notes = "获取商品信息详情")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "retrieve.html") })
	@ReturnCodeInfo(value = "Retrieve")
	public Object retrieve(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, name = "id", value = "卡id") @PathVariable String id) {

		try {
			GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(id);
			if (null == goodsInfo) {
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "没有商品信息！");
			}
			JSONObject object = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(goodsInfo);
			object.put("homeTypes", goodsRefTypeManager.queryForGoods(id));
			
			return new ServiceResult(ReturnResult.SUCCESS, "获取商品信息成功", object);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "CardInfoService" + ".retrieve:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "获取商品信息失败", e.getMessage());
		}
	}
	
	/**
	 * 新增商品编码
	 * 
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/generateGoodsCode", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "生成商品编码", httpMethod = "GET", response = GenerateGoodsCode.GoodsInfoServiceCreateGoodsCodeResp.class, notes = "生成商品编码")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "createGoodsCode.html") })
	@ReturnCodeInfo(value = "generateGoodsCode")
	public @ResponseBody Object generateGoodsCode(HttpServletRequest request
			){
		try {
			String code = IdUtils.generateId();
			return new ServiceResult(ReturnResult.SUCCESS, "生成商品编码成功",code);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "GoodsInfoService" + ".generateGoodsCode:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "生成商品编码失败: " + e.getMessage());
		}
	}
}
