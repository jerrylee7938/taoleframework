package com.taole.framework.rest.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWithHeaderResponseHandler implements ResponseHandler<Object[]>{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		Object[] objects = new Object[2];
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
		objects[0] = json;
		
		Header[] headers = response.getAllHeaders();
		Map<String, String> headerMap = new HashMap<String, String>();
		for (Header header : headers ){
			headerMap.put(header.getName(),header.getValue());
		}
		objects[1] = headerMap;
		return objects;
	}
}
