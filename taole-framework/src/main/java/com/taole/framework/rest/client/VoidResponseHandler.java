/**
 * Project:taole-heaframework
 * File:VoidResponseHandler.java
 * Copyright 2004-2012  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

/**
 * @author Rory
 * @date Apr 13, 2012
 * @version $Id: VoidResponseHandler.java 5 2014-01-15 13:55:28Z yedf $
 */
public class VoidResponseHandler implements ResponseHandler<StatusLine> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatusLine handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		return response.getStatusLine();
	}

}
