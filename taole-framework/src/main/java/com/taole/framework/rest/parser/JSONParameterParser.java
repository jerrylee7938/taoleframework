package com.taole.framework.rest.parser;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.taole.framework.util.IOUtils;

@Component
public class JSONParameterParser extends AbstractReqestParser {
	
	public String getName() {
		return "json";
	}
	
	public Object parseRequest(HttpServletRequest request) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			CharArrayWriter data = new CharArrayWriter();
			IOUtils.copyAndCloseIOStream(data, in);
			String source = data.toString();
			if (source.startsWith("[")) {
				JSONArray jsonRequest = new JSONArray(source);
				return new JSONArrayRequestParameters(jsonRequest);
			} else {
				JSONObject jsonRequest = null;
				if (StringUtils.isBlank(source)) {
					jsonRequest = new JSONObject();
				} else {
					jsonRequest = new JSONObject(source);
				}
				return new JSONObjectRequestParameters(jsonRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
