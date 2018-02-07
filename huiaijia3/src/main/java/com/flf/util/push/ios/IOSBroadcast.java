package com.flf.util.push.ios;

import com.flf.util.push.IOSNotification;

public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");
	}
}
