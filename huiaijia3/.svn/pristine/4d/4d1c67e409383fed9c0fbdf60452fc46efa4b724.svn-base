package com.flf.service;

import java.math.BigDecimal;

public interface IHajSMSPushService {

	/**
	 * 小区激活短信推送
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月23日下午5:11:10
	 * @param villageName
	 * @return
	 */
	String villageActivateSMSPush(String villageName);

	/**
	 * 用户上报小区录入后短信推送
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月23日下午5:11:05
	 * @param phoneList
	 * @param village
	 * @return
	 */
	String userReportSMSPush(String[] phoneList, String village);

	/**
	 * 用户签到每满10次短信推送
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月23日下午5:11:01
	 * @param tel
	 * @param username
	 * @return
	 */
	String userSignSMSPush(String tel, String username);

	/**
	 * 发送登录验证码
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月23日下午5:11:15
	 * @param mobile
	 * @return
	 */
	String getLoginVerificationCode(String mobile);

	String rechargeSMSPush(int money, String mobile);

	/**
	 * 充值成功短信通知
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月20日下午5:39:25
	 * @param mobile
	 * @param money
	 * @return
	 */
	String rechargeDone(String mobile, BigDecimal money);

	/**
	 * 用户上报的小区已成功录入到后台，短信通知给用户
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月11日下午2:37:03
	 * @param phoneList
	 * @param community
	 * @return
	 */
	String communityEntry(String[] phoneList, String community);

	/**
	 * 用户状态改为一元购后的短信通知
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月11日下午2:39:27
	 * @param mobile
	 * @param community
	 * @return
	 */
	String becameVIP(String mobile, String community);

	/**
	 * 用户状态改为会员取消
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月11日下午2:48:00
	 * @param mobile
	 * @param community
	 * @return
	 */
	String cancleVIP(String mobile, String community);

	/**
	 * 余额提现短信通知
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月27日上午11:34:53
	 * @param mobile
	 *            用户手机号
	 * @param amount
	 *            提现金额
	 * @return
	 */

	String withdrawal(String mobile, BigDecimal amount);

	String orderRefund(String mobile, BigDecimal money, String datetime);

	/**
	 * 获取登录语音验证码
	 */
	String getLoginTtsCode(String mobile);
	
	/**
	 * 发送注册验证码
	 */
	String sendRegisterCode(String mobile);

	/**
	 * 获取修改密码code
	 */

	String getUpdatePwdCode(String phone);
	/**
	 * 获取修改手机code
	 */
	String getUpdatePhoneCode(String phone);
	/**
	 * 开启支付验证码
	 */
	String getPayPwdCode(String phone);
	/**
	 * 关闭支付验证码
	 */
	String getColsePayPwdCode(String phone);
}
