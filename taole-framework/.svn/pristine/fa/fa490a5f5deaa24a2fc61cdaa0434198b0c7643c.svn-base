package com.taole.framework.rest.parser;

import org.json.JSONArray;
import org.json.JSONException;

import com.taole.framework.rest.RequestParameters;

public class JSONArrayRequestParameters implements RequestParameters {

	private JSONArray jsonArray;

	public JSONArrayRequestParameters(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}


	public Object getDelegateObject() {
		return jsonArray;
	}

	public Object getParameter(String name) {
		return null;
	}

	public String[] getParameterNames() {
		return new String[0];
	}

	public Object[] getParameterValues(String name) {
		return new Object[0];
	}

	public <T> T getParameter(String name, Class<T> requiredType) {
		return null;
	}
	
	public void setParameter(String name, Object value) {
	}

	public Object clone() {
		JSONArray array = new JSONArray();
		try {
			array = new JSONArray(jsonArray.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONArrayRequestParameters(array);		
	}
}
