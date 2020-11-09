package com.taole.toolkit.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by ChengJ on 2016/4/10.
 * prop获取文件
 */
@SuppressWarnings("ALL")
public class CommonConfig {
    /**
     * 获取服务器http全地址
     *
     * @param folderName 文件夹
     * @return
     */
    public static String getHttpServerPath(HttpServletRequest request, String folderName) {
        if (null != request) {
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + File.separator;
            if (org.apache.commons.lang.StringUtils.isEmpty(folderName))
                return basePath;
            else return basePath + folderName;
        }
        return null;
    }

    /**
     * 获取服务器磁盘全地址
     *
     * @param folderName
     * @return
     * @throws Exception
     */
    public static String getServerPath(HttpServletRequest request, String folderName) {
        StringBuffer serverPath1 = new StringBuffer(request.getSession().getServletContext().getRealPath("/"));
        return serverPath1.append(folderName).toString();
    }

    /*文件上传根目录*/
    public static String UPLOAD_PATH_ROOT = "upload";
    /*图片上传路径*/
    public static String UPLOAD_PATH_PIC = "/upload/";


}
