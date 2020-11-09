package com.taole.framework.rest.parser;

import javax.servlet.http.HttpServletRequest;


public interface ParameterParser {

	String getName();

	Object parseRequest(HttpServletRequest request);

}
