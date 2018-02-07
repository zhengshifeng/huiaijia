package com.flf.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * Created by SevenWong on 2017-3-23 023 15:25
 */
public class HttpClientUtil {

	private static final Logger log = Logger.getLogger(HttpClientUtil.class);

	public static final String UTF_8 = "UTF-8";

	public static final String GBK = "GBK";

	public static final String ISO_8859_1 = "ISO-8859-1";

	public static final String CONTENT_TYPE_JSON = "application/json";

	public static final String CONTENT_TYPE_XML = "text/xml";


	/**
	 * 使用httpClient发送post请求，默认使用UTF-8编码
	 *
	 * @param url 指定url
	 * @param params 指定参数
	 * @param contentType 指定文本类型
	 * @return 根据url请求返回的数据
	 */
	public static String sendPost(String url, String params, String contentType) {
		return sendPost(url, params, contentType, UTF_8);
	}

	/**
	 * 使用httpClient发送post请求
	 *
	 * @param url 指定url
	 * @param params 指定参数
	 * @param contentType 指定文本类型
	 * @param charset 指定编码
	 * @return 根据url请求返回的数据
	 */
	public static String sendPost(String url, String params, String contentType, String charset) {
		return sendPost(url, params, contentType, charset, null);
	}

	/**
	 * 使用httpClient发送post请求
	 *
	 * @param url 指定url
	 * @param params 指定参数
	 * @param contentType 指定文本类型
	 * @param charset 指定编码
	 * @param responseCharset 指定响应数据编码
	 * @return 根据url请求返回的数据
	 */
	public static String sendPost(String url, String params, String contentType,
								  String charset, String responseCharset) {
		PostMethod post = new PostMethod(url);
		try {
			post.setRequestEntity(new StringRequestEntity(params, contentType, charset));
			HttpClient httpClient = new HttpClient();
			int result = httpClient.executeMethod(post);
			log.info("send post result: " + result);
			log.info("getResponseBodyAsString: " + post.getResponseBodyAsString());
			if (result == 200) {
				if (Tools.isNotEmpty(responseCharset)) {
					// 当响应的数据中存在中文乱码时，可指定响应的编码。有以下两种方式实现，目前退款第一种
					post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, responseCharset);
					// return new String(post.getResponseBodyAsString().getBytes(responseCharset), charset);
				}
				return post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}

}
