/**
 * Project:taole-heaframework
 * File:StringResponseHandler.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest.client;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

/**
 * @author Rory
 * @date Mar 7, 2012
 * @version $Id: StringResponseHandler.java 5 2014-01-15 13:55:28Z yedf $
 */
public class StringResponseHandler implements ResponseHandler<String> {
	
	private String encoding = "UTF-8";
	
	private StatusLine statusLine;

	public StringResponseHandler() {
	}

	public StringResponseHandler(String encoding) {
		if (StringUtils.isNotBlank(encoding)) {
			this.encoding = encoding;
		}
	}
	
	public StatusLine getStatusLine() {
		return statusLine;
	}

    /**
     * Returns the response body as a String if the response was successful (a
     * 2xx status code). If no response body exists, this returns null. If the
     * response was unsuccessful (>= 300 status code), throws an
     * {@link HttpResponseException}.
     */
	@Override
    public String handleResponse(final HttpResponse response)
            throws HttpResponseException, IOException {
        statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }

        HttpEntity entity = response.getEntity();
        return entity == null ? null : EntityUtils.toString(entity, encoding);
    }

}
