/*
 * @(#)IOUtils.java 0.1 Nov 28, 2007
 * Copyright 2004-2007  Co., Ltd. All rights reserved.
 */

package com.taole.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;


/**
 * Class: IOUtils Remark: TODO
 * 
 * @author: 
 */
public abstract class IOUtils {

	/**
	 * @param is
	 */
	public static void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @param os
	 */
	public static void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @param in
	 */
	public static void close(Reader in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @param os
	 */
	public static void close(Writer out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @param os
	 */
	public static void flush(OutputStream os) {
		if (os != null) {
			try {
				os.flush();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @param os
	 * @param is
	 * @throws IOException
	 */
	public static void copyIOStream(OutputStream os, InputStream is)
			throws IOException {

		int len = 0, bufferSize = 8192;
		byte[] buffer = new byte[bufferSize];
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
	}

	/**
	 * @param os
	 * @param is
	 */
	public static void copyAndCloseIOStream(OutputStream os, InputStream is) {
		try {
			copyIOStream(os, is);
		} catch (IOException e) {
		} finally {
			IOUtils.close(os);
			IOUtils.close(is);
		}
	}

	/**
	 * @param os
	 * @param is
	 */
	public static void copyIOStream(Writer writer, Reader reader)
			throws IOException {

		int len = 0, bufferSize = 8192;
		char[] buffer = new char[bufferSize];
		while ((len = reader.read(buffer)) != -1) {
			writer.write(buffer, 0, len);
		}
	}

	/**
	 * @param os
	 * @param is
	 */
	public static void copyAndCloseIOStream(Writer writer, Reader reader)
			throws IOException {

		try {
			copyIOStream(writer, reader);
		} catch (IOException e) {
		} finally {
			IOUtils.close(writer);
			IOUtils.close(reader);
		}
	}

	public static String inputStreamToString(InputStream is) {
		return inputStreamToString(is, "utf-8");
	}
	
	public static String inputStreamToString(InputStream is, String encoding) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, encoding));
			int bufferSize = 8192, size = -1;
			char[] buffer = new char[bufferSize];
			while ((size = reader.read(buffer)) != -1) {
				sb.append(buffer, 0, size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(is);
			if (reader != null) {
				close(reader);
			}
		}
		return sb.toString();
	}
	
	public static InputStream stringToInputStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	public static byte[] getBytes(InputStream is) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			copyIOStream(os, is);
		} catch (IOException e) {
			e.printStackTrace();
			close(os);
		} finally {
			if (is != null) {
				close(is);
			}
		}
		return os.toByteArray();
	}
}
