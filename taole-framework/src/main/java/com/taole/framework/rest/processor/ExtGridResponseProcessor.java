package com.taole.framework.rest.processor;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.util.ExtjsDataConvertor;

@Component
public class ExtGridResponseProcessor extends AbstractResponseProcessor {
	
	@Override
	public String getName() {
		return "ext-grid";
	}
	
	@SuppressWarnings("unchecked")
	public Object process(Object input) {
		
		JSONObject json = null;
		ExtjsDataConvertor convertor = new ExtjsDataConvertor();
		if (input == null) {
			json = convertor.convertGridStoreData(new PaginationSupport<Object>());
		} else if (ResultSet.class.isInstance(input)) {
			json = convertor.convertGridStoreData((ResultSet)input);
		} else if (PaginationSupport.class.isInstance(input)) {
			json = convertor.convertGridStoreData((PaginationSupport<?>)input);
		} else  if (List.class.isInstance(input)) {
			PaginationSupport<Object> ps = new PaginationSupport<Object>((List<Object>)input);
			json = convertor.convertGridStoreData(ps);
		} else  if (input.getClass().isArray()) {
			PaginationSupport<Object> ps = new PaginationSupport<Object>((Object[])input);
			json = convertor.convertGridStoreData(ps);
		} else if (JSONArray.class.isInstance(input)) {
			JSONArray jsonArray = (JSONArray) input;
			json = new JSONObject();
			try {
				json.put("items", input);
				json.put("total", jsonArray.length());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			json = convertor.convertGridStoreData(new PaginationSupport<Object>());
		}
		String jsonStr = json.toString();
		String jsonp = RestSessionFactory.getCurrentContext().getHttpServletRequest().getParameter("jsonp");
		if (StringUtils.isNotBlank(jsonp)) {
			jsonStr = jsonp + "(" + jsonStr + ");";
		}
		HttpHeaders headers = new HttpHeaders();
		try {
			byte[] data = jsonStr.getBytes("utf-8");
			headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			headers.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			return new ResponseEntity<String>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

}
