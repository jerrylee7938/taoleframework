package com.taole.toolkit.util;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class DownloadPicture {
    /**
     * @param urlString 图片网络目标地址
     * @param desc      下载至目的地(结尾无需带/)，例：E:/temp
     * @param fileName  取一个名字(需要带后缀)，例：user.png
     */
    public static void downloadPicture(String urlString, String desc, String fileName) {
        try {
            Objects.requireNonNull(urlString);
            Objects.requireNonNull(desc);
            Objects.requireNonNull(fileName);
            URL url = new URL(urlString);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String imageName = desc + File.separator + fileName;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main123(String[] args) {
        downloadPicture("http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", "F:\\tmp\\img\\", "user.png");
        System.out.println(FileUtil.getFileSuffix("http://www.baidu.com/img/bd_logo1.png"));
    }


    public static String[] imgBitsDeal(byte[] bits, String prefix) {
        String[] rt = new String[2];
        // snippet for JMimeMagic lib
        // http://sourceforge.net/projects/jmimemagic/
        Magic parser = new Magic();
        MagicMatch match = null;
        try {
            match = parser.getMagicMatch(bits);
            rt[1] = match.getMimeType();//文件类型
            rt[0] = prefix + "." + match.getExtension();//构造文件名称（含扩展名）
            System.out.println(match.getMimeType());
            System.out.println(match.getExtension());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            rt[0] = prefix + "." + "png";   //默认文件名
            rt[1] = "image/png";    //默认文件类型
            e.printStackTrace();
        }

        return rt;
    }

    public static void downloadPictureOfHttps(String urlString, String desc, String fileName) {
        try {
            Objects.requireNonNull(urlString);
            Objects.requireNonNull(desc);
            Objects.requireNonNull(fileName);
            byte[] btImg = getImageFromNetByUrl4Client(urlString);
            String imageName = desc + File.separator + fileName;
            File folder = new File(desc);
            if (!folder.exists()) folder.mkdirs();
            if (null != btImg && btImg.length > 0) {
                System.out.println("读取到：" + btImg.length + " 字节");
                writeImageToDisk(btImg, imageName);
            } else {
                System.out.println("没有从该连接获得内容");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String url = "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLIQGzHQOZZ3pko4HicBEmiaJxR8ObakNBntQNEshS1vRQWjBtZQPYOaCYLRia8ymicJQbZfQiaoxMxnQA/0";
        downloadPictureOfHttps(url, "F:\\tmp\\img\\", "user.png");

        /*byte[] btImg = getImageFromNetByUrl(url);
        if (null != btImg && btImg.length > 0) {
            System.out.println("读取到：" + btImg.length + " 字节");
            String fileName = "百度.gif";
            writeImageToDisk(btImg, fileName);
        } else {
            System.out.println("没有从该连接获得内容");
        }*/
    }

    /**
     * 将图片写入到磁盘
     *
     * @param img      图片数据流
     * @param fileName 文件保存时的名称
     */
    public static void writeImageToDisk(byte[] img, String fileName) {
        try {
            File file = new File(fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            System.out.println("图片已经写入到E盘");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            String https = "https";
            String http = "https";
            strUrl = strUrl.replaceAll(https, http);
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl4Client(String strUrl) {
        try {
            return getByte(strUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 下载文件
     *
     * @param url
     */
    private static byte[] getByte(String url) {
        CloseableHttpClient httpclient = null;
        byte[] btImg = null;
        try {
            httpclient = HttpClientUtils.getHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                HttpEntity httpEntity = response1.getEntity();
                InputStream is = httpEntity.getContent();
                btImg = readInputStream(is);
                EntityUtils.consume(httpEntity);
            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return btImg;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

}
