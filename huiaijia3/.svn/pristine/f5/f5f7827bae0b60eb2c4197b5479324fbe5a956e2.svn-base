package com.flf.util.push;

import com.flf.util.push.android.AndroidCustomizedcast;
import com.flf.util.push.ios.IOSBroadcast;
import com.flf.util.push.ios.IOSCustomizedcast;
import com.flf.util.push.ios.IOSUnicast;

/**
 * APP推送消息工具
 * 
 * @author SevenWong<br>
 * @date 2016年6月20日下午3:16:19
 */
public class APPPushUtil {
	private static String APPKEY_IOS = "57666c24e0f55a4440003231";
	private static String APP_MASTER_SECRET_IOS = "ykadss5vxwsx8reic1up8scw7vf8j7pv";
	private static String ALIAS_TYPE_IOS = "huiaijia_iOS";

	private static String APPKEY_ANDROID = "5755661d67e58ed68e00244c";
	private static String APP_MASTER_SECRET_ANDROID = "habygn7wx2euqd5parui45pq9u7kycoj";
	private static String ALIAS_TYPE_ANDROID = "huiaijia_android";

	private static PushClient client = new PushClient();

	/**
	 * 广播(broadcast): 向安装该App的所有设备发送消息。
	 *
	 * @author SevenWong<br>
	 * @date 2016年6月20日下午3:36:21
	 * @param alert
	 *            推送的消息内容
	 * @throws Exception
	 */
	public static boolean sendIOSBroadcast(String alert) throws Exception {
		IOSBroadcast iosBroadcast = new IOSBroadcast(APPKEY_IOS, APP_MASTER_SECRET_IOS);
		iosBroadcast.setAlert(alert);
		return client.send(iosBroadcast);
	}

	/**
	 * 单播(unicast): 向指定的设备发送消息，包括向单个device_token或者单个alias发消息。
	 *
	 * @author SevenWong<br>
	 * @date 2016年6月20日下午3:16:40
	 * @throws Exception
	 */
	public static boolean sendIOSUnicast(String deviceToken, String alert) throws Exception {
		IOSUnicast unicast = new IOSUnicast(APPKEY_IOS, APP_MASTER_SECRET_IOS);
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(alert);
		return client.send(unicast);
	}

	/**
	 * 根据用户id(友盟alias)发送自定义推送通知(android设备)
	 * @param alias
	 * @param title
	 * @param content
	 * @throws Exception
	 */
	public static void sendAndroidCustomizedcast(String alias, String title, String content,
												 String notifyType) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(APPKEY_ANDROID, APP_MASTER_SECRET_ANDROID);
		customizedcast.setAlias(alias, ALIAS_TYPE_ANDROID);
		customizedcast.setTicker(title);
		customizedcast.setTitle(title);
		customizedcast.setText(content);
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		customizedcast.setProductionMode();
		customizedcast.setCustomField(notifyType);
		customizedcast.setAfterOpenAction(AndroidNotification.AfterOpenAction.go_custom);
		client.send(customizedcast);
	}

	/**
	 * 根据用户id(友盟alias)发送自定义推送通知(iOS设备)
	 * @param alias
	 * @param content
	 * @throws Exception
	 */
	public static void sendIOSCustomizedcast(String alias, String content, String notifyType) throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(APPKEY_IOS, APP_MASTER_SECRET_IOS);
		customizedcast.setAlias(alias, ALIAS_TYPE_IOS);
		customizedcast.setAlert(content);
		customizedcast.setBadge(0);
		customizedcast.setSound("default");
		customizedcast.setTestMode();
		customizedcast.setCustomizedField("notifyType", notifyType);
		client.send(customizedcast);
	}

	public static void main(String[] args) {
		try {
			String title = "退款到账";
			String content = "您有一笔退款xxx.xx元已经到账，请在【交易历史】中查看退款明细。";
			content = content.replace("xxx.xx", "888.88");
			APPPushUtil.sendAndroidCustomizedcast("214", title, content, "refund");
			// APPPushUtil.sendIOSCustomizedcast("2176", content, "refund");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
