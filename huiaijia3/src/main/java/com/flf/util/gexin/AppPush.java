package com.flf.util.gexin;

import com.flf.service.RedisSpringProxy;
import com.flf.util.Config;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SevenWong on 2017/8/4 16:49
 */
public class AppPush {

	private static final Logger log = Logger.getLogger(AppPush.class);

	//定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
	private static String URL = Config.getGexinProp("gexin.url");

	private static String APP_ID = Config.getGexinProp("gexin.app.id");

	private static String APP_KEY = Config.getGexinProp("gexin.app.key");

	private static String MASTER_SECRET = Config.getGexinProp("gexin.master.secret");


	/**
	 * 根据用户ID发送推送
	 * @param alias 推送别名（用户ID）
	 * @param title 推送标题
	 * @param body 推送内容
	 * @param notifyType 通知的类型（refund: 表示这是一条退款的通知）
	 */
	public static void sendByAlias(String alias, String title, String body, String notifyType) {
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin_pushList_needDetails", "true");

		// 配置返回每个别名及其对应cid的用户状态，可选
		// System.setProperty("gexin_pushList_needAliasDetails", "true");
		IGtPush push = new IGtPush(URL, APP_KEY, MASTER_SECRET);

		// 通知透传模板
		TransmissionTemplate template = getTemplate(title, body, notifyType);
		ListMessage message = new ListMessage();
		message.setData(template);

		// 设置消息离线，并设置离线时间
		message.setOffline(true);

		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);

		// 配置推送目标
		List<Target> targets = new ArrayList<>();
		Target target = new Target();
		target.setAppId(APP_ID);
		target.setAlias(alias);
		targets.add(target);

		// taskId用于在推送时去查找对应的message
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		log.info(ret.getResponse().toString());
	}

	/**
	 * 透传模板
	 * @param title
	 * @param body
	 * @return
	 */
	private static TransmissionTemplate getTemplate(String title, String body, String notifyType) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(APP_ID);
		template.setAppkey(APP_KEY);
		String jsonBody = "{\"body\":\""+body+"\",\"notifyType\":\""+notifyType+"\"}";
		template.setTransmissionContent(jsonBody);
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();

		//在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("$由客户端定义");

		//简单模式APNPayload.SimpleMsg
		// payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
		payload.setAlertMsg(getDictionaryAlertMsg(title, body));

		template.setAPNInfo(payload);
		return template;
	}

	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String body){
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setTitle(title);
		alertMsg.setBody(body);
		return alertMsg;
	}

	public static void main(String[] args) throws IOException {
		String title = "手机验证码！";
//		String body = "震惊！汇爱家产品经理在公共场合竟然...";
		String code = RandomStringUtils.randomNumeric(4);
		RedisSpringProxy redisSpringProxy = new RedisSpringProxy();
//		log.info("修改登录手机" + phone + "生成的验证码:" + code);
//		redisSpringProxy.saveEx("update_phone_code_" + phone, 5 * 60L, code);
		String body = "手机验证码="+code;
		String notifyType = "refund";
		sendByAlias("5", title, body, notifyType);
		// sendByAlias("8", title, body, notifyType);
		// sendByAlias("214", title, body, "refund");
	}
}
