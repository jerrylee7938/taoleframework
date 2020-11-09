package com.taole.portal.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.taole.userauthen.LocalDataInst;
import com.taole.usersystem.manager.UserSystemManager;

public class loadAuthUrlServlet extends HttpServlet {
	private static final long serialVersionUID = 9067809049677890987L;

	protected WebApplicationContext ctx;

	private UserSystemManager userSystemManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		response.setContentType("text;html;charset=utf-8");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		doGet(request, response);
	}

	protected final Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		Map<String, String> refMap = new HashMap<String, String>();

		String appCode = servletConfig.getInitParameter("appCode");
		if (appCode == null) {
			System.out.println("[com.taole.portal.servlet.loadAuthUrlServlet][init]Can not find the appCode param from filter config.please set it!");
		} else {
			try {
				super.init();
//				ServletContext servletContext = this.getServletContext();
//				WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//				userSystemManager = (UserSystemManager) ctx.getBean("UserSystemManager");
//				JSONArray ja = userSystemManager.getAuthUrl(appCode);
//				for (int i = 0; i < ja.length(); i++) {
//					JSONObject itemJo = (JSONObject) ja.get(i);
//					String mapKey = itemJo.getString("resCode");
//					String mapValue = itemJo.getString("resUri");
//					System.out.println("[com.taole.portal.servlet.loadAuthUrlServlet][init]mapKey=" + mapKey + ";mapValue=" + mapValue);
//					refMap.put(mapKey, mapValue);
//				}
//				LocalDataInst.getInstance().setAuthUrlMap(refMap);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
