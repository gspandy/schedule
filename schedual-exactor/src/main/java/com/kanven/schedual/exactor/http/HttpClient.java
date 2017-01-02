package com.kanven.schedual.exactor.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http工具类
 * 
 * @author kanven
 *
 */
public class HttpClient {

	private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

	private static final String ENCODE = "UTF-8";

	private static final int SUCCESS = 200;

	public static String parseParameter(Map<String, String> params) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String value = entry.getValue();
			if (value != null) {
				pairs.add(new BasicNameValuePair(entry.getKey(), value));
			}
		}
		try {
			return EntityUtils.toString(new UrlEncodedFormEntity(pairs, ENCODE));
		} catch (ParseException e) {
			log.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public static String doGet(String url, int connTimeOut, int socketTimeout)
			throws ClientProtocolException, IOException {
		if (StringUtils.isEmpty(url)) {
			log.error("没有指定url地址！");
			return null;
		}
		long startTime = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(url);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connTimeOut).setSocketTimeout(socketTimeout)
				.build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		CloseableHttpResponse response = null;
		String result = null;
		try {
			response = client.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status != SUCCESS) {
				log.error(url + "请求失败,状态码:" + status);
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, ENCODE);
				}
				EntityUtils.consume(entity);
			}
		} finally {
			try {
				client.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		if (log.isInfoEnabled()) {
			log.debug("cost:" + (System.currentTimeMillis() - startTime) + ",url:" + url);
		}
		return result;
	}

	public static String doPost(String url, Map<String, String> params, int connTimeOut, int socketTimeout)
			throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(url)) {
			log.error("没有指定url地址！");
			return null;
		}
		if (log.isDebugEnabled()) {
			log.debug("参数:" + params);
		}
		HttpPost post = new HttpPost(url);
		if (params == null || params.isEmpty()) {
			return null;
		}
		long startTime = System.currentTimeMillis();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		Set<String> keys = params.keySet();
		for (String key : keys) {
			pairs.add(new BasicNameValuePair(key, params.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(pairs, ENCODE));
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connTimeOut).setSocketTimeout(socketTimeout)
				.build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		CloseableHttpResponse response = null;
		String result = null;
		try {
			response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			if (status != SUCCESS) {
				log.error(url + "请求失败,状态码:" + status);
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, ENCODE);
					if (log.isDebugEnabled()) {
						log.debug("请求结果：" + result);
					}
					EntityUtils.consume(entity);
				}
			}
		} catch (ClientProtocolException e) {
			log.error("Http访问异常,参数：" + params, e);
		} catch (IOException e) {
			log.error("Http访问异常,参数：" + params, e);
		} finally {
			try {
				client.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error("Http连接关闭异常！", e);
			}
		}
		if (log.isInfoEnabled()) {
			log.debug("cost:" + (System.currentTimeMillis() - startTime) + ",url:" + url);
		}
		return result;
	}
}
