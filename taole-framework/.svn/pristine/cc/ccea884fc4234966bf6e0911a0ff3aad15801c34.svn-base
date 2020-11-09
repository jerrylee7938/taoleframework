package com.taole.framework.rest.sysimpl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.ModelAndView;

import com.taole.framework.protocol.ProtocolRegistry;
import com.taole.framework.rest.ActionMethod;
import com.taole.framework.rest.RestContext;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestSessionFactory;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.rest.bind.annotation.ParameterVariable;
import com.taole.framework.rest.bind.annotation.ResourceVariable;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.BeanUtils;
import com.taole.framework.util.DomainObjectUtils;
import com.taole.framework.util.MessageUtils;
import com.taole.framework.util.MimeUtils;

@RestService(name = "fw.System")
public class SystemService {

	private final Logger logger = LoggerFactory.getLogger(SystemService.class);

	@Autowired
	ProtocolRegistry protocolRegistry;

	@SuppressWarnings({ "unchecked" })
	@ActionMethod(response = "json")
	public Object enums(@ResourceVariable String typeName) {
		try {
			String enumType = StringUtils.substringBefore(typeName, ":");
			Class<Enum<?>> clz = (Class<Enum<?>>) Class.forName(enumType);
			if (!clz.isEnum()) {
				return null;
			}
			String[] names = StringUtils.split(StringUtils.substringAfter(typeName, ":"), ",");
			if (names == null || names.length == 0) {
				names = new String[clz.getEnumConstants().length];
				int i = 0;
				for (Enum<?> enu : clz.getEnumConstants()) {
					names[i++] = enu.name();
				}
			}
			JSONArray list = new JSONArray();
			for (String name : names) {
				JSONObject json = new JSONObject();
				json.put("name", name);
				json.put("local", MessageUtils.getLocaleMessage(clz.getName() + "." + name, name));
				list.put(json);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ActionMethod(response = "json")
	public String local(@ResourceVariable String code) {
		List<String> lst = new ArrayList<String>();
		for (String s : StringUtils.split(code, ",")) {
			lst.add(MessageUtils.getLocaleMessage(s));
		}
		return StringUtils.join(lst.toArray(), ",");
	}

	@ActionMethod(response = "json")
	public Object meta(@ResourceVariable String typeName) {
		try {
			return BeanUtils.getMeta(Class.forName(typeName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@ActionMethod(response = "json")
	public Object translate(@ResourceVariable String uri) {
		Object obj = protocolRegistry.getObject(uri);
		if (obj != null) {
			return new ServiceResult(ReturnResult.SUCCESS, null, DomainObjectUtils.getObjectName(obj));
		}
		return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "not found object with uri:" + uri);
	}

	@ActionMethod(request = "form", response = "custom")
	public Object res(@ParameterVariable("path") String path, HttpServletRequest request) {

		HttpHeaders headers = new HttpHeaders();
		long requestLastModifiedSince = request.getDateHeader("If-Modified-Since");
		long lastModifiedSince = requestLastModifiedSince;

		// long ts = System.currentTimeMillis();
		MediaType mediaType = MimeUtils.getMediaType(path);

		if (StringUtils.isBlank(path)) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

		try {

			// 如果是文本类型的内容

			if (mediaType.getType().contains("text") || mediaType.getSubtype().contains("javascript")) {
				StringBuilder content = new StringBuilder();
				Set<String> appendedFileNames = new LinkedHashSet<String>();
				if (StringUtils.isNotBlank(path)) {
					if (path.startsWith("/")) {
						path = path.substring(1);
					}
					lastModifiedSince = loadClasspathResource(lastModifiedSince, content, appendedFileNames, "classpath:res/" + path);
				}
				if (lastModifiedSince <= requestLastModifiedSince) {
					return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
				}
				headers.setContentType(new MediaType(mediaType.getType(), mediaType.getSubtype(), Charset.forName("utf-8")));
				headers.setLastModified(lastModifiedSince);
				String outStr = replaceSystemVars(content.toString());
				return new ResponseEntity<String>(outStr, headers, HttpStatus.OK);

			} else {

				// 其他类型的内容
				String resPath = "classpath:res/";
				if (path.startsWith("/")) {
					resPath += path.substring(1);
				} else {
					resPath += path;
				}
				URL url = org.springframework.util.ResourceUtils.getURL(resPath);
				URL fileUrl = url;
				if (ResourceUtils.isJarURL(url)) {
					fileUrl = ResourceUtils.extractJarFileURL(url);
				}
				File file = ResourceUtils.getFile(fileUrl);
				if (file != null && lastModifiedSince < file.lastModified()) {
					lastModifiedSince = file.lastModified();
				}
				if (lastModifiedSince <= requestLastModifiedSince) {
					return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
				}
				InputStream inputStream = url.openStream();
				byte[] data = com.taole.framework.util.IOUtils.getBytes(inputStream);
				headers.setContentType(mediaType);
				headers.setLastModified(lastModifiedSince);
				return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
			}
		} catch (FileNotFoundException ex) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			// logger.debug("Load resource cost ms:" + (System.currentTimeMillis() - ts) + "\t path=" + path);
		}
	}

	@ActionMethod(request = "form", response = "custom")
	public Object css(@ParameterVariable("path") String path, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		long requestLastModifiedSince = request.getDateHeader("If-Modified-Since");
		long lastModifiedSince = requestLastModifiedSince;
		headers.setContentType(new MediaType("text", "css", Charset.forName("utf-8")));
		StringBuilder content = new StringBuilder();
		Set<String> appendedFileNameSet = new LinkedHashSet<String>();
		if (StringUtils.isNotBlank(path)) {
			String[] names = StringUtils.split(path, ",");
			for (String name : names) {
				if ("*".equals(name)) {
					lastModifiedSince = loadClasspathResource(lastModifiedSince, content, appendedFileNameSet, "classpath:css/common.css");
					lastModifiedSince = loadAllCssContent(lastModifiedSince, content, appendedFileNameSet);
				} else {
					if (!StringUtils.endsWithIgnoreCase(name, ".css")) {
						name += ".css";
					}
					if (name.startsWith("/")) {
						name = name.substring(1);
					}
					lastModifiedSince = loadClasspathResource(lastModifiedSince, content, appendedFileNameSet, "classpath:css/" + name);
				}
			}
		}
		if (lastModifiedSince <= requestLastModifiedSince) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
		}
		headers.setLastModified(lastModifiedSince);
		String outStr = replaceSystemVars(content.toString());
		return new ResponseEntity<String>(outStr, headers, HttpStatus.OK);
	}

	@ActionMethod(request = "form", response = "custom")
	public Object xml(@ParameterVariable("path") String path, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		long requestLastModifiedSince = request.getDateHeader("If-Modified-Since");
		long lastModifiedSince = requestLastModifiedSince;
		headers.setContentType(new MediaType("text", "xml", Charset.forName("utf-8")));
		StringBuilder content = new StringBuilder();
		Set<String> appendedFileNameSet = new LinkedHashSet<String>();
		if (StringUtils.isNotBlank(path)) {
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
			lastModifiedSince = loadClasspathResource(lastModifiedSince, content, appendedFileNameSet, "classpath:xml/" + path);
		}
		if (lastModifiedSince <= requestLastModifiedSince) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
		}
		headers.setLastModified(lastModifiedSince);
		String outStr = replaceSystemVars(content.toString());
		return new ResponseEntity<String>(outStr, headers, HttpStatus.OK);
	}

	@ActionMethod(response = "view")
	public Object jsp(@ParameterVariable("path") String path, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("classpath:" + path);
		return mav;
	}

	@ActionMethod(request = "form", response = "custom")
	public Object js(@ParameterVariable("path") String path, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		long requestLastModifiedSince = request.getDateHeader("If-Modified-Since");
		long lastModifiedSince = requestLastModifiedSince;
		headers.setContentType(new MediaType("application", "javascript", Charset.forName("utf-8")));
		StringBuilder content = new StringBuilder();
		Set<String> appendedFileNameSet = new LinkedHashSet<String>();
		if (StringUtils.isNotBlank(path)) {
			String[] names = StringUtils.split(path, ",");
			for (String name : names) {
				if ("*".equals(name)) {
					lastModifiedSince = loadAllCssContent(lastModifiedSince, content, appendedFileNameSet);
				} else {
					if (!StringUtils.endsWithIgnoreCase(name, ".js")) {
						name += ".js";
					}
					if (name.startsWith("/")) {
						name = name.substring(1);
					}
					lastModifiedSince = loadClasspathResource(lastModifiedSince, content, appendedFileNameSet, "classpath:js/" + name);
				}
			}
		}
		if (lastModifiedSince <= requestLastModifiedSince) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
		}
		headers.setLastModified(lastModifiedSince);
		String outStr = replaceSystemVars(content.toString());
		return new ResponseEntity<String>(outStr, headers, HttpStatus.OK);
	}

	private String replaceSystemVars(String content) {
		RestContext context = RestSessionFactory.getCurrentContext();
		String contextPath = context.getHttpServletRequest().getContextPath();
		String resRoot = contextPath + "/service/rest/" + context.getModule() + "/" + context.getResource() + "/res?path=";
		String imgRoot = contextPath + "/service/rest/" + context.getModule() + "/" + context.getResource() + "/img?path=";
		String cssRoot = contextPath + "/service/rest/" + context.getModule() + "/" + context.getResource() + "/css?path=";
		String jsRoot = contextPath + "/service/rest/" + context.getModule() + "/" + context.getResource() + "/js?path=";
		String xmlRoot = contextPath + "/service/rest/" + context.getModule() + "/" + context.getResource() + "/xml?path=";
		content = content.replaceAll("\\{context\\-path\\}", contextPath);
		content = content.replaceAll("\\{res\\-root\\}", resRoot);
		content = content.replaceAll("\\{img\\-root\\}", imgRoot);
		content = content.replaceAll("\\{css\\-root\\}", cssRoot);
		content = content.replaceAll("\\{js\\-root\\}", jsRoot);
		content = content.replaceAll("\\{xml\\-root\\}", xmlRoot);
		return content;
	}

	@ActionMethod(request = "form", response = "custom")
	public Object img(@ParameterVariable("path") String path, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		long requestLastModifiedSince = request.getDateHeader("If-Modified-Since");
		long lastModifiedSince = requestLastModifiedSince;
		// headers.setContentType(new MediaType("image", "javascript"));
		if (StringUtils.isBlank(path)) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		try {
			String resPath = "classpath:img/";
			if (path.startsWith("/")) {
				resPath += path.substring(1);
			} else {
				resPath += path;
			}
			URL url = org.springframework.util.ResourceUtils.getURL(resPath);
			URL fileUrl = url;
			if (ResourceUtils.isJarURL(url)) {
				fileUrl = ResourceUtils.extractJarFileURL(url);
			}
			File file = ResourceUtils.getFile(fileUrl);
			if (file != null && lastModifiedSince < file.lastModified()) {
				lastModifiedSince = file.lastModified();
			}
			headers.setContentType(MimeUtils.getMediaType(url.getPath()));
			if (lastModifiedSince <= requestLastModifiedSince) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
			}
			InputStream inputStream = url.openStream();
			byte[] data = com.taole.framework.util.IOUtils.getBytes(inputStream);
			headers.setLastModified(lastModifiedSince);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (FileNotFoundException ex) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @param lastModifiedSince
	 * @param content
	 * @param appendedFileNames
	 * @param resourceLocation
	 * @return
	 */
	private long loadClasspathResource(long lastModifiedSince, StringBuilder content, Set<String> appendedFileNameSet, String resourceLocation) {
		try {
			URL url = ResourceUtils.getURL(resourceLocation);
			URL fileUrl = url;
			boolean jarFile = false;
			if (ResourceUtils.isJarURL(url)) {
				jarFile = true;
				fileUrl = ResourceUtils.extractJarFileURL(url);
			}
			File file = ResourceUtils.getFile(fileUrl);
			if (!jarFile) {
				if (appendedFileNameSet.contains(file.getAbsolutePath())) {
					return lastModifiedSince;
				}
				appendedFileNameSet.add(file.getAbsolutePath());
			}
			if (file != null && lastModifiedSince < file.lastModified()) {
				lastModifiedSince = file.lastModified();
			}
			InputStream inputStream = url.openStream();
			content.append(IOUtils.toString(inputStream, "UTF-8")).append("\r\n");
			IOUtils.closeQuietly(inputStream);
		} catch (FileNotFoundException e) {
			logger.warn("not found resource [" + resourceLocation + "]");
		} catch (IOException e) {
			logger.error("read css resource [" + resourceLocation + "] error", e);
		}
		return lastModifiedSince;
	}

	/**
	 * @param lastModifiedSince
	 * @param content
	 * @param appendedFileNameSet
	 * @return
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private long loadAllCssContent(long lastModifiedSince, StringBuilder content, Set<String> appendedFileNameSet) {
		try {
			Class<?> configClz = Class.forName("spring.WebConfig");
			Import importAnnotation = configClz.getAnnotation(Import.class);
			if (importAnnotation != null) {
				Class<?>[] clazzs = importAnnotation.value();
				for (Class<?> projClazz : clazzs) {
					if (!StringUtils.endsWith(projClazz.getSimpleName(), "ProjectConfig")) {
						continue;
					}
					String moduleName = null;
					try {
						moduleName = (String) projClazz.getField("NAME").get(null);
					} catch (Exception e) {
						logger.error("get ProjectConfig's NAME field failed", e);
					}
					if (StringUtils.isNotBlank(moduleName)) {
						lastModifiedSince = loadClasspathResource(lastModifiedSince, content, appendedFileNameSet, "classpath:css/" + moduleName + ".css");
					}
				}
			}
			File cssDir = null;
			try {
				cssDir = ResourceUtils.getFile("classpath:css/");
			} catch (FileNotFoundException e) {
				logger.info("not found css directory [classpath:css/]", e);
			}
			if (cssDir != null && cssDir.isDirectory()) {
				File[] files = cssDir.listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						if (pathname.isFile() && pathname.getName().toLowerCase().endsWith(".css")) {
							return true;
						}
						return false;
					}
				});
				for (File cssFile : files) {
					if (!appendedFileNameSet.contains(cssFile.getAbsolutePath())) {
						if (cssFile.lastModified() > lastModifiedSince) {
							lastModifiedSince = cssFile.lastModified();
						}
						try {
							FileInputStream inputStream = new FileInputStream(cssFile);
							content.append(IOUtils.toString(inputStream, "UTF-8")).append("\r\n");
							IOUtils.closeQuietly(inputStream);
						} catch (FileNotFoundException e) {
							logger.info("not found css file [" + cssFile.getAbsolutePath() + "]");
						} catch (IOException e) {
							logger.error("read css content error [" + cssFile.getAbsolutePath() + "]", e);
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			logger.info("no spring.WebConfig class found so not load module css content.", e);
		}
		return lastModifiedSince;
	}

	public static void main(String[] args) {
		String s = "background-image: url({img-root}/organization/organization_group16.png) !important;";
		s = s.replaceAll("\\{img\\-root\\}", "ABC");
		System.out.println(s);
	}
}
