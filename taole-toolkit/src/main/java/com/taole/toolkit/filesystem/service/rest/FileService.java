/**
 * Project:taole-toolkit
 * File:FileService.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.toolkit.filesystem.service.rest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.RequestParameters;
import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.rest.bind.annotation.ParameterVariable;
import com.taole.framework.rest.bind.annotation.ResourceVariable;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.CharacterDetectorUtils;
import com.taole.framework.util.JSONTransformer;
import com.taole.framework.util.MimeUtils;
import com.taole.framework.util.UUID;
import com.taole.framework.util.ZipUtil;
import com.taole.toolkit.ProjectConfig;
import com.taole.toolkit.filesystem.FileSystemPath;
import com.taole.toolkit.filesystem.condition.FileCondition;
import com.taole.toolkit.filesystem.domain.FastDFSFile;
import com.taole.toolkit.filesystem.domain.File;
import com.taole.toolkit.filesystem.manager.FileManager;
import com.taole.toolkit.filesystem.manager.FileSystemManager;
import com.taole.toolkit.filesystem.service.ListenedDiskFileItemFactory;
import com.taole.toolkit.filesystem.service.UploadListener;
import com.taole.toolkit.util.ExcelToHtml;
import com.taole.toolkit.util.PictureSize;
import com.taole.toolkit.util.ThumbUtils;
import com.taole.toolkit.util.Watermark;
import com.taole.toolkit.util.Watermark.Position;
import com.taole.toolkit.util.WatermarkConfig;

/**
 * @author Rory
 * @date Mar 27, 2012
 * @version $Id: FileService.java 9668 2017-05-23 02:06:38Z lizhm $
 */
@RestService(name = ProjectConfig.PREFIX + "File")
public class FileService {

	private final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	FileSystemPath fileSystemPath;

	@Resource(name = "filesystem.maxFileUploadSize")
	Integer maxSize = 2048; // accept file max size

	String[] imagesExts = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

	@Autowired
	Environment environment;
	
	@Value(value = "#{configProperties['" + ProjectConfig.PREFIX + "file.nfs.upload.url']}")
	private String uploadUrl;
	
	@Value(value = "#{configProperties['" + ProjectConfig.PREFIX + "file.nfs.download.url']}")
	private String downloadUrl;
	
	@Value(value = "#{configProperties['fdfs.download.url']}")
	private String fastDFSDownloadUrl;
	
	/**
	 * 生成水印的配置
	 */
	@Autowired(required = false)
	WatermarkConfig watermarkConfig;

	/**
	 * 缓存七天
	 */
	private int maxAge = 7 * 60 * 60 * 24;
	
	public Integer getMaxSize() {
		if (maxSize == null) {
			maxSize = 2048;
		}
		return maxSize;
	}

	@Autowired
	FileSystemManager fileSystemManager;
	
	@Autowired
	FileManager fileManager;

	@Autowired(required = false)
	RestUrlRuler restUrlRuler;

	@ActionMethod(response = "json")
	public Object meta() {
		return BeanUtils.getMeta(File.class);
	}
	
	@ActionMethod
	public Object info(RequestParameters params, HttpServletRequest request) {
		String[] ids = params.getParameter("ids", String[].class);
		if (ids != null && ids.length > 0) {
			JSONArray jsonArray = new JSONArray();
			for (String id : ids) {
				File file = fileSystemManager.getFile(id);
				if (file != null) {
					try {
						JSONObject json = new JSONObject();
						json.put("name", file.getName());
						json.put("id", file.getId());
						json.put("type", file.getMimeType());
						json.put("url", request.getContextPath() + RestUrlRuler.getRestServiceRoot() + "/" + ProjectConfig.PREFIX + "File" + "/" + id + "/");
						json.put("size", file.getSize());
						if (file.getCreateDate() != null) {
							json.put("createDate", DateFormatUtils.format(file.getCreateDate(), JSONTransformer.JSON_DATE_FORMAT));
						}
						if (file.getUpdateDate() != null) {
							json.put("updateDate", DateFormatUtils.format(file.getUpdateDate(), JSONTransformer.JSON_DATE_FORMAT));
						}
						jsonArray.put(json);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			return jsonArray;
		}
		return null;
	}

	@ActionMethod(request = "original", response = "json")
	public Object upload(HttpServletRequest request, HttpServletResponse response, RestContext context) {
		context.setAttribute("_contentType", new MediaType("text", "html", Charset.forName("utf8")));
		Map<String, Object> messages = Maps.newHashMap();
		if (!request.getMethod().equals(HttpMethod.POST.name())) {
			messages.put("error", 1);
			messages.put("success", false);
			messages.put("message", "only POST method support.");
			return messages;
		}
		
		if ((request.getContentLength()/1024) > getMaxSize()) {
			messages.put("error", 1);
			messages.put("success", false);
			messages.put("message", "超出大小限制:" + getMaxSize());
			return messages;
		} else {
			
			UploadListener listener = new UploadListener(request);
			DiskFileItemFactory factory = new ListenedDiskFileItemFactory(listener);
			// maximum size that will be stored in memory
			factory.setSizeThreshold(4096);

			java.io.File repository = new java.io.File(StringUtils.isNotBlank(uploadUrl) ? uploadUrl : fileSystemPath.getRealTempDir());
			
			//如果temp目录不存在则尝试创建
			if (!repository.exists()) {
				repository.mkdir();
			}
			factory.setRepository(repository);
			ServletFileUpload upload = new ServletFileUpload(factory);
			// maximum size before a FileUploadException will be thrown
			upload.setSizeMax(getMaxSize() * 1024);

			String fileId = request.getParameter("fileId");
			String owner = request.getParameter("owner");
			String ignoreMimeType = request.getParameter("ignoreMimeType");
			String type = request.getParameter("type");//文件类型   如：头像、身份证复印件
			String singleFileName = null;
			int imageHeight = -1;
			int imageWidth = -1;
			Map<String, String> paramMap = Maps.newHashMap();
			try {
				List<?> items = upload.parseRequest(request);
				List<FileItem> fileItems = new ArrayList<FileItem>();
				
				for (Object obj : items) {
					FileItem item = (FileItem) obj;
					if (item.isFormField()) {
						if (StringUtils.isBlank(fileId) && StringUtils.equals(item.getFieldName(), "fileId")) {
							fileId = item.getString("UTF-8");
						}
						if (StringUtils.isBlank(owner) && StringUtils.equals(item.getFieldName(), "owner")) {
							owner = item.getString("UTF-8");
						}
						if (StringUtils.isBlank(type) && StringUtils.equals(item.getFieldName(), "type")) {
							type = item.getString("UTF-8");
						}
						if (StringUtils.isBlank(ignoreMimeType) && StringUtils.equals(item.getFieldName(), "ignoreMimeType")) {
							ignoreMimeType = item.getString("UTF-8");
						}
						paramMap.put(item.getFieldName(), item.getString("UTF-8"));
					} else {
						fileItems.add(item);
					}
				}
				context.setAttribute("_upload_params", paramMap);
				List<String> fileIds = new ArrayList<String>();
				Long fileSize = 0L;
				if (fileItems.isEmpty()) {
					messages.put("error", 1);
					messages.put("success", false);
					messages.put("message", "未选择上传文件");
					return messages;
				} else {
					String dateString = DateFormatUtils.format(new Date(), "yyyy-MM");
					String readUrl = null;
					String fileDownloadPrefix = downloadUrl;
					for (FileItem item : fileItems) {
						File file = new File();
						if (fileItems.size() == 1) {
							file.setId(StringUtils.isBlank(fileId) ? UUID.generateUUID() : fileId);
						} else {
							file.setId(UUID.generateUUID());
						}
						String fileName = FilenameUtils.getName(item.getName());
						String ext = FilenameUtils.getExtension(fileName);
						if (StringUtils.isNotBlank(ext)) {
							ext = "." + ext;
						}
						file.setName(fileName);
						file.setSize(item.getSize());
						file.setOwner(owner);
						String contentType = item.getContentType();
						if (StringUtils.isBlank(contentType) || StringUtils.equals("true", ignoreMimeType)) {
							contentType = MimeUtils.getMimeType(fileName);
						}
						file.setMimeType(contentType);
						
						//文件上传路径
						String dirPath = fileSystemPath.getStorageDir() + "/" + dateString + "/";
						
						if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(uploadUrl)){
							
							if(type.equals("1") || type.equals("2") || type.equals("5") || type.equals("0")){
								if(StringUtils.isBlank(ext))
									ext = ".jpg";
							}
							
							if(type.equals("1")){//上传的文件为用户头像
								dirPath = uploadUrl + "/image/avatar/";
								readUrl = "/image/avatar/"+file.getId() + ext;
							}else if(type.equals("2")){//上传的文件为用户身份证
								dirPath = uploadUrl + "/image/IDCard/";
								readUrl = "/image/IDCard/"+file.getId() + ext;
							}else if(type.equals("3")){//语音文件
								if(StringUtils.isBlank(ext))
									ext = ".mp3";
								dirPath = uploadUrl + "/audio/";
								readUrl = "/audio/"+file.getId() + ext;
							}else if(type.equals("4")){//视频文件
								dirPath = uploadUrl + "/video/";
								readUrl = "/video/"+file.getId() + ext;
							}else if(type.equals("5")){//病历图片
								dirPath = uploadUrl + "/image/case/";
								readUrl = "/image/case/"+file.getId() + ext;
							}else if(type.equals("0")){
								dirPath = uploadUrl + "/image/other/";
								readUrl = "/image/other/"+file.getId() + ext;
							}
							
							file.setName(file.getId() + ext);
						}
						
						
						if(fileManager.isFastDFSForFileSystem()){
							
							logger.info("upload file type : " + type);
							
							FastDFSFile fastDFSFile = new FastDFSFile(item.get(),ext.substring(1, ext.length()));
							
					        NameValuePair[] meta_list = new NameValuePair[4];
					        meta_list[0] = new NameValuePair("fileName", item.getName());
					        meta_list[1] = new NameValuePair("fileLength", String.valueOf(item.getSize()));
					        meta_list[2] = new NameValuePair("fileExt", ext);
					        meta_list[3] = new NameValuePair("fileAuthor", owner);
					        
					        String groupName = getFastDFSGroupName(type);
					        String filePath = fileManager.upload(groupName, fastDFSFile,meta_list);
					        readUrl = filePath;
					        fileDownloadPrefix = fastDFSDownloadUrl;
							file.setPath(filePath);
						}else{
							file.setPath(dirPath + file.getId() + ext);
							java.io.File dir = new java.io.File((StringUtils.isNotBlank(type) && StringUtils.isNotBlank(uploadUrl)) ? dirPath : fileSystemPath.getRealPath(dirPath));
							if (!dir.exists()) {
								dir.mkdirs();
							}
							java.io.File destinationFile = new java.io.File((StringUtils.isNotBlank(type) && StringUtils.isNotBlank(uploadUrl)) ? file.getPath() : fileSystemPath.getRealPath(file.getPath()));
							item.write(destinationFile);
							listener.setFileInfo(file);
							
							if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(uploadUrl)){
								file.setPath(readUrl);
							}
							
							if (MimeUtils.isImage(contentType)) {
								FileInputStream imageInputStream = null;
								try {
									imageInputStream = new FileInputStream(destinationFile);
									BufferedImage image = ImageIO.read(imageInputStream);
									if (image != null) {
										imageWidth = image.getWidth();
										imageHeight = image.getHeight();
									}
								} finally {
									IOUtils.closeQuietly(imageInputStream);
								}
							}
						}
						
						fileSystemManager.createFile(file);
						
						if (fileItems.size() == 1) {
							fileId = file.getId();
							singleFileName = file.getName();
							fileSize = file.getSize();
						}
						if (fileItems.size() > 1) {
							fileIds.add(file.getId());
						}
					}

					messages.put("error", 0);
					if (StringUtils.isNotBlank(fileId) && fileIds.isEmpty()) {
						messages.put("fileId", fileId);
						messages.put("name", singleFileName);
						messages.put("url", StringUtils.isNotBlank(readUrl)?fileDownloadPrefix+readUrl:request.getContextPath() + RestUrlRuler.getRestServiceRoot() + "/" + ProjectConfig.PREFIX + "File" + "/" + fileId + "/");
						if(StringUtils.isNotBlank(readUrl))
							messages.put("path", readUrl);
						messages.put("size", fileSize);
						messages.put("ext", singleFileName.substring(singleFileName.lastIndexOf(".")));
						if (imageWidth != -1) {
							messages.put("imageWidth", imageWidth);
						}
						if (imageHeight != -1) {
							messages.put("imageHeight", imageHeight);
						}
					}
					if (!fileIds.isEmpty()) {
						messages.put("fileIds", fileIds);
					}
					messages.put("success", true);
					listener.done();
					return messages;
				}
			} catch (Exception e) {
				listener.error();
				logger.error("upload file error.", e);
				messages.put("error", 1);
				messages.put("success", false);
			}
		}
		messages.put("error", 1);
		messages.put("success", false);
		messages.put("message", "system error...");
		return messages;
	}
	
	/**
	 * 根据文件类型获取文件分组，默认为第一个分组
	 * @param type
	 * @return
	 */
	private String getFastDFSGroupName(String type){
		String groupName = "other";
		if(StringUtils.isNotBlank(type)){
			if(type.equals("1"))
				groupName = "head";
			else if(type.equals("5") || type.equals("3") || type.equals("4"))
				groupName = "sick";
		}
		return groupName;
	}
	
	/**
	 * 根据数据库中存储的文件路径获取文件grouName和fileName数组
	 * @param path 据库中存储的文件路径
	 * @return
	 */
	private String[] getFastDFSFileInfo(String path){
		String[] pathAry = path.split("/");
		String groupName = pathAry[1];
		String fileName = "";
		for(int i=2; i<pathAry.length; i++){
			fileName = fileName + pathAry[i] + "/";
		}
		
		String[] fileInfoAry = new String[]{groupName, fileName.substring(0, fileName.length()-1)};
		return fileInfoAry;
	}

	/**
	 * 上传并裁剪图片，支持缩放的处理。
	 * 
	 * @param request
	 * @param response
	 * @param context
	 * @return
	 */
	@ActionMethod(request = "original", response = "json")
	public Object uploadImage(HttpServletRequest request, HttpServletResponse response, RestContext context) {
		context.setAttribute("_contentType", new MediaType("text", "html", Charset.forName("utf8")));
		@SuppressWarnings("unchecked")
		Map<String, Object> messages = (Map<String, Object>) upload(request, response, context);
		@SuppressWarnings("unchecked")
		Map<String, String> paramMap = (Map<String, String>) context.getAttribute("_upload_params");
		return doCropImage(request, messages, paramMap);
	}

	/**
	 * 裁剪已上传的图片，支持缩放的处理。
	 * 
	 * @param request
	 * @param response
	 * @param context
	 * @return
	 */
	@ActionMethod(request = "auto", response = "json")
	public Object cropImage(@ResourceVariable String fileId, RequestParameters params, RestContext context, HttpServletRequest request) {
		context.setAttribute("_contentType", new MediaType("text", "html", Charset.forName("utf8")));
		Map<String, Object> messages = new HashMap<String, Object>();
		Map<String, String> paramMap = new HashMap<String, String>();
		for (String name : params.getParameterNames()) {
			paramMap.put(name, String.valueOf(params.getParameter(name)));
		}
		if (StringUtils.isBlank(fileId)) {
			messages.put("success", false);
			messages.put("error", 1);
			messages.put("message", "parameters error, the fileId is required.");
			return messages;
		} else {
			messages.put("fileId", fileId);
		}
		messages.put("success", true);
		messages.put("error", 0);
		return doCropImage(request, messages, paramMap);
	}

	/**
	 * @param request
	 * @param messages
	 * @param paramMap
	 * @return
	 */
	private Object doCropImage(HttpServletRequest request, Map<String, Object> messages, Map<String, String> paramMap) {
		int h = 0, w = 0, x = 0, y = 0, height = 0, width = 0;
		try {
			if (paramMap.get("h") != null) {
				h = NumberUtils.createNumber(paramMap.get("h")).intValue();
			} else if (request.getParameter("h") != null) {
				h = NumberUtils.createNumber(request.getParameter("h")).intValue();
			}
			if (paramMap.get("w") != null) {
				w = NumberUtils.createNumber(paramMap.get("w")).intValue();
			} else if (request.getParameter("w") != null) {
				w = NumberUtils.createNumber(request.getParameter("w")).intValue();
			}
			if (paramMap.get("x") != null) {
				x = NumberUtils.createNumber(paramMap.get("x")).intValue();
			} else if (request.getParameter("x") != null) {
				x = NumberUtils.createNumber(request.getParameter("x")).intValue();
			}
			if (paramMap.get("y") != null) {
				y = NumberUtils.createNumber(paramMap.get("y")).intValue();
			} else if (request.getParameter("y") != null) {
				y = NumberUtils.createNumber(request.getParameter("y")).intValue();
			}
			if (paramMap.get("imageHeight") != null) {
				height = NumberUtils.createNumber(paramMap.get("imageHeight")).intValue();
			} else if (request.getParameter("imageHeight") != null) {
				height = NumberUtils.createNumber(request.getParameter("imageHeight")).intValue();
			}
			if (paramMap.get("imageWidth") != null) {
				width = NumberUtils.createNumber(paramMap.get("imageWidth")).intValue();
			} else if (request.getParameter("imageWidth") != null) {
				width = NumberUtils.createNumber(request.getParameter("imageWidth")).intValue();
			}
		} catch (NumberFormatException e) {
			messages.put("success", false);
			messages.put("error", 1);
			messages.put("message", "parameters error, please pass parameter [h] [w] [x] [y] [imageHeight] [imageWidth] and all should numbers.");
			logger.error("parse the parameter error, should all numbers", e);
			return messages;
		}
		x = x < 0 ? 0 : x;
		y = y < 0 ? 0 : y;
		if ((Boolean) messages.get("success") == true) {
			String fileId = (String) messages.get("fileId");
			cropImage(fileId, h, w, x, y, height, width, messages);
		}
		return messages;
	}

	/**
	 * 裁剪图片,这里会处理图片被缩放的情况。上传的原图会根据 {@code height} 和 {@code width} 进行缩放后再剪切。
	 * 
	 * @param fileId
	 * @param h
	 *            剪切区域的高度
	 * @param w
	 *            剪切区域的宽度
	 * @param x
	 *            剪切区域所在图片的x坐标
	 * @param y
	 *            剪切区域所在图片的y坐标
	 * @param height
	 *            原图的高度
	 * @param width
	 *            原图的宽度
	 * @param messages
	 */
	private void cropImage(String fileId, int h, int w, int x, int y, int height, int width, Map<String, Object> messages) {
		File file = fileSystemManager.getFile(fileId);
		if (file == null) {
			messages.put("success", false);
			messages.put("error", 1);
			messages.put("message", "image file not found with:" + fileId);
			return;
		}
		if (messages.get("name") == null) {
			messages.put("name", file.getName());
		}
		String realFilePath = fileSystemPath.getRealPath(file.getPath());
		java.io.File f = new java.io.File(realFilePath);
		String ext = FilenameUtils.getExtension(realFilePath);
		// 是图片才进行操作
		if (StringUtils.isNotBlank(ext) && Arrays.<String> asList(imagesExts).contains(ext.toLowerCase())) {
			try {
				Image image = ImageIO.read(f);
				PictureSize originalSize = new PictureSize(image.getWidth(null), image.getHeight(null));
				PictureSize picSize = originalSize;
				PictureSize limitSize = new PictureSize(width, height);
				String tempFile = fileSystemPath.getRealTempDir() + "/" + fileId + "_tmp." + ext;
				java.io.File tmpFile = new java.io.File(tempFile);
				if (limitSize.getWidth() < originalSize.getWidth() || limitSize.getHeight() < originalSize.getHeight()) {// 处理缩略
					picSize = ThumbUtils.getThumbSize(limitSize, originalSize);
					if (!picSize.equals(originalSize)) {
						OutputStream out = null;
						InputStream in = null;
						try {
							out = new FileOutputStream(tmpFile);
							in = new FileInputStream(f);
							ThumbUtils.encodeThumb(in, out, limitSize, ext);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							IOUtils.closeQuietly(in);
							IOUtils.closeQuietly(out);
						}
					}
				} else if (limitSize.getWidth() > originalSize.getWidth() && limitSize.getHeight() > originalSize.getHeight()) {// 处理放大
					picSize = ThumbUtils.getEnlargeSize(limitSize, originalSize);
					if (!picSize.equals(originalSize)) {
						OutputStream out = null;
						InputStream in = null;
						try {
							out = new FileOutputStream(tmpFile);
							in = new FileInputStream(f);
							ThumbUtils.encodeEnlarge(in, out, limitSize, ext);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							IOUtils.closeQuietly(in);
							IOUtils.closeQuietly(out);
						}
					}
				}
				// 如果图片不存在就是不存在缩放的情况，就把原图片copy到tempFile进行处理。
				java.io.File cropingFile = tmpFile;
				if (!cropingFile.exists()) {
					FileUtils.copyFile(f, cropingFile);
				}
				file.setSize(cropingFile.length());
				file.setUpdateDate(new Date(cropingFile.lastModified()));
				InputStream in = null;
				OutputStream out = null;
				try {
					in = new FileInputStream(cropingFile);
					out = new FileOutputStream(f);
					if ((x + w) > picSize.getWidth()) {
						w = picSize.getWidth() - x;
					}
					if ((y + h) > picSize.getHeight()) {
						h = picSize.getHeight() - y;
					}
					ThumbUtils.crop(in, out, new PictureSize(w, h), x, y, ext);
					file.setSize(f.length());
					file.setUpdateDate(new Date(f.lastModified()));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(in);
					IOUtils.closeQuietly(out);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//剪切完成之后要更新toolkit 里的File对象，因为大小和最后修改时间都有修改。
			fileSystemManager.updateFile(file);
		}
	}

	@ActionMethod(response = "json")
	public Object list(@ParameterVariable("owner") String owner, @ParameterVariable("order") String order, HttpServletRequest request) {
		FileCondition condition = new FileCondition();
		condition.setOwner(owner);
		List<File> files = fileSystemManager.list(condition);
		Map<String, Object> result = Maps.newHashMap();
		result.put("moveup_dir_path", "");
		result.put("current_url", request.getContextPath() + RestUrlRuler.getRestServiceRoot() + "/" + ProjectConfig.PREFIX + "File/");
		result.put("total_count", files.size());
		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
		if (!files.isEmpty()) {
			for (File file : files) {
				Map<String, Object> fileMap = Maps.newHashMap();
				fileMap.put("is_dir", false);
				fileMap.put("has_file", false);
				fileMap.put("filesize", file.getSize());
				String ext = FilenameUtils.getExtension(file.getName());
				fileMap.put("is_photo", Arrays.<String> asList(imagesExts).contains(ext.toLowerCase()));
				fileMap.put("filetype", ext);
				fileMap.put("id", file.getId());
				fileMap.put("filename", file.getName());
				fileMap.put("datetime", DateFormatUtils.format(file.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
				children.add(fileMap);
			}
		}
		result.put("file_list", children);
		return result;
	}

	@ActionMethod(response = "json")
	public Object listByIds(RequestParameters params) {
		String[] ids = params.getParameter("ids", String[].class);
		String owner = params.getParameter("owner", String.class);
		FileCondition condition = new FileCondition();
		if (ids != null) {
			if (Arrays.asList(ids).size() == 0) {
				return null;
			}
			condition.setIds(Arrays.asList(ids));
			return fileSystemManager.list(condition, new Sorter().desc("createDate"));
		} else if (StringUtils.isNotBlank(owner)) {
			condition.setOwner(owner);
			return fileSystemManager.list(condition, new Sorter().desc("createDate"));
		}
		return new ArrayList<File>();
	}

	@ActionMethod(response = "json")
	public Object delete(@ResourceVariable String id) {
		File file = fileSystemManager.getFile(id);
		if (file == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		String path = file.getPath();
		java.io.File realFile = new java.io.File(fileSystemPath.getRealPath(path));
		if (realFile.exists()) {
			FileUtils.deleteQuietly(realFile);
		}
		fileSystemManager.deleteFile(file);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	/**
	 * 读文件，如果是图片就直接显示。
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ActionMethod(response = "custom")
	public void view(@ResourceVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		readFile(id, request, response, false);
	}

	/**
	 * 为上传的图片生成水印,同时也支持缩略图
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ActionMethod(response = "custom")
	public void watermark(@ResourceVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = fileSystemManager.getFile(id);
		if (file == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "The File[" + id + "] Not Found");
			return;
		}
		String realFilePath = fileSystemPath.getRealPath(file.getPath());
		java.io.File f = new java.io.File(realFilePath);
		if (!f.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found File in path:" + realFilePath);
			return;
		}
		String thumb = request.getParameter("thumb");
		boolean isImage = false;
		if (MimeUtils.isImage(f)) {
			isImage = true;
		}
		if (!isImage) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only image support watermark.");
			return;
		}
		long modifiedSince = request.getDateHeader("If-Modified-Since");
		String ext = FilenameUtils.getExtension(realFilePath);
		if (StringUtils.isNotBlank(thumb)) {
			String thumbDirPath = fileSystemPath.getRealThumbDir();
			java.io.File thumbDir = new java.io.File(thumbDirPath);
			if (!thumbDir.exists()) {
				thumbDir.mkdirs();
			}
			java.io.File thumbFile = new java.io.File(thumbDirPath + "/" + id + "_" + thumb + "." + ext);
			if (!thumbFile.exists()) {
				// 未找到就生成缩略图
				InputStream inputStream = null;
				OutputStream outputStream = null;
				try {
					int limitWidth = 0;
					int limitHeight = 0;
					inputStream = new FileInputStream(f);
					BufferedImage image = ImageIO.read(inputStream);
					PictureSize originalSize = new PictureSize(image.getWidth(null), image.getHeight(null));
					String[] strs = StringUtils.split(thumb, "x");
					String width = null;
					String height = null;
					if (strs != null && strs.length > 0) {
						width = strs[0];
					}
					if (strs != null && strs.length > 1) {
						height = strs[1];
					}
					if (StringUtils.isNotBlank(width) && NumberUtils.isNumber(width)) {
						limitWidth = NumberUtils.toInt(width);
					} else {
						limitWidth = originalSize.getWidth();
					}
					if (StringUtils.isNotBlank(height) && NumberUtils.isNumber(height)) {
						limitHeight = NumberUtils.toInt(height);
					} else {
						limitHeight = originalSize.getHeight();
					}
					PictureSize limitSize = new PictureSize(limitWidth, limitHeight);
					// 如果有缩略才去处理，否则还是返回原始图片
					if (limitSize.getWidth() < originalSize.getWidth() || limitSize.getHeight() < originalSize.getHeight()) {
						outputStream = new FileOutputStream(thumbFile);
						ThumbUtils.encodeThumb(image, outputStream, limitSize, ext);
					} else {
						thumbFile = f;
					}
				} catch (Exception e) {
					logger.error(String.format("generate thumb for file:%s error", f.getAbsolutePath()), e);
				} finally {
					IOUtils.closeQuietly(inputStream);
					IOUtils.closeQuietly(outputStream);
				}
			}
			if (thumbFile == null || !thumbFile.exists()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found thumb file.");
				return;
			}
			f = thumbFile;
		}
		WatermarkConfig config = watermarkConfig;
		if (config == null) {
			config = new WatermarkConfig(Watermark.Character, "未配置水印!");
		}
		String watermarkDirPath = fileSystemPath.getRealWatermarkDir();
		java.io.File watermarkDir = new java.io.File(watermarkDirPath);
		if (!watermarkDir.exists()) {
			watermarkDir.mkdirs();
		}
		java.io.File watermarkFile = new java.io.File(watermarkDirPath + "/" + id + "_watermark." + ext);
		if (watermarkFile.exists()) {
			if (watermarkFile.lastModified() <= modifiedSince) {// 如果没有更新就返回304
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}
			dealFile(response, false, watermarkFile, MimeUtils.getMimeType(watermarkFile), watermarkFile.lastModified(), null);
		} else { // 生成水印
			try {
				BufferedImage original = ImageIO.read(f);
				BufferedImage bufferedImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
				// Draw the actual image.
				g2d.drawImage(original, 0, 0, null);
				Position position = new Position(config.getX(), config.getY(), original.getWidth(), original.getHeight());
				if (config.getWatermark() == Watermark.Character) {
					config.getWatermark().mark(g2d, config.getMarkingString(), config.getFontSize(), config.getOpacity(), position);
				} else if (config.getWatermark() == Watermark.Image) {
					BufferedImage markImage = config.getMarkingImageFetcher().fetch();
					position.setWatermarkWidth(markImage.getWidth());
					position.setWatermarkHeight(markImage.getHeight());
					config.getWatermark().mark(g2d, markImage, config.getOpacity(), position);
				}
				g2d.dispose();
				ImageIO.write(bufferedImage, ext, watermarkFile);
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
				return;
			}
		}
		dealFile(response, false, watermarkFile, MimeUtils.getMimeType(watermarkFile), watermarkFile.lastModified(), null);
	}

	/**
	 * 打包文件，可以通过owner来下打包这个owner下所有的文件，也可以通过传ids参数来打包下载指定的文件列表
	 * 
	 * @param owner
	 * @param params
	 * @param response
	 * @throws IOException
	 */
	@ActionMethod(response = "custom")
	public void zip(@ResourceVariable String owner, RequestParameters params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<File> files = new ArrayList<File>();
		final String[] ids = params.getParameter("ids", String[].class);
		String zipName = params.getParameter("name", String.class);
		if (StringUtils.isNotBlank(owner) && !StringUtils.equalsIgnoreCase("collection", owner)) {
			files = fileSystemManager.listByOwner(owner);
			if (ids != null && ids.length > 0) {
				Collection<File> filteredFiles = Collections2.filter(files, new Predicate<File>() {
					@Override
					public boolean apply(File input) {
						if (ArrayUtils.contains(ids, input.getId())) {
							return true;
						}
						return false;
					}
				});
				files.clear();
				files.addAll(filteredFiles);
			}
		}
		if (files.isEmpty() && ids != null && ids.length > 0) {
			FileCondition condition = new FileCondition();
			condition.addIds(ids);
			files = fileSystemManager.list(condition);
		}
		if (files.isEmpty()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No Files found");
			return;
		} else if (files.size() == 1) {
			doDownload(files.get(0), request, response);
			return;
		}
		String tempDirPath = fileSystemPath.getRealTempDir() + "/zip";
		java.io.File tempDir = new java.io.File(tempDirPath);
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		String currentTime = String.valueOf(System.currentTimeMillis());
		java.io.File tempZip = new java.io.File(tempDirPath + "/" + currentTime + ".zip");
		java.io.File tempZipDir = new java.io.File(tempDirPath + "/" + currentTime);
		tempZipDir.mkdir();
		List<String> fileNames = new ArrayList<String>();
		for (File file : files) {
			java.io.File f = new java.io.File(fileSystemPath.getRealPath(file.getPath()));
			if (f.exists()) {
				try {
					String pathName = tempDirPath + "/" + currentTime + "/" + file.getName();
					FileUtils.copyFile(f, new java.io.File(pathName), true);
					fileNames.add(pathName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (StringUtils.isBlank(zipName)) {
			zipName = currentTime;
		}
		zipName += ".zip";
		ZipUtil.zip(tempZip, fileNames.toArray(new String[fileNames.size()]));
		FileUtils.deleteQuietly(tempZipDir);
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipName, "UTF-8"));
			response.setContentLength((int) tempZip.length());
			com.taole.framework.util.IOUtils.copyAndCloseIOStream(response.getOutputStream(), new FileInputStream(tempZip));
		} catch (Exception e) {
			logger.error("zipFile error.", e);
			response.setHeader("Pargma", null);
			response.setHeader("Cache-Control", null);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "server error.");
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws MyException 
	 */
	@ActionMethod(response = "custom")
	public void download(@ResourceVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, MyException {
		File file = fileSystemManager.getFile(id);
		if (file == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "The File[" + id + "] Not Found");
			return;
		}
		
		doDownload(file, request, response);
	}

	/**
	 * @param file
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void doDownload(File file, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		long modifiedSince = request.getDateHeader("If-Modified-Since");
		if (file.getUpdateDate().getTime() <= modifiedSince) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		response.setContentType("application/octet-stream");

		// 如果没有指定文件名则用file的name属性
		String filename = request.getParameter("filename");
		if (StringUtils.isEmpty(filename)) {
			filename = file.getName();
		}

		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
		response.setHeader("Pargma", "max-age=" + maxAge);
		response.setHeader("Cache-Control", "max-age=" + maxAge);
		if (file.getUpdateDate().getTime() > 0) {
			response.setDateHeader("Last-Modified", file.getUpdateDate().getTime());
		}
		if (StringUtils.isBlank(file.getPath())) {
			byte[] data = new byte[0];
			response.setContentLength(data.length);
			OutputStream os = response.getOutputStream();
			os.write(data);
			IOUtils.closeQuietly(os);
		} else {
			
			String filePath = file.getPath();
			if(filePath.startsWith(fileSystemPath.getStorageDir())){
				//原来文件方式
				filePath = fileSystemPath.getRealPath(file.getPath());
			}else if(filePath.startsWith("/head") || filePath.startsWith("/sick")){
				//fastDFS下载文件
				try {
					String[] fileInfoAry = getFastDFSFileInfo(filePath);
					byte[] content = fileManager.download(fileInfoAry[0], fileInfoAry[1]);
					response.setContentLength(content.length);
					InputStream input = new ByteArrayInputStream(content);
					com.taole.framework.util.IOUtils.copyAndCloseIOStream(response.getOutputStream(), input);
				} catch (MyException e) {
					e.printStackTrace();
				}
				return;
			}else{
				//本地指定目录下载文件
				filePath = uploadUrl + filePath;
			}
			
			java.io.File f = new java.io.File(filePath);
			if (!f.exists()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found File in path:" + filePath);
				return;
			}
			try {
				response.setContentLength((int) f.length());
				com.taole.framework.util.IOUtils.copyAndCloseIOStream(response.getOutputStream(), new FileInputStream(f));
			} catch (Exception e) {
				e.printStackTrace();
				response.setHeader("Pargma", null);
				response.setHeader("Cache-Control", null);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "server error.");
			}
		}
	}

	/**
	 * 预览文件
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ActionMethod(response = "custom")
	public void preview(@ResourceVariable String id, RequestParameters params, final HttpServletRequest request, HttpServletResponse response) throws IOException {
		final File file = fileSystemManager.getFile(id);
		if (file == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "The File[" + id + "] Not Found");
			return;
		}
		long modifiedSince = request.getDateHeader("If-Modified-Since");
		if (file.getUpdateDate().getTime() <= modifiedSince) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		String mimeType = file.getMimeType();
		InputStream in = null;
		try {
			if (StringUtils.isNotBlank(file.getPath())) {
				java.io.File realFile = new java.io.File(fileSystemPath.getRealPath(file.getPath()));
				if (mimeType == null) {
					mimeType = MimeUtils.getMimeType(realFile);
				}
				if (!realFile.exists()) {
					logger.error(String.format("############ not found file[%s], can't preview", realFile.getPath()));
					response.getWriter().write("<html><head><title>" + file.getName() + "</title></head><body>文件未找到，不能预览。</body></html>");
					return;
				}
				in = new BufferedInputStream(new FileInputStream(realFile));
			}
			if (in == null) {
				response.getWriter().write("<html><head><title>" + file.getName() + "</title></head><body>System error,No content found</body></html>");
				return;
			}
			if (MimeUtils.isXls(mimeType) || MimeUtils.isXlsx(mimeType)) {
				try {
					StringBuilder content = new StringBuilder();
					ExcelToHtml excelToHtml = ExcelToHtml.create(in, content);
					excelToHtml.setCompleteHTML(true);
					excelToHtml.setTitle(file.getName());
					excelToHtml.printPage();

					response.setHeader("Pargma", "max-age=" + maxAge);
					response.setHeader("Cache-Control", "max-age=" + maxAge);
					response.setDateHeader("Last-Modified", file.getUpdateDate().getTime());
					response.getWriter().write(content.toString());
				} catch (Exception e) {
					response.getWriter().write("<html><head><title>" + file.getName() + "</title></head><body>解析Excel文档出错。<p>" + e.getLocalizedMessage() + "</p></body></html>");
				}
			} else if (MimeUtils.isImage(mimeType)) {
				response.getWriter().write(
						"<!doctype html> <html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <title>" + file.getName() + "</title> <style>html, body, p, div, h1, h2, h3, h4, h5, h6, img, pre, form, fieldset { margin: 0; padding: 0; } p{margin:8px;} .p2{font-size:11pt;} ul, ol, dl { margin: 0; padding: 0; } img, fieldset { border: 0; } body{background:#3e3e3e;} .document{margin:20px auto; text-align:center;} .document img{padding:5px; border-radius:4px; -webkit-box-shadow:0 3px 6px rgba(0, 0, 0, 0.175); box-shadow:0 3px 6px rgba(0, 0, 0, 0.175); background-clip: padding-box; background:#fff;} </style> </head> <body> <div class=\"document\"><img src=\"" + request.getContextPath() + restUrlRuler.getEntityUrl(file, "view") + "\" alt=\"" + file.getName() + "\"/></div> </body> </html>");
			} else if (MimeUtils.isText(mimeType)) {
				String text = IOUtils.toString(in, CharacterDetectorUtils.detectEncoding(in, "UTF-8"));
				response.getWriter().write(
						"<!doctype html> <html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <title>" + file.getName() + "</title> <style> html, body, div { margin: 0; padding: 0; } body{background:#3e3e3e;} .wrapper{padding:10px; margin:20px auto; width:940px; min-height:500px; border-radius:4px;   -webkit-box-shadow:0 3px 6px rgba(0, 0, 0, 0.175); box-shadow:0 3px 6px rgba(0, 0, 0, 0.175); background-clip: padding-box; background:#fff;} </style> </head> <body> <div class=\"wrapper\">" + com.taole.framework.util.StringUtils.formatHTML(text)
								+ "</div> </body> </html>");
			} else {
				response.getWriter().write("<html><head><title>" + file.getName() + "</title></head><body>不支持该文件类型的预览。</body></html>");
			}
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @param download
	 * @throws IOException
	 */
	public void readFile(String id, HttpServletRequest request, HttpServletResponse response, boolean download) throws IOException {
		request.setCharacterEncoding("UTF-8");

		File file = fileSystemManager.getFile(id);
		if (file == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "The File[" + id + "] Not Found");
			return;
		}
		String realFilePath = fileSystemPath.getRealPath(file.getPath());
		java.io.File f = new java.io.File(realFilePath);
		if (!f.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found File in path:" + realFilePath);
			return;
		}
		String thumb = request.getParameter("thumb");
		boolean isImage = false;
		if (MimeUtils.isImage(f)) {
			isImage = true;
		}
		long modifiedSince = request.getDateHeader("If-Modified-Since");
		if (f.lastModified() <= modifiedSince && (!isImage || StringUtils.isBlank(thumb))) {// 如果不是需要缩略的图片则根据文件修改时间返回304
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		String ext = FilenameUtils.getExtension(realFilePath);
		String mimeType = MimeUtils.getMimeType(file.getName());
		String fileName = file.getName();
		if (StringUtils.isNotBlank(thumb) && isImage) {
			String thumbDirPath = fileSystemPath.getRealThumbDir();
			java.io.File thumbDir = new java.io.File(thumbDirPath);
			if (!thumbDir.exists()) {
				thumbDir.mkdirs();
			}
			// 找到生成的缩略图返回
			java.io.File thumbFile = new java.io.File(thumbDirPath + "/" + id + "_" + thumb + "." + ext);
			if (thumbFile.exists()) {
				if (thumbFile.lastModified() <= modifiedSince) {// 如果没有更新就返回304
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return;
				}
				dealFile(response, download, thumbFile, mimeType, thumbFile.lastModified(), fileName + "_" + thumb);
				return;
			} else {
				// 未找到就生成缩略图
				InputStream inputStream = null;
				OutputStream outputStream = null;

				try {
					int limitWidth = 0;
					int limitHeight = 0;
					inputStream = new FileInputStream(f);
					BufferedImage image = ImageIO.read(inputStream);
					PictureSize originalSize = new PictureSize(image.getWidth(null), image.getHeight(null));
					String[] strs = StringUtils.split(thumb, "x");
					String width = null;
					String height = null;
					if (strs != null && strs.length > 0) {
						width = strs[0];
					}
					if (strs != null && strs.length > 1) {
						height = strs[1];
					}
					if (StringUtils.isNotBlank(width) && NumberUtils.isNumber(width)) {
						limitWidth = NumberUtils.toInt(width);
					} else {
						limitWidth = originalSize.getWidth();
					}
					if (StringUtils.isNotBlank(height) && NumberUtils.isNumber(height)) {
						limitHeight = NumberUtils.toInt(height);
					} else {
						limitHeight = originalSize.getHeight();
					}
					PictureSize limitSize = new PictureSize(limitWidth, limitHeight);
					// 如果有缩略才去处理，否则还是返回原始图片
					if (limitSize.getWidth() < originalSize.getWidth() || limitSize.getHeight() < originalSize.getHeight()) {
						outputStream = new FileOutputStream(thumbFile);
						ThumbUtils.encodeThumb(image, outputStream, limitSize, ext);
					} else {
						thumbFile = f;
					}
					dealFile(response, download, thumbFile, mimeType, thumbFile.lastModified(), fileName + "_" + thumb);
				} catch (Exception e) {
					logger.error(String.format("generate thumb for file:%s error", f.getAbsolutePath()), e);
				} finally {
					IOUtils.closeQuietly(inputStream);
					IOUtils.closeQuietly(outputStream);
				}
			}
		}

		long lastModifyDate = 0l;
		if (file.getUpdateDate() != null) {
			lastModifyDate = file.getUpdateDate().getTime();
		}

		dealFile(response, download, f, mimeType, lastModifyDate, fileName);
	}

	/**
	 * 
	 * 处理读取到的文件。
	 * 
	 * @param response
	 * @param download
	 * @param f
	 * @param mimeType
	 * @param lastModifyDate
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void dealFile(HttpServletResponse response, boolean download, java.io.File f, String mimeType, long lastModifyDate, String fileName) throws UnsupportedEncodingException, IOException {
		if (download) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} else {
			response.setContentType(mimeType);
		}

		response.setHeader("Pargma", "max-age=" + maxAge);
		response.setHeader("Cache-Control", "max-age=" + maxAge);

		if (lastModifyDate > 0) {
			response.setDateHeader("Last-Modified", lastModifyDate);
		}

		FileInputStream input = null;
		ServletOutputStream output = null;
		try {
			input = new FileInputStream(f);
			output = response.getOutputStream();
			response.setContentLength((int) f.length());
			if (!f.exists()) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			IOUtils.copy(input, output);
		} catch (Exception e) {
			e.printStackTrace();
			response.setHeader("Pargma", null);
			response.setHeader("Cache-Control", null);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "server error.");
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * 判断文件是否为图片
	 * 
	 * @param pInput
	 *            文件名
	 * @param pImgeFlag
	 *            判断具体文件类型
	 * @return 检查后的结果
	 * @throws Exception
	 */
	public static boolean isPicture(String pInput, String pImgeFlag)
			throws Exception {
		// 文件名称为空的场合
		if (StringUtils.isEmpty(pInput)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1,
				pInput.length());
		// 声明图片后缀名数组
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" },
				{ "gif", "2" }, { "jfif", "3" }, { "jpe", "4" },
				{ "jpeg", "5" }, { "jpg", "6" }, { "png", "7" },
				{ "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (!StringUtils.isEmpty(pImgeFlag)
					&& imgeArray[i][0].equals(tmpName.toLowerCase())
					&& imgeArray[i][1].equals(pImgeFlag)) {
				return true;
			}
			// 判断符合全部类型的场合
			if (StringUtils.isEmpty(pImgeFlag)
					&& imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
