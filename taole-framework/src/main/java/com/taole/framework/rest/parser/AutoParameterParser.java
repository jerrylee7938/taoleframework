package com.taole.framework.rest.parser;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.taole.framework.rest.RequestParameters;
import com.taole.framework.util.IOUtils;

@Component
public class AutoParameterParser extends AbstractReqestParser {

	public String getName() {
		return "auto";
	}

	public RequestParameters parseRequest(HttpServletRequest request) {

		String contentType = request.getContentType();
		if (contentType != null && contentType.toLowerCase().contains("application/json")) {
			String source = "";
			if ("post".equalsIgnoreCase(request.getMethod())) {
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
					CharArrayWriter data = new CharArrayWriter();
					IOUtils.copyAndCloseIOStream(data, in);
					source = data.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				source = request.getParameter("q");
			}
			if (StringUtils.isBlank(source) || "null".equals(source)) {
				source = "{}";
			}
			try {
				if (source.startsWith("[")) {
					JSONArray jsonRequest = new JSONArray(source);
					return new JSONArrayRequestParameters(jsonRequest);
				} else {
					JSONObject jsonRequest = new JSONObject(source);
					return new JSONObjectRequestParameters(jsonRequest);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return new FormRequestParameters(request.getParameterMap());

	}

}
