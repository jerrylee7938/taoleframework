package com.taole.toolkit.util.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XlsPoiAction {

	public void generateXls(XlsObject xlsObject) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "wbs strurs");

			//HSSFHeader header = sheet.getHeader();

			HSSFFont titleFont = workbook.createFont();
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
			titleFont.setFontHeight((short) 380); // 设置字体大小
			titleFont.setFontName("宋体"); // 设置单元格字体

			//HSSFFont dataFont = workbook.createFont();

			int rowIndex = 0;// 行标记,记录创建到第几行了
			int cellIndex = 0;// CELL的位置标记
			// 创建第一行的标题
			HSSFRow titleRow = sheet.createRow(rowIndex++);
			titleRow.setHeight((short) 400);
			String[] titles = xlsObject.getXlsTitles();
			for (int i = 0; i < titles.length; i++) {
				HSSFCell titleCell = titleRow.createCell(cellIndex++);
				titleCell.setCellValue(titles[i]);
			}

			// 生成EXCEL行数据
			JSONObject dataJo = getData();

			// 输出excel文件
			FileOutputStream fileOut = new FileOutputStream("d:\\workbook.xls");
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 自定义的方法，主要用于插入中文的字符格式
	private void setGB2312String(HSSFCell cell, String value) throws Exception {
		cell.setCellValue(value);
	}

	private JSONObject getData() throws Exception{
		JSONObject resultJo = null;
		
		try {
			String jsonStr="";
			String filePath = "";
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (StringUtils.isNotBlank(lineTxt)) {
						jsonStr = jsonStr + lineTxt;
					}
				}
				read.close();
			} else {
				System.out.println("[com.taole.userauthen.LocalData][loadTxtLocalRef]can not find the txt file.file Path=" + filePath);
			}
			
			resultJo = new JSONObject(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultJo;
	}

}
