/**
 * Project:member
 * File:memberCardManager.java 
 * Copyright 2014-2016 Bei Jing Yi Sheng Jia Technology Development Co., Ltd. All rights reserved.
 */
package com.taole.member.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.taole.framework.annotation.DomainEngine;
import com.taole.framework.dao.DomainObjectDao;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.events.EventMethod;
import com.taole.framework.util.UUID;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.OrderServiceCondition;
import com.taole.member.domain.OrderService;

/**
 * @author  Generator
 * @version $Id$
 */
@DomainEngine(types = OrderService.class)
@Transactional(readOnly = true)
public class OrderServiceManager {
	
	@Resource(name = ProjectConfig.PREFIX + "orderServiceDao")
	DomainObjectDao<OrderService> orderServiceDao;
	
	@DomainEngine.C
	@EventMethod
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String createOrderService(OrderService orderService) {
		if(StringUtils.isBlank(orderService.getId()))
			orderService.setId(UUID.generateUUID());
		
		orderService.setCreateTime(new Date());
		return orderServiceDao.createObject(orderService);
	}

	@DomainEngine.U
	@EventMethod
	@Transactional
	public void updateOrderService(OrderService orderService) {
		orderServiceDao.updateObject(orderService);
	}
	
	@DomainEngine.D
	@EventMethod
	@Transactional
	public void deleteOrderService(OrderService orderService) {
		orderServiceDao.deleteObject(orderService);
	}
	
	@DomainEngine.R
	public OrderService getOrderService(String id) {
		return orderServiceDao.loadObject(id);
	}
	
	public List<OrderService> list(OrderServiceCondition condition) {
		return orderServiceDao.listByCondition(condition);
	}
	public List<OrderService> list(OrderServiceCondition condition, Sorter sorter, int limit) {
		return orderServiceDao.listByCondition(condition, sorter, limit);
	}
	
	public List<OrderService> list(String orderId){
		OrderServiceCondition condition = new OrderServiceCondition();
		condition.setOrderId(orderId);
		return list(condition);
	}
	
	public PaginationSupport<OrderService> search(OrderServiceCondition condition, Range range, Sorter sroter) {
		return orderServiceDao.search(condition, range, sroter);
	}
	
	public int count(OrderServiceCondition condition){
		return orderServiceDao.countByCondition(condition);
	}
	
	public OrderService findByCondition(OrderServiceCondition condition) {
		List<OrderService> list = orderServiceDao.listByCondition(condition, 1);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
