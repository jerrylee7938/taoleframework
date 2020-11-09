package com.taole.toolkit.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.taole.toolkit.filesystem.domain.Files;

/**
 * SpringVmc文件上下载工具类，直接调用，返回的是entity包下file实体对象直接获取相应属性即可
 * 
 * @author CJ
 */
@SuppressWarnings("ALL")
public class FileUploadAndDownLoad {
	/**
	 * 文件上传，支持多文件
	 * 
	 * @param imagePath
	 * @param request
	 * @param response
	 * @return
	 */

	Files files = null;

	// 返回與表單相對應的文件及名稱
	static Map<String, List<Files>> map = new HashMap<String, List<Files>>();
	List<Files> fileDetailList = new ArrayList<Files>();

	public static Map<String, List<Files>> uploader(String filePath,
			HttpServletRequest request, HttpServletResponse response) {

		CommonsMultipartResolver resolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = servletRequest.getFileNames();
			while (iterator.hasNext()) {
				CommonsMultipartFile file = (CommonsMultipartFile) servletRequest
						.getFile(iterator.next());

				byte filesize[] = file.getBytes();
				if (filesize.length > 0) {
					// 获取实例
					FileUploadAndDownLoad uploadAndDownLoad = new FileUploadAndDownLoad();
					/**
					 * 文件屬性begin
					 */
					uploadAndDownLoad.files = new Files();
					// 上傳實際名稱
					String fileName = new Date().getTime()
							+ file.getOriginalFilename();
					uploadAndDownLoad.files.setFileNameOfOld(file
							.getOriginalFilename());

					uploadAndDownLoad.files.setFilePath(filePath
							+ File.separator + fileName);
					uploadAndDownLoad.files.setFiledName(file.getFileItem()
							.getFieldName());
					uploadAndDownLoad.files.setFileName(fileName);
					uploadAndDownLoad.files.setFileSize(file.getSize());

					/**
					 * end
					 */
					// 文件信息集填充

					uploadAndDownLoad.fileDetailList = new ArrayList<Files>();

					uploadAndDownLoad.fileDetailList
							.add(uploadAndDownLoad.files);

					File file2 = new File(uploadAndDownLoad.files.getFilePath());

					if (file2.exists()) {
						if (file2.delete()) {
							try {
								file.transferTo(file2);
								// 完成填充
								map.put(uploadAndDownLoad.files.getFiledName(),
										uploadAndDownLoad.fileDetailList);
								// return fileName;
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								break;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								break;
							}
						}
					} else {
						try {
							file.transferTo(file2);
							map.put(uploadAndDownLoad.files.getFiledName(),
									uploadAndDownLoad.fileDetailList);
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}

					}

				}

			}
		}
		return map;

	}

	/**
	 * 檢查文件是否缺失
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String uploadJudge(HttpServletRequest request,
			HttpServletResponse response) {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = servletRequest.getFileNames();
			if (iterator.hasNext()) {
				MultipartFile file = servletRequest.getFile(iterator.next());
				String fileName = new Date().getTime()
						+ file.getOriginalFilename();
				return fileName;
			}
		}
		return "null";
	}

	/**
	 * 文件下載 注意：路径包括文件名称
	 * 
	 * @param filePath
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String download(String filePath, String fileName,
			HttpServletRequest request, HttpServletResponse response,
			String contentType) throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		// String ctxPath =
		// request.getSession().getServletContext().getRealPath(File.separator)
		// + File.separator + filePath + File.separator;
		String downLoadPath = filePath;
		System.out.println(downLoadPath);
		try {
			long fileLength = new File(downLoadPath).length();
			// application/x-zip-compressed
			if (StringUtils.isEmpty(contentType))
				response.setContentType("application/x-msdownload;");
			else
				response.setContentType(contentType);
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

}