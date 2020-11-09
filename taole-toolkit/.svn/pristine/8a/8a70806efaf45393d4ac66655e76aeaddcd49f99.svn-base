package com.taole.toolkit.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 暂用版本
 */
@SuppressWarnings("ALL")
public class ReadExcel {


    /**
     * 读取文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public List readExcel(String path) throws IOException {
        if (path == null || Common.EMPTY.equals(path)) {
            return null;
        } else {
            String postfix = Util.getPostfix(path);
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path);
                } else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                }
            } else {
                System.out.println(path + Common.NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    /**
     * 解析文件2010
     *
     * @param path
     * @return
     * @throws IOException
     */
    public List<XSSFRow> readXlsx(String path) throws IOException {
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<XSSFRow> list = new ArrayList<XSSFRow>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    list.add(xssfRow);
                }
            }
        }
        return list;
    }

    /**
     * 解析文件2003/07
     *
     * @param path
     * @return
     * @throws IOException
     */
    public List<HSSFRow> readXls(String path) throws IOException {
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<HSSFRow> list = new ArrayList<HSSFRow>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    list.add(hssfRow);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    public static String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    public static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    public class Common {
        public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
        public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
        public static final String EMPTY = "";
        public static final String POINT = ".";
        public static final String NOT_EXCEL_FILE = " : 没有找到excel文件!";
        public static final String PROCESSING = "执行中...";

    }


    /**
     * @author Hongten
     * @created 2014-5-21
     */
    public static class Util {

        /**
         * get postfix of the path
         *
         * @param path
         * @return
         */
        public static String getPostfix(String path) {
            if (path == null || Common.EMPTY.equals(path.trim())) {
                return Common.EMPTY;
            }
            if (path.contains(Common.POINT)) {
                return path.substring(path.lastIndexOf(Common.POINT) + 1, path.length());
            }
            return Common.EMPTY;
        }
    }

    public static void main(String[] args) throws IOException {
        String excel2003_2007 = "F:\\投资人信息-2016-10-12.xls";
        String excel2010 = "23";
        // read the 2003-2007 excel
        List<HSSFRow> list = new ReadExcel().readExcel(excel2003_2007);
        System.out.println( ReadExcel.getValue(list.get(0).getCell(0)));
    }


}