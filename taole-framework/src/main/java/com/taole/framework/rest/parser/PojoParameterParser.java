package com.taole.framework.rest.parser;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.taole.framework.util.IOUtils;
import com.taole.framework.util.JSONTransformer;

@Component
public class PojoParameterParser extends AbstractReqestParser {

	public String getName() {
		return "pojo";
	}
	public Object parseRequest(HttpServletRequest request) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			CharArrayWriter data = new CharArrayWriter();
			IOUtils.copyAndCloseIOStream(data, in);
			String source = data.toString();
			if (source.startsWith("[")) {
				JSONArray jsonRequest = new JSONArray(source);
				return JSONTransformer.transformJso2Pojo(jsonRequest, null);
			} else {
				JSONObject jsonRequest = new JSONObject(source);
				return JSONTransformer.transformJso2Pojo(jsonRequest, null);
			}
		} catch (Exception e) {
			return null;
		}
	}

}
