package com.taole.framework.rest.processor;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.taole.framework.dao.util.PaginationSupport;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ResultSet;
import com.taole.framework.util.ExtjsDataConvertor;

@Component
public class ExtTreeResponseProcessor extends AbstractResponseProcessor {
	
	@Override
	public String getName() {
		return "ext-tree";
	}
	
	@SuppressWarnings("unchecked")
	public Object process(Object input) {
		
		Object json = null;
		ExtjsDataConvertor convertor = getExtjsDataConvertor();
		if (input == null) {
			json = convertor.convertTreeStoreData(new Object[0]);
		} else if (ResultSet.class.isInstance(input)) {
			json = convertor.convertTreeStoreData(((ResultSet)input).getRows());
		} else if (PaginationSupport.class.isInstance(input)) {
			json = convertor.convertTreeStoreData(((PaginationSupport<?>)input).getItems());
		} else  if (List.class.isInstance(input)) {
			json = convertor.convertTreeStoreData((Object[])((List<Object>)input).toArray(new Object[0]));
		} else  if (input.getClass().isArray()) {
			json = convertor.convertTreeStoreData((Object[])input);
		} else {
			json = convertor.convertTreeStoreData(input);
		}
		String jsonStr = json.toString();
		String jsonp = RestSessionFactory.getCurrentContext().getHttpServletRequest().getParameter("jsonp");
		if (StringUtils.isNotBlank(jsonp)) {
			jsonStr = jsonp + "(" + jsonStr + ");";
		}
		HttpHeaders headers = new HttpHeaders();
		try {
			byte[] data = jsonStr.getBytes("utf-8");
			headers.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			headers.setContentLength(data.length);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			return new ResponseEntity<String>(e.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	private ExtjsDataConvertor convertor;

	public void setExtjsDataConvertor(ExtjsDataConvertor convertor) {
		this.convertor = convertor;
	}

	private ExtjsDataConvertor getExtjsDataConvertor() {
		if (convertor == null) {
			convertor = ExtTreeGridResponseProcessor.createExtjsDataConvertorFromContext();
		}
		return convertor;
	}

}
