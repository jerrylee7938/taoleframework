package com.taole.toolkit.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpClientUtils {
	public static final int THREAD_POOL_SIZE = 5;

	public interface HttpClientDownLoadProgress {
		public void onProgress(int progress);
	}

	private static HttpClientUtils httpClientDownload;

	private ExecutorService downloadExcutorService;

	private HttpClientUtils() {
		downloadExcutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	private static final String HTTP = "http";
	private static final String HTTPS = "https";

	public static HttpClientUtils getInstance() {
		if (httpClientDownload == null) {
			httpClientDownload = new HttpClientUtils();
		}
		return httpClientDownload;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 */
	public void download(final String url, final String filePath) {
		downloadExcutorService.execute(new Runnable() {

			@Override
			public void run() {
				httpDownloadFile(url, filePath, null, null);
			}
		});
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 * @param progress
	 *            进度回调
	 */
	public void download(final String url, final String filePath, final HttpClientDownLoadProgress progress) {
		downloadExcutorService.execute(new Runnable() {

			@Override
			public void run() {
				httpDownloadFile(url, filePath, progress, null);
			}
		});
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 */
	private void httpDownloadFile(String url, String filePath, HttpClientDownLoadProgress progress, Map<String, String> headMap) {
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getHttpClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpGet httpGet = new HttpGet(url);
			setGetHead(httpGet, headMap);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				HttpEntity httpEntity = response1.getEntity();
				long contentLength = httpEntity.getContentLength();
				InputStream is = httpEntity.getContent();
				// 根据InputStream 下载文件
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int r = 0;
				long totalRead = 0;
				while ((r = is.read(buffer)) > 0) {
					output.write(buffer, 0, r);
					totalRead += r;
					if (progress != null) {// 回调进度
						progress.onProgress((int) (totalRead * 100 / contentLength));
					}
				}
				FileOutputStream fos = new FileOutputStream(filePath);
				output.writeTo(fos);
				output.flush();
				output.close();
				fos.close();
				EntityUtils.consume(httpEntity);
			} finally {
				response1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString)
					throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate, String paramString)
					throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	public static CloseableHttpClient getHttpClient() throws Exception {
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();
		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register(HTTP, PlainConnectionSocketFactory.INSTANCE).register(HTTPS, new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);
		// 创建自定义的httpclient对象
		
		RequestConfig defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(5000)
			    .setConnectTimeout(5000)
			    .setConnectionRequestTimeout(5000)
			    .setStaleConnectionCheckEnabled(true).build();
			    
		CloseableHttpClient client = HttpClients.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(defaultRequestConfig)
				.build();
		return client;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public String httpGet(String url) {
		return httpGet(url, null);
	}

	/**
	 * http get请求
	 * 
	 * @param url
	 * @return
	 */
	public String httpGet(String url, Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getHttpClient();
		} catch (Exception e) {
			System.out.println("[HttpClientUtils.httpGet]getHttpClient error!");
			e.printStackTrace();
		}
		try {
			HttpGet httpGet = new HttpGet(url);
			setGetHead(httpGet, headMap);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				HttpEntity entity = response1.getEntity();
				responseContent = getRespString(entity);
				EntityUtils.consume(entity);
			} finally {
				response1.close();
			}
		} catch (Exception e) {
			System.out.println("[HttpClientUtils.httpGet]httpGet error!");
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				System.out.println("[HttpClientUtils.httpGet]httpclient close error!");
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public String httpPost(String url, Map<String, String> paramsMap) {
		return httpPost(url, paramsMap, null);
	}

	public String httpPost(String url, JSONObject jsonObject) {
		return httpPost(url, jsonObject, null);
	}

	public String httpPut(String url, JSONObject paramsMap) {
		return httpPut(url, paramsMap, null);
	}
	
	/**
	 * http的post请求
	 * 
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public String httpPost(String url, String params, Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getHttpClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			setPostHead(httpPost, headMap);
			setStringParams(httpPost, params);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				responseContent = getRespString(entity);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * http的post请求
	 * 
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public String httpPost(String url, Map<String, String> paramsMap, Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getHttpClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			setPostHead(httpPost, headMap);
			setPostParams(httpPost, paramsMap);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				responseContent = getRespString(entity);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * http的post请求
	 * 
	 * @param url
	 * @param jsonObject
	 * @return
	 */
	public String httpPost(String url, JSONObject jsonObject, Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getHttpClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			setPostHead(httpPost, headMap);
			setJsonParams(httpPost, jsonObject);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				responseContent = getRespString(entity);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * http的put请求
	 * 
	 * @param url
	 * @param jsonObject
	 * @return
	 */
	public String httpPut(String url, JSONObject jsonObject, Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = null;
		try {
			httpclient = getHttpClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpPut httpPut = new HttpPut(url);
			setPutHead(httpPut, headMap);
			setJsonParams(httpPut, jsonObject);
			CloseableHttpResponse response = httpclient.execute(httpPut);
			try {
				HttpEntity entity = response.getEntity();
				responseContent = getRespString(entity);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * 设置POST的参数
	 * 
	 * @param httpPost
	 * @param paramsMap
	 * @throws Exception
	 */
	private void setPostParams(HttpPost httpPost, Map<String, String> paramsMap) throws Exception {
		if (paramsMap != null && paramsMap.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = paramsMap.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		}
	}

	private <T extends HttpEntityEnclosingRequestBase> void setJsonParams(T t, JSONObject jsonParam) throws Exception {
		if (null == jsonParam)
			return;
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		t.setEntity(entity);
	}
	
	private <T extends HttpEntityEnclosingRequestBase> void setStringParams(T t, String param) throws Exception {
		if (null == param)
			return;
		StringEntity entity = new StringEntity(param, "utf-8");
		entity.setContentEncoding("UTF-8");
//		entity.setContentType("application/json");
		t.setEntity(entity);
	}

	/**
	 * 设置POST的参数
	 * 
	 * @param httpPut
	 * @param paramsMap
	 * @throws Exception
	 */
	private void setPutParams(HttpPut httpPut, Map<String, String> paramsMap) throws Exception {
		if (paramsMap != null && paramsMap.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = paramsMap.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
			}
			httpPut.setEntity(new UrlEncodedFormEntity(nvps));
		}
	}

	/**
	 * 设置http的HEAD
	 * 
	 * @param httpPost
	 * @param headMap
	 */
	private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpPost.addHeader(key, headMap.get(key));
			}
		}
	}

	/**
	 * 设置http的HEAD
	 * 
	 * @param httpPut
	 * @param headMap
	 */
	private void setPutHead(HttpPut httpPut, Map<String, String> headMap) {
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpPut.addHeader(key, headMap.get(key));
			}
		}
	}

	/**
	 * 设置http的HEAD
	 * 
	 * @param httpGet
	 * @param headMap
	 */
	private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpGet.addHeader(key, headMap.get(key));
			}
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param serverUrl
	 *            服务器地址
	 * @param localFilePath
	 *            本地文件路径
	 * @param serverFieldName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String uploadFileImpl(String serverUrl, String localFilePath, String serverFieldName, Map<String, String> params) throws Exception {
		String respStr = null;
		CloseableHttpClient httpclient = getHttpClient();
		try {
			HttpPost httppost = new HttpPost(serverUrl);
			FileBody binFileBody = new FileBody(new File(localFilePath));

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			// add the file params
			multipartEntityBuilder.addPart(serverFieldName, binFileBody);
			// 设置上传的其他参数
			setUploadParams(multipartEntityBuilder, params);

			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity resEntity = response.getEntity();
				respStr = getRespString(resEntity);
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return respStr;
	}

	/**
	 * 设置上传文件时所附带的其他参数
	 * 
	 * @param multipartEntityBuilder
	 * @param params
	 */
	private void setUploadParams(MultipartEntityBuilder multipartEntityBuilder, Map<String, String> params) {
		if (params != null && params.size() > 0) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				multipartEntityBuilder.addPart(key, new StringBody(params.get(key), ContentType.TEXT_PLAIN));
			}
		}
	}

	/**
	 * 将返回结果转化为String
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private String getRespString(HttpEntity entity) throws Exception {
		if (entity == null) {
			return null;
		}
		InputStream is = entity.getContent();
		StringBuffer strBuf = new StringBuffer();
		byte[] buffer = new byte[4096];
		int r = 0;
		while ((r = is.read(buffer)) > 0) {
			strBuf.append(new String(buffer, 0, r, "UTF-8"));
		}
		return strBuf.toString();
	}

	public static void main(String args[]) {
		String result = HttpClientUtils.getInstance().httpGet(
				"https://www.wodaibao.com/wdb-service-webapp/service/rest/Support.LinkApi/collection/getHomeLink");
		System.out.println("[HttpGetTask]result:" + result);
	}
}