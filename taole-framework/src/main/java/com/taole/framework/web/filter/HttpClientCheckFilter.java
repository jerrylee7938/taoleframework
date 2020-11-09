/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taole.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taole.framework.util.StringUtils;

public class HttpClientCheckFilter implements Filter {

	private String acceptUserAgent;
	private boolean checkUserAgent = true;
	
	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		if (checkUserAgent) {
			HttpServletRequest request = (HttpServletRequest) arg0;
			HttpServletResponse response = (HttpServletResponse) arg1;
			String userAgent = request.getHeader("user-agent");
			if (!StringUtils.matches(acceptUserAgent, userAgent)) {
				response.setStatus(400);
				response.getWriter().write("Bad User Agent.");
				return;
			}
		}
		arg2.doFilter(arg0, arg1);

	}

	public void init(FilterConfig arg0) throws ServletException {
		acceptUserAgent = arg0.getInitParameter("acceptUserAgent");
		if (acceptUserAgent == null) {
			acceptUserAgent = "";
		} else if ("*".equals(acceptUserAgent)) {
			checkUserAgent = false;
		}
	}

}
