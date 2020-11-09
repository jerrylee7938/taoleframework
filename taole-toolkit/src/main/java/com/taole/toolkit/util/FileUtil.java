package com.taole.toolkit.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Objects;

/**
 * Created by ChengJ on 2016/4/7.
 */
@SuppressWarnings("ALL")
public class FileUtil {
    public static Log logger = LogFactory.getLog(FileUtil.class);

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.exists() && file.isFile()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
        if (null == file) throw new RuntimeException();
        boolean flag = false;
        if (file.exists() && file.isFile()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        boolean flag;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectoryNotSelf(String sPath) {
        boolean flag;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        return true;
    }

    /**
     * 读取txt文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file) {
        String result = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result = result + "\n" + s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException o) {
                o.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 复制一个目录及其子目录、文件到另外一个目录
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }
            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                // 递归复制
                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new FileInputStream(src);
                out = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];

                int length;

                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                if (null != out) {
                    out.close();
                    logger.info("关闭流成功");
                }
                if (null != in) {
                    in.close();
                    logger.info("关闭流成功");
                }
            }
        }
    }

    public static void copyFile(File sourcefile, File targetFile) throws IOException {


        //新建文件输入流并对它进行缓冲
        FileInputStream input = null;
        BufferedInputStream inbuff = null;

        //新建文件输出流并对它进行缓冲
        FileOutputStream out = null;
        BufferedOutputStream outbuff = null;
        try {
            input = new FileInputStream(sourcefile);
            inbuff = new BufferedInputStream(input);
            //新建文件输出流并对它进行缓冲
            out = new FileOutputStream(targetFile);
            outbuff = new BufferedOutputStream(out);
            //缓冲数组
            byte[] b = new byte[1024 * 5];
            int len = 0;
            while ((len = inbuff.read(b)) != -1) {
                outbuff.write(b, 0, len);
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            //关闭流
            outbuff.close();
            out.close();
            inbuff.close();
            input.close();
            //刷新此缓冲的输出流
            // outbuff.flush();
        }
    }

    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {

        //新建目标目录

        (new File(targetDir)).mkdirs();

        //获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                //源文件
                File sourceFile = file[i];
                //目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());

                copyFile(sourceFile, targetFile);

            }

            if (file[i].isDirectory()) {
                //准备复制的源文件夹
                String dir1 = sourceDir + file[i].getName();
                //准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();

                copyDirectiory(dir1, dir2);
            }
        }

    }


    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String filePath) {
        if (StringUtils.isNotBlank(filePath)) {
            return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        }
        return null;
    }

    /**
     * 获取文件名称
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isNotBlank(filePath)) {
            String fileOld = filePath.replaceAll("\\\\", "/");
            if (fileOld.lastIndexOf("/") > 0)
                return filePath.substring(fileOld.lastIndexOf("/") + 1, filePath.length());
        }
        return null;
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public static File renameFile(String oldPath, String newFileName) {
        File oldFile = new File(oldPath);
        if (!oldFile.exists()) {
            logger.info("源文件名不存在！");
            return null;
        }
        String filePath = oldFile.getAbsolutePath();
        String newFilePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        File newFile = new File(newFilePath + File.separator + newFileName);
        if (newFile.exists()) {
            logger.info("目标文件名已经存在！");
            return null;
        }
        oldFile.renameTo(newFile);
        return newFile;
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public static boolean createFolder(String path) {
        File file = new File(path);
        if (!file.exists())
            return file.mkdirs();
        return true;
    }

    /**
     * 创建文
     *
     * @param path
     * @return
     */
    public static boolean createFile(String path) {
        File file = new File(path);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件
     *
     * @param path
     * @return
     */
    public static File createFile4NotC(String path) {
        File file = new File(path);
        return file;
    }

    /**
     * 解析内容
     *
     * @param htmlFile
     * @return
     */
    public static StringBuffer getFileContent(File file) {
        BufferedReader br = null;
        InputStreamReader read = null;
        StringBuffer htmlContent = null;
        if (null != file) {
            try {
                read = new InputStreamReader(new FileInputStream(file),
                        "UTF-8");
                htmlContent = new StringBuffer();
                br = new BufferedReader(read);
                String s = null;
                while ((s = br.readLine()) != null) {
                    htmlContent.append(s + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (read != null)
                        read.close();
                    if (br != null)
                        br.close();
                } catch (IOException io) {

                    io.printStackTrace();
                }
            }
        }
        return htmlContent;
    }

    /**
     * 写入新数据
     *
     * @param content
     */
    public static void createnewFileFlush(File generateFile, String content) {
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
    }

}
