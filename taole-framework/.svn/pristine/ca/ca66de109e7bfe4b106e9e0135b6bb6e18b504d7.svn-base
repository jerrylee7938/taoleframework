/**
 * Project:taole-heaframework
 * File:JSONResponseHandler.java
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest.client;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rory
 * @date Dec 13, 2011
 * @version $Id: JSONResponseHandler.java 5 2014-01-15 13:55:28Z yedf $
 */
public class JSONResponseHandler implements ResponseHandler<JSONObject> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSONObject handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return null;
		}
		String jsonStr = EntityUtils.toString(entity, "UTF-8");
		JSONObject json = null;
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

}
