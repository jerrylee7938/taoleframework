package com.taole.toolkit.manager;

import java.io.File;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taole.toolkit.filesystem.FileSystemPath;

/**
 * 文件路径获取组件
 */
@Service
public class FilePathSystemManager implements Serializable {
    private static String appDir = "wdb"; // wdb应用资源目录根名
    private static String portalDir = "portal"; // portal应用资源目录根名
    private static String userDir = "user"; // wdb用户资源目录名
    private static String bannerDir = "banner"; // wdb横幅图片资源目录名
    private static String articleDir = "article"; // wdb文章图片资源目录名
    private static String projectDir = "project"; // wdb项目图片资源目录名
    private static String creditDir = "credit"; // 小贷项目资源目录名
    private static String pcacDir = "pcac"; // 小微项目资源目录名
    private static String weichat = "weichat"; // 微信平台项目资源目录名
    private static String otherDir = "other"; // 供全局使用的其他文件存放地址(不好分类的情况下使用,比如临时组装下载文件打包文件等)
    private static String weChatappDir = "wechat"; // 微信应用资源目录根名
    private static String QRCodeDir = "qrCode"; // 二维码目录
    private static String queueDir = "queue"; // 消息队列相关
    @Autowired
    private FileSystemPath fileSystemPath;

    /**
     * 获取文件系统URL根路径
     * 与DB中的文件项目路径拼接后，可直接采用http方式访问
     *
     * @return
     */
    public String getFileSystemUrl() {
        return fileSystemPath.getFileSystemUrlRoot();
    }

    /**
     * 获取文件系统根路径
     * 与DB中的文件项目路径拼接后，可直接采用http方式访问
     *
     * @return
     */
    public String getFileSystemPath() {
        return fileSystemPath.getFileSystemRoot();
    }

    /**
     * 由于要兼容老WDB的文件结构，保存到DB中的文ot，例：/etc/httpd/user/13811020380/94951435735916663.png
     * 因此，需要调用该方法，对路径进行整理，将/etc/ht件位置是/upload/20150701/94951435735916663.png
     * 但该方法中所有获取path的方法都带有fileSystemRotpd移除
     *
     * @param path
     * @return
     */
    public String adjustPathForSave(String path) {
        String root = fileSystemPath.getFileSystemRoot();
        path = path.replace(root, "");
        return path;
    }

    /**
     * 获取存放用户资料的位置
     *
     * @return
     */
    public String getUserFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + appDir + File.separator + userDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取存放用户资料的位置
     *
     * @return
     */
    public String getUserFilePath(String userId) {
        String filePath = getUserFilePath() + File.separator + userId;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取存放用户资料的URL
     *
     * @return
     */
    public String getUserFileUrl() {
        String fileUrl = fileSystemPath.getFileSystemUrlRoot() + "/" + appDir + "/" + userDir;
        return fileUrl;
    }

    /**
     * 获取存放用户资料的位置
     *
     * @return
     */
    public String getUserFileUrl(String userId) {
        String fileUrl = getUserFileUrl() + "/" + userId;
        return fileUrl;
    }

    /**
     * 获取存放banner资源的位置
     *
     * @return
     */
    public String getBannerFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + "/" + appDir + "/" + bannerDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取访问banner资源的位置
     *
     * @return
     */
    public String getBannerFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + appDir + "/" + bannerDir;
        return filePath;
    }

    /**
     * 获取存放banner资源的位置
     *
     * @return
     */
    public String getArticleFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + appDir + File.separator + articleDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取访问banner资源的URL
     *
     * @return
     */
    public String getArticleFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + appDir + "/" + articleDir;
        return filePath;
    }

    /**
     * 获取存放项目资源的位置
     *
     * @return
     */
    private String getProjectFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + appDir + File.separator + projectDir;
        return filePath;
    }

    /**
     * 获取存放项目资源的位置
     *
     * @return
     */
    public String getProjectFilePath(String projectId) {
        String filePath = getProjectFilePath() + File.separator + projectId;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取模板资源路径
     *
     * @return
     */
    public String getTemplateFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + portalDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取模板资源URL
     *
     * @return
     */
    public String getTemplateFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + portalDir;
        return filePath;
    }

    /**
     * 获取小贷资源路径
     *
     * @return
     */
    public String getCreditFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + creditDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取小贷资源URL
     *
     * @return
     */
    public String getCreditFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + creditDir;
        return filePath;
    }

    /**
     * 获取小薇资源路径
     *
     * @return
     */
    public String getPcacFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + pcacDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取小薇源URL
     *
     * @return
     */
    public String getPcacFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + pcacDir;
        return filePath;
    }


    /**
     * 获取queue资源路径
     *
     * @return
     */
    public String getQueueFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + queueDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取queue源URL
     *
     * @return
     */
    public String getQueueFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + queueDir;
        return filePath;
    }

    /**
     * 获取微信平台资源路径
     *
     * @return
     */
    public String getWeiChatFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + weichat;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取微信平台资源URL
     *
     * @return
     */
    public String getWeiChatFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + weichat;
        return filePath;
    }

    /**
     * 获取供全局使用存放资源路径
     *
     * @return
     */
    public String getOtherFilePath() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + otherDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取供全局使用存放资源URL
     *
     * @return
     */
    public String getOtherFileUrl() {
        String filePath = fileSystemPath.getFileSystemUrlRoot() + "/" + otherDir;
        return filePath;
    }


    /**
     * 获取访问项目资源的URL
     *
     * @return
     */
    private String getProjectFileUrl() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + appDir + File.separator + projectDir;
        return filePath;
    }

    /**
     * 获取营销主体根目录
     *
     * @return
     */
    public String getSubjectDir(String subjectId) {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + weChatappDir + File.separator + QRCodeDir + File.separator + subjectId
                + File.separator;
        return filePath;
    }

    /**
     * 获取批量生成二维码目录
     *
     * @return
     */
    public String getBatchDir(String subDir) {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + weChatappDir + File.separator + "batch" + File.separator + subDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取应用目录
     *
     * @return
     */
    public String getWhcatDir() {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + weChatappDir;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取存放主体资源的目录
     *
     * @return
     */
    public String getSubjectResPath(String subjectId) {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + weChatappDir + File.separator + QRCodeDir + File.separator + subjectId
                + File.separator + "res" + File.separator;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取主体资源目录url
     *
     * @return
     */
    public String getSubjectResUrl(String subjectId) {
        String fileUrl = getFileSystemUrl() + "/" + weChatappDir + "/" + QRCodeDir + "/" + subjectId + "/res/";
        return fileUrl;
    }

    /**
     * 获取存放二维码的目录
     *
     * @return
     */
    public String getQRCodePath(String subjectId) {
        String filePath = fileSystemPath.getFileSystemRoot() + File.separator + weChatappDir + File.separator + QRCodeDir + File.separator + subjectId
                + File.separator;
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取二维码目录url
     *
     * @return
     */
    public String getQRCodeUrl(String subjectId) {
        String fileUrl = getFileSystemUrl() + "/" + weChatappDir + "/" + QRCodeDir + "/" + subjectId + "/";
        return fileUrl;
    }

    /**
     * 获取模板资源目录
     *
     * @return
     */
    public String getQRTmplPath() {
        String filePath = getWhcatDir() + File.separator + QRCodeDir + File.separator + "tmpl";
        autoCreate(filePath);
        return filePath;
    }

    /**
     * 获取二维码目录url
     *
     * @return
     */
    public String getQRTmplUrl() {
        String fileUrl = getFileSystemUrl() + "/" + appDir + "/" + QRCodeDir + "/tmpl/";
        return fileUrl;
    }


    /**
     * 获取访问项目资源的URL
     *
     * @return
     */
    public String getProjectFileUrl(String projectId) {
        String filePath = getProjectFileUrl() + "/" + projectId;
        return filePath;
    }

    /**
     * 自动创建目录
     */
    private void autoCreate(String path) {
        File f = new File(path);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
}