package com.flf.util.wms;

import com.flf.util.DateUtil;
import com.flf.util.MD5;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by SevenWong on 2017/11/6 14:55
 */
public class WmsUtil {

	private final static Logger log = Logger.getLogger(WmsUtil.class);

	/**
	 * 获取jos系统级别参数
	 */
	public static Map<String, Object> getSystemParams(String method) {
		Map<String, Object> map = new HashMap<>();
		map.put("access_token", WmsConfig.ACCESS_TOKEN);
		map.put("app_key", WmsConfig.APP_KEY);
		map.put("method", method);
		map.put("timestamp", DateUtil.datetime2Str(new Date()));
		map.put("v", "2.0");

		return map;
	}

	/**
	 * 生成jos所需的签名
	 * 具体查看文档 http://jos.jd.com/doc/channel.htm?id=157
	 */
	public static String getSign(Map<String, Object> map) {
		ArrayList<String> list = new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			list.add(entry.getKey() + entry.getValue());
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();

		// 头尾拼接app_secret
		sb.append(WmsConfig.APP_SECRET);
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		sb.append(WmsConfig.APP_SECRET);

		// log.info(sb);

		return MD5.MD5Encode(sb.toString()).toUpperCase();
	}

	/**
	 * 将所有参数序列化成请求参数
	 */
	public static String generateRequestParams(Map<String, Object> map) {
		ArrayList<String> list = new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}

		log.info(sb);
		return sb.toString();
	}

}
