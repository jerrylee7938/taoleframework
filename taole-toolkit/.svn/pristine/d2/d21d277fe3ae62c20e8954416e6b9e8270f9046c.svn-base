/**
 * Project:taole-toolkit
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.filesystem;

import com.taole.framework.util.EnvironmentHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: FileSystemPath.java 10031 2017-12-15 06:18:00Z chengjun $
 * @since Jun 21, 2011 1:06:17 PM
 */
public class FileSystemPath implements Serializable {

    public static String FILE_SYSTEM_ROOT_VARIABLE = "%{filesystem-root}";

    @Resource(name = "filesystem.root")
    String fileSystemRoot;

    @Autowired
    Environment environment;


    public String getFileSystemRoot() {
        return fileSystemRoot;
    }

    public String getFileSystemUrlRoot() {
        return getProperty("filesystem.urlRoot");
    }

    private String getProperty(String key) {
        EnvironmentHelper envHelper = new EnvironmentHelper(environment);
        return envHelper.getProperty(key);
    }

    /**
     * 注意这里得到的地址不是实际存储的地址。是包含 {@code FILE_SYSTEM_ROOT_VARIABLE} 的地址。
     * 如果要得到实际的存储地址请使用方法 {@link #getRealStorageDir()}
     *
     * @return
     */
    public String getStorageDir() {
        return getProperty("filesystem.storeDir");
    }

    public String getRealStorageDir() {
        return getRealPath(getStorageDir());
    }

    /**
     * 取配置的filesystem.tempDir 如果没有配置则取系统的临时文件夹.
     *
     * @return
     */
    public String getTempDir() {
        String tempDir = getProperty("filesystem.tempDir");
        if (StringUtils.isBlank(tempDir)) {
            return System.getProperty("java.io.tmpdir");
        }
        return tempDir;
    }

    public String getRealTempDir() {
        return getRealPath(getTempDir());
    }

    protected String getThumbDir() {
        return getProperty("filesystem.thumbDir");
    }


    public String getRealThumbDir() {
        return getRealPath(getThumbDir());
    }

    public String getRealWatermarkDir() {
        return getRealPath(getProperty("filesystem.watermarkDir"));
    }

    public String getRealPath(String path) {
        if (path == null) {
            return path;
        } else {
            return StringUtils.replaceOnce(path, FILE_SYSTEM_ROOT_VARIABLE, getFileSystemRoot());
        }
    }

}
