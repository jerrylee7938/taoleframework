/*
 * @Copyright: Copyright 北京网擎科技有限公司 (c) 2012
 * @修改记录： 1、2012-7-14 上午10:14:46，Administrator创建。
 */
package com.taole.toolkit.util.excel;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc：解析excel（03、07版）工具类</br> 前提必须保证模版正确《兼容各种格式、银行卡号、函数等》
 * @Filename:ReadExcelUtil.java</br>
 * @Author:CJ</br>
 * @Date:2016/10/11上午10:14:46</br>
 */
@SuppressWarnings("ALL")
public class ReadExcelUtil {

    public static final int CELL_TYPE_STRING = 0; // 字符串类型
    public static final int CELL_TYPE_DATE = 1; // 日期类型
    public static final int CELL_TYPE_FORMULA = 2;// 自定义格式
    public static final int CELL_TYPE_BOOLEAN = 3;// 布尔类型
    public static final int CELL_TYPE_NUMERIC = 4;// 数值类型
    public static final int CELL_TYPE_BANKCODE = 5;// 银行卡号类型
    public static final int CELL_TYPE_ERROR = 6; // 错误类型
    public static final int CELL_TYPE_BLANK = 7; // 空
    public static List list; // 返回List里面为Object数组
    public static int model_begin_row_valid; // 模版有效数据开始行
    public static int[] headsType; // 表头每个单元格属性
    public static String[] headsName; // 表头每个单元格名称
    public static final String MESSAGE = "message"; // map中错误信息key
    public static final String RESULT_LIST = "list"; // map中结果集key

    public static void main(String args[]) {
        File file = new File("F:\\现金流量表.xls");
        Map map = ReadExcelUtil.read(file);
        System.out.println(map);
    }

    /**
     * 导入文件总接口
     *
     * @param uploadfile 上传文件的url
     * @return map 返回数据：错误信息、解析后数据集合
     */
    public static Map read(File uploadfile) {
        Map resultMap = new HashMap();
        // 模版验证是否存在
        if (!uploadfile.exists()) {
            resultMap.put(MESSAGE, "尚未找到模版");
            return resultMap;
        }
        // 获得模版信息
        readModel(uploadfile.getAbsolutePath());
        headsName = ReadExcelUtil.headsName;
        headsType = ReadExcelUtil.headsType;
        if (null == headsName || null == headsType
                || headsType.length != headsName.length) {
            resultMap.put(MESSAGE, "加载模版失败，请检查模版");
            return resultMap;
        }
        for (int type : headsType) {
            if (type == CELL_TYPE_ERROR) {
                resultMap.put(MESSAGE, "加载模版失败，请检查模版");
                return resultMap;
            }
        }
        // 验证合法性
        String message = ReadExcelUtil.check(uploadfile.getAbsolutePath(),
                headsType, model_begin_row_valid);
        if (!"验证完毕 请导入数据".equals(message)) {
            resultMap.put(MESSAGE, message);
            return resultMap;
        }
        // 开始解析文件
        List resultList = new ArrayList();
        String fileUrl = uploadfile.getAbsolutePath();
        File readfile = new File(fileUrl);
        if (readfile.exists()) {
            String fname = readfile.getName();
            if (null != fname) {
                String hzname = fname.substring(fname.lastIndexOf(".") + 1,
                        fname.length());
                if ("xls".equalsIgnoreCase(hzname)
                        || "xlsx".equalsIgnoreCase(hzname)) {
                    // 读取2003版本
                    if ("xls".equalsIgnoreCase(hzname)) {
                        resultList = readXLS(fileUrl, headsType.length, model_begin_row_valid);
                        // 读取2007版本
                    } else {
                        resultList = readXLSX(fileUrl, headsType.length, model_begin_row_valid);
                    }
                }
            }
        }
        resultMap.put(RESULT_LIST, resultList);
        return resultMap;
    }


    /**
     * 读取模版信息主方法
     *
     * @param (String) fileUrl --模版路径（支持xls\xlsx模版）
     * @return (List) List --模版最后一行类型 倒数第二行表头名称
     */
    public static void readModel(String fileUrl) {
        File file = new File(fileUrl);
        if (file.exists()) {
            String fname = file.getName();
            if (null != fname) {
                String hzname = fname.substring(fname.lastIndexOf(".") + 1,
                        fname.length());
                // 2003版模类型
                if ("xls".equalsIgnoreCase(hzname)) {
                    readXLSModel(fileUrl);
                }
                // 2007版模类型
                if ("xlsx".equalsIgnoreCase(hzname)) {
                    readXLSXModel(fileUrl);
                }
            }
        }
    }

    /**
     * 检查数据合法性主方法
     *
     * @param (String) fileUrl --上传文件路径
     * @param (int[])  headsType --之前解析获得的head的类型
     * @param (int)    benginRow --数据开始行
     * @return (String) messag --验证结果
     */
    public static String check(String fileUrl, int[] headsType, int benginRow) {
        String messag = "验证完毕 请导入数据";
        // String messag = "不合法，请检查excel文件";
        // excel文件加载
        File file = new File(fileUrl);
        if (file.exists()) {
            String fname = file.getName();
            if (null != fname) {
                String hzname = fname.substring(fname.lastIndexOf(".") + 1,
                        fname.length());
                if ("xls".equalsIgnoreCase(hzname)
                        || "xlsx".equalsIgnoreCase(hzname)) {
                    if ("xls".equalsIgnoreCase(hzname)) {
                        // check2003版本文件
                        messag = checkXLS(fileUrl, headsType, benginRow);
                    } else {
                        // check2007版本文件
                        messag = checkXLSX(fileUrl, headsType, benginRow);
                    }
                }
            }
        }
        return messag;
    }

    /**
     * 解析excel的主方法
     *
     * @param (String) fileUrl --上传文件路径
     * @param (int)    maxCell --之前解析获得的head的长度
     * @param (int)    benginRow --数据开始行
     * @return(List) list --模版最后一行类型 倒数第二行表头名称
     */
    public static List read(String fileUrl, int maxCell, int benginRow) {

        File file = new File(fileUrl);
        try {
            if (file.exists()) {
                String fname = file.getName();
                System.out.println("解析文件的时候，检查可以拿到文件");
                if (null != fname) {
                    System.out.println("解析到的文件名称是" + fname);
                    String hzname = fname.substring(fname.lastIndexOf(".") + 1,
                            fname.length());

                    System.out.println("后缀名为:" + hzname);
                    if ("xls".equalsIgnoreCase(hzname)
                            || "xlsx".equalsIgnoreCase(hzname)) {

                        // 读取2003版本
                        if ("xls".equalsIgnoreCase(hzname)) {
                            System.out.println("是2003格式的:");

                            list = readXLS(fileUrl, maxCell, benginRow);
                            // 读取2007版本
                        } else {
                            System.out.println("是2007格式的:");
                            list = readXLSX(fileUrl, maxCell, benginRow);
                        }
                    } else {
                        System.out.println("解析文件的时候，沒有拿到相應的后缀名，此刻后缀名为：" + hzname);
                    }
                }
            } else {
                System.out.println("解析文件时，获取文件失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读取XLS模版子方法
     *
     * @param (String) fileUrl --模版文件路径
     * @return (List) list --模版最后一行类型 倒数第二行表头名称
     */
    public static void readXLSModel(String fileUrl) {
        FileInputStream fis = null;
        HSSFWorkbook hwb = null;
        HSSFSheet hsheet = null;
        HSSFRow hrow = null;
        HSSFCell hcell = null;
        int[] objArrayHeadType = null;
        String[] objArrayHeadName = null;
        int rowCount = 0;
        try {
            fis = new FileInputStream(fileUrl);
            hwb = new HSSFWorkbook(fis);
            // 只读第一个sheet
            hsheet = hwb.getSheetAt(0);
            rowCount = hsheet.getLastRowNum();
            // 删除空行
            // 从后外前读取信息 倒数第一行为数据类型

            while (rowCount >= 0) {
                hrow = hsheet.getRow(rowCount);
                // 判断是否为空
                if (null != hrow) {
                    // 设置有效数据开始行<生成文件中会使用到>
                    model_begin_row_valid = rowCount;
                    // 获得列数
                    int colNum = hrow.getLastCellNum();
                    // 定义数组表头属性数组大小
                    objArrayHeadType = new int[colNum];
                    int k;
                    for (k = 0; k < colNum; k++) {
                        hcell = hrow.getCell(k);
                        objArrayHeadType[k] = getObjectType(hcell);
                    }
                    // 添加表头属性信息
                    headsType = objArrayHeadType;
                    // 定义数组表头数组大小
                    objArrayHeadName = new String[colNum];
                    hrow = hsheet.getRow(rowCount - 1);
                    for (k = 0; k < colNum; k++) {
                        hcell = hrow.getCell(k);
                        objArrayHeadName[k] = String.valueOf(getObject(hcell));
                    }
                    // 添加表头名称信息
                    headsName = objArrayHeadName;
                    break;
                }
                // 删除空行
                if (hrow == null) {
                    try {
                        hsheet.shiftRows(rowCount + 1, hsheet.getLastRowNum(), -1);
                    } catch (Exception ex) {
                    }
                }
                rowCount--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 读取XLSX模版子方法
     *
     * @param (String) fileUrl --模版文件路径
     * @return (List) list --模版最后一行类型 倒数第二行表头名称
     */
    public static void readXLSXModel(String fileUrl) {
        FileInputStream fis = null;
        XSSFWorkbook xwb = null;
        XSSFSheet xsheet = null;
        XSSFRow xrow = null;
        XSSFCell xcell = null;
        int[] objArrayHeadType = null;
        String[] objArrayHeadName = null;
        int rowCount = 0;
        try {
            fis = new FileInputStream(fileUrl);
            xwb = new XSSFWorkbook(fis);
            // 只读第一个sheet
            xsheet = xwb.getSheetAt(0);
            rowCount = xsheet.getLastRowNum();
            // 删除空行
            // 从后外前读取信息 倒数第一行为数据类型
            while (rowCount >= 0) {
                xrow = xsheet.getRow(rowCount);
                // 判断是否为空
                if (null != xrow) {
                    // 设置有效数据开始行<生成文件中会使用到>
                    model_begin_row_valid = rowCount;
                    // 获得列数
                    int colNum = xrow.getLastCellNum();
                    // 定义数组表头属性数组大小
                    objArrayHeadType = new int[colNum];
                    int k;
                    for (k = 0; k < colNum; k++) {
                        xcell = xrow.getCell(k);
                        objArrayHeadType[k] = getObjectType(xcell);
                    }
                    // 添加表头属性信息
                    headsType = objArrayHeadType;
                    // 定义数组表头数组大小
                    objArrayHeadName = new String[colNum];
                    xrow = xsheet.getRow(rowCount - 1);
                    for (k = 0; k < colNum; k++) {
                        xcell = xrow.getCell(k);
                        objArrayHeadName[k] = String.valueOf(getObject(xcell));
                    }
                    // 添加表头名称信息
                    headsName = objArrayHeadName;
                    break;
                }
                // 删除空行
                if (xrow == null) {
                    xsheet.shiftRows(rowCount + 1, xsheet.getLastRowNum(), -1);
                }
                rowCount--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check 2003版excel 的子方法
     *
     * @param (String) fileUrl --上传文件路径
     * @param (int[])  headsType --之前解析获得的head的类型
     * @param (int)    benginRow --数据开始行
     * @return (String) messag --验证结果
     */
    public static String checkXLS(String fileUrl, int[] headsType, int benginRow) {
        String messag = "验证成功 请导入数据";
        FileInputStream fis = null;
        HSSFWorkbook hwb = null;
        HSSFSheet hsheet = null;
        HSSFRow hrow = null;
        HSSFCell hcell = null;
        int rowCount = 0;
        int sheetCount = 0;
        int maxCell = headsType.length;
        try {
            fis = new FileInputStream(fileUrl);
            hwb = new HSSFWorkbook(fis);
            sheetCount = hwb.getNumberOfSheets();
            // 循环sheet
            for (int i = 0; i < sheetCount; i++) {
                hsheet = hwb.getSheetAt(i);
                rowCount = hsheet.getLastRowNum();
                // 删除空行
                while (rowCount > 0) {
                    hrow = hsheet.getRow(rowCount);
                    // 循环row
                    if (rowCount >= benginRow) {// 大于等于开始行数据
                        hrow = hsheet.getRow(rowCount);
                        if (null != hrow) {
                            // 检查每行长度
                            if (0 != hrow.getLastCellNum()
                                    && hrow.getLastCellNum() != maxCell) {
                                return "非法文件与模版行数不对应" + hsheet.getSheetName()
                                        + "中第" + rowCount + "行";
                            }
                            // 循环每一列
                            for (int k = 0; k < maxCell; k++) {
                                hcell = hrow.getCell(k);
                                if (null != hcell) {// 只检查非空数据
                                    if (headsType[k] != getObjectType(hcell)
                                            && CELL_TYPE_BLANK != getObjectType(hcell)) {
                                        int col = k + 1;
                                        int row = rowCount + 1;
                                        return "非法文件与模版类型不对应"
                                                + hsheet.getSheetName() + "中第"
                                                + row + "行,第" + col + "列"
                                                + getObject(hcell) + "格式不正确请检查！";
                                    }
                                }
                            }
                        }
                    }
                    // 删除空行
                    if (hrow == null) {
                        hsheet.shiftRows(rowCount + 1, hsheet.getLastRowNum(),
                                -1);
                    }
                    rowCount--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return messag;
    }

    /**
     * Check 2007版excel 的子方法
     *
     * @param (String) fileUrl --上传文件路径
     * @param (int[])  headsType --之前解析获得的head的类型
     * @param (int)    benginRow --数据开始行
     * @return(String) messag --验证结果
     */
    public static String checkXLSX(String fileUrl, int[] headsType,
                                   int benginRow) {
        System.out.println("进入解析Excel2007版的方法");
        String messag = "验证成功 请导入数据";
        FileInputStream fis = null;
        XSSFWorkbook xwb = null;
        XSSFSheet xsheet = null;
        XSSFRow xrow = null;
        XSSFCell xcell = null;
        int rowCount = 0;
        int sheetCount = 0;
        int maxCell = headsType.length;
        try {
            fis = new FileInputStream(fileUrl);
            xwb = new XSSFWorkbook(fis);
            sheetCount = xwb.getNumberOfSheets();

            // 循环sheet
            for (int i = 0; i < sheetCount; i++) {
                System.out.println("正式开始解析Excel文件，已经进入循环状态，循环值为" + i);
                xsheet = xwb.getSheetAt(i);
                rowCount = xsheet.getLastRowNum();
                // 删除空行
                while (rowCount > 0) {
                    xrow = xsheet.getRow(rowCount);
                    // 循环row
                    if (rowCount >= benginRow) {// 大于等于开始行数据
                        xrow = xsheet.getRow(rowCount);
                        if (null != xrow) {
                            // 检查每行长度
                            if (0 != xrow.getLastCellNum()
                                    && xrow.getLastCellNum() != maxCell) {
                                return "非法文件与模版行数不对应" + xsheet.getSheetName()
                                        + "中第" + rowCount + "行";
                            }
                            // 循环每一列
                            for (int k = 0; k < maxCell; k++) {
                                xcell = xrow.getCell(k);
                                if (null != xcell) {// 只检查非空数据
                                    if (headsType[k] != getObjectType(xcell)
                                            && CELL_TYPE_BLANK != getObjectType(xcell)) {
                                        int col = k + 1;
                                        int row = rowCount + 1;
                                        return "非法文件与模版类型不对应"
                                                + xsheet.getSheetName() + "中第"
                                                + row + "行,第" + col + "列"
                                                + getObject(xcell) + "格式不正确请检查！";
                                    }
                                }
                            }
                        }
                    }
                    // 删除空行
                    if (xrow == null) {
                        xsheet.shiftRows(rowCount + 1, xsheet.getLastRowNum(),
                                -1);
                    }
                    rowCount--;
                }
            }
        } catch (Exception e) {
            System.out.println("开始解析的时候报了一个异常");
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("关闭流异常");
            }
        }
        if (null != list && list.size() > 0) {
            System.out.println("07版这里解析完毕后list的大小为：" + list.size());
        } else {
            System.out.println("o7版list为空值，也就所解析失败，或有问题");
        }
        return messag;
    }

    /**
     * 读取03版excel 子方法
     *
     * @param (String) fileUrl --上传文件路径
     * @param (int)    maxCell --之前解析获得的head的长度
     * @param (int)    benginRow --数据开始行
     * @return (List) list -- 解析excel返回的List 其中 为OBJECT[]数组每个类型都为OBJECT
     */
    public static List readXLS(String fileUrl, int maxCell, int benginRow) {
        System.out.println("已经开始解析2003的文件Excel");
        FileInputStream fis = null;
        HSSFWorkbook hwb = null;
        HSSFSheet hsheet = null;
        HSSFRow hrow = null;
        HSSFCell hcell = null;
        Object[] objArray = null;
        int rowCount = 0;
        int sheetCount = 0;
        try {
            fis = new FileInputStream(fileUrl);
            hwb = new HSSFWorkbook(fis);
            sheetCount = hwb.getNumberOfSheets();
            list = new ArrayList();
            // 循环sheet
            for (int i = 0; i < sheetCount; i++) {
                System.out.println("正式开始解析Excel文件，已经进入循环状态，循环值为" + i);
                hsheet = hwb.getSheetAt(i);
                rowCount = hsheet.getLastRowNum();
                // 删除空行
                while (rowCount > 0) {
                    hrow = hsheet.getRow(rowCount);
                    // 循环row
                    if (rowCount >= benginRow) {// 大于等于开始行数据
                        hrow = hsheet.getRow(rowCount);
                        if (null != hrow) {
                            // 循环每行每列
                            objArray = new Object[maxCell];
                            // 循环每一列
                            for (int k = 0; k < maxCell; k++) {
                                hcell = hrow.getCell(k);
                                if (null == hcell) {//
                                    objArray[k] = null;
                                } else {
                                    objArray[k] = getObject(hcell);
                                }
                            }
                        }
                        list.add(objArray);
                    }
                    // 删除空行
                    if (hrow == null) {
                        hsheet.shiftRows(rowCount + 1, hsheet.getLastRowNum(),
                                -1);
                    }
                    // list.add(objArray);
                    rowCount--;
                }
            }
        } catch (Exception e) {
            System.out.println("解析的时候开始抛了一个异常");
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("关闭流异常");
                e.printStackTrace();
            }
        }

        if (null != list && list.size() > 0) {
            System.out.println("这里解析完毕后list的大小为：" + list.size());
        } else {
            System.out.println("list为空值，也就所解析失败，或有问题");
        }
        return list;
    }

    /**
     * 读取07版excel 的子方法
     *
     * @param (String) fileUrl --上传文件路径
     * @param (int)    maxCell --之前解析获得的head的长度
     * @param (int)    benginRow --数据开始行
     * @return (List) list-- 解析excel返回的List 其中 为OBJECT[]数组每个类型都为OBJECT
     */
    public static List readXLSX(String fileUrl, int maxCell, int benginRow) {
        FileInputStream fis = null;
        XSSFWorkbook xwb = null;
        XSSFSheet xsheet = null;
        XSSFRow xrow = null;
        XSSFCell xcell = null;
        Object[] objArray = null;
        int rowCount = 0;
        int sheetCount = 0;
        try {
            fis = new FileInputStream(fileUrl);
            xwb = new XSSFWorkbook(fis);
            sheetCount = xwb.getNumberOfSheets();
            list = new ArrayList();

            // 循环sheet
            for (int i = 0; i < sheetCount; i++) {
                xsheet = xwb.getSheetAt(i);
                rowCount = xsheet.getLastRowNum();
                // 删除空行
                while (rowCount > 1) {
                    xrow = xsheet.getRow(rowCount);
                    // 循环row
                    if (rowCount >= benginRow) {
                        xrow = xsheet.getRow(rowCount);
                        if (null != xrow) {
                            // 循环每行每列
                            objArray = new Object[maxCell];
                            // 循环每一列
                            for (int k = 0; k < maxCell; k++) {
                                xcell = xrow.getCell(k);
                                if (null == xcell) {
                                    objArray[k] = null;
                                } else {
                                    objArray[k] = getObject(xcell);
                                }
                            }
                        }
                        list.add(objArray);
                    }
                    if (xrow == null) {
                        xsheet.shiftRows(rowCount + 1, xsheet.getLastRowNum(),
                                -1);
                    }
                    // 倒序
                    rowCount--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获得XSSFCell类型
     *
     * @param (XSSFCell) xcell --单位格对象
     * @return (int) 0/1/2/3/4--返回类型
     */
    public static int getObjectType(XSSFCell xcell) {
        int type = 0;
        switch (xcell.getCellType()) {
            case XSSFCell.CELL_TYPE_BOOLEAN:
                type = CELL_TYPE_BOOLEAN;
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                // 先看是否是日期格式
                if (DateUtil.isCellDateFormatted(xcell)) {
                    // 转换类型日期格式并且检查格式
                    type = checkDate(xcell.getDateCellValue());
                } else {
                    // 读取数字
                    type = CELL_TYPE_NUMERIC;
                }
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                // 读取公式
                type = CELL_TYPE_FORMULA;
                break;
            case XSSFCell.CELL_TYPE_STRING:
                // 检查文本是银行卡号 还是日期还是普通字符串
                type = checkBankORDateORString(xcell.getRichStringCellValue()
                        .toString());
                break;
            // 当excel数字删除后为空则返回数值类型《特殊性》
            case XSSFCell.CELL_TYPE_BLANK:
                type = CELL_TYPE_BLANK;
                break;

        }
        return type;
    }

    /**
     * 获得HSSFCell类型
     *
     * @param (HSSFCell) hcell--单位格对象
     * @return (int) 0/1/2/3/4--返回类型
     */
    public static int getObjectType(HSSFCell hcell) {
        int type = 0;
        switch (hcell.getCellType()) {
            case HSSFCell.CELL_TYPE_BOOLEAN:
                type = CELL_TYPE_BOOLEAN;
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                // 先看是否是日期格式
                if (DateUtil.isCellDateFormatted(hcell)) {
                    // 读取日期格式并且尝试转换格式"yyyy-MM-dd"
                    type = checkDate(hcell.getDateCellValue());
                } else {
                    // 读取数字
                    type = CELL_TYPE_NUMERIC;
                }
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                // 读取公式
                type = CELL_TYPE_FORMULA;
                break;
            case HSSFCell.CELL_TYPE_STRING:
                // 检查文本是银行卡号 还是日期还是普通字符串
                type = checkBankORDateORString(hcell.getRichStringCellValue()
                        .toString());
                break;
            // 当excel数字删除后为空则返回数值类型《特殊性》
            case HSSFCell.CELL_TYPE_BLANK:
                type = CELL_TYPE_BLANK;
                break;
        }
        return type;
    }

    /**
     * 返回Object类型的内容
     *
     * @param (HSSFCell) hcell --单元格对象
     * @return (Object) obj --返回单元格内容 类型为object
     */
    public static Object getObject(HSSFCell hcell) {
        Object obj = null;
        switch (hcell.getCellType()) {
            case HSSFCell.CELL_TYPE_BOOLEAN:
                obj = hcell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                // 先看是否是日期格式
                if (DateUtil.isCellDateFormatted(hcell)) {
                    // 读取日期格式
                    obj = hcell.getDateCellValue();
                } else {
                    // 读取数字
                    obj = hcell.getNumericCellValue();
                }
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                // 读取公式
                obj = hcell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_STRING:
                // 读取String
                obj = hcell.getRichStringCellValue();
                break;
        }
        return obj;
    }

    /**
     * 返回Object类型的内容
     *
     * @param (XSSFCell) xcell--单元格对象
     * @return (Object) obj--返回单元格内容 类型为object
     */
    public static Object getObject(XSSFCell xcell) {
        Object obj = null;
        // System.out.println(xcell.getCachedFormulaResultType());
        switch (xcell.getCellType()) {
            case XSSFCell.CELL_TYPE_BOOLEAN:
                obj = xcell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                // 先看是否是日期格式
                if (DateUtil.isCellDateFormatted(xcell)) {
                    // 读取日期格式
                    obj = xcell.getDateCellValue();
                } else {
                    // 读取数字
                    obj = xcell.getNumericCellValue();
                }
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                // 读取公式
                obj = xcell.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_STRING:
                // 读取String
                obj = xcell.getRichStringCellValue();
                break;
        }
        return obj;
    }

    /**
     * 转换日期格式并check类型
     *
     * @param (Date) obj --单元格类型为 日期的对象
     * @return (int) --返回类型
     */
    public static int checkDate(Date obj) {

        String dateString = null;
        DateFormat format;
        try {
            // 转成yyyy-MM-dd类型 Date对象
            format = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy",
                    Locale.US);
            obj = format.parse(obj.toString());
            // 转成yyyy-MM-dd类型
            SimpleDateFormat sformat;
            if (obj != null) {
                sformat = new SimpleDateFormat("yyyy-MM-dd");
                dateString = sformat.format(obj);
            }
            // 正则判断yyyy-MM-dd
            String datePattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
            Pattern p = Pattern.compile(datePattern);
            Matcher m = p.matcher(dateString);
            boolean dateFlag = m.matches();
            // 不符合日期格式yyyy-MM-dd返回错误类型
            if (!dateFlag) {
                return CELL_TYPE_ERROR;
            } else {
                return CELL_TYPE_DATE;
            }
        } catch (ParseException e) {
            return CELL_TYPE_ERROR;
        }
    }

    /**
     * 检查文本是银行卡号 还是日期还是普通字符串
     *
     * @param (String) obj --文本字符串
     * @return (int) --返回类型
     */

    public static int checkBankORDateORString(String obj) {
        int i = CELL_TYPE_STRING;
        Matcher m;
        Pattern p;
        // yyyy-MM-dd格式正则
        p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        m = p.matcher(obj);
        // 日期格式的字符串
        if (m.matches()) {
            i = CELL_TYPE_DATE;
        }
        // 银行卡号的字符串
        p = Pattern.compile("[0-9]*");
        m = p.matcher(obj.trim());
        // 如果全部为数字将进一步检查是否为银行卡号
        if (m.matches()) {
            // if (checkBankCard(obj.trim())) {
            i = CELL_TYPE_STRING;
            // }
        }
        return i;
    }

    /**
     * 校验银行卡卡号 add by hanyu
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        int len = cardId.trim().length();
        if (len >= 4) {
            return true;
        }
        return false;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard2(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 导入文件总接口
     *
     * @param fileUrl  上传文件的url
     * @param fileName 文件名称
     * @param modelUrl 模板url
     * @return map 返回数据：错误信息、解析后数据集合
     */
    public static Map read(String upLoadFileUrl, String fileName,
                           String modelUrl) {
        Map resultMap = new HashMap();
        // 设置模版
        File modelFile = new File(modelUrl);
        // 模版验证是否存在
        if (!modelFile.exists()) {
            resultMap.put(MESSAGE, "尚未找到模版");
            return resultMap;
        }
        // 获得模版信息
        readModel(modelFile.getAbsolutePath());
        headsName = ReadExcelUtil.headsName;
        headsType = ReadExcelUtil.headsType;
        if (null == headsName || null == headsType
                || headsType.length != headsName.length) {
            resultMap.put(MESSAGE, "加载模版失败，请检查模版");
            return resultMap;
        }
        for (int type : headsType) {
            if (type == CELL_TYPE_ERROR) {
                resultMap.put(MESSAGE, "加载模版失败，请检查模版");
                return resultMap;
            }
        }
        // 获得上传文件地址
        File file = new File(upLoadFileUrl);
        String fileUrl = file.getAbsolutePath();
        // 验证合法性
        String message = ReadExcelUtil.check(fileUrl,
                headsType, model_begin_row_valid);
        if (!"验证成功 请导入数据".equals(message)) {
            resultMap.put(MESSAGE, message);
            return resultMap;
        }
        // 开始解析文件
        List resultList = new ArrayList();
        File readfile = new File(fileUrl);
        if (readfile.exists()) {
            String fname = readfile.getName();
            if (null != fname) {
                String hzname = fname.substring(fname.lastIndexOf(".") + 1,
                        fname.length());
                if ("xls".equalsIgnoreCase(hzname)
                        || "xlsx".equalsIgnoreCase(hzname)) {
                    // 读取2003版本
                    if ("xls".equalsIgnoreCase(hzname)) {
                        resultList = readXLS(fileUrl, headsType.length, 1);
                        // 读取2007版本
                    } else {
                        resultList = readXLSX(fileUrl, headsType.length, 1);
                    }
                }
            }
        }
        resultMap.put(RESULT_LIST, resultList);
        return resultMap;
    }
}
