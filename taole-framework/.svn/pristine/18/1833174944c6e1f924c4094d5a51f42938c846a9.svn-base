/**
 * Project:taole-heaframework
 * File:PojoResponseHandler.java
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

import com.taole.framework.bean.DomainObject;
import com.taole.framework.util.JSONTransformer;

/**
 * 处理返回json到pojo的自动转换.
 * 
 * @author Rory
 * @date Dec 13, 2011
 * @version $Id: PojoResponseHandler.java 5 2014-01-15 13:55:28Z yedf $
 */
public class PojoResponseHandler<T extends DomainObject> implements ResponseHandler<T> {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException{
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return null;
		}
		String jsonStr = EntityUtils.toString(entity);
		JSONObject json;
		try {
			json = new JSONObject(jsonStr);
			return (T) JSONTransformer.transformJso2Pojo(json, null);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
