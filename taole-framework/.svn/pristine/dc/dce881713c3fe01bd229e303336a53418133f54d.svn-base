/**
 * Project:taole-heaframework
 * File:ExcelReader.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Rory
 * @date Jun 9, 2012
 * @version $Id: ExcelReader.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ExcelReader {

	private ExcelReader() {
	}
	
	/**
	 * 取第一个sheet里的数据。第一行为字段定义，会做为map的key，因此第一行的字段如果有重复的话，值会被覆盖掉。
	 * @param file
	 * @return
	 */
	public static List<Map<String, Object>> readToMapList(File file) {
		return readToMapList(file, null);
	}

	/**
	 * 取第一个sheet里的数据。第一行为字段定义，会做为map的key，因此第一行的字段如果有重复的话，值会被覆盖掉。
	 * 
	 * @param file
	 *            File对象
	 * @param fields
	 *            要读取的xls中的字段,如果为null或者空数组就返回所有的字段
	 * @return
	 */
	public static List<Map<String, Object>> readToMapList(File file, String[] fields) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		InputStream inputStream = null;
		try {
			
			inputStream = new BufferedInputStream(new FileInputStream(file));

			if (file.getName().toLowerCase().endsWith("xlsx")) {
				XSSFWorkbook wb = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = wb.getSheetAt(0);
				int rows = sheet.getPhysicalNumberOfRows();
				XSSFRow headerRow = sheet.getRow(0);
				Map<Integer, String> fieldIndex = new HashMap<Integer, String>();

				int headerCellCount = headerRow.getLastCellNum();
				for (int i = 0; i < headerCellCount; i++) {
					XSSFCell cell = headerRow.getCell(i);
					if (cell == null) {
						continue;
					}
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						String cellValue = cell.getStringCellValue();
						if (fields != null && fields.length > 0 && org.apache.commons.lang3.ArrayUtils.contains(fields, cellValue)) {
							fieldIndex.put(i, cellValue);
						} else {
							fieldIndex.put(i, cellValue);
						}
					}
				}

				//DecimalFormat format = new DecimalFormat("#.##");

				for (int r = 1; r < rows; r++) {
					XSSFRow row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					Map<String, Object> dataMap = new HashMap<String, Object>();

					for (int c = 0; c < headerCellCount; c++) {
						if (fieldIndex.get(c) == null) {
							continue;
						}
						XSSFCell cell = row.getCell(c);
						Object value = null;
						if (cell != null) {
							switch (cell.getCellType()) {

							case HSSFCell.CELL_TYPE_FORMULA:
								value = cell.getCellFormula();
								break;

							case HSSFCell.CELL_TYPE_NUMERIC:
								//value = format.format(cell.getNumericCellValue());
								if (DateUtil.isCellDateFormatted(cell)) {
									value = cell.getDateCellValue();
								} else {
									value = cell.getNumericCellValue();
								}
								break;

							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;

							default:
							}
						}
						dataMap.put(fieldIndex.get(c), value == null ? null : value);
					}
					dataList.add(dataMap);
				}
			} else {
				HSSFWorkbook wb = new HSSFWorkbook(inputStream);
				HSSFSheet sheet = wb.getSheetAt(0);
				int rows = sheet.getPhysicalNumberOfRows();
				HSSFRow headerRow = sheet.getRow(0);
				Map<Integer, String> fieldIndex = new HashMap<Integer, String>();

				int headerCellCount = headerRow.getLastCellNum();
				for (int i = 0; i < headerCellCount; i++) {
					HSSFCell cell = headerRow.getCell(i);
					if (cell == null) {
						continue;
					}
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						String cellValue = cell.getStringCellValue();
						if (fields != null && fields.length > 0 && org.apache.commons.lang3.ArrayUtils.contains(fields, cellValue)) {
							fieldIndex.put(i, cellValue);
						} else {
							fieldIndex.put(i, cellValue);
						}
					}
				}
				//DecimalFormat format = new DecimalFormat("#.##");
				for (int r = 1; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					Map<String, Object> dataMap = new HashMap<String, Object>();

					for (int c = 0; c < headerCellCount; c++) {
						if (fieldIndex.get(c) == null) {
							continue;
						}
						HSSFCell cell = row.getCell(c);
						Object value = null;
						if (cell != null) {
							switch (cell.getCellType()) {

							case HSSFCell.CELL_TYPE_FORMULA:
								value = cell.getCellFormula();
								break;

							case HSSFCell.CELL_TYPE_NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									value = cell.getDateCellValue();
								} else {
									value = cell.getNumericCellValue();
								}
								break;

							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;

							default:
							}
						}
						dataMap.put(fieldIndex.get(c), value == null ? null : value);
					}
					dataList.add(dataMap);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return dataList;
	}

	public static String cutZero(String v) {
		if (v.indexOf(".") > -1) {
			while (true) {
				if (v.lastIndexOf("0") == (v.length() - 1)) {
					v = v.substring(0, v.lastIndexOf("0"));
				} else {
					break;
				}
			}
			if (v.lastIndexOf(".") == (v.length() - 1)) {
				v = v.substring(0, v.lastIndexOf("."));
			}
		}
		return v;
	}
}
