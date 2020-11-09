package com.taole.framework.rest.parser;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class FormParameterParser extends AbstractReqestParser {

	public String getName() {
		return "form";
	}
	
	public Object parseRequest(HttpServletRequest request) {
		return new FormRequestParameters(request.getParameterMap());
	}

}
