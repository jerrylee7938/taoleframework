package com.taole.framework.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.support.RequestContext;

import com.google.common.collect.Maps;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: RestHandlerHelper.java 5 2014-01-15 13:55:28Z yedf $
 */
public final class RestHandlerHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(RestHandlerHelper.class);
	
	private static final Integer DEFAULT_PAGE_SIZE = 20;
	
	public static ResponseEntity<byte[]> createResponseEntity(String html, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("utf-8")));
		try {
			byte[] data = html.getBytes("utf-8");
			headers.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data, headers, status);
		} catch (UnsupportedEncodingException e) {
			return new ResponseEntity<byte[]>(e.toString().getBytes(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static ResponseEntity<byte[]> createJSONResponse(int code, Object result) {
		JSONObject json = new JSONObject();
		try {
			json.put("code", code);
			json.put("result", JSONTransformer.transformPojo2Jso(result));
		} catch (Exception e) {
			try {
				json.put("error", e.toString());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return createJSONResponse(json);
	}
	
	public static ResponseEntity<byte[]> createJSONResponse(Object result) {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("no-cache");
		headers.setPragma("no-cache");
		headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
		String jsonStr = "";
		try {
			if (result instanceof JSONObject) {
				jsonStr = result.toString();
			} else {
				jsonStr = JSONTransformer.transformPojo2Jso(result).toString();
			}
		} catch (JSONException e) {
			jsonStr = e.toString();
		}
		try {
			byte[] data = jsonStr.getBytes("utf-8");
			headers.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			return new ResponseEntity<byte[]>(e.toString().getBytes(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public static Sorter getSorter(HttpServletRequest request, String defaultSortField, boolean desc) {
		String[] sorts = ServletRequestUtils.getStringParameters(request, "sort");
		String[] dirs = ServletRequestUtils.getStringParameters(request, "dir");
		Sorter sorter = new Sorter();
		if (sorts != null && sorts.length > 0) {
			for (int i = 0; i < sorts.length; i++) {
				String sort = sorts[i];
				// 如果是 JSON 格式
				if (JSONUtils.isJSON(sort)) {
					return ExtjsDataConvertor.convertSorter(sort);
				}
				String dir = null;
				if (dirs != null && dirs.length > i) {
					dir = dirs[i];
				}
				if (StringUtils.equalsIgnoreCase("desc", dir)) {
					sorter.desc(sort);
				} else {
					sorter.asc(sort);
				}
			}
		} else {
			if (StringUtils.isNotBlank(defaultSortField)) {
				if (desc) {
					sorter.desc(defaultSortField);
				} else {
					sorter.asc(defaultSortField);
				}
			}
		}
		return sorter;
	}
	
	public static Range getRange(HttpServletRequest request) {
		try {
			Integer start = ServletRequestUtils.getIntParameter(request, "start");
			Integer page = ServletRequestUtils.getIntParameter(request, "page");
			Integer limit = ServletRequestUtils.getIntParameter(request, "limit");
			if (limit == null) {
				limit = ServletRequestUtils.getIntParameter(request, "size");
			}
			if (limit == null) {
				limit = DEFAULT_PAGE_SIZE;
			}
			if (page == null || page < 1) {
				page = 1;
			}
			if (start == null) {
				start = (page - 1) * limit;
			}
			return new Range(start, limit);
		} catch (ServletRequestBindingException e) {
			return new Range(0, DEFAULT_PAGE_SIZE);
		}
	}
	
	/**
	 * get binding error message.
	 * @param request
	 * @param errors
	 * @param clz
	 * @return
	 */
	public static  Map<String, String> getErrorMessage(HttpServletRequest request, List<ObjectError> errors, Class<?> clz) {
		Map<String, String> errorMap = Maps.newHashMap();
		if (errors != null && !errors.isEmpty()) {
			for (ObjectError error : errors) {
				if (error instanceof FieldError) {
					FieldError fieldError = (FieldError) error;
					String message = null;
					String field = fieldError.getField();
					RequestContext requestContext = new RequestContext(request);
					String codeMessage = null; 
					String fieldMessage = null;
					try {
						codeMessage = requestContext.getMessage(fieldError.getCode());
					} catch (NoSuchMessageException e) {
						logger.warn(e.getMessage());
					}
					try {
						fieldMessage = requestContext.getMessage(clz.getName() + "." + field);
					} catch (NoSuchMessageException e) {
						logger.warn(e.getMessage());
					}
					if (StringUtils.isBlank(fieldMessage)) {
						fieldMessage = field;
					}
					if (StringUtils.isNotBlank(codeMessage) && StringUtils.isNotBlank(fieldMessage)) {
						message = fieldMessage + codeMessage;
					} else if (StringUtils.isNotBlank(codeMessage)) {
						message = codeMessage;
					} else if (StringUtils.isNotBlank(fieldMessage)){
						message = fieldMessage + fieldError.getDefaultMessage();
					} else {
						message = fieldError.getDefaultMessage();
					}
					errorMap.put(field, message);
				} else {
					errorMap.put(error.toString(), error.getDefaultMessage());
				}
			}
		}
		return errorMap;
	}
}
