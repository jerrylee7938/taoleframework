package com.taole.framework.rest.processor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.taole.framework.rest.ResultSet;
import com.taole.framework.service.ServiceResult;

@Component
public class CustomResponseProcessor extends AbstractResponseProcessor {

	public Object process(Object input) {
		if (ModelAndView.class.isInstance(input)) {
			return new ViewResponseProcessor().process(input);
		} else if (JSONObject.class.isInstance(input) || JSONArray.class.isInstance(input)) {
			return new JSONResponseProcessor().process(input);
		} else if (ServiceResult.class.isInstance(input)) {
			return new JSONResponseProcessor().process(input);
		} else if (ResultSet.class.isInstance(input)) {
			return new ExtGridResponseProcessor().process(input);
		} else {
			return input;
		}
	}

	@Override
	public String getName() {
		return "custom";
	}

}
