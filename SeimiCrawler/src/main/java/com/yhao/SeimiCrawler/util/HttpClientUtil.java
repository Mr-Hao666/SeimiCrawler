package com.yhao.SeimiCrawler.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HTTP连接服务的帮助类
 *
 * @author long.hua
 * @version 1.0.0
 * @date 2014年8月28日
 */
public final class HttpClientUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
	
	
	private static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf =
					new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
						public boolean verify(String arg0, SSLSession arg1) {
							return true;
						}
						
						public void verify(String host, SSLSocket ssl) throws IOException {
						}
						
						public void verify(String host, X509Certificate cert) throws SSLException {
						}
						
						public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
						}
					});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException e) {
			LOGGER.error("Create ssl socket factory error!", e);
			return null;
		}
	}
	
	public static JSONObject json(String url, Map<String, String> headers, Map<String, Object> parameters) throws IllegalAccessException {
		
		CloseableHttpClient client;
		if (url.startsWith("https://")) {
			client = createSSLInsecureClient();
		} else {
			client = HttpClients.createDefault();
		}
		if (client == null) {
			throw new IllegalAccessException("Failed to create http client.");
		}
		
		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			post.setHeader("Accept", "application/json; charset=UTF-8");
			if (headers != null && headers.size() != 0) {
				for (String key : headers.keySet()) {
					post.setHeader(key, headers.get(key));
				}
			}
			
			post.setEntity(new StringEntity(JSON.toJSONString(parameters), Charset.forName("UTF-8")));
			
			CloseableHttpResponse response = client.execute(post);
			if (response == null) {
				throw new IllegalAccessException("Failed to get http response.");
			} else if (response.getStatusLine() == null) {
				throw new IllegalAccessException("Failed to generate http response.");
			} else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new IllegalAccessException("Http status exception, and code: " + response.getStatusLine().getStatusCode());
			}
			
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			is.close();
			
			return JSON.parseObject(builder.toString());
		} catch (Exception e) {
			LOGGER.error("请求异常", e);
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 发送GET类型的HTTP请求，
	 *
	 * @param url       请求的URL地址
	 * @param headerMap 设置Header
	 * @return 返回请求连接相应的json字符串
	 */
	public static String getData(String url, Map<String, String> headerMap) {
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https://")) {
			httpClient = createSSLInsecureClient();
			if (httpClient == null) {
				return null;
			}
		} else {
			httpClient = HttpClients.createDefault();
		}
		
		// 创建HttpGet请求
		int status = -1;
		String result = null;
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		try {
			
			// 设置请求头部
			httpGet = new HttpGet(url);
			Header[] headers = toHeaders(headerMap);
			if (null != headers && headers.length > 0) {
				httpGet.setHeaders(headers);
			}
			
			// 执行get请求.
			long b = System.currentTimeMillis();
			response = httpClient.execute(httpGet);
			if (null != response && response.getStatusLine() != null) {
				status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_OK || status == HttpStatus.SC_CREATED) {
					// 获取响应实体
					HttpEntity entity = response.getEntity();
					if (null != entity) {
						result = EntityUtils.toString(entity, "UTF-8");
					}
				}
			}
			long e = System.currentTimeMillis();
			
			// 打印响应内容
			LOGGER.info("End request, cost time: {}", (e - b));
			LOGGER.debug("Response content: {}", ((null != result && result.length() > 512) ? result.substring(0, 512) : result));
			
		} catch (Exception e) {
			LOGGER.error("Request failed, HTTP status code: {}, url: {}", status, url, e);
		} finally {
			closeResource(response, httpGet, httpClient);
		}
		
		return result;
	}
	
	/**
	 * 发送GET类型的HTTP请求，
	 *
	 * @param url 请求的URL地址
	 * @return 返回请求连接相应的json字符串
	 */
	public static String getData(String url) {
		return getData(url, null);
	}
	
	/**
	 * 通过Get方法请求url获取字节数据
	 *
	 * @param url 请求地址
	 * @return 字节数据，失败为null
	 */
	public static byte[] getByte(String url) {
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https://")) {
			httpClient = createSSLInsecureClient();
			if (httpClient == null) {
				return null;
			}
		} else {
			httpClient = HttpClients.createDefault();
		}
		
		// 创建HttpGet请求
		int status = -1;
		byte data[] = null;
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		try {
			
			httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			if (null != response && response.getStatusLine() != null) {
				status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_OK || status == HttpStatus.SC_CREATED) {
					// 获取响应实体
					HttpEntity entity = response.getEntity();
					if (null != entity) {
						data = EntityUtils.toByteArray(entity);
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("Request failed, HTTP status code: {}, url: {}", status, url, e);
		} finally {
			closeResource(response, httpGet, httpClient);
		}
		
		return data;
	}
	
	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 *
	 * @param url      post请求的链接
	 * @param paramMap 请求的参数Map
	 */
	public static String postData(String url, Map<String, Object> paramMap) {
		return postData(url, null, paramMap);
	}
	
	private static Header[] toHeaders(Map<String, String> headerMap) {
		if (null == headerMap || headerMap.isEmpty()) {
			return null;
		}
		
		int i = 0;
		Header[] headers = new Header[headerMap.size()];
		for (Entry<String, String> entry : headerMap.entrySet()) {
			if (null != entry.getValue()) {
				headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
			}
		}
		
		return headers;
	}
	
	/**
	 * 将保存POST请求的参数以及值得键值对mao转换为HTTPClient POST请求需要的List<NameValuePair>类型
	 *
	 * @param paramMap 请求参数
	 */
	private static List<NameValuePair> toNameValuePairList(Map<String, Object> paramMap) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		if (paramMap != null) {
			for (Entry<String, Object> entry : paramMap.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					if (value instanceof String[]) {
						String[] values = (String[]) value;
						if (values.length > 0) {
							for (String str : values) {
								nvps.add(new BasicNameValuePair(entry.getKey(), str));
							}
						}
					} else {
						nvps.add(new BasicNameValuePair(entry.getKey(), value.toString()));
					}
				}
			}
		}
		
		return nvps;
	}
	
	public static String postData(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https://")) {
			httpClient = createSSLInsecureClient();
			if (httpClient == null) {
				return null;
			}
		} else {
			httpClient = HttpClients.createDefault();
		}
		
		// 创建httpPost
		int status = -1;
		String result = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		try {
			
			// 设置请求头部
			httpPost = new HttpPost(url);
			Header[] headers = toHeaders(headerMap);
			if (null != headers && headers.length > 0) {
				httpPost.setHeaders(headers);
			}
			
			// 设置请求参数
			List<NameValuePair> valuePairs = toNameValuePairList(paramMap);
			if (null != valuePairs && !valuePairs.isEmpty()) {
				httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, "UTF-8"));
			}
			
			long b = System.currentTimeMillis();
			response = httpClient.execute(httpPost);
			if (null != response && response.getStatusLine() != null) {
				status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_OK || status == HttpStatus.SC_CREATED) {
					HttpEntity entity = response.getEntity();
					if (null != entity) {
						result = EntityUtils.toString(entity, "UTF-8");
					}
				}
			}
			long e = System.currentTimeMillis();
			
			// 打印响应内容
			LOGGER.info("End request, cost time: {}", (e - b));
			LOGGER.debug("Response content: {}", ((null != result && result.length() > 512) ? result.substring(0, 512) : result));
			
		} catch (Exception e) {
			LOGGER.error("Request failed, HTTP status code: {}, url: {}", status, url, e);
		} finally {
			closeResource(response, httpPost, httpClient);
		}
		
		return result;
	}
	
	/**
	 * 发送post请求，参数为字符串
	 *
	 * @param url  请求地址
	 * @param post 请求数据
	 * @return 返回的内容
	 */
	public static String postData(String url, String post) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https://")) {
			httpClient = createSSLInsecureClient();
			if (httpClient == null) {
				return null;
			}
		} else {
			httpClient = HttpClients.createDefault();
		}
		
		// 创建httpPost
		int status = -1;
		String result = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		try {
			
			// 设置请求头部
			httpPost = new HttpPost(url);
			
			// 设置请求参数
			StringEntity stringEntity = new StringEntity(post, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			
			// 执行post请求
			long b = System.currentTimeMillis();
			System.out.println("Start request, url: " + url);
			response = httpClient.execute(httpPost);
			if (null != response && response.getStatusLine() != null) {
				status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_OK || status == HttpStatus.SC_CREATED) {
					HttpEntity entity = response.getEntity();
					if (null != entity) {
						result = EntityUtils.toString(entity, "UTF-8");
					}
				}
			}
			long e = System.currentTimeMillis();
			
			// 打印响应内容
			LOGGER.info("End request, cost time: {}", (e - b));
			LOGGER.debug("Response content: {}", ((null != result && result.length() > 512) ? result.substring(0, 512) : result));
			
		} catch (Exception e) {
			LOGGER.error("Request failed, HTTP status code: {}, url: {}", status, url, e);
		} finally {
			closeResource(response, httpPost, httpClient);
		}
		
		return result;
		
	}
	
	
	public String postFile(String url, Map<String, File> files, Map<String, String> params) {
		if (Colls.isEmpty(files)) {
			return null;
		}
		
		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https://")) {
			httpClient = createSSLInsecureClient();
			if (httpClient == null) {
				return null;
			}
		} else {
			httpClient = HttpClients.createDefault();
		}
		
		// 创建httpPost
		int statusCode = -1;
		String result = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		try {
			
			httpPost = new HttpPost(url);
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			for (Entry<String, File> entry : files.entrySet()) {
				builder.addBinaryBody(entry.getKey(), entry.getValue());
			}
			for (Entry<String, String> entry : params.entrySet()) {
				builder.addPart(entry.getKey(), new StringBody(entry.getValue()));
			}
			httpPost.setEntity(builder.build());
			
			response = httpClient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED) {
				
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity);
				EntityUtils.consume(resEntity);
			}
			
		} catch (ParseException | IOException e) {
			LOGGER.error("上传文件失败", e);
		} finally {
			closeResource(response, httpPost, httpClient);
		}
		
		return result;
	}
	
	/**
	 * 关闭连接HTTP过程产生的资源
	 *
	 * @param httpResponse HTTP响应
	 * @param httpClient   HTTP客户端
	 */
	private static void closeResource(CloseableHttpResponse httpResponse, HttpRequestBase httpMethod, CloseableHttpClient httpClient) {
		if (httpResponse != null) {
			try {
				httpResponse.close();
			} catch (IOException e) {
				LOGGER.error("Close http response error!", e);
			}
		}
		
		if (null != httpMethod) {
			httpMethod.releaseConnection();
		}
		
		// 关闭连接,释放资源
		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOGGER.error("Close http client error!", e);
			}
		}
	}
	
}
