package com.flf.service.impl;

import com.base.criteria.Criteria;
import com.flf.entity.HajFrontUser;
import com.flf.mapper.HajFrontUserMapper;
import com.flf.service.IHajSMSPushService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.Config;
import com.flf.util.SMSUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hajSMSPushService")
public class HajSMSPushServiceImpl implements IHajSMSPushService {
	private final static Logger log = Logger.getLogger(HajSMSPushServiceImpl.class);

	@Autowired
	private HajFrontUserMapper userDao;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	public static final String SMS_FREE_SIGN_NAME = Config.getSmsProperties("sms.free.sign.name");

	/** 会员激活短信通知模版ID */
	public static final String SMS_TEMPLATE_ACTIVATION_NOTIFY = Config
			.getSmsProperties("sms.template.activation.notify");

	/** 登录短信验证码模版ID */
	public static final String SMS_TEMPLATE_LOGIN_CODE = Config.getSmsProperties("sms.template.login.code");

	/** 注册短信验证码模版ID */
	public static final String SMS_TEMPLATE_REGISTER_CODE = Config.getSmsProperties("sms.template.register.code");
	
	/** 充值成功短信验证码模版ID */
	public static final String SMS_TEMPLATE_RECHARGE_DONE = Config.getSmsProperties("sms.template.recharge.done");

	/** 小区激活短信推模版ID */
	public static final String SMS_TEMPLATE_VILLAGE_ACTIVE = Config.getSmsProperties("sms.template.village.active");

	/** 用户上报小区短信模板ID */
	public static final String SMS_TEMPLATE_VILLAGE_REPORT = Config.getSmsProperties("sms.template.village.report");

	/** 用户签到满10次短信通知 */
	public static final String SMS_TEMPLATE_SIGNIN10 = Config.getSmsProperties("sms.template.signin10");

	/** 用户上报的小区已成功录入到后台的短信模板ID */
	public static final String SMS_TEMPLATE_COMMUNITY_ENTRY = Config.getSmsProperties("sms.template.community.entry");

	/** 用户状态改为一元购后的短信模板ID */
	public static final String SMS_TEMPLATE_BECAME_VIP = Config.getSmsProperties("sms.template.became.vip");

	/** 用户状态改为会员取消的短信模板ID */
	public static final String SMS_TEMPLATE_CANCLE_VIP = Config.getSmsProperties("sms.template.cancel.vip");

	/** 余额提现办理短信模板ID */
	public static final String SMS_TEMPLATE_WITHDRAWAL = Config.getSmsProperties("sms.template.withdrawal");

	/** 重复下单退款短信模板ID */
	public static final String SMS_TEMPLATE_ORDER_REFUND = Config.getSmsProperties("sms.template.order.refund");

	/** 登录验证码语音转文字模板ID */
	public static final String SMS_TTS_TEMPLATE_LOGIN_CODE = Config.getSmsProperties("sms.tts.template.login.code");
	/**修改密码验证码模板ID**/
	public static  final String SMS_TEMPLATE_UPDATEPASSWORD_VIP=Config.getSmsProperties("sms.template.updatepassword.vip");

	/**修改手机验证码模板ID**/
	public static  final String SMS_TEMPLATE_UPDATEPHONE_VIP=Config.getSmsProperties("sms.template.updatephone.vip");
	/**开启支付密码，支付密码修改**/
	public static  final String SMS_TEMPLATE_OPENPAYPASSWORD_VIP=Config.getSmsProperties("sms.template.openpaypassword.vip");
	/**关闭支付密码**/
	public static  final String SMS_TEMPLATE_CLOSEAYPASSWORD_VIP=Config.getSmsProperties("sms.template.closepaypassword.vip");

	@Override
	public String villageActivateSMSPush(String villageName) {
		log.info("发送小区激活短信...");
		String smsParamString = "{\"username\":\"" + villageName + "\"}";
		try {
			// 短信推送
			if (villageName != null && !"".equals(villageName)) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("residential", villageName);
				condition.put("ismember", "3");
				Criteria criteria = new Criteria();
				criteria.setCondition(condition);
				criteria.setPageSize(1000);
				List<HajFrontUser> queryByList = userDao.queryByList(criteria);
				if (queryByList != null && queryByList.size() > 0) {
					for (HajFrontUser user : queryByList) {
						SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_VILLAGE_ACTIVE,
								user.getMobilePhone());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	@Override
	public String userReportSMSPush(String[] phoneList, String village) {
		log.info("用户上报小区录入后短信推送...");
		String phones = phoneList2String(phoneList);
		String smsParamString = "{\"village\":\"" + village + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_VILLAGE_REPORT, phones);
	}

	@Override
	@Deprecated
	public String userSignSMSPush(String tel, String username) {
		log.info("用户签到每满10次短信推送...");
		String smsParamString = "{\"username\":\"" + username + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_SIGNIN10, tel);
	}

	@Override
	public String getLoginVerificationCode(String mobile) {
		String code = RandomStringUtils.randomNumeric(4);
		log.info("给手机号" + mobile + "生成的验证码:" + code);
		redisSpringProxy.saveEx("login_code_" + mobile, 5 * 60L, code);
		String smsParamString = "{\"code\":\"" + code + "\",\"product\":\"" + SMS_FREE_SIGN_NAME + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_LOGIN_CODE, mobile);
	}

	@Override
	public String rechargeSMSPush(int money, String mobile) {
		log.info("充值短信推送短信接口...");
		String smsParamString = "{\"money\":\"" + money + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_RECHARGE_DONE, mobile);
	}

	@Override
	public String rechargeDone(String mobile, BigDecimal money) {
		log.info("充值成功短信通知短信接口...");
		String service = (String) redisSpringProxy.read("SystemConfiguration_xiao_er_wei_xin");
		String smsParamString = "{\"money\":\"" + money + "\",\"service\":\"" + service + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_RECHARGE_DONE, mobile);
	}

	@Override
	public String communityEntry(String[] phoneList, String community) {
		log.info("用户上报的小区已成功录入到后台，短信通知给用户短信接口...");
		String phones = phoneList2String(phoneList);
		String service = (String) redisSpringProxy.read("SystemConfiguration_xiao_er_wei_xin");
		String smsParamString = "{\"community\":\"" + community + "\",\"service\":\"" + service + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_COMMUNITY_ENTRY, phones);
	}

	@Override
	public String becameVIP(String mobile, String community) {
		log.info("用户状态改为一元购后的短信接口...");
		String smsParamString = "{\"community\":\"" + community + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_BECAME_VIP, mobile);
	}

	@Override
	public String cancleVIP(String mobile, String community) {
		log.info("用户状态改为会员取消短信接口...");
		String service = (String) redisSpringProxy.read("SystemConfiguration_xiao_er_wei_xin");
		String smsParamString = "{\"community\":\"" + community + "\",\"service\":\"" + service + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_CANCLE_VIP, mobile);
	}

	private String phoneList2String(String[] phoneList) {
		StringBuilder tel = new StringBuilder();
		String reg = ",";
		// 封装需要推送的用户电话号码字符串
		for (int i = 0; i < phoneList.length; i++) {
			tel.append(phoneList[i]);
			if (i < phoneList.length - 1) {
				tel.append(reg);
			}
		}
		return tel.toString();
	}

	@Override
	public String withdrawal(String mobile, BigDecimal amount) {
		log.info("余额提现办理短信接口...");
		String smsParamString = "{\"amount\":\"" + amount + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_WITHDRAWAL, mobile);
	}

	@Override
	public String orderRefund(String mobile, BigDecimal money, String datetime) {
		log.info("重复下单退款短信接口...");
		String smsParamString = "{\"time\":\"" + datetime + "\",\"money\":\"" + money + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_ORDER_REFUND, mobile);
	}

	@Override
	public String getLoginTtsCode(String mobile) {
		String code = RandomStringUtils.randomNumeric(4);
		log.info("给手机号" + mobile + "生成的语音验证码:" + code);
		redisSpringProxy.saveEx("login_code_" + mobile, 5 * 60L, code);
		String smsParamString = "{\"code\":\"" + code + "\",\"product\":\"" + SMS_FREE_SIGN_NAME + "\"}";
		return SMSUtil.ttsSingleCall(smsParamString, SMS_TTS_TEMPLATE_LOGIN_CODE, mobile);
	}

	public String sendRegisterCode(String mobile) {
		String code = RandomStringUtils.randomNumeric(4);
		log.info("给手机号" + mobile + "生成的验证码:" + code);
		redisSpringProxy.saveEx("register_code_" + mobile, 5 * 60L, code);
		String smsParamString = "{\"code\":\"" + code + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_REGISTER_CODE, mobile);
	}
 

	/**修改密码下发验证码短信***/
	public String getUpdatePwdCode(String phone) {
		String code = RandomStringUtils.randomNumeric(4);
		log.info("修改登录密码" + phone + "生成的验证码:" + code);
		redisSpringProxy.saveEx("update_password_code_" + phone, 5 * 60L, code);
		String smsParamString = "{\"code\":\"" + code + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_UPDATEPASSWORD_VIP, phone);
	}

     /***获取修改手机验证码短信***/
	public String getUpdatePhoneCode(String phone) {
		String code = RandomStringUtils.randomNumeric(4);
		log.info("修改登录手机" + phone + "生成的验证码:" + code);
		redisSpringProxy.saveEx("update_phone_code_" + phone, 5 * 60L, code);
		String smsParamString = "{\"code\":\"" + code + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_UPDATEPHONE_VIP, phone);
	}

	 /***开启支付。支付密码修改验证码**/
	public String getPayPwdCode(String phone) {
		String code = RandomStringUtils.randomNumeric(4);
		log.info("开启支付,支付密码修改验证码" + phone + "生成的验证码:" + code);
		redisSpringProxy.saveEx("open_paypassword_code_" + phone, 5 * 60L, code);
		String smsParamString = "{\"code\":\"" + code + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_OPENPAYPASSWORD_VIP, phone);
	}

	/***关闭支付密码验证码**/
	public String getColsePayPwdCode(String phone) {
		String code = RandomStringUtils.randomNumeric(4);
		log.info("关闭支付密码验证码" + phone + "生成的验证码:" + code);
		redisSpringProxy.saveEx("close_paypassword_code_" + phone, 5 * 60L, code);
		String smsParamString = "{\"code\":\"" + code + "\"}";
		return SMSUtil.sendSms(SMS_FREE_SIGN_NAME, smsParamString, SMS_TEMPLATE_CLOSEAYPASSWORD_VIP, phone);
	}

}
