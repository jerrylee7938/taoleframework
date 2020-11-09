package com.taole.member.service.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.member.Constants.OrderState;
import com.taole.member.ProjectConfig;
import com.taole.member.domain.Order;
import com.taole.member.manager.OrderManager;
import com.taole.member.utils.RestClientUtil;

@Controller
@Api(tags = { "订单接口管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "OrderApi")
public class OrderApi {
	
	private Logger logger = LoggerFactory.getLogger(OrderApi.class);
	
	@Autowired
	private OrderManager orderManager;

	@RequestMapping(value = "/collection/payResult", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "订单支付结果查询", httpMethod = "GET", response = ServiceResult.class)
	public @ResponseBody Object payResult(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "订单ID", required = true) @RequestParam String orderId){
		
		try {
			if(StringUtils.isBlank(orderId))
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "订单ID不能为空！");
			
			Order order = orderManager.getOrder(orderId);
			if(order == null)
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "订单信息不存在！");
			
			if(order.getState() == Integer.valueOf(OrderState.PAY_SUCCESS))
				return new ServiceResult(ReturnResult.SUCCESS, "订单支付成功！");
			
			String channelPayState = RestClientUtil.payResult(orderId);
			if("SUCCESS".equals(channelPayState)){
				return new ServiceResult(ReturnResult.SUCCESS, "订单支付成功！"); 
			}else {
				return new ServiceResult(ReturnResult.FAILURE, channelPayState);
			}
			
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "OrderApi" + ".payResult:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "查询失败: "+ e.getMessage());
		}
	}
}
