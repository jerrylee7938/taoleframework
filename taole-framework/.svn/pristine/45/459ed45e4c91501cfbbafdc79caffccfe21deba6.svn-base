package com.taole.framework.rest.processor;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.JSONUtils;

@Component
public class JSONResponseProcessor extends AbstractResponseProcessor {

	@Override
	public String getName() {
		return "json";
	}

	public Object process(Object input) {
		HttpHeaders headers = new HttpHeaders();
		RestContext context = RestSessionFactory.getCurrentContext();
		if (context.getAttribute("_contentType") != null && context.getAttribute("_contentType") instanceof MediaType) {
			headers.setContentType((MediaType) context.getAttribute("_contentType"));
		} else {
			headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
		}
		String jsonStr = "";
		try {
			if (input instanceof JSONObject){
				jsonStr = ((JSONObject) input).toString();
				
			} else if (input instanceof JSONArray) {
				jsonStr = ((JSONArray) input).toString();
			} else {
				Object json = JSONTransformer.transformPojo2Jso(input);
				jsonStr = JSONUtils.convertJava2JSON(json);
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

}
