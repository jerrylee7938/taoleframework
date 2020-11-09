package com.taole.toolkit.util.excel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.taole.toolkit.util.PathUtil;
import jxl.*;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

public class XlsJxlAction {
	
	//特殊名单
	private final static String SPECIAL_NAME = "specialReport.xls";
	//稳定性评估
	private final static String STABILITY_NAME = "stabilityReport.xls";
	//商品消费评估
	private final static String CONSUMPTION_NAME = "consumptionReport.xls";
	//月度收支等级
	private final static String ACM_NAME = "acmReport.xls";
	//媒体阅览评估
	private final static String MEDIA_NAME = "mediaReport.xls";
	//支付消费评估
	private final static String PAYCONSUMPTION_NAME = "payConsumption.xls";
	//百融评分
	private final static String BRCREDIT_POINT = "brcreditPoint.xls";
	//反欺诈规则
	private final static String RULE = "rule.xls";

	public Workbook getWorkbook(File f) throws Exception {
		return Workbook.getWorkbook(f);
	}

	public Workbook getWorkbook(InputStream os) throws Exception {
		return Workbook.getWorkbook(os);
	}

	public WritableWorkbook getWritableWorkbook(File f) throws Exception {
		return Workbook.createWorkbook(f);
	}

	public WritableWorkbook getWritableWorkbook(OutputStream os) throws Exception {
		return Workbook.createWorkbook(os);
	}
	
	/**
	 * 生成特殊名单Excel
	 * @param titles
	 * @param rowList
	 * @throws Exception 
	 */
	public void generateSpecialXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList, String[] falseTitles) throws Exception{

		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ SPECIAL_NAME));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("特殊名单核查", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

		for (int i = 0; i < falseTitles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, 0, falseTitles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		
		int rowIndex = 1; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}
		
		sheet1.mergeCells(0, 0, 0, 1); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(1, 0, 5, 0); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(6, 0, 10, 0); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(11, 0, 12, 0); // 从第几列第几行，合并到第几列第几行

		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}
	
	public void generateRuleXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList, String[] falseTitles) throws Exception{

		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ RULE));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("反欺诈规则", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

		for (int i = 0; i < falseTitles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, 0, falseTitles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		
		int rowIndex = 1; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}
		
		sheet1.mergeCells(0, 0, 1, 0); // 从第几列第几行，合并到第几列第几行

		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}
	
	/**
	 * 生成稳定性评估Excel
	 * @param titles
	 * @param rowList
	 * @throws Exception 
	 */
	public void generateStabilityXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList) throws Exception{


		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ STABILITY_NAME));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("稳定性评估", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

		int rowIndex = 0; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}

		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}
	
	public void generateBrcreditPointXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList) throws Exception{


		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ BRCREDIT_POINT));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("百融评分", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

		int rowIndex = 0; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}

		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}
	
	/**
	 * 生成商品消费评估Excel
	 * @param titles
	 * @param rowList
	 * @throws Exception 
	 */
	public void generateConsumptionXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList, String[] falseTitles) throws Exception{


		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ CONSUMPTION_NAME));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("商品消费评估", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
		
		for (int i = 0; i < falseTitles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, 0, falseTitles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		
		int rowIndex = 1; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}

		sheet1.mergeCells(0, 0, 3, 0); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(4, 0, 5, 0); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(6, 0, 7, 0); // 从第几列第几行，合并到第几列第几行
		
		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}
	
	/**
	 * 生成月度收支等级Excel
	 * @param titles
	 * @param rowList
	 * @throws Exception 
	 */
	public void generateAcmXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList, String[] cardIndex) throws Exception{


		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ ACM_NAME));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("月度收支等级", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

		for (int i = 0; i < cardIndex.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, 0, cardIndex[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		
		String[] secondFalseTitles = {"过去18个月中", "信用卡","","","","","储蓄卡","","","","贷款金额"};
		for (int i = 0; i < secondFalseTitles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, 1, secondFalseTitles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		
		int rowIndex = 2; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}
		
		sheet1.mergeCells(0, 1, 0, 2); // 从第几列第几行，合并到第几列第几行
		
		sheet1.mergeCells(1, 0, 10, 0); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(1, 1, 5, 1); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(6, 1, 9, 1); // 从第几列第几行，合并到第几列第几行

		sheet1.mergeCells(10, 1, 10, 2); // 从第几列第几行，合并到第几列第几行

		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}
	
	/**
	 * 生成媒体阅览评估Excel文件
	 * @param titles
	 * @param rowList
	 * @throws Exception 
	 */
	public void generateMediaXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList) throws Exception{


		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ MEDIA_NAME));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("媒体阅览评估", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

		int rowIndex = 0; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}

		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}
	
	/**
	 * 生成支付消费评估Excel
	 * @param titles
	 * @param rowList
	 * @throws Exception 
	 */
	public void generatePayConsumptionXls(HttpServletRequest request, String id, String[] titles, List<String[]> rowList) throws Exception{

		XlsJxlAction jxlAction = new XlsJxlAction();
		 WritableWorkbook book = jxlAction.getWritableWorkbook(new File(PathUtil.getRiskControlDir(request, id)+ PAYCONSUMPTION_NAME));
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		WritableSheet sheet1 = book.createSheet("支付消费评估", 0);
		
		/**
		 * 定义单元格样式
		 */
		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
		WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);

		// 定义title单元格的样式1
		WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
		wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
		wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

		// 定义数据单元格的样式1
		WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
		wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
		wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
		wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

		String[] falseTitles = {"过去12个月中", "消费金额", "消费次数","消费金额最多商户类型","","","消费次数最多商户类型","","", "最多消费次数城市", "夜消费金额", "夜消费次数" };
		for (int i = 0; i < falseTitles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, 0, falseTitles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		
		int rowIndex = 1; // 行号索引
		
		int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

		// 建立标题行
		for (int i = 0; i < titles.length; i++) {
			sheet1.setColumnView(i, 15); // 设置每列的宽度
			Label title = new Label(i, rowIndex, titles[i], wcf_title1);
			sheet1.addCell(title); // 将单元格放入工作表中
		}
		rowIndex = rowIndex + 1;
		for (int i = 0; i < startIndexsForColumn.length; i++) {
			startIndexsForColumn[i] = 1;
		}
		
		//添加数据
		String[] prevRowData = null;
		for (int i = 0; i < rowList.size(); i++) {// 行数据循环
			String[] rowData = rowList.get(i);
			for (int j = 0; j < rowData.length; j++) { // 列数据循环
				Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
				sheet1.addCell(data);
			}

			if (prevRowData != null) {
				for (int m = 0; m < rowData.length; m++) {
					if (prevRowData[m].equals(rowData[m])) {// 需要合并
						sheet1.mergeCells(m, startIndexsForColumn[m], m, startIndexsForColumn[m] + 1); // 从第几列第几行，合并到第几列第几行
						startIndexsForColumn[m] = startIndexsForColumn[m] + 1; // 对应列的起始行号+1
					} else {// 后面的列不用再比较，后面列的起始行索引全部更新成当前行号
						for (int n = m; n < rowData.length; n++) {
							startIndexsForColumn[n] = rowIndex;
						}
						break;
					}
				}
			}

			prevRowData = rowData;
			rowIndex = rowIndex + 1;
		}
		
		sheet1.mergeCells(0, 0, 0, 1); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(1, 0, 1, 1); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(2, 0, 2, 1); // 从第几列第几行，合并到第几列第几行

		sheet1.mergeCells(3, 0, 5, 0); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(6, 0, 8, 0); // 从第几列第几行，合并到第几列第几行
		
		sheet1.mergeCells(9, 0, 9, 1); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(10, 0, 10, 1); // 从第几列第几行，合并到第几列第几行
		sheet1.mergeCells(11, 0, 11, 1); // 从第几列第几行，合并到第几列第几行
		// 写入数据并关闭文件
		book.write();
		book.close();
	
	}

	/**
	 * 生成EXCEL文件
	 * @param xlsObject
	 * @param book
	 * @param sheetName
	 */
	public void generateXls(XlsObject xlsObject, WritableWorkbook book, String sheetName) {

		try {
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet1 = book.createSheet(sheetName, 0);

			/**
			 * 定义单元格样式
			 */
			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 字体格式 ,大小,粗体,斜体 ,下划线,颜色
			WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			// 定义title单元格的样式1
			WritableCellFormat wcf_title1 = new WritableCellFormat(wf_title);
			wcf_title1.setBackground(jxl.format.Colour.LIGHT_BLUE); // 设置单元格的背景颜色
			wcf_title1.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
			wcf_title1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框

			// 定义数据单元格的样式1
			WritableCellFormat wcf_table1 = new WritableCellFormat(wf_table);
			wcf_table1.setBackground(jxl.format.Colour.LIGHT_GREEN);
			wcf_table1.setAlignment(jxl.format.Alignment.CENTRE);
			wcf_table1.setWrap(true);// 通过调整宽度和高度自动换行
			wcf_table1.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

			int rowIndex = 0; // 行号索引

			String[] titles = xlsObject.getXlsTitles();
			int[] startIndexsForColumn = new int[titles.length];// 对于每一列，合并的起始行号索引

			// 建立标题行
			for (int i = 0; i < titles.length; i++) {
				sheet1.setColumnView(i, 15); // 设置每列的宽度
				Label title = new Label(i, rowIndex, titles[i], wcf_title1);
				sheet1.addCell(title); // 将单元格放入工作表中
			}
			rowIndex = rowIndex + 1;
			for (int i = 0; i < startIndexsForColumn.length; i++) {
				startIndexsForColumn[i] = 1;
			}

			// 添加数据
			List<String[]> rowList = xlsObject.getRowList();
			for (int i = 0; i < rowList.size(); i++) {// 行数据循环
				String[] rowData = rowList.get(i);
				for (int j = 0; j < rowData.length; j++) { // 列数据循环
					Label data = new Label(j, rowIndex, rowData[j], wcf_table1);
					sheet1.addCell(data);
				}

				rowIndex = rowIndex + 1;
			}

			// 写入数据并关闭文件
			book.write();
			book.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * main测试类
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			XlsJxlAction jxlAction = new XlsJxlAction();
//			// 生成xls
//			String[] titles =  {"","不良","短期逾期","欺诈","失联","拒绝","不良","短期逾期","欺诈","失联","拒绝","失信人名单","被执行人名单"};
//			List<String[]> dataList = new ArrayList<String[]>();
//			String[] data = { "通过身份证查询", "1", "1", "1", "1","1","1","1","1","1","1","1","1"};
//			dataList.add(data);
//			String[] falseTitles = {"", "银行","","","","","小贷/P2P","","","","","法院黑名单",""};
//			jxlAction.generateSpecialXls(titles, dataList, falseTitles);
			
			
			
			
			
			
//			XlsObject xlsObject = jxlAction.getData();// 存放至excel中的数据
//			 WritableWorkbook book = jxlAction.getWritableWorkbook(new
//			 File("d:\\wbsStruts1.xls"));
//			 jxlAction.generateWbsXls(xlsObject, book);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试数据
	 * 
	 * @return
	 */
	private XlsObject getData() {
		XlsObject xlsObject = new XlsObject();
		String[] titles = { "一级", "编码", "二级", "编码", "三级", "编码", "四级", "编码" };
		List<String[]> dataList = new ArrayList<String[]>();

		String[] data1 = { "克拉苏气田主干道路工程第三标段", "10", "项目", "1001", "临时用电", "100101", "电力线路", "1001010001" };
		dataList.add(data1);
		String[] data2 = { "克拉苏气田主干道路工程第三标段", "10", "项目", "1001", "临时用电", "100101", "场地内用电", "1001010002" };
		dataList.add(data2);
		String[] data3 = { "克拉苏气田主干道路工程第三标段", "10", "项目", "1001", "临时用电", "100101", "电力维护", "1001010003" };
		dataList.add(data3);
		String[] data4 = { "克拉苏气田主干道路工程第三标段", "10", "项目", "1001", "道路施工用水", "100102", "供水管线", "1001020001" };
		dataList.add(data4);
		String[] data5 = { "克拉苏气田主干道路工程第三标段", "10", "项目", "1001", "道路施工用水", "100102", "水费", "1001020002" };
		dataList.add(data5);
		String[] data6 = { "克拉苏气田主干道路工程第三标段", "10", "项目", "1001", "道路施工用水", "100102", "抽水", "1001020003" };
		dataList.add(data6);
		String[] data7 = { "克拉苏气田主干道路工程第三标段", "10", "项目", "1001", "道路施工用水", "100102", "运水", "1001020004" };
		dataList.add(data7);

		xlsObject.setXlsTitles(titles);
		xlsObject.setRowList(dataList);
		return xlsObject;
	}

}
