package com.flf.entity.wms;

/**
 * Created by SevenWong on 2017/10/31 15:22
 *
 * 系统级别输入参数
 *
 */
public class WmsSystemParams {

	private String method; // API接口名称
	private String access_token; // API接口名称
	private String app_key; // API接口名称
	private String sign; // API接口名称
	private String timestamp; // API接口名称

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
