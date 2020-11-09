/**
 * Copyright 2004-2009  Co., Ltd. All rights reserved.
 */
package com.taole.framework.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author rory
 * @since Jul 22, 2009 1:42:57 PM
 * @version $Id: ZipUtil.java 5 2014-01-15 13:55:28Z yedf $
 */
public class ZipUtil {

	private ZipUtil() {
	}

	/**
	 * 指定每个source文件在zip包中的目录结构,eg:
	 * <ol>
	 * <li>打包{@code /home/xxx/download} 目录，path为 {@code backup/20090722}, 最后生成的包中就为 {@code backup/20090722/download}</li>
	 * </ol>
	 * 
	 * @param zipFile
	 *            最终生成的zip包文件
	 * @param sources
	 *            需要打包的目录，文件列表
	 * @param paths
	 */
	public static void zip(File zipFile, String[] sources, String[] paths) {
		FileOutputStream dest = null;
		ZipArchiveOutputStream out = null;
		try {
			dest = new FileOutputStream(zipFile);
			out = new ZipArchiveOutputStream(new BufferedOutputStream(dest));
			out.setUseZip64(Zip64Mode.Always);
			out.setEncoding("UTF-8");
			for (int i = 0; i < sources.length; i++) {
				File f = new File(sources[i]);
				addEntry(f, paths[i], out);
			}
			out.closeArchiveEntry();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(dest);
		}
	}

	/**
	 * 以默认目录结构打包，eg:
	 * <ol>
	 * <li>打包 {@code /home/xxx/download} 目录,最后生成的包中包含的就是 {@code download目录}.</li>
	 * <li>打包 {@code /home/xxx/1.txt, /home/xxx/2.txt} 两文件，最后生成的包中包含的就是 {@code 1.txt, 2.txt}</li>
	 * </ol>
	 * 
	 * @param zipFile
	 *            最终生成的zip包文件
	 * @param sources
	 *            需要打包的目录，文件列表
	 */
	public static void zip(File zipFile, String[] sources) {
		List<String> paths = new ArrayList<String>();
		for (int i = 0; i < sources.length; i++) {
			paths.add("");
		}
		zip(zipFile, sources, paths.toArray(new String[sources.length]));
	}
	
	/**
	 * 打包资源到文件 {@code zipFile}。
	 * 这里可以是个文件，也可以是个目录
	 * 
	 * @param zipFile
	 * @param resource
	 * @see #zip(File, String[])
	 */
	public static void zip(File zipFile, String resource) {
		zip(zipFile, new String[]{resource});
	}

	/**
	 * 解压压缩文件，这里默认用GBK编码初始化ZipFile是为了能兼容老的zip格式，支持解压从windows下打包的含中文文件名的压缩包。
	 * 
	 * @param file
	 * @param dir
	 * @throws IOException
	 */
	public static void unZip(File file, String dir) throws IOException {
		ZipFile zipFile = null;
		try {
			if (!dir.endsWith(File.separator)) {
				dir += File.separator;
			}
			zipFile = new ZipFile(file, "GBK");
			Enumeration<ZipArchiveEntry> emu = zipFile.getEntries();
			while (emu.hasMoreElements()) {
				ZipArchiveEntry entry = emu.nextElement();
				if (entry.isDirectory()) {
					new File(dir + entry.getName()).mkdirs();
					continue;
				}
				file = new File(dir + entry.getName());
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}

				FileOutputStream output = new FileOutputStream(file);
				InputStream input = zipFile.getInputStream(entry);
				try {
					IOUtils.copy(input, output);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
			}
			zipFile.close();
		} catch (IOException e) {
			throw e;
		} finally {
			ZipFile.closeQuietly(zipFile);
		}

	}

	private static void addEntry(File file, String path, ZipArchiveOutputStream out) throws IOException {
		if (!file.exists() || isLink(file)) {
			return;
		}
		String entryName = null;
		if (StringUtils.isNotBlank(path)) {
			entryName = path + File.separator + file.getName();
		} else {
			entryName = file.getName();
		}
		if (file.isDirectory()) {
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File f = new File(file + File.separator + files[i]);
				addEntry(f, entryName, out);
			}
		} else {
			FileInputStream input = new FileInputStream(file);
			ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
			out.putArchiveEntry(entry);
			try {
				IOUtils.copy(input, out);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(input);
			}
		}
	}

	public static boolean isLink(final File file) throws IOException {
		if (file == null || !file.exists()) {
			return false;
		}
		return !file.getCanonicalFile().equals(file.getAbsoluteFile());
	}
}
