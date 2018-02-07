package com.flf.util;

/**
 * Created by SevenWong on 2017-3-28 028 11:45
 */
public class HajRechargeUtil {

	/**
	 * 汇爱家账户充值
	 */
	public static final int RECHARGE = 0;

	/**
	 * 汇爱家会员年费
	 */
	public static final int ANNUAL_FEE = 1;

	/**
	 * 汇爱家订单支付
	 */
	public static final int ORDER_PAYMENT = 2;

	/**
	 * 汇爱家配送费
	 */
	public static final int POST_FEE = 3;

	/**
	 * 处理调用第三方支付平台的原因
	 */
	public static String getRechargeFor(Integer rechargeFor) {
		switch (rechargeFor){
			case RECHARGE : return "汇爱家账户充值";
			case ANNUAL_FEE : return "汇爱家会员年费";
			case ORDER_PAYMENT : return "汇爱家订单支付";
			case POST_FEE : return "汇爱家配送费";
			default : return "未知原因";
		}
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(getRechargeFor(String.valueOf(4)));
		// System.out.println(getRechargeFor(null));
	}

}
