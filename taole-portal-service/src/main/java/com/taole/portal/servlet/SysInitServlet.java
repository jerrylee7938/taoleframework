package com.taole.portal.servlet;

import com.taole.page.template.manager.PageTemplateManager;
import com.taole.page.template.manager.FileSystemManager;
import com.taole.page.template.util.CommonConfig;
import com.taole.usersystem.manager.MenuManager;
import com.taole.usersystem.manager.ResourceManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

/**
 * @Desc：登录模板初始化工具</br>
 * @Filename:SysInitServlet.java</br>
 * @Author:CJ</br>
 */
public class SysInitServlet extends HttpServlet {
    private static final long serialVersionUID = 7000124830237124679L;
    private static final Log log = LogFactory.getLog("");
    @Autowired
    PageTemplateManager pageTemplateManager;
    @Autowired
    ResourceManager resourceManager;
    @Autowired
    MenuManager menuManager;


    public static ServletContext context = null;
    public static WebApplicationContext WEB_CONTEXT;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
    }

    public void init(ServletConfig servletconfig) {
        try {
            super.init(servletconfig);
            context = servletconfig.getServletContext();
            context.setAttribute("servletConfig", servletconfig);
            WEB_CONTEXT = WebApplicationContextUtils
                    .getWebApplicationContext(context);
            WebApplicationContextUtils
                    .getWebApplicationContext(getServletContext())
                    .getAutowireCapableBeanFactory().autowireBean(this);
            log.info("<<<<<<<<<<<-----------------------路径、url等配置项赋值------------------------>>>>>>>>>>>>>>>");
            String diskPath = servletconfig.getServletContext().getRealPath("/");
            diskPath = diskPath.substring(0, diskPath.length() - 1);
            CommonConfig.diskPath = diskPath;
            context = servletconfig.getServletContext();
            FileSystemManager fileSystemManager = (FileSystemManager) WEB_CONTEXT.getBean("fileSystemManager");
            CommonConfig.diskPath_login = fileSystemManager.getTemplateFilePath();
            CommonConfig.httpPath_login = fileSystemManager.getTemplateFileUrl();
            log.debug("获取贷的磁盘路径:" + CommonConfig.diskPath_login);
            log.info("<<<<<<<<<<<-----------------------END------------------------>>>>>>>>>>>>>>>");
            pageTemplateManager.copyResourcesToPoratl();
            
            /*开始执行初始化权限数据*/
			/*1、将v_alluserres用户url权限数据放入缓存*/
			//resourceManager.initUserAuthToCache();
			/*2、 将v_alluserleaves_granted视图中的数据放入sys_alluserleaves_granted*/
			//menuManager.initUserMenuGranted(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取初始化参数值
     *
     * @param para 参数名称
     * @return
     */

    protected String getServletInitParamValue(String para) {
        Enumeration initParams = getInitParameterNames();
        while (initParams.hasMoreElements()) {
            if (initParams.nextElement().equals(para)) {
                return getInitParameter(para);
            }
        }
        return "";
    }
}
