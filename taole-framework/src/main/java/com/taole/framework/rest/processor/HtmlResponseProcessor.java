package com.taole.framework.rest.processor;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HtmlResponseProcessor extends AbstractResponseProcessor {
	
	@Override
	public String getName() {
		return "html";
	}

	
	public Object process(Object input) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		return new ResponseEntity<String>(input == null ? "" : String.valueOf(input), headers, HttpStatus.OK);
	}

}
