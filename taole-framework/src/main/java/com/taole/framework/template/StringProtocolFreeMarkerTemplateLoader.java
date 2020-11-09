package com.taole.framework.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.TemplateLoader;

public class StringProtocolFreeMarkerTemplateLoader implements TemplateLoader {

	public final static String PROTOCOL = "data:tpl/freemarker;";

    private final Map<String, StringTemplateSource> templates = new HashMap<String, StringTemplateSource>();
    

	public void closeTemplateSource(Object templateSource) throws IOException {
	}

	public Object findTemplateSource(String name) throws IOException {
		StringTemplateSource tpl = templates.get(name);
		if (tpl == null) {
			String content = name.trim();
			if (!content.startsWith(PROTOCOL)) {
				return null;
			}
			content = content.substring(PROTOCOL.length());
			tpl = new StringTemplateSource(name, content);
			templates.put(name, tpl);;
		}
		return tpl;
	}

	public long getLastModified(Object templateSource) {
		return Long.MAX_VALUE;
	}

	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return new StringReader(((StringTemplateSource)templateSource).source);
	}
	private static class StringTemplateSource {
        private final String name;
        private final String source;
        
        StringTemplateSource(String name, String source) {
            if(name == null) {
                throw new IllegalArgumentException("name == null");
            }
            if(source == null) {
                throw new IllegalArgumentException("source == null");
            }
            this.name = name;
            this.source = source;
        }
        
        public boolean equals(Object obj) {
            if(obj instanceof StringTemplateSource) {
                return name.equals(((StringTemplateSource)obj).name);
            }
            return false;
        }
        
        public int hashCode() {
            return name.hashCode();
        }
    }
}
