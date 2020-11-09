/**
 * Project:taole-heaframework
 * File:CharacterDectorUtils.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.util;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动检测编码 可以自动检测文件的编码，或者上传文本文件的编码。
 * 
 * @author Rory
 * @date Feb 27, 2012
 * @version $Id: CharacterDetectorUtils.java 5 2014-01-15 13:55:28Z yedf $
 */
public final class CharacterDetectorUtils {

	/**
	 * 
	 */
	public static final String DEFAULT_CHARACTER_ENCODING = "utf8";

	private static final Logger logger = LoggerFactory.getLogger(CharacterDetectorUtils.class);

	private CharacterDetectorUtils() {
	}

	/**
	 * 注意这里的InputStream需要支持 {@code markSupported},默认的FileInputStream是不支持 {@code markSupported}的。需要使用 BufferedInputStream和 ByteArrayInputStream 才能进行检测.
	 * 
	 * @param is
	 * @param defaultCharacterEncoding
	 * @return
	 */
	public static String detectEncodingAndClose(InputStream is, String defaultCharacterEncoding) {
		String characterEncoding = defaultCharacterEncoding;

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(UnicodeDetector.getInstance());
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		java.nio.charset.Charset charset = null;
		try {
			charset = detector.detectCodepage(is, is.available());
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (charset != null)
			characterEncoding = charset.name();
		return characterEncoding;
	}

	/**
	 *  注意这里的InputStream需要支持 {@code markSupported},默认的FileInputStream是不支持 {@code markSupported}的。需要使用 BufferedInputStream和 ByteArrayInputStream 才能进行检测.
	 * @param is
	 * @param defaultCharacterEncoding
	 * @return
	 */
	public static String detectEncoding(InputStream is, String defaultCharacterEncoding) {
		String characterEncoding = defaultCharacterEncoding;

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(UnicodeDetector.getInstance());
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		java.nio.charset.Charset charset = null;
		try {
			charset = detector.detectCodepage(is, is.available());
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (charset != null) {
			characterEncoding = charset.name();
		}
		return characterEncoding;
	}

	/**
	 * 
	 * @param is
	 * @return
	 * @see #detectEncodingAndClose(InputStream, String)
	 */
	public static String detectEncodingAndClose(InputStream is) {
		return detectEncodingAndClose(is, DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * 
	 * @param content
	 * @param defaultCharacterEncoding
	 * @return
	 * @see #detectEncoding(InputStream, String)
	 */
	public static String detectEncoding(String content, String defaultCharacterEncoding) {
		InputStream is = IOUtils.toInputStream(content);
		return detectEncodingAndClose(is, defaultCharacterEncoding);
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	public static String detectEncoding(String content) {
		InputStream is = IOUtils.toInputStream(content);
		return detectEncodingAndClose(is, DEFAULT_CHARACTER_ENCODING);
	}

}
