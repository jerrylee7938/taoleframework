/**
 * Project:taole-heaframework
 * File:ExcelReaderTest.java
 * Copyright 2004-2013  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.taole.framework.util.ExcelReader;

/**
 * @author rory
 * @date Oct 14, 2013
 * @version $Id: ExcelReaderTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ExcelReaderTest {

	@Test
	public void testExcelReaderReadXlsx() throws Exception {
		List<Map<String, Object>> records = ExcelReader.readToMapList(new File(ExcelReaderTest.class.getResource("/test.xlsx").toURI()), null);
		assertFalse(records.isEmpty());
		assertEquals("13824482356490", records.get(0).get("电话"));
		assertTrue(records.get(0).get("数字2") instanceof Double);
		assertTrue(new Double("492929.22").equals(records.get(0).get("数字")));
		assertTrue(new Double("5").equals(records.get(0).get("数字2")));
		assertTrue(new Double("99202.89").equals(records.get(0).get("数字3")));
		assertTrue(records.get(0).get("日期") instanceof Date);
		assertEquals(DateUtils.parseDate("2013-10-12", "yyyy-MM-dd").getTime(), ((Date) records.get(0).get("日期")).getTime());
	}
	
	@Test
	public void testExcelReaderReadXls() throws Exception {
		List<Map<String, Object>> records = ExcelReader.readToMapList(new File(ExcelReaderTest.class.getResource("/test.xls").toURI()), null);
		assertFalse(records.isEmpty());
		assertEquals("13824482356490", records.get(0).get("电话"));
		assertTrue(records.get(0).get("数字2") instanceof Double);
		assertTrue(new Double("492929.22").equals(records.get(0).get("数字")));
		assertTrue(new Double("5").equals(records.get(0).get("数字2")));
		assertTrue(new Double("99202.89").equals(records.get(0).get("数字3")));
		assertTrue(records.get(0).get("日期") instanceof Date);
		assertEquals(DateUtils.parseDate("2013-10-12", "yyyy-MM-dd").getTime(), ((Date) records.get(0).get("日期")).getTime());
	}
	
}
