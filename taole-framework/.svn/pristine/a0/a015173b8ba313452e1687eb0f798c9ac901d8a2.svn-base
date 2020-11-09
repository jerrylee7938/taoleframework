/**
 * Project:sba-passport-web
 * Copyright 2004-2010  Co., Ltd. All rights reserved.
 */
package com.taole.framework.json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * <pre>
 * {@code
 * // Create a POJO
   MyPojo pojo = new MyPojo();

   // Populate the POJO

   // Convert the POJO to a JSON string
   String pojoAsString = PojoMapper.toJson(pojo, true);

   // Convert the JSON string back into a POJO
   pojo = (MyPojo) PojoMapper.fromJson(pojoAsString, MyPojo.class);

   // Convert the POJO to a JSON string that gets stored in a file
   FileWriter fw = new FileWriter("pojo.txt");
   PojoMapper.toJson(pojo3, fw, true);

   // Convert the JSON string in the file back into a POJO
   FileReader fr = new FileReader("pojo.txt");
   pojo = (MyPojo) PojoMapper.fromJson(fr, MyPojo.class);}
 * </pre>
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @since Jul 14, 2011 10:32:17 AM
 * @version $Id: PojoJsonMapper.java 5 2014-01-15 13:55:28Z yedf $
 */
public class PojoJsonMapper {
	
	private static ObjectMapper m;
	
	static {
		m = new ObjectMapper();
		m.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		m.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
	}

	
	private static JsonFactory jf = new JsonFactory();

	public static <T> T fromJson(String jsonAsString, Class<T> pojoClass) throws JsonMappingException, JsonParseException, IOException {
		return m.readValue(jsonAsString, pojoClass);
	}
	
	public static <T> T fromJson(String jsonAsString, TypeReference<T> valueTypeRef) throws JsonMappingException, JsonParseException, IOException {
		return m.readValue(jsonAsString, valueTypeRef);
	}

	public static <T> T fromJson(FileReader fr, Class<T> pojoClass) throws JsonParseException, IOException {
		return m.readValue(fr, pojoClass);
	}
	public static <T> T fromJson(FileReader fr, TypeReference<T> valueTypeRef) throws JsonParseException, IOException {
		return m.readValue(fr, valueTypeRef);
	}

	public static <T> T fromJson(InputStream is, Class<T> pojoClass) throws JsonParseException, IOException {
		return m.readValue(is, pojoClass);
	}
	
	public static <T> T fromJson(InputStream is , TypeReference<T> valueTypeRef) throws JsonParseException, IOException {
		return m.readValue(is, valueTypeRef);
	}
	
	public static String toJson(Object pojo, boolean prettyPrint) throws JsonMappingException, JsonGenerationException, IOException {
		StringWriter sw = new StringWriter();
		JsonGenerator jg = jf.createJsonGenerator(sw);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		m.writeValue(jg, pojo);
		return sw.toString();
	}

	public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint) throws JsonMappingException, JsonGenerationException, IOException {
		JsonGenerator jg = jf.createJsonGenerator(fw);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		m.writeValue(jg, pojo);
	}
}
