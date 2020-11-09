package com.taole.framework.rest.parser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.taole.framework.util.StringUtils;

public class ParameterParserRegistry {

	@Autowired
	protected ApplicationContext applicationContext;

	private Map<String, ParameterParser> table = new HashMap<String, ParameterParser>();

	public ParameterParser getParameterParser(String name) {
		ParameterParser parser = table.get(name);
		if (parser == null) {
			Map<String, ParameterParser> map = applicationContext.getBeansOfType(ParameterParser.class);
			if (applicationContext.getParent() != null) {
				map.putAll(applicationContext.getParent().getBeansOfType(ParameterParser.class));
			}
			for (ParameterParser obj : map.values()) {
				if (StringUtils.compare(obj.getName(), name)) {
					table.put(obj.getName(), parser = obj);
					break;
				}
			}
		}
		return parser;
	}

	public void register(ParameterParser parser) {
		table.put(parser.getName(), parser);
	}
}
