package com.flf.util.push;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class PushClient {
	private final static Logger log= Logger.getLogger(PushClient.class);
	
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new HttpClient();

	// The host
	protected static final String HOST = "http://msg.umeng.com";

	// The upload path
	protected static final String UPLOADPATH = "/upload";

	// The post path
	protected static final String POSTPATH = "/api/send";

	public boolean send(UmengNotification msg) throws Exception {
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
		String url = HOST + POSTPATH;
		String postBody = msg.getPostBody();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
		url = url + "?sign=" + sign;

		PostMethod post = new PostMethod(url);
		post.setRequestEntity(new StringRequestEntity(postBody, "text/xml", "UTF-8"));

		HttpClient httpClient = new HttpClient();
		int result = httpClient.executeMethod(post);
		log.info("send()Response Code : " + result);
		String responseBodyAsString = post.getResponseBodyAsString();

		log.info("send()responseBodyAsString:"+responseBodyAsString);
		if (result == 200) {
			log.info("send()Notification sent successfully.");
		} else {
			log.info("send()Failed to send the notification!");
			return false;
		}
		return true;
	}

	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey, String appMasterSecret, String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = HOST + UPLOADPATH;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		
		PostMethod post = new PostMethod(url);
		post.setRequestEntity(new StringRequestEntity(postBody, "text/xml", "UTF-8"));

		HttpClient httpClient = new HttpClient();
		int result = httpClient.executeMethod(post);
		log.info("uploadContents()Response Code : " + result);
		String responseBodyAsString = post.getResponseBodyAsString();
		log.info(responseBodyAsString);
		
		// Decode response string and get file_id from it
		JSONObject respJson = new JSONObject(responseBodyAsString);
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue

		return fileId;
	}

}
