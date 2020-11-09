/**
 * Project:taole-heaframework
 * File:RestClient.java
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.framework.rest.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.taole.framework.bean.DomainObject;
import com.taole.framework.util.DomainObjectUtils;

/**
 * Rest client for rest service.
 * 
 * <pre>
 * RestClient client = new RestClient(null, null);
 * String response = client.request(url).post().handleWith(new StringResponseHandler()).getResult(String.class);
 * </pre>
 * @author Rory
 * @date Dec 13, 2011
 * @version $Id: RestClient.java 9691 2017-07-10 07:48:34Z lizhm $
 */
public class RestClient {
	
	/**
	 * 定义请求格式，用于自动绑定到对象
	 */
	public static final String X_REQUEST_FORMAT = "x-request-format";
	
	/**
	 * 定义json格式的请求header值为 {@code application/json}
	 */
	public static final String APPLICATION_JSON = "application/json";

	/**
	 * The rest service url.
	 */
	private String url;
	
	/**
	 * The params to pass.
	 */
	private Map<String, Object> params;
	
	/**
	 * 上传文件的列表.
	 */
	private Map<String, Object> fileOrInputStreamMap;
	
	/**
	 * The json string to post.
	 */
	private String json;
	
	/**
	 * The http response.
	 */
	private HttpResponse httpResponse;
	
	private Map<String, String> headers;
	
	/**
	 * The final result.
	 */
	private Object result;
	
	private String username;
	
	private String password;
	
	private HttpMethod method;
	
	private String referer;
	
	private boolean trustAnySsl = false;
	
	private String agent = "taole-rest-client";
	
	public RestClient() {
	}
	
	public RestClient(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public RestClient request(String url) {
		Assert.hasText(url, "url must have text; it must not be null, empty, or blank");
		this.url = url;
		return this;
	}
	
	/**
	 * 信任所有的ssl，包括自签名的证书。
	 * @return
	 */
	public RestClient trustAnySsl() {
		this.trustAnySsl = true;
		return this;
	}

	public RestClient agent(String agent) {
		this.agent = agent;
		return this;
	}
	
	public RestClient request(String baseUrl, Class<? extends DomainObject> clazz, String action) {
		return request(baseUrl, clazz, "collection", action);
	}
	
	public RestClient request(String baseUrl, Class<? extends DomainObject> clazz, String resource, String action) {
		StringBuilder sb = new StringBuilder();
		if (baseUrl.endsWith("/")) {
			sb.append(baseUrl.substring(0, baseUrl.length() -1));
		} else {
			sb.append(baseUrl);
		}
		sb.append("/service/rest/").append(DomainObjectUtils.getEntityName(clazz)).append("/").append(resource).append("/").append(action);
		this.url = sb.toString();
		return this;
	}
	
	public RestClient withJsonData(String json) {
		this.json = json;
		return this;
	}
	
	public RestClient referer(String referer) {
		this.referer = referer;
		return this;
	}
	
	public RestClient withJsonData(JSONObject json) {
		this.json = json.toString();
		return this;
	}
	
	public RestClient withJsonData(Map<String, Object> params) {
		this.json = new JSONObject(params).toString();
		return this;
	}
	
	public RestClient withData(Map<String, Object> params) {
		this.params = params;
		return this;
	}
	
	public RestClient withData(String name, String value) {
		if (params == null) {
			params = Maps.newHashMap();
		}
		params.put(name, value);
		return this;
	}
	
	public RestClient withHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}
	
	public RestClient get() throws RestClientException {
		Assert.hasText(url, "url must have text; it must not be null, empty, or blank.");
		this.method = HttpMethod.GET;
		return this;
	}

	/**
	 * @param responseHandler
	 * @return
	 * @throws RestClientException
	 */
	private Object doGet(ResponseHandler<?> responseHandler) throws RestClientException {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		if (trustAnySsl) {
			SSLContext sslContext = createTrustAnySslContext();
			clientBuilder.setSslcontext(sslContext);
		}
		CloseableHttpClient httpClient = clientBuilder.build();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
		try {
			if (params != null && !params.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					Object value = entry.getValue();
					if (value == null) {
						continue;
					}
					String strVal = null;
					if (value instanceof Date) {
						strVal = DateFormatUtils.ISO_DATETIME_FORMAT.format((Date) value);
					} else {
						strVal = String.valueOf(value);
					}
					sb.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(strVal, "UTF-8"));
				}
				String paramString = sb.toString();
				if (StringUtils.isNotBlank(paramString)) {
					if (url.indexOf("?") != -1) {
						url = url + paramString;
					} else {
						url = url + "?" + paramString.substring(1);
					}
				}
			}
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			httpGet.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
			if (StringUtils.isNotBlank(agent)) {
				httpGet.addHeader(HTTP.USER_AGENT, agent);
			}
			if (StringUtils.isNotBlank(referer)) {
				httpGet.addHeader("Referer", referer);
			}
			
			//添加header
			if(headers != null && headers.size() > 0){
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpGet.addHeader(header.getKey(), header.getValue());
				}
			}
			
			URI uri = new URI(url);
			HttpClientContext context = HttpClientContext.create();
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				HttpHost host = URIUtils.extractHost(uri);
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), new UsernamePasswordCredentials(username, password));
				context.setCredentialsProvider(credsProvider);
			}
			httpResponse = httpClient.execute(httpGet, context);
			if (!(responseHandler instanceof VoidResponseHandler)) {
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					String stringResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
					throw new RestClientException(-1, String.format("HTTP Status - %s (%d) [%s] response:\n %s", httpResponse.getStatusLine().getReasonPhrase(), 
							statusCode, url, stringResponse), stringResponse);
				}
			}
			return responseHandler.handleResponse(httpResponse);
		} catch (RestClientException e) {
			throw e;
		} catch (Exception e) {
			throw new RestClientException(-1, "get the rest service failed", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public RestClient post() throws RestClientException {
		Assert.hasText(url, "url must have text; it must not be null, empty, or blank.");
		this.method = HttpMethod.POST;
		return this;
	}
	
	/**
	 * 上传文件的名称和内容对应的Map,支持 {@link java.io.File}, {@code byte[]}  数组, {@code java.io.InputStream} inputstream
	 * @param fileOrInputStreamMap
	 * @return
	 * @throws RestClientException
	 */
	public RestClient upload(Map<String, Object> fileOrInputStreamMap) throws RestClientException {
		Assert.hasText(url, "url must have text; it must not be null, empty, or blank.");
		Assert.notNull(fileOrInputStreamMap, "upload file is required.");
		Assert.isTrue(!fileOrInputStreamMap.isEmpty(), "upload file is required.");
		this.method = HttpMethod.PUT;
		this.fileOrInputStreamMap = fileOrInputStreamMap;
		return this;
	}

	/**
	 * @param responseHandler
	 * @return
	 * @throws RestClientException
	 */
	private Object doPost(ResponseHandler<?> responseHandler) throws RestClientException {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		if (trustAnySsl) {
			SSLContext sslContext = createTrustAnySslContext();
			clientBuilder.setSslcontext(sslContext);
		}
		CloseableHttpClient httpClient = clientBuilder.build();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
			if (StringUtils.isNotBlank(agent)) {
				httpPost.addHeader(HTTP.USER_AGENT, agent);
			}
			if (StringUtils.isNotBlank(referer)) {
				httpPost.addHeader("Referer", referer);
			}
			
			//添加header
			if(headers != null && headers.size() > 0){
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpPost.addHeader(header.getKey(), header.getValue());
				}
			}
			
			URI uri = new URI(url);
			HttpClientContext context = HttpClientContext.create();
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				HttpHost host = URIUtils.extractHost(uri);
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), new UsernamePasswordCredentials(username, password));
				context.setCredentialsProvider(credsProvider);
			}
			HttpEntity httpEntity = null;
			if (StringUtils.isNotBlank(json)) {
				httpPost.addHeader(X_REQUEST_FORMAT, APPLICATION_JSON);
				httpEntity = new StringEntity(json, "UTF-8");
			} else if (params != null && !params.isEmpty()) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					Object value = entry.getValue();
					if (value == null) {
						continue;
					}
					String strVal = null;
					if (value instanceof Date) {
						strVal = DateFormatUtils.ISO_DATETIME_FORMAT.format((Date) value);
					} else {
						strVal = String.valueOf(value);
					}
					nvps.add(new BasicNameValuePair(entry.getKey(), strVal));
				}
				httpEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
			}
			httpPost.setEntity(httpEntity);
			httpPost.setConfig(requestConfig);
			httpResponse = httpClient.execute(httpPost, context);
			if (!(responseHandler instanceof VoidResponseHandler)) {
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					String stringResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
					throw new RestClientException(-1, String.format("HTTP Status - %s (%d) [%s] response:\n %s", httpResponse.getStatusLine().getReasonPhrase(), 
							statusCode, url, stringResponse), stringResponse);
				}
			}
			return responseHandler.handleResponse(httpResponse);
		} catch (RestClientException e) {
			throw e;
		} catch (Exception e) {
			throw new RestClientException(-1, "post the rest service failed", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	private SSLContext createTrustAnySslContext() {
		SSLContext sslContext = SSLContexts.createDefault();
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					return true;
				}
			}).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sslContext;
	}
	
	/**
	 * @param responseHandler
	 * @return
	 * @throws RestClientException
	 */
	private Object doUpload(ResponseHandler<?> responseHandler) throws RestClientException {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		if (trustAnySsl) {
			SSLContext sslContext = createTrustAnySslContext();
			clientBuilder.setSslcontext(sslContext);
		}
		CloseableHttpClient httpClient = clientBuilder.build();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
			if (StringUtils.isNotBlank(agent)) {
				httpPost.addHeader(HTTP.USER_AGENT, agent);
			}
			if (StringUtils.isNotBlank(referer)) {
				httpPost.addHeader("Referer", referer);
			}
			
			//添加header
			if(headers != null && headers.size() > 0){
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpPost.addHeader(header.getKey(), header.getValue());
				}
			}
			
			URI uri = new URI(url);
			HttpClientContext context = HttpClientContext.create();
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				HttpHost host = URIUtils.extractHost(uri);
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), new UsernamePasswordCredentials(username, password));
				context.setCredentialsProvider(credsProvider);
			}
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					Object value = entry.getValue();
					if (value == null) {
						continue;
					}
					String strVal = null;
					if (value instanceof Date) {
						strVal = DateFormatUtils.ISO_DATETIME_FORMAT.format((Date) value);
					} else {
						strVal = String.valueOf(value);
					}
					entityBuilder.addPart(entry.getKey(), new StringBody(strVal, ContentType.create("text/plain", Charset.forName("UTF-8"))));
				}
			}
			for (Map.Entry<String, Object> entry : fileOrInputStreamMap.entrySet()) {
				Object value = entry.getValue();
				if (value.getClass().isArray()) {
					entityBuilder.addPart(entry.getKey(), new ByteArrayBody((byte[])value, entry.getKey()));
				} else if (value instanceof File) {
					entityBuilder.addPart(entry.getKey(), new FileBody((File)entry.getValue()));
				} else if (value instanceof InputStream)
					entityBuilder.addPart(entry.getKey(), new InputStreamBody((InputStream) value, entry.getKey()));
			}
			HttpEntity httpEntity = entityBuilder.build();
			httpPost.setEntity(httpEntity);
			httpPost.setConfig(requestConfig);
			httpResponse = httpClient.execute(httpPost, context);
			if (!(responseHandler instanceof VoidResponseHandler)) {
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					String stringResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
					throw new RestClientException(-1, String.format("HTTP Status - %s (%d) [%s] response:\n %s", httpResponse.getStatusLine().getReasonPhrase(), 
							statusCode, url, stringResponse), stringResponse);
				}
			}
			return responseHandler.handleResponse(httpResponse);
		} catch (RestClientException e) {
			throw e;
		} catch (Exception e) {
			throw new RestClientException(-1, "post the rest service failed", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public RestClient handleWith(ResponseHandler<?> responseHandler) throws RestClientException {
		Assert.notNull(this.method, "httpMethod is required.");
		try {
			if (this.method == HttpMethod.GET) {
				this.result = doGet(responseHandler);
			} else if (this.method == HttpMethod.POST) {
				this.result = doPost(responseHandler);
			} else if (this.method == HttpMethod.PUT) {
				this.result = doUpload(responseHandler);
			}
			
		} catch (RestClientException e) {
			throw e;
		} catch (Exception e) {
			throw new RestClientException(-2, "can not handle the httpresponse.", e);
		}
		return this;
	}
	
	public Object getResult() {
		return this.result;
	}
	
	public <T> T getResult(Class<T> clzz) {
		if (result == null) {
			return null;
		}
		return clzz.cast(result);
	}
	
}
