package com.taole.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import com.google.common.collect.Maps;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

public final class MimeUtils {
	
	/**
	 * office 2007/2010 excel 格式的 MimeType {@code application/vnd.openxmlformats-officedocument.spreadsheetml.sheet}
	 */
	public static final String XLSX_MIMETYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/**
	 * office 2000/2003 excel 格式的 MimeType {@code application/vnd.ms-excel}
	 */
	public static final String XLS_MIMETYPE = "application/vnd.ms-excel";
	
	/**
	 * office 2007/2010 word 格式的 MimeType {@code application/vnd.openxmlformats-officedocument.wordprocessingml.document}
	 */
	public static final String DOCX_MIMETYPE_OFFICE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	
	/**
	 * wps office docx word 格式的 MimeType {@code application/wps-office.docx}
	 */
	public static final String DOCX_MIMETYPE_WPS = "application/wps-office.docx";
	
	/**
	 * wps office doc word 格式的 MimeType {@code application/wps-office.doc}
	 */
	public static final String DOC_MIMETYPE_WPS = "application/wps-office.doc";
	
	/**
	 * wps office xls word 格式的 MimeType {@code application/wps-office.xls}
	 */
	public static final String XLS_MIMETYPE_WPS = "application/wps-office.xls";
	
	/**
	 * wps office xlsx word 格式的 MimeType {@code application/wps-office.xlsx}
	 */
	public static final String XLSX_MIMETYPE_WPS = "application/wps-office.xlsx";
	
	/**
	 * office 2000/2003 word 格式的 MimeType {@code application/msword}
	 */
	public static final String DOC_MIMETYPE = "application/msword";
	
	public final static Set<String> OFFICE_DOCUMENT_MIMETYPES;
	
	public final static Map<String, String> MIMETYPE_MAP;
	
	private static Map<String, Collection<?>> extMimeMap = new HashMap<String, Collection<?>>();
	
	private MimeUtils() {
	}

	static {
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.WindowsRegistryMimeDetector");
		OFFICE_DOCUMENT_MIMETYPES = new LinkedHashSet<String>();
		OFFICE_DOCUMENT_MIMETYPES.add(DOC_MIMETYPE);
		OFFICE_DOCUMENT_MIMETYPES.add(DOC_MIMETYPE_WPS);
		OFFICE_DOCUMENT_MIMETYPES.add(DOCX_MIMETYPE_OFFICE);
		OFFICE_DOCUMENT_MIMETYPES.add(DOCX_MIMETYPE_WPS);
		OFFICE_DOCUMENT_MIMETYPES.add(XLS_MIMETYPE);
		OFFICE_DOCUMENT_MIMETYPES.add(XLS_MIMETYPE_WPS);
		OFFICE_DOCUMENT_MIMETYPES.add(XLSX_MIMETYPE);
		OFFICE_DOCUMENT_MIMETYPES.add(XLSX_MIMETYPE_WPS);
		OFFICE_DOCUMENT_MIMETYPES.add("application/vnd.ms-powerpoint");
		OFFICE_DOCUMENT_MIMETYPES.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
		MIMETYPE_MAP = new HashMap<String, String>();
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("eu/medsea/mimeutil/mime-types.properties"));
			for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {
				String propertyName = (String) e.nextElement();
				String value = properties.getProperty(propertyName);
				MIMETYPE_MAP.put(propertyName, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//FIXME 修复mp3 mime类型检测
		extMimeMap.put("mp3", Arrays.asList(new MimeType("audio/mpeg")));
	}

	public static String getMimeType(String path) {
		String ext = FilenameUtils.getExtension(path);
		Collection<?> mimes = extMimeMap.get(ext);
		if (mimes == null) {
			mimes = MimeUtil.getMimeTypes(path);
			extMimeMap.put(ext, mimes);
		}
		return MimeUtil.getMostSpecificMimeType(mimes).toString();
	}
	
	public static String getMimeType(File file) {
		Collection<?> mimeTypes =  MimeUtil.getMimeTypes(file);
		return MimeUtil.getMostSpecificMimeType(mimeTypes).toString();
	}
	
	public static MediaType getMediaType(String path) {
		String type = getMimeType(path);
		String[] mts = type.split("\\/");
		return new MediaType(mts[0], mts[1]);
	}
	
	public static MediaType getMediaType(File file) {
		String type = getMimeType(file);
		String[] mts = type.split("\\/");
		return new MediaType(mts[0], mts[1]);
	}

	/**
	 * 判断文件是不是office文档， xls, doc, ppt..
	 * @param mimeType
	 * @return
	 */
	public static final boolean isOfficeDocuments(String mimeType) {
		return OFFICE_DOCUMENT_MIMETYPES.contains(mimeType);
	}
	
	/**
	 * 判断文件是不是 office 2000/2003 word 格式
	 * @param file
	 * @return
	 */
	public static final boolean isDoc(File file) {
		String mimeType = getMimeType(file);
		return isDoc(mimeType);
	}
	
	/**
	 * 判断文件是不是 office 2000/2003 word 格式
	 * @param mimeType
	 * @return
	 */
	public static final boolean isDoc(String mimeType) {
		if (StringUtils.equals(DOC_MIMETYPE, mimeType) || StringUtils.equals(DOC_MIMETYPE_WPS, mimeType)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断文件是不是 office 2007/2010 word 格式
	 * @param file
	 * @return
	 */
	public static final boolean isDocx(File file) {
		String mimeType = getMimeType(file);
		return isDocx(mimeType);
	}
	
	/**
	 * 判断文件是不是 office 2007/2010 word 格式
	 * @param mimeType
	 * @return
	 */
	public static final boolean isDocx(String mimeType) {
		if (StringUtils.equals(DOCX_MIMETYPE_OFFICE, mimeType) || StringUtils.equals(DOCX_MIMETYPE_WPS, mimeType)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断文件是不是 office 2000/2003 excel 格式
	 * @param file
	 * @return
	 */
	public static final boolean isXls(File file) {
		String mimeType = getMimeType(file);
		return isXls(mimeType);
	}
	
	/**
	 * 判断文件是不是 office 2000/2003 excel 格式
	 * @param mimeType
	 * @return
	 */
	public static final boolean isXls(String mimeType) {
		if (StringUtils.equals(XLS_MIMETYPE, mimeType) || StringUtils.equals(XLS_MIMETYPE_WPS, mimeType)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断文件是不是 office 2007/2010 excel 格式
	 * @param file
	 * @return
	 */
	public static final boolean isXlsx(File file) {
		String mimeType = getMimeType(file);
		return isXlsx(mimeType);
	}
	
	/**
	 * 判断文件是不是 office 2007/2010 excel 格式
	 * @param mimeType
	 * @return
	 */
	public static final boolean isXlsx(String mimeType) {
		if (StringUtils.equals(XLSX_MIMETYPE, mimeType) || StringUtils.equals(XLSX_MIMETYPE_WPS, mimeType)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断文件是不是 图片格式。
	 * @param file
	 * @return
	 */
	public static final boolean isImage(File file) {
		return isImage(getMimeType(file));
	}
	
	/**
	 * 判断类型是不是图片格式。
	 * @param mimeType
	 * @return
	 */
	public static final boolean isImage(String mimeType) {
		if (mimeType != null && mimeType.startsWith("image")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断文件是不是 文本格式。
	 * @param file
	 * @return
	 */
	public static final boolean isText(File file) {
		return isText(getMimeType(file));
	}
	
	/**
	 * 判断类型是不是文本格式。
	 * @param mimeType
	 * @return
	 */
	public static final boolean isText(String mimeType) {
		if (mimeType != null &&  mimeType.startsWith("text")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断文件是不是pdf文件格式。
	 * @param file
	 * @return
	 */
	public static final boolean isPdf(File file) {
		return isPdf(getMimeType(file));
	}
	
	/**
	 * 判断类型是不是pdf文件格式。
	 * @param mimeType
	 * @return
	 */
	public static final boolean isPdf(String mimeType) {
		if (mimeType != null &&  (mimeType.equals("application/pdf") || mimeType.equals("application/x-pdf"))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据mime 类型得到扩展名。
	 * @param mimeType
	 * @return
	 */
	public static final String getExtension(String mimeType) {
		if (StringUtils.isBlank(mimeType)) {
			return null;
		}
		mimeType = StringUtils.trim(mimeType);
		int maxLength = 1;
		Map<String, List<String>> matchMap = Maps.newHashMap();
		for (Map.Entry<String, String> entry : MIMETYPE_MAP.entrySet()) {
			if (StringUtils.contains(entry.getValue(), mimeType)) {
				List<String> mimeList = Arrays.asList(StringUtils.split(entry.getValue(), ","));
				if (mimeList.size() > maxLength) {
					maxLength = mimeList.size();
				}
				matchMap.put(entry.getKey(), mimeList);
			}
		}
		for (int i = 0; i < maxLength; i++) {
			for (Map.Entry<String, List<String>> entry : matchMap.entrySet()) {
				List<String> valueList = entry.getValue();
				if (valueList.size() < (i + 1)) {
					continue;
				}
				if (StringUtils.equals(valueList.get(i), mimeType)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据mime 类型得到扩展名。
	 * @param mimeType
	 * @param suggestExt
	 * @return
	 */
	public static final String getExtension(String mimeType, String suggestExt) {
		if (StringUtils.isBlank(mimeType)) {
			return null;
		}
		mimeType = StringUtils.trim(mimeType);
		int maxLength = 1;
		Map<String, List<String>> matchMap = Maps.newHashMap();
		for (Map.Entry<String, String> entry : MIMETYPE_MAP.entrySet()) {
			if (StringUtils.contains(entry.getValue(), mimeType)) {
				List<String> mimeList = Arrays.asList(StringUtils.split(entry.getValue(), ","));
				if (mimeList.size() > maxLength) {
					maxLength = mimeList.size();
				}
				matchMap.put(entry.getKey(), mimeList);
			}
		}
		if (StringUtils.isNotBlank(suggestExt) && matchMap.containsKey(suggestExt)) {
			return suggestExt;
		}
		for (int i = 0; i < maxLength; i++) {
			for (Map.Entry<String, List<String>> entry : matchMap.entrySet()) {
				List<String> valueList = entry.getValue();
				if (valueList.size() < (i + 1)) {
					continue;
				}
				if (StringUtils.equals(valueList.get(i), mimeType)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println("mime:" + getMimeType("/aaaa/abc.js"));
	}
	
}
