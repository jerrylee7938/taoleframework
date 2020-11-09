/**
 * Project:taole-heaframework
 * File:XlsResponseProcessor.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest.processor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.MessageUtils;
import com.taole.framework.util.Progress;

/**
 * <ul>
 * 	<li>在RestContext中放入 {@link XlsResponseProcessor#XLS_FILE_NAME} 可以指定导出excel的文件名,默认为当前时间的文件名,eg:20120221182501.xlsx(可选)</li>
 * 	<li>在RestContext中放入 {@link XlsResponseProcessor#XLS_TYPE} 可以指定导出excel的类型，支持两种类型 xls, xlsx 不指定(默认)为xlsx(可选)</li>
 * 	<li>在RestContext中放入 {@link XlsResponseProcessor#SHEET_NAME} 可以指定导出excel的sheet名称(可选)</li>
 * 	<li>在RestContext中放入 {@link XlsResponseProcessor#XLS_PROPERTIES_MAPPING} 指定导出excel的表头配置</li>
 * </ul>
 * 	表头配置格式:
 * <p>
 * 	name:姓名,gender:性别,joinDate:入职日期|yyyy年MM月dd日
 * </p>
 * 
 * @author Rory
 * @date Feb 21, 2012
 * @version $Id: XlsResponseProcessor.java 5 2014-01-15 13:55:28Z yedf $
 */
@Component
public class XlsResponseProcessor extends AbstractResponseProcessor {
	
	private final Logger logger = LoggerFactory.getLogger(XlsResponseProcessor.class);

	/**
	 * 
	 */
	public static final String XLS_FILE_NAME = "_XLS_FILE_NAME";

	/**
	 * 生成excel的类型在RestContext中的 key 值 {@code _XLS_TYPE}
	 */
	public static final String XLS_TYPE = "_XLS_TYPE";

	/**
	 * 生成的excel sheet的名称在 RestContext中的 key 值 {@code _SHEET_NAME}
	 */
	public static final String SHEET_NAME = "_SHEET_NAME";

	/**
	 * 将表头的定义映射放到RestContext中的 {@code _XLS_PROPERTIES_MAPPING} key中。
	 */
	public static final String XLS_PROPERTIES_MAPPING = "_XLS_PROPERTIES_MAPPING";

	public static final String NAME = "xls";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object process(Object input) {
		String mappingConfig = (String) RestSessionFactory.getCurrentContext().getAttribute(XLS_PROPERTIES_MAPPING);
		Assert.isTrue(StringUtils.isNotBlank(mappingConfig), "xls properties mapping is required, please set in RestContext.");
		String sheetName = (String) RestSessionFactory.getCurrentContext().getAttribute(SHEET_NAME);
		String xlsType = (String) RestSessionFactory.getCurrentContext().getAttribute(XLS_TYPE);
		String fileName = (String) RestSessionFactory.getCurrentContext().getAttribute(XLS_FILE_NAME);
		if (StringUtils.equalsIgnoreCase("xls", xlsType)) {
			xlsType = "xls";
		} else {
			xlsType = "xlsx";
		}
		if (StringUtils.isBlank(fileName)) {
			fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		}
		fileName += "." + xlsType;
		if (input == null) {
			return null;
		} else if (ResultSet.class.isInstance(input) && ResultSet.class.cast(input).getRowCount() > 0) {
			return generateExcel(mappingConfig, sheetName, xlsType, fileName, null, ResultSet.class.cast(input));
		} else if (List.class.isInstance(input)) {
			List<?> inputList = List.class.cast(input);
			return generateExcel(mappingConfig, sheetName, xlsType, fileName, inputList, null);
		} else if (PaginationSupport.class.isInstance(input)) {
			PaginationSupport<?> ps = PaginationSupport.class.cast(input);
			if (ps.getItems() != null && !ps.getItems().isEmpty()) {
				return generateExcel(mappingConfig, sheetName, xlsType, fileName, ps.getItems(), null);
			}
		} else if(Map.class.isInstance(input)) {
			//多个sheets处理
			@SuppressWarnings("unchecked")
			Map<String,List<?>> sheetMap = (Map<String,List<?>>) input;
			return generateExcel(mappingConfig, xlsType, fileName, sheetMap, null);
		}
		return null;
	}
	

	/**
	 * @param mappingConfig
	 * @param sheetName
	 * @param xlsType
	 * @param fileName
	 * @param inputList
	 * @param resultSet
	 * @return
	 */
	protected Object generateExcel(String mappingConfig, String xlsType, String fileName, Map<String,List<?>> sheetMap, ResultSet resultSet) {
		Map<String, String> mappingMap = parseMappingMap(mappingConfig);
		Map<String, String> formatterMap = parseFormatterMap(mappingConfig);
		List<String> fields = new ArrayList<String>();
		Workbook wb = null;
		if ("xls".equals(xlsType)) {
			wb = new HSSFWorkbook();
		} else {
			wb = new XSSFWorkbook();
		}
		CreationHelper creationHelper = wb.getCreationHelper();
		
		Progress progress = Progress.currentProgressInThread();
		if(progress!=null) {
			progress.setDescription("正在导出至Excel文件");
			progress.setValue(0);
		}
		
		for(Entry<String, List<?>> sheetEntry : sheetMap.entrySet()) {
			
			String sheetName = sheetEntry.getKey();
			List<?> inputList = sheetEntry.getValue();

			Sheet sheet = wb.createSheet(sheetName);
			Font headerFont = wb.createFont();
			headerFont.setFontHeightInPoints((short)12);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
			headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
			CellStyle headerCellStype = wb.createCellStyle();
			headerCellStype.setFont(headerFont);
			headerCellStype.setBorderBottom(CellStyle.BORDER_THIN);
			
			int startRow = 0;
			if (mappingMap != null && !mappingMap.isEmpty()) {
				Row header = sheet.createRow(0);
				header.setHeightInPoints(20f);
				startRow = 1;
				int col = 0;
				for (Map.Entry<String, String> entry : mappingMap.entrySet()) {
					fields.add(entry.getKey());
					Cell cell = header.createCell(col);
					cell.setCellStyle(headerCellStype);
					cell.setCellValue(entry.getValue());
					col ++;
				}
			}
			
			if (inputList != null) {
				fillData(inputList, formatterMap, fields, wb, creationHelper, sheet, startRow);
			} else {
				fillData(resultSet, formatterMap, fields, wb, creationHelper, sheet, startRow);
			}
			
			for (int i = 0; i < mappingMap.size(); i ++) {
				sheet.autoSizeColumn(i);
				int width = sheet.getColumnWidth(i);
				int newWidth = new Double(width * 1.1).intValue();
				if (newWidth > 255 * 256) {
					newWidth = 255 * 256;
				}
				sheet.setColumnWidth(i, newWidth);
			}
			
			if(progress!=null) {
				progress.increment();
			}
			
		}
		
		return toResponseEntity (fileName,wb);
	}
	

	/**
	 * @param mappingConfig
	 * @param sheetName
	 * @param xlsType
	 * @param fileName
	 * @param inputList
	 * @param resultSet
	 * @return
	 */
	protected Object generateExcel(String mappingConfig, String sheetName, String xlsType, String fileName, List<?> inputList, ResultSet resultSet) {
		Map<String, String> mappingMap = parseMappingMap(mappingConfig);
		Map<String, String> formatterMap = parseFormatterMap(mappingConfig);
		List<String> fields = new ArrayList<String>();
		Workbook wb = null;
		if ("xls".equals(xlsType)) {
			wb = new HSSFWorkbook();
		} else {
			wb = new XSSFWorkbook();
		}
		CreationHelper creationHelper = wb.getCreationHelper();
		Sheet sheet = null;
		if (StringUtils.isNotBlank(sheetName)) {
			sheet = wb.createSheet(sheetName);
		} else {
			sheet = wb.createSheet();
		}
	
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short)12);
		headerFont.setColor(IndexedColors.BLUE.getIndex());
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		CellStyle headerCellStype = wb.createCellStyle();
		headerCellStype.setFont(headerFont);
		headerCellStype.setBorderBottom(CellStyle.BORDER_THIN);
		
		int startRow = 0;
		if (mappingMap != null && !mappingMap.isEmpty()) {
			Row header = sheet.createRow(0);
			header.setHeightInPoints(20f);
			startRow = 1;
			int col = 0;
			for (Map.Entry<String, String> entry : mappingMap.entrySet()) {
				fields.add(entry.getKey());
				Cell cell = header.createCell(col);
				cell.setCellStyle(headerCellStype);
				cell.setCellValue(entry.getValue());
				col ++;
			}
		}
		
		Progress progress = Progress.currentProgressInThread();
		if(progress!=null) {
			progress.setDescription("正在导出至Excel文件");
			progress.setTotal(inputList.size());
		}
		
		if (inputList != null) {
			fillData(inputList, formatterMap, fields, wb, creationHelper, sheet, startRow);
		} else {
			fillData(resultSet, formatterMap, fields, wb, creationHelper, sheet, startRow);
		}
		
		for (int i = 0; i < mappingMap.size(); i ++) {
			sheet.autoSizeColumn(i);
			int width = sheet.getColumnWidth(i);
			int newWidth = new Double(width * 1.1).intValue();
			if (newWidth > 255 * 256) {
				newWidth = 255 * 256;
			}
			sheet.setColumnWidth(i, newWidth);
		}
		
		return toResponseEntity (fileName,wb);
	}
	
	protected ResponseEntity<?> toResponseEntity(String fileName, Workbook wb) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			byte[] data = baos.toByteArray();
			headers.setContentLength(data.length);
			headers.set("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\"");
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			IOUtils.closeQuietly(baos);
		}
	}
	
	/**
	 * @param inputList
	 * @param formatterMap
	 * @param fields
	 * @param wb
	 * @param creationHelper
	 * @param sheet
	 * @param startRow
	 */
	@SuppressWarnings("rawtypes")
	private void fillData(List<?> inputList, Map<String, String> formatterMap, List<String> fields, Workbook wb, CreationHelper creationHelper, Sheet sheet,
			int startRow) {
		
		Progress progress = Progress.currentProgressInThread();
		if(progress!=null) {
			progress.setDescription("正在导出至表["+ sheet.getSheetName()+"]");
			progress.setTotal(inputList.size());
			progress.setValue(0);
		}
		
		for (Object obj : inputList) {
			Row row = sheet.createRow(inputList.indexOf(obj) + startRow);
			if (BeanUtils.isBasicInstance(obj)) {
				Cell cell = row.createCell(0);
				setCellValueAndStyle(cell, obj, creationHelper, wb, null);
			} else if (obj instanceof Map) {
				Map map = (Map) obj;
				for (String fieldName : fields) {
					Cell cell = row.createCell(fields.indexOf(fieldName));
					Object value = map.get(fieldName);
					setCellValueAndStyle(cell, value, creationHelper, wb, formatterMap.get(fieldName));
				}
			} else {
				for (String fieldName : fields) {
					Cell cell = row.createCell(fields.indexOf(fieldName));
					Object value = BeanUtils.getValue(obj, fieldName);
					setCellValueAndStyle(cell, value, creationHelper, wb, formatterMap.get(fieldName));
				}
			}
			
			if(progress!=null) {
				progress.increment();
			}
		}
	}
	
	/**
	 * @param inputList
	 * @param formatterMap
	 * @param fields
	 * @param wb
	 * @param creationHelper
	 * @param sheet
	 * @param startRow
	 */
	private void fillData(ResultSet resultSet, Map<String, String> formatterMap, List<String> fields, Workbook wb, CreationHelper creationHelper, Sheet sheet,
			int startRow) {
		for (int i = 0; i < resultSet.getRowCount(); i ++) {
			ResultSet.Row rsRow = resultSet.getRowAt(i);
			Row row = sheet.createRow(i + startRow);
			for (String fieldName : fields) {
				Cell cell = row.createCell(fields.indexOf(fieldName));
				setCellValueAndStyle(cell, rsRow.getValue(fieldName), creationHelper, wb, formatterMap.get(fieldName));
			}
		}
	}

	/**
	 * 解析表头配置
	 * 
	 * @param mappingConfig
	 * @return
	 */
	private Map<String, String> parseMappingMap(String mappingConfig) {
		Map<String, String> mappingMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotBlank(mappingConfig)) {
			String[] strs = StringUtils.split(mappingConfig, ",");
			for (String pair : strs) {
				String[] keyValue = StringUtils.split(pair, ":");
				if (keyValue.length != 2) {
					logger.error(String.format("wrong mapping config:%s use gender:姓名 format.", pair));
				} else {
					String value = keyValue[1];
					if (value.indexOf("|") != -1) {
						value = StringUtils.substringBefore(value, "|");
					}
					mappingMap.put(keyValue[0], value);
				}
			}
		}
		return mappingMap;
	}
	
	/**
	 * 解析表头配置
	 * 
	 * @param mappingConfig
	 * @return
	 */
	private Map<String, String> parseFormatterMap(String mappingConfig) {
		Map<String, String> formatterMap = new LinkedHashMap<String, String>();
		if (StringUtils.isNotBlank(mappingConfig)) {
			String[] strs = StringUtils.split(mappingConfig, ",");
			for (String pair : strs) {
				String[] keyValue = StringUtils.split(pair, ":");
				if (keyValue.length != 2) {
					logger.error(String.format("wrong mapping config:%s use joinDate:入职日期|yyyy年MM月dd日 format.", pair));
				} else {
					String value = keyValue[1];
					if (value.indexOf("|") != -1) {
						value = StringUtils.substringAfter(value, "|");
						formatterMap.put(keyValue[0], value);
					}
				}
			}
		}
		return formatterMap;
	}
	

	/**
	 * @param cell
	 * @param obj
	 * @param creationHelper
	 * @param workbook
	 * @param formatter
	 */
	private void setCellValueAndStyle(Cell cell, Object obj, CreationHelper creationHelper, Workbook workbook, String formatter) {
		CellStyle cellStyle = workbook.createCellStyle();
		if (obj == null) {
			cell.setCellValue("");
		} else if (int.class.isInstance(obj) || Integer.class.isInstance(obj)) {
			cell.setCellValue((Integer) obj);
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("########"));
		} else if (boolean.class.isInstance(obj) || Boolean.class.isInstance(obj)) {
			cell.setCellValue((Boolean) obj);
		} else if (double.class.isInstance(obj) || Double.class.isInstance(obj)) {
			cell.setCellValue((Double) obj);
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("######0.00"));
		} else if (float.class.isInstance(obj) || Float.class.isInstance(obj)) {
			cell.setCellValue((Float) obj);
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("######0.00"));
		} else if (Date.class.isInstance(obj)) {
			if (StringUtils.isBlank(formatter)) {
				formatter = "yyyy-MM-dd";
			}
			cell.setCellValue(DateFormatUtils.format((Date) obj, formatter));
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("text"));
		} else if (Calendar.class.isInstance(obj)) {
			if (StringUtils.isBlank(formatter)) {
				formatter = "yyyy-MM-dd";
			}
			cell.setCellValue(DateFormatUtils.format((Calendar) obj, formatter));
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("text"));
		} else if (Enum.class.isInstance(obj)) {
			String enumName = Enum.class.cast(obj).name();
			String value = MessageUtils.getLocaleMessage(obj.getClass().getName() + "." + enumName, enumName);
			cell.setCellValue(creationHelper.createRichTextString(value));
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("text"));
		} else if (String.class.isInstance(obj)) {
			cell.setCellValue(creationHelper.createRichTextString((String) obj));
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("text"));
		} else {
			cell.setCellValue(String.valueOf(obj));
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat("text"));
		}
		cell.setCellStyle(cellStyle);
	}

}
