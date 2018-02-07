package com.flf.entity;

import java.io.Serializable;

public class WMSOrderCancle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String whcode;// "仓库编码",
	private String ordeno;// "出库通知单号",
	private String reason;// "取消原因"

	public String getWhcode() {
		return whcode;
	}

	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}

	public String getOrdeno() {
		return ordeno;
	}

	public void setOrdeno(String ordeno) {
		this.ordeno = ordeno;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
