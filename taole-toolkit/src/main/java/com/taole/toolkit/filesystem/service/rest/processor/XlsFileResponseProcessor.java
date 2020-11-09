package com.taole.toolkit.filesystem.service.rest.processor;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.rest.processor.ResponseProcessorRegistry;
import com.taole.framework.rest.processor.XlsResponseProcessor;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.FormatUtils;
import com.taole.framework.util.MimeUtils;
import com.taole.toolkit.filesystem.FileSystemPath;
import com.taole.toolkit.filesystem.domain.File;
import com.taole.toolkit.filesystem.manager.FileSystemManager;

@Component
public class XlsFileResponseProcessor extends XlsResponseProcessor {
	public static final String NAME = "xls-file";
	
	public static final String XLS_FILE_ID = "_XLS_FILE_ID";
	
	@Autowired
	FileSystemManager fileSystemManager;
	
	@Autowired
	FileSystemPath fileSystemPath;
	
	@Autowired
	private ResponseProcessorRegistry processorRegistry;
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@PostConstruct
	public void init() {
		processorRegistry.register(this);
	}

	protected ResponseEntity<?> toResponseEntity(String fileName, Workbook wb) {
		HttpHeaders headers = new HttpHeaders();
		RestContext context = RestSessionFactory.getCurrentContext();
		if (context.getAttribute("_contentType") != null && context.getAttribute("_contentType") instanceof MediaType) {
			headers.setContentType((MediaType) context.getAttribute("_contentType"));
		} else {
			headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
		}
		ByteArrayOutputStream baos = null;
		
		try {
	
			baos = new ByteArrayOutputStream();
			wb.write(baos);
			byte[] data = baos.toByteArray();
			
			String fileId = (String) RestSessionFactory.getCurrentContext().getAttribute(XLS_FILE_ID);
			
			String ym = FormatUtils.formatDate(new Date(), "yyyy-MM");
			
			java.io.File dirFile = new java.io.File(fileSystemPath.getRealStorageDir()+"/" + ym);
			
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			String path = fileSystemPath.getRealStorageDir() + "/"+ ym +"/" + fileName;
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);
			fos.close();
			
			File file = new File();
			file.setId(fileId);
			file.setName(fileName);
			file.setSize(data.length);
			file.setCreateDate(new Date());
			file.setMimeType(MimeUtils.getMimeType(fileName));
			file.setPath(path);
			fileSystemManager.createFile(file);
			
			return new ResponseEntity<ServiceResult>(new ServiceResult(ReturnResult.SUCCESS, file), headers, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			IOUtils.closeQuietly(baos);
		}
	}
	
}
