package com.flf.controller.app;

import com.flf.service.HajUserReportService;
import com.flf.service.IHajSMSPushService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/hajSMSPush")
public class HajSMSPush {

	private final static Logger log = Logger.getLogger(HajCommunityPersionAppController.class);

	@Autowired(required = false)
	private IHajSMSPushService hajSMSPushService;

	@Autowired(required = false)
	private HajUserReportService hajUserReportService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;




	// 用户签到每满10次短信推送
	@RequestMapping(value = "/userSignSMSPush")
	public void userSignSMSPush(@RequestParam String sign, @RequestParam String tel, @RequestParam String username,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (tel != null && !"".equals(tel)) {
					String status = hajSMSPushService.userSignSMSPush(tel, username);
					if ("true".equals(status)) {
						jsonMap.put("status", status);
						jsonMap.put("msg", "推送成功");
					} else {
						jsonMap.put("status", status);
						jsonMap.put("msg", "推送失败");
					}
				} else {
					jsonMap.put("msg", "电话号码有误");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// 用户上报小区录入后短信推送
	@RequestMapping(value = "/userReportSMSPush")
	public void userReportSMSPush(@RequestParam String[] phoneList, @RequestParam String village,
			HttpServletResponse response) {
		String status = "false";
		try {
			if (phoneList != null && phoneList.length > 0) {
				// 短信通知
				status = hajSMSPushService.communityEntry(phoneList, village);

				// 修改推送状态
				if ("true".equals(status)) {
					for (String phone : phoneList) {
						hajUserReportService.updatePushStatus(phone);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject("{status:'" + status + "'}", response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送登录短信验证码
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月16日上午10:38:49
	 * @param sign
	 * @param mobile
	 * @param response
	 */
	@RequestMapping(value = "/getLoginVerificationCode")
	public void getLoginVerificationCode(@RequestParam String sign, @RequestParam String mobile,
			HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();

		if (MD5Tools.checkSign(sign)) {
			// 判断是否禁用登录验证码，后台配置
			String disable_login_code = (String) redisSpringProxy.read("SystemConfiguration_disable_vcode");
			boolean isDisableLoginCode = (Boolean.parseBoolean(disable_login_code));

			// 为true时，不发送短信，直接返回false
			String status = isDisableLoginCode ? "false" : hajSMSPushService.getLoginVerificationCode(mobile);

			// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
			jsonMap.put("status", status);
		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送语音验证码
	 * @param sign
	 * @param mobile
	 * @param response
	 */
	@RequestMapping(value = "/getLoginCodeTTS")
	public void getLoginCodeTTS(@RequestParam String sign, @RequestParam String mobile,
			HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();

		if (MD5Tools.checkSign(sign)) {
			// 判断是否禁用登录验证码，后台配置
			String disable_login_code_tts = (String) redisSpringProxy.read("SystemConfiguration_disable_login_code_tts");
			boolean isDisableLoginCode = (Boolean.parseBoolean(disable_login_code_tts));

			// 为true时，不发送短信，直接返回false
			String status = isDisableLoginCode ? "false" : hajSMSPushService.getLoginTtsCode(mobile);

			// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
			jsonMap.put("status", status);
		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 充值短信推送
	 * 
	 */
	@RequestMapping(value = "/rechargeSMSPush")
	public void rechargeSMSPush(@RequestParam String sign, @RequestParam String mobile, @RequestParam int money,
			HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();

		try {
			if (MD5Tools.checkSign(sign)) {
				String status = hajSMSPushService.rechargeSMSPush(money, mobile);
				// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
				jsonMap.put("status", status);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
			JSONUtils.printObject(jsonMap, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 发送修改密码短信验证码
	 *
	 */
	@RequestMapping(value = "/getUpdatePwdCode")
	public void getUpdatePassWordCode(@RequestParam String sign, @RequestParam String phone,
										 HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();
		if (MD5Tools.checkSign(sign)) {
 			String status =  hajSMSPushService.getUpdatePwdCode(phone);
			// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
			jsonMap.put("status", status);
			jsonMap.put("error", "0");
		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 发送修改手机短信验证码
	 *
	 */
	@RequestMapping(value = "/getUpdatePhoneCode")
	public void getUpdatePhoneCode(@RequestParam String sign, @RequestParam String phone,
									  HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();
		if (MD5Tools.checkSign(sign)) {
			String status =  hajSMSPushService.getUpdatePhoneCode(phone);
			// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
			jsonMap.put("status", status);
			jsonMap.put("error", "0");
		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开启支付密码验证码
	 *
	 */
	@RequestMapping(value = "/getPayPwdCode")
	public void getPayPwdCode(@RequestParam String sign, @RequestParam String phone,
								   HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();
		if (MD5Tools.checkSign(sign)) {
			String status = hajSMSPushService.getPayPwdCode(phone);
			// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
			jsonMap.put("status", status);
			jsonMap.put("error", "0");
		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * 开启支付密码验证码
	 *
	 */
	@RequestMapping(value = "/getColsePayPwdCode")
	public void getColsePayPwdCode(@RequestParam String sign, @RequestParam String phone,
								   HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();
		if (MD5Tools.checkSign(sign)) {
			String status = hajSMSPushService.getColsePayPwdCode(phone);
			// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
			jsonMap.put("status", status);
			jsonMap.put("error", "0");
		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}



}
