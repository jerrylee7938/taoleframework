/**
 * Project:taole-heaframework
 * File:ZipUtilTest.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.Maps;
import com.taole.framework.util.MD5Util;
import com.taole.framework.util.ZipUtil;

/**
 * @author Rory
 * @date Mar 24, 2012
 * @version $Id: ZipUtilTest.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ZipUtilTest {

	
	@Test
	public void testZipAndUnZip() throws Exception {
		URL url = ZipUtilTest.class.getResource("/zip");
		File file = ResourceUtils.getFile(url);
		Map<String, String> md5Map = Maps.newHashMap();
		for (File f : file.listFiles()) {
			md5Map.put(f.getName(), MD5Util.digest(IOUtils.toByteArray(new FileInputStream(f))));
		}
		System.out.println(md5Map);
		String tempDir = System.getProperty("java.io.tmpdir");
		if (!tempDir.endsWith(File.separator)) {
			tempDir += File.separator;
		}
		String zipFileName = tempDir + "test.zip";
		ZipUtil.zip(new File(zipFileName), new String[]{file.getAbsolutePath()});
		
		String unzipedDir = tempDir + "unziped";
		FileUtils.deleteQuietly(new File(unzipedDir));
		
		ZipUtil.unZip(new File(zipFileName), unzipedDir);
		
		FileUtils.deleteQuietly(new File(zipFileName));
		
		File dir = new File(unzipedDir + File.separator + "zip");
		for (File f : dir.listFiles()) {
			assertEquals(md5Map.get(f.getName()), MD5Util.digest(IOUtils.toByteArray(new FileInputStream(f))));
		}
		FileUtils.deleteQuietly(new File(unzipedDir));
	}
}
