package com.xwtec.xwserver.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.xwtec.xwserver.constant.ConstantKeys;

/**
 * 标题、简要说明. <br>
 * 提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具类 类详细说明.目前支持3种提交方法（POST，GET，数据流）
 * <p>
 * Copyright: Copyright (c) 2012-12-27 下午03:20:50
 * <p>
 * Company: 欣网视讯
 * <p>
 * 
 * @author xiongwei@mail.xwtec.cn
 * @version 1.0.0
 */
public class WxcsHttpClient {

	private final static Logger logger = Logger.getLogger(WxcsHttpClient.class);

	public static final String UTF8 = "UTF-8";

	public static final String GBK = "GBK";

	/** http的端口 */
	private static final int HTTP_PORT = 80;

	/** https的端口 */
	private static final int HTTPS_PORT = 443;

	/** http标记位 */
	private static final String HTTP = "http";

	/** https标记位 */
	private static final String HTTPS = "https";

	/** 设置编码格式 */
	private String encoding = UTF8;

	/** 设置每个路由默认最大连接数 */
	private int maxConnector = 50;

	// 请求超时
	private int connectTimeout = 60000;

	// 读取超时
	private int readTimeout = 60000;

	private static DefaultHttpClient httpclient = null;

	private static SchemeRegistry schemeRegistry = new SchemeRegistry();

	private static PoolingClientConnectionManager clientConnectionManager = null;

	static {

		schemeRegistry.register(new Scheme(HTTP, HTTP_PORT, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme(HTTPS, HTTPS_PORT, SSLSocketFactory.getSocketFactory()));

		clientConnectionManager = new PoolingClientConnectionManager(schemeRegistry);
	}

	private void _init() {
		clientConnectionManager.setDefaultMaxPerRoute(this.maxConnector);
		httpclient = new DefaultHttpClient(clientConnectionManager);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.connectTimeout);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, this.readTimeout);
	}

	/**
	 * 默认构造方法 ，
	 * 
	 * @param maxConnector
	 */
	public WxcsHttpClient() {
		_init();
	}

	/**
	 * 构造方法
	 * 
	 * @param maxConnector
	 *            设置每个实例化对象的最大连接数
	 */
	public WxcsHttpClient(int maxConnector) {
		this.maxConnector = maxConnector;
		_init();
	}

	/**
	 * 初始化超时时间
	 * 
	 * @param connectTimeout
	 * @param readTimeout
	 */
	public WxcsHttpClient(int maxConnector, int connectTimeout, int readTimeout) {
		this.maxConnector = maxConnector;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		_init();
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 通过post方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param params
	 *            请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByPost(String url, List<NameValuePair> params) throws Exception {

		logger.info("[WxcsHttpClient:sendRequestByStream],url:" + url);

		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(params, encoding);
			httpPost.setEntity(entity);

			response = httpclient.execute(httpPost);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));

			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {
			logger.debug("[WxcsHttpClient:sendRequestByPost],url:" + url);
			logger.debug("[WxcsHttpClient:sendRequestByPost]", e);

			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpPost = null;

			httpclient.getConnectionManager().closeExpiredConnections();
		}

		logger.info("[WxcsHttpClient:sendRequestByStream],response content:" + builder.toString());

		return builder.toString();
	}

	/**
	 * 通过get方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @return Map<String,Object> 返回状态码和返回内容Map date:2014-5-28
	 * @author wangfengtong@xwtec.cn
	 */
	public Map<String, Object> sendRequestByGetUpgrades(String url) throws Exception {
		return sendRequestByGetUpgrades(url, ConstantKeys.WebKey.CHARACTER_ENCODING_UTF8);
	}

	/**
	 * 通过post方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param params
	 *            请求参数
	 * @return Map<String,Object> 返回状态码和返回内容Map date:2014-5-28
	 * @author wangfengtong@xwtec.cn
	 */
	public Map<String, Object> sendRequestByPostUpgrades(String url, List<NameValuePair> params) throws Exception {
		return sendRequestByPostUpgrades(url, params, ConstantKeys.WebKey.CHARACTER_ENCODING_UTF8);
	}

	/**
	 * 方法描述:使用get方式从服务器转发请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param encodeType
	 *            编码格式
	 * @return Map<String,Object> 返回状态码和返回内容Map date:May 21, 2013 add by:
	 *         houxu@xwtec.cn
	 */
	public Map<String, Object> sendRequestByGetUpgrades(String url, String encodeType) throws Exception {

		logger.info("[WxcsHttpClient:sendRequestByGetUpgrades],url:" + url);
		logger.info("[WxcsHttpClient:sendRequestByGetUpgrades],encodeType:" + encodeType);

		Map<String, Object> requestResult = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpGet httpGet = null;
		try {

			httpGet = new HttpGet(url);

			response = httpclient.execute(httpGet);

			// 返回状态码
			requestResult.put("statusCode", response.getStatusLine().getStatusCode());

			logger.info("[WxcsHttpClient:sendRequestByStream],response statusCode:" + requestResult.get("statusCode"));

			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encodeType));

			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {

			logger.debug("[WxcsHttpClient:sendRequestByGetUpgrades],url:" + url);
			logger.debug("[WxcsHttpClient:sendRequestByGetUpgrades],encodeType:" + encodeType);
			logger.debug("[WxcsHttpClient:sendRequestByGetUpgrades]", e);
			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpGet = null;
			httpclient.getConnectionManager().closeExpiredConnections();
		}

		logger.info("[WxcsHttpClient:sendRequestByGetUpgrades],response content:" + builder.toString());

		// 返回内容
		requestResult.put("content", builder.toString());
		return requestResult;
	}

	/**
	 * 通过post方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param params
	 *            请求参数
	 * @param encodingType
	 *            请求编码
	 * @return Map<String,Object> 返回状态码和返回内容Map date:2014-5-28
	 * @author wangfengtong@xwtec.cn
	 */
	public Map<String, Object> sendRequestByPostUpgrades(String url, List<NameValuePair> params, String encodingType) throws Exception {
		logger.info("[WxcsHttpClient:sendRequestByPostUpgrades],url:" + url);
		logger.debug("[WxcsHttpClient:sendRequestByPostUpgrades],encodingType:" + encodingType);

		Map<String, Object> requestResult = new HashMap<String, Object>();
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(params, encodingType);
			httpPost.setEntity(entity);

			response = httpclient.execute(httpPost);

			// 返回状态码
			requestResult.put("statusCode", response.getStatusLine().getStatusCode());

			logger.info("[WxcsHttpClient:sendRequestByStream],response statusCode:" + requestResult.get("statusCode"));

			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encodingType));

			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {
			logger.debug("[WxcsHttpClient:sendRequestByPostUpgrades],url:" + url);
			logger.debug("[WxcsHttpClient:sendRequestByPostUpgrades],encodingType:" + encodingType);
			logger.debug("[WxcsHttpClient:sendRequestByPostUpgrades]", e);

			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpPost = null;

			httpclient.getConnectionManager().closeExpiredConnections();
		}

		logger.info("[WxcsHttpClient:sendRequestByPostUpgrades],response content:" + builder.toString());

		// 返回内容
		requestResult.put("content", builder.toString());
		return requestResult;
	}

	/**
	 * 方法描述:通过post方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param strContent
	 *            请求参数
	 * @return 请求结果 date:2014-3-19 add by: liu_tao@xwtec.cn
	 */
	public String sendRequestByPost(String url, String strContent) throws Exception {
		return sendRequestByStream(url, strContent);
	}

	/**
	 * 通过流形式方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param strContent
	 *            请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByStream(String url, String strContent) throws Exception {

		logger.info("[WxcsHttpClient:sendRequestByStream],url:" + url);
		logger.info("[WxcsHttpClient:sendRequestByStream],sendContent:" + strContent);

		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try {

			httpPost = new HttpPost(url);
			HttpEntity entity = new StringEntity(strContent, encoding);
			httpPost.setEntity(entity);

			response = httpclient.execute(httpPost);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));

			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {

			logger.debug("[WxcsHttpClient:sendRequestByStream],url:" + url);
			logger.debug("[WxcsHttpClient:sendRequestByStream],sendContent:" + strContent);
			logger.debug("[WxcsHttpClient:sendRequestByStream]", e);
			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpPost = null;
			httpclient.getConnectionManager().closeExpiredConnections();
		}

		logger.info("[WxcsHttpClient:sendRequestByStream],response content:" + builder.toString());

		return builder.toString();
	}

	/**
	 * 通过Get方式提交发送请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param strContent
	 *            请求参数
	 * @return String
	 * @throws Exception
	 */
	public String sendRequestByGet(String url) throws Exception {

		logger.info("[WxcsHttpClient:sendRequestByStream],url:" + url);

		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		HttpResponse response = null;
		HttpGet httpGet = null;
		try {

			httpGet = new HttpGet(url);

			response = httpclient.execute(httpGet);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));

			String lines = "";
			while ((lines = reader.readLine()) != null) {
				builder.append(lines);
			}
		}
		catch (Exception e) {

			logger.debug("[WxcsHttpClient:sendRequestByStream],url:" + url);
			logger.debug("[WxcsHttpClient:sendRequestByStream]", e);
			throw e;
		}
		finally {

			if (null != reader) {
				reader.close();
				reader = null;
			}

			if (null != response) {
				EntityUtils.consume(response.getEntity());
				response = null;
			}

			httpGet = null;
			httpclient.getConnectionManager().closeExpiredConnections();
		}

		logger.info("[WxcsHttpClient:sendRequestByStream],response content:" + builder.toString());

		return builder.toString();
	}

	public static JSONObject xmlStr2JsonObj(String xml) {
		if (null == xml || "".equals(xml)) {
			return null;
		}
		XMLSerializer xmlSerializer = new XMLSerializer();
		String jsonStr = xmlSerializer.read(xml).toString();
		if (getJSONType(jsonStr) == JSON_TYPE.JSON_TYPE_OBJECT) {
			return JSONObject.fromObject(jsonStr);
		}
		return null;
	}

	/**
	 * JSON类型
	 * 
	 * @author Administrator
	 */
	public static enum JSON_TYPE {
		JSON_TYPE_OBJECT, JSON_TYPE_ARRAY, JSON_TYPE_ERROR
	}

	/**
	 * 判断json字符串格式对应的JSON类型
	 * 
	 * @param str
	 * @return
	 */
	public static JSON_TYPE getJSONType(String str) {
		if (null == str)
			return JSON_TYPE.JSON_TYPE_ERROR;
		str = str.trim();
		if (str.startsWith("{") && str.endsWith("}")) {
			return JSON_TYPE.JSON_TYPE_OBJECT;
		}
		if (str.startsWith("[") && str.endsWith("]")) {
			return JSON_TYPE.JSON_TYPE_ARRAY;
		}
		return JSON_TYPE.JSON_TYPE_ERROR;
	}

	public static void main(String[] args) {
		try {
			// sendRequestByStream("http://localhost:8080/xw_demo/demo/demo.do?method=23","sdsds");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
