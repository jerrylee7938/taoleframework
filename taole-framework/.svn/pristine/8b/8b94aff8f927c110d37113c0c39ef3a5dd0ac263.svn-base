package com.taole.framework.rest.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.taole.framework.util.StringUtils;

public class ResponseProcessorRegistry {

	@Autowired
	protected ApplicationContext applicationContext;

	private Map<String, ResponseProcessor> table = new HashMap<String, ResponseProcessor>();

	public ResponseProcessor getResponseProcessor(String name) {
		ResponseProcessor processor = table.get(name);
		if (processor == null) {
			Map<String, ResponseProcessor> map = applicationContext.getBeansOfType(ResponseProcessor.class);
			if (applicationContext.getParent() != null) {
				map.putAll(applicationContext.getParent().getBeansOfType(ResponseProcessor.class));
			}
			for (ResponseProcessor obj : map.values()) {
				if (StringUtils.compare(obj.getName(), name)) {
					table.put(obj.getName(), processor = obj);
					break;
				}
			}
		}
		return processor;
	}

	public void register(ResponseProcessor processor) {
		table.put(processor.getName(), processor);
	}

}
