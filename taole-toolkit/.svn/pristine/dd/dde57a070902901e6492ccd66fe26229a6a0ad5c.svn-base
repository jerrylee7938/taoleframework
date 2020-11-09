package com.taole.toolkit.util;

import java.io.*;

import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

/**
 * HTML转化PDF
 *
 * @author ChengJ
 */
public class Html2Pdf {

    /**
     * 以HTML作為模板 进行转换
     *
     * @param termplatePath E:\\temp\\template.html
     * @param tempaltePath  :E:\\temp\\iText_3.pdf
     */
    @SuppressWarnings({"resource"})
    private static void html2PdfConvert(String termplatePath, String pdfPath, String ttfPath) {
        InputStreamReader read = null;
        OutputStream os = null;
        try {
            String outputFile = pdfPath;
            File file = new File(termplatePath);
            os = new FileOutputStream(outputFile);
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(ttfPath,
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            StringBuffer html = new StringBuffer();
            read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            StringBuffer resultBuffer = new StringBuffer();
            BufferedReader br = new BufferedReader(read);
            String s = null;
            while ((s = br.readLine()) != null) {
                resultBuffer.append(s + "\n");
            }
            html.append(resultBuffer.toString());
            renderer.setDocumentFromString(html.toString());
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
            throw new RuntimeException(" Convert PDF error！" + e.getMessage());
        } finally {
            try {
                if (null != read)
                    read.close();
                if (null != os)
                    os.close();
            } catch (IOException e) {
                throw new RuntimeException("close IO Exception ！"
                        + e.getMessage());
            }

        }
    }

    /**
     * @param termplatePath
     * @param pdfPath
     * @param ttfPath
     * @return
     */
    public static boolean convert(String termplatePath, String pdfPath, String ttfPath) {
        boolean flag = false;
        try {
            html2PdfConvert(termplatePath, pdfPath, ttfPath);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    //public static String tempHtml = "E:\\common\\server\\apache-tomcat-res\\webapps\\wdb\\user\\15104452952\\contract\\invest\\20170516000984\\70516114904042\\debt_invest_transfer.html";
    public static String base = "E:\\common\\server\\apache-tomcat-res\\webapps\\wdb\\user\\13698769876\\contract\\loan\\20170516000985\\";
    public static String tempHtml = base + "driect_loan.html";
    public static String tempHtm_to = base + "debt_invest_transfer_to.html";
    public static String descPdf = base + "debt_invest_transfer_sign_finish.PDF";

    public static void main1(String[] args) {
        html2PdfConvert(tempHtml, descPdf, "E:\\common\\server\\apache-tomcat-p2p-web\\webapps\\wdb-service-webapp\\WEB-INF\\font\\simsun.ttc");
    }

    public static void main(String[] args) throws Exception {

        Tidy tidy = new Tidy();
      /*  tidy.setConfigurationFromFile("config.txt");
        tidy.setErrout(new PrintWriter("error.txt")); //输出错误与警告信息,默认输出到stdout*/
        //需要转换的文件，当然你也可以转换URL的内容
        FileInputStream in = new FileInputStream(tempHtml);
        FileOutputStream out = new FileOutputStream(tempHtm_to);    //输出的文件
        tidy.parse(in, out);    //开始转换了~~~Jtidy把所有东西都封装好了，哈哈~~
        out.close();    //转换完成关闭输入输出流
        in.close();
    }
}
