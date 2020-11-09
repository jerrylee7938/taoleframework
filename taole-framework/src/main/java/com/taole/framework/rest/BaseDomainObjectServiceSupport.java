/**
 * Project:HEAFramework 3.0
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.manager.BeanFieldWirer;
import com.taole.framework.manager.DomainCRUDWrapper;
import com.taole.framework.manager.DomainEngineFactory;
import com.taole.framework.rest.bind.annotation.ParameterObject;
import com.taole.framework.rest.bind.annotation.ResourceVariable;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.BeanUpdater;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.DomainObjectUtils;
import com.taole.framework.util.RestHandlerHelper;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since May 20, 2011 6:50:53 PM
 * @version $Id: BaseDomainObjectServiceSupport.java 5 2014-01-15 13:55:28Z yedf $
 */
public abstract class BaseDomainObjectServiceSupport<T extends DomainObject> {

	private Class<T> entityClass;

	@Autowired(required = false)
	private Validator validator;

	@Autowired
	private Configurator configurator;

	@Autowired
	protected DomainEngineFactory domainEngineFactory;

	@Autowired(required = false)
	protected BeanFieldWirer beanFieldWirer;

	@SuppressWarnings("unchecked")
	public Class<T> getEntityType() {
		if (entityClass == null) {
			ParameterizedType pmType = (ParameterizedType) getClass().getGenericSuperclass();
			entityClass = (Class<T>) pmType.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	@ActionMethod(response = "json")
	public Object meta() {
		return DataModel.getDataModel(getEntityType()).toJSONObject();
	}

	protected JSONObject extrameta(String name, String type) {
		JSONObject meta = DataModel.getDataModel(getEntityType()).toJSONObject();
		try {
			meta.getJSONArray("fields").put(new JSONObject().put("name", name).put("type", type));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return meta;
	}

	protected JSONObject extrametas(String... fields) {
		JSONObject meta = DataModel.getDataModel(getEntityType()).toJSONObject();
		for (String field : fields) {
			try {
				meta.getJSONArray("fields").put(new JSONObject().put("name", field).put("type", "string"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return meta;
	}

	protected ModelAndView getModelAndView(String entityName, String action) {
		String view = configurator.getViewName(entityName, action);
		return new ModelAndView(view);
	}

	protected ModelAndView getModelAndView(Object entity, String action) {
		return getModelAndView(BeanUtils.getClass(entity), action);
	}

	protected ModelAndView getModelAndView(Class<?> entityType, String action) {
		return getModelAndView(DomainObjectUtils.getEntityName(entityType), action);
	}

	protected ModelAndView getModelAndView() {
		return getModelAndView(DomainObjectUtils.getEntityName(getEntityType()), RestSessionFactory.getCurrentContext().getAction());
	}

	/**
	 * 验证对象
	 * 
	 * @param o
	 * @param restContext
	 * @return
	 */
	protected Map<String, String> validate(Object o, RestContext restContext) {
		String objectName = StringUtils.uncapitalize(o.getClass().getSimpleName());
		MapBindingResult mapBindingResult = new MapBindingResult(Maps.newHashMap(), objectName);
		validator.validate(o, mapBindingResult);
		return RestHandlerHelper.getErrorMessage(restContext.getHttpServletRequest(), mapBindingResult.getAllErrors(), o.getClass());
	}

	@ActionMethod(response = "view")
	public Object view(@ResourceVariable String id) {
		DomainCRUDWrapper<T> wrapper = domainEngineFactory.getDomainCRUDWrapper(getEntityType());
		T entity = wrapper.get(id);
		ModelAndView mv = getModelAndView();
		mv.addObject("entity", entity);
		return mv;
	}

	@ActionMethod(response = "view")
	public Object edit(@ResourceVariable String id) {
		DomainCRUDWrapper<T> wrapper = domainEngineFactory.getDomainCRUDWrapper(getEntityType());
		T entity = wrapper.get(id);
		ModelAndView mv = getModelAndView();
		mv.addObject("entity", entity);
		return mv;
	}

	@ActionMethod(response = "json")
	public Object retrieve(@ResourceVariable String id) {
		DomainCRUDWrapper<T> wrapper = domainEngineFactory.getDomainCRUDWrapper(getEntityType());
		return wrapper.get(id);
	}

	@ActionMethod(request = "json", response = "json")
	public Object update(@ResourceVariable String id, @ParameterObject JSONObject updateObj) {
		DomainCRUDWrapper<T> wrapper = domainEngineFactory.getDomainCRUDWrapper(getEntityType());
		T entity = wrapper.get(id);
		if (entity == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		BeanUpdater.updateObject(entity, updateObj);
		wrapper.update(entity);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	@ActionMethod(response = "json")
	public Object delete(@ResourceVariable String id) {
		DomainCRUDWrapper<T> wrapper = domainEngineFactory.getDomainCRUDWrapper(getEntityType());
		T entity = wrapper.get(id);
		if (entity == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		wrapper.delete(entity);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	@ActionMethod(request = "pojo", response = "json")
	public Object create(@ParameterObject T entity) {
		DomainCRUDWrapper<T> wrapper = domainEngineFactory.getDomainCRUDWrapper(getEntityType());
		wrapper.create(entity);
		return new ServiceResult(ReturnResult.SUCCESS, DomainObjectUtils.getPrimaryKeyValue(entity));
	}

	@ActionMethod(response = "json")
	public Object compose(@ResourceVariable String id) {
		DomainCRUDWrapper<T> wrapper = domainEngineFactory.getDomainCRUDWrapper(getEntityType());
		T entity = wrapper.get(id);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = new HashMap<String, Object>(BeanMap.create(entity));
		if (beanFieldWirer != null) {
			map.put("$parent", beanFieldWirer.getParentObject(entity));
			map.put("$displays", beanFieldWirer.getDisplayMap(entity));
			map.put("$relations", beanFieldWirer.getRelationMap(entity));
		}
		return map;
	}
}
