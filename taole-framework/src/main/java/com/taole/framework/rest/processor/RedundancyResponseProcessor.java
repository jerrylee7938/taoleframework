package com.taole.framework.rest.processor;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.taole.framework.bean.DomainObject;
import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.manager.BeanFieldWirer;
import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.JSONUtils;

@Component
public class RedundancyResponseProcessor extends AbstractResponseProcessor {

	@Autowired
	private BeanFieldWirer beanFieldWirer;

	@Override
	public String getName() {
		return "redundancy";
	}

	public Object process(Object input) {
		HttpHeaders headers = new HttpHeaders();
		RestContext context = RestSessionFactory.getCurrentContext();
		if (context.getAttribute("_contentType") != null && context.getAttribute("_contentType") instanceof MediaType) {
			headers.setContentType((MediaType) context.getAttribute("_contentType"));
		} else {
			headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
		}
		Boolean withParent = context.getAttribute("withParent", Boolean.class);
		Boolean withRelations = context.getAttribute("withRelations", Boolean.class);
		String jsonStr = "";
		try {
			if (input instanceof JSONObject) {
				jsonStr = ((JSONObject) input).toString();

			} else if (input instanceof JSONArray) {
				jsonStr = ((JSONArray) input).toString();
			} else {
				if (List.class.isInstance(input)) {
					JSONArray array = new JSONArray();
					for (Object obj : (List<?>) input) {
						transform2Json(withParent, withRelations, array, obj);
					}
					jsonStr = JSONUtils.convertJava2JSON(array);
				} else if (PaginationSupport.class.isInstance(input)) {
					PaginationSupport<?> ps = (PaginationSupport<?>) input;
					List<?> items = ps.getItems();
					ps.setItems(null);
					JSONObject psJson = (JSONObject) JSONTransformer.transformPojo2Jso(input);
					JSONArray array = new JSONArray();
					for (Object obj : items) {
						transform2Json(withParent, withRelations, array, obj);
					}
					psJson.put("items", array);
					jsonStr = JSONUtils.convertJava2JSON(psJson);
				} else {
					Object json = JSONTransformer.transformPojo2Jso(input);
					if (DomainObject.class.isInstance(input) && JSONObject.class.isInstance(json)) {
						((JSONObject) json).put("$displays", new JSONObject(beanFieldWirer.getDisplayMap(input)));
					}
					jsonStr = JSONUtils.convertJava2JSON(json);
				}
			}
		} catch (JSONException e) {
			jsonStr = e.toString();
		}
		String jsonp = context.getHttpServletRequest().getParameter("jsonp");
		if (StringUtils.isNotBlank(jsonp)) {
			jsonStr = jsonp + "(" + jsonStr + ");";
		}
		try {
			byte[] data = jsonStr.getBytes("utf-8");
			headers.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			return new ResponseEntity<String>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @param withParent
	 * @param withRelations
	 * @param array
	 * @param obj
	 * @throws JSONException
	 */
	private void transform2Json(Boolean withParent, Boolean withRelations, JSONArray array, Object obj) throws JSONException {
		if (DomainObject.class.isInstance(obj)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = new HashMap<String, Object>(BeanMap.create(obj));
			map.put("$displays", beanFieldWirer.getDisplayMap(obj));
			if (withParent != Boolean.FALSE) {
				map.put("$parent", beanFieldWirer.getParentObject(obj));
			}
			if (withRelations != Boolean.FALSE) {
				map.put("$relations", beanFieldWirer.getRelationMap(obj));
			}
			Object json = JSONTransformer.transformPojo2Jso(map);
			array.put(json);
		} else {
			Object json = JSONTransformer.transformPojo2Jso(obj);
			array.put(json);
		}
	}

}
