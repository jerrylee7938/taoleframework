package com.taole.framework.rest.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.taole.framework.util.IOUtils;

@Component
public class BinaryResponseProcessor extends AbstractResponseProcessor {
	
	@Override
	public String getName() {
		return "binary";
	}

	public Object process(Object input) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		try {
			byte[] data = new byte[0];
			if (input instanceof InputStream) {
				data = IOUtils.getBytes((InputStream)input);
			} else if (input instanceof byte[]) {
				data = (byte[]) input;
			} else if (input instanceof File) {
				File file = (File) input;
				headers.set("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(file.getName(), "utf-8") + "\"");
				try {
					data = IOUtils.getBytes(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				data = input.toString().getBytes("utf-8");
			}
			headers.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			return new ResponseEntity<String>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
