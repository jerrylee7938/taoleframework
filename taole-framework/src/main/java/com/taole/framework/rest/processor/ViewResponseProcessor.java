package com.taole.framework.rest.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.taole.framework.rest.RequestParameters;
import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.util.EnvironmentHelper;
import com.taole.framework.util.MD5Util;

@Component
public class ViewResponseProcessor extends AbstractResponseProcessor {

	@Override
	public String getName() {
		return "view";
	}

	public Object process(Object input) {
		RestContext context = RestSessionFactory.getCurrentContext();
		if (ModelAndView.class.isInstance(input)) {
			ModelAndView mv = (ModelAndView) input;
			if (StringUtils.isEmpty(mv.getViewName())) {
				mv.setViewName(getView());
			} else {
				mv.setViewName(convertPath(mv.getViewName()));
			}
			mv.addObject("applicationContext", context.getAttribute("applicationContext"));
			if ((mv.getView() != null && mv.getView() instanceof RedirectView) || (mv.getViewName() != null && mv.getViewName().startsWith("redirect"))) {
			} else {
				mv.addObject("contextPath", context.getHttpServletRequest().getContextPath());
			}
			mv.addObject("context", context);
			return mv;
		} else if (String.class.isInstance(input)) {
			return convertPath((String) input);
		} else if (ResponseEntity.class.isInstance(input)) {
			return input;
		}
		ModelAndView mv = new ModelAndView(getView());
		mv.addObject("context", context);
		mv.addObject("applicationContext", context.getAttribute("applicationContext"));
		mv.addObject("contextPath", context.getHttpServletRequest().getContextPath());
		mv.addObject("result", input);
		return mv;
	}

	protected String getView() {
		RestContext ctx = RestSessionFactory.getCurrentContext();
		String view = null;
		Object po = ctx.getParameterObject();
		if (RequestParameters.class.isInstance(po)) {
			view = (String) ((RequestParameters) po).getParameter("view");
		}
		if (StringUtils.isEmpty(view)) {
			String viewProKey = "views." + ctx.getModule() + "." + ctx.getAction();
			ApplicationContext applicationContext = (ApplicationContext) ctx.getAttribute("applicationContext");
			Environment environment = applicationContext.getEnvironment();
			EnvironmentHelper helper = new EnvironmentHelper(environment);
			view = helper.getProperty(viewProKey);
		}
		return convertPath(view);
	}

	public String convertPath(String path) {
		if (path == null) {
			return null;
		}
		HttpServletRequest request = RestSessionFactory.getCurrentContext().getHttpServletRequest();
		if (path.startsWith("classpath:") && path.endsWith(".jsp")) {
			try {
				URL url = org.springframework.util.ResourceUtils.getURL(path);
				URL fileUrl = url;
				if (ResourceUtils.isJarURL(url)) {
					fileUrl = ResourceUtils.extractJarFileURL(url);
				}
				File file = ResourceUtils.getFile(fileUrl);
				long lastModified = file.lastModified();
				String destPath = getDestPath(path);
				File destFile = new File(request.getSession(true).getServletContext().getRealPath(destPath));
				// 目标文件不存在，生成
				if (!destFile.exists() || lastModified != file.lastModified()) {
					if (checkDir(request)) {
						com.taole.framework.util.IOUtils.copyAndCloseIOStream(new FileOutputStream(destFile), url.openStream());
						destFile.setLastModified(lastModified);
					} else {
						return null;
					}
				}
				return destPath;
			} catch (Exception e) {
				e.printStackTrace();

			}
			return null;
		} else {
			return path;
		}
	}

	private boolean checkDir(HttpServletRequest request) {
		File dir = new File(request.getSession().getServletContext().getRealPath(temp_jsp_home));
		if (!dir.exists() && !dir.mkdirs()) {
			return false;
		}
		return true;
	}

	public static final String temp_jsp_home = "/temp/jsp";

	private String getDestPath(String path) {
		return temp_jsp_home + "/" + MD5Util.digest(path) + ".jsp";
	}

}
