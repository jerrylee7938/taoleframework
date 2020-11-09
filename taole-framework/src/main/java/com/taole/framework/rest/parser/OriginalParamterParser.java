package com.taole.framework.rest.parser;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class OriginalParamterParser extends AbstractReqestParser {

	public String getName() {
		return "original";
	}

	public Object parseRequest(HttpServletRequest request) {
		return request;
	}

}
