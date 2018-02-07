package com.flf.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumQueryRequest;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumQueryResponse;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * 用户发送短信，阿里大鱼短信平台
 * 
 * @author SevenWong<br>
 * @date 2016年6月16日上午10:50:51
 */
public class SMSUtil {
	private final static Logger log = Logger.getLogger(SMSUtil.class);

	public static final String URL = Config.getSmsProperties("sms.url");
	public static final String APPKEY = Config.getSmsProperties("sms.appkey");
	public static final String SECRET = Config.getSmsProperties("sms.secret");
	public static final String SMS_SEND_FLAG = Config.getSmsProperties("sms.send.flag");
	public static final String SMS_TTS_SHOW_NUMBER = Config.getSmsProperties("sms.tts.show.number");

	/**
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月16日上午11:00:33
	 * @param freeSignName
	 *            <p>
	 *            短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。
	 *            如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。
	 *            短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
	 *            </p>
	 *
	 * @param smsParamString
	 *            <p>
	 *            短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
	 *            示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，
	 *            传参时需传入{"code":"1234","product":"alidayu"}
	 *            </p>
	 *
	 * @param smsTemplateCode
	 *            <p>
	 *            短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。 示例：SMS_585014
	 *            </p>
	 *
	 * @param mobile
	 *            <p>
	 *            接受短信的手机号码
	 *            </p>
	 * @return
	 *         <p>
	 *         error: 未知异常<br>
	 *         true: 发送成功<br>
	 *         false: 发送失败<br>
	 *         其他: 发送异常描述<br>
	 *         </p>
	 */
	public static String sendSms(String freeSignName, String smsParamString, String smsTemplateCode, String mobile) {
		String status = "error";

		if (Tools.isEmpty(mobile)) {
			return status;
		}

		// 是否开启发短信功能
		if ("false".equals(SMS_SEND_FLAG)) {
			log.info("测试环境禁止发送短信...");
			return status;
		}

		TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

		// 短信类型，固定
		req.setSmsType("normal");
		req.setSmsFreeSignName(freeSignName);
		req.setSmsParamString(smsParamString);
		req.setSmsTemplateCode(smsTemplateCode);
		req.setRecNum(mobile);

		// 短信推送
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

			String body = rsp.getBody();
			log.info(mobile + " sms send result: " + body);
			JSONObject json = JSONObject.fromObject(body);
			Object jsonObject = json.get("alibaba_aliqin_fc_sms_num_send_response");
			status = getSmsResponse(json, jsonObject);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return status;
	}

	/**
	 * 根据手机号码查询短信发送记录
	 * @param mobile 接受短信的号码
	 * @param sendDate yyyyMMdd
	 */
	public static void smsQuery(String mobile, String sendDate) {
		TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
		AlibabaAliqinFcSmsNumQueryRequest req = new AlibabaAliqinFcSmsNumQueryRequest();
		req.setRecNum(mobile);
		req.setQueryDate(sendDate);
		req.setCurrentPage(1L);
		req.setPageSize(10L);
		AlibabaAliqinFcSmsNumQueryResponse rsp;
		try {
			rsp = client.execute(req);
			log.info(rsp.getBody());
		} catch (ApiException e) {
			log.info(e.getMessage(), e);
		}
	}

	/**
	 * 发送语音验证码
	 * @param smsParamString
	 * @param smsTemplateCode
	 * @param mobile
	 */
	public static String ttsSingleCall(String smsParamString, String smsTemplateCode, String mobile) {
		String status = null;
		TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
		AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
		req.setCalledNum(mobile);
		req.setCalledShowNum(SMS_TTS_SHOW_NUMBER);
		req.setTtsCode(smsTemplateCode);
		req.setTtsParam(smsParamString);
		try {
			AlibabaAliqinFcTtsNumSinglecallResponse response = client.execute(req);
			log.info(response.getBody());
			JSONObject json = JSONObject.fromObject(response.getBody());
			Object jsonObject = json.get("alibaba_aliqin_fc_tts_num_singlecall_response");
			status = getSmsResponse(json, jsonObject);
		} catch (ApiException e) {
			log.info(e.getMessage(), e);
		}
		return status;
	}

	private static String getSmsResponse(JSONObject json, Object jsonObject) {
		String status = "";

		// body一般返回两种状态，成功与失败，两种参数key不一样，所以需要做如下判断
		// 获取alibaba_aliqin_fc_sms_num_send_response为null时说明发送异常
		JSONObject alibaba_response;
		if (jsonObject == null) {
			// 发送异常
			alibaba_response = (JSONObject) json.get("error_response");
			if (alibaba_response != null) {
				if (alibaba_response.get("sub_msg") == null) {
					// 其他异常
					status = String.valueOf(alibaba_response.get("msg"));
				} else {
					status = String.valueOf(alibaba_response.get("sub_msg"));
				}
			}
		} else {
			alibaba_response = (JSONObject) jsonObject;

			// 无异常时获取发送状态，success一般返回true与false
			status = String.valueOf(((JSONObject) alibaba_response.get("result")).get("success"));
		}
		return status;
	}

	public static void main(String[] args) {
		String smsProperties = Config.getSmsProperties("sms.tts.template.login.code");
		String status = ttsSingleCall("{\"product\":\"汇爱家\",\"code\":\"1234\"}", smsProperties, "15712131701");
		log.info(status);
	}

}
