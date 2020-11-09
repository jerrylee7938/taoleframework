/*
 * @(#)IOUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.*;

/**
 * Class: IOUtils Remark:
 *
 * @author:
 */
public abstract class FileUtils {

    public static long getLastModified(String path) {
        File file = new File(path);
        return file.exists() ? file.lastModified() : -1;
    }

    public static boolean saveTextFile(String path, String text, String encoding) {
        return saveTextFile(path, text, encoding, false);
    }

    public static boolean saveTextFile(String path, String text, String encoding, boolean append) {
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file, append);
            out.write(text.getBytes(encoding));
            IOUtils.close(out);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readTextFile(String path, String encoding) {
        try {
            InputStream is = new FileInputStream(path);
            return IOUtils.inputStreamToString(is, encoding);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static byte[] readBinFile(String path) {
        try {
            InputStream is = new FileInputStream(path);
            return IOUtils.getBytes(is);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static byte[] readBinFile(File file) {
        try {
            InputStream is = new FileInputStream(file);
            return IOUtils.getBytes(is);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static boolean checkFileExists(String path) {
        try {
            return new File(path).exists();
        } catch (Exception e) {

            return false;
        }
    }

    public static boolean createNewFileFlush(File generateFile, String content) {
        Writer writer = null;
        try {
            if (generateFile.exists()) {
                generateFile.delete();
            }
            generateFile.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(generateFile), "UTF-8"));
            writer.write(content);
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }
}
