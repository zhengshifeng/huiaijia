package com.flf.util;

/**
 * Created by SevenWong on 2017/10/17 16:24
 */
public class HajOrderUtil {

	/**
	 * 返回订单状态码对应的状态
	 */
	public static String getOrderStatus(String status) {
		String returnStatus = "";
		switch (status) {
			case "1":
				returnStatus = "等待付款";
				break;
			case "2":
				returnStatus = "等待配送";
				break;
			case "3":
				returnStatus = "已发货";
				break;
			case "4":
				returnStatus = "已完成";
				break;
			case "5":
				returnStatus = "待退款";
				break;
			case "6":
				returnStatus = "已取消";
				break;
			case "7":
				returnStatus = "已退款";
				break;
			case "8":
				returnStatus = "补单";
				break;
			case "9":
				returnStatus = "系统取消";
				break;
			default:
				break;
		}
		return returnStatus;
	}

}
