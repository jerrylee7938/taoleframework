package com.taole.framework.rest.processor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class XmlResponseProcessor extends AbstractResponseProcessor {
	
	@Override
	public String getName() {
		return "xml";
	}


	public Object process(Object input) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "text/xml");
		//TODO
		return new ResponseEntity<String>(input.toString(), headers, HttpStatus.OK);
	}

}
