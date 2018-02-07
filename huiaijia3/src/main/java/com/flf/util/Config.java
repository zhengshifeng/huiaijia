package com.flf.util;

import java.io.File;
import java.util.Properties;

public class Config {
	private final static String PROPERTIES_FILE = "elasticsearch.properties";
	private final static String CONFIGS_FILE = "config.properties";
	private final static String REDIS_FILE = "redis.properties";
	private final static String SMS_FILE = "sms.properties";
	private final static String GEXIN_FILE = "gexin.properties";
	private static Properties properties;
	private static Properties configs;
	private static Properties redis;
	private static Properties sms;
	private static Properties gexin;

	static {
		String filePath = File.separator + "config/" + PROPERTIES_FILE;
		String configFilePath = File.separator + "config/" + CONFIGS_FILE;
		String redisFilePath = File.separator + "config/" + REDIS_FILE;
		String smsFilePath = File.separator + "config/" + SMS_FILE;
		String gexinFilePath = File.separator + "config/" + GEXIN_FILE;
		try {
			properties = PropertyUtil.getProperties(filePath);
			configs = PropertyUtil.getProperties(configFilePath);
			redis = PropertyUtil.getProperties(redisFilePath);
			sms = PropertyUtil.getProperties(smsFilePath);
			gexin = PropertyUtil.getProperties(gexinFilePath);
		} catch (Throwable t2) {
			t2.printStackTrace();
		}
	}

	/**
	 * 获取属性值返回类型String
	 * 
	 * @param aName
	 * @param aDefault
	 * @return
	 */
	public static String getProperty(String aName, String aDefault) {
		return properties.getProperty(aName, aDefault);
	}

	/**
	 * 获取属性值返回类型boolean
	 * 
	 * @param aName
	 * @param aDefault
	 * @return
	 */
	public static boolean getBoolProperty(String aName, String aDefault) {
		return Boolean.parseBoolean(getProperty(aName, aDefault));
	}

	/**
	 * 获取属性值返回类型int
	 * 
	 * @param aName
	 * @param aDefault
	 * @return
	 */
	public static Integer getIntProperty(String aName, String aDefault) {
		return Integer.parseInt(getProperty(aName, aDefault));
	}

	/**
	 * 获取属性值返回类型long
	 * 
	 * @param aName
	 * @param aDefault
	 * @return
	 */
	public static Long getLongProperty(String aName, String aDefault) {
		return Long.parseLong(getProperty(aName, aDefault));
	}

	/**
	 * 根据key读取config.properties中的值
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月21日上午11:51:36
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getConfigProperties(String key, String defaultValue) {
		return configs.getProperty(key, defaultValue);
	}

	/**
	 * 根据key读取redis.properties中的值
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月21日上午11:51:36
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getRedisProperties(String key, String defaultValue) {
		return redis.getProperty(key, defaultValue);
	}

	/**
	 * 从 sms.properties 获取短信配置信息
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月19日上午11:43:07
	 * @param key
	 * @return
	 */
	public static String getSmsProperties(String key) {
		return sms.getProperty(key);
	}

	/**
	 * 从 gexin.properties 获取个推的配置信息
	 */
	public static String getGexinProp(String key) {
		return gexin.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println("config:" + getRedisProperties("redis.ip", ""));
	}
}
