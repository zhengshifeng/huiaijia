package com.flf.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	public static final String EMPTY = "";

	public static final String NULL = "null";

	/**
	 * 检测字符串是否不为空(null,"","null")
	 *
	 * @param str
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 同 notEmpty() 检测字符串是否不为空(null,"","null")
	 *
	 * @param str
	 * @return 不为空则返回true，否则返回false
	 * @author SevenWong<br>
	 * @date 2016年9月1日下午2:28:35
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 *
	 * @param str
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String str) {
		return str == null || EMPTY.equals(str) || NULL.equals(str);
	}

	/**
	 * 字符串转换为字符串数组
	 *
	 * @param str        字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 *
	 * @param str 字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 *
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 *
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (isNotEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return EMPTY;
		}
	}

	/**
	 * 将emoji表情处理成"?"保存
	 *
	 * @param source
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年6月30日上午11:32:53
	 */
	public static String filterEmoji(String source) {
		if (source != null) {
			Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
					Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll("?");
			}
		}
		return source;
	}

	/**
	 * 根据areaCode判断值得合法性，如果为空，则默认返回深圳的areaCode
	 *
	 * @param areaCode
	 * @return
	 */
	public static String getAreaCode(String areaCode) {
		areaCode = Tools.isEmpty(areaCode) ? Const.DEFAULT_AREA_CODE : areaCode;
		return areaCode;
	}

	/**
	 * 根据areaCode返回城市名
	 */
	public static String getCityNameByAreaCode(String areaCode) {
		String city = "";
		switch (areaCode) {
			case "100002":
				city = "深圳市";
				break;
			case "100010":
				city = "广州市";
				break;
		}
		return city;
	}

	/**
	 * 根据areaCode返回城市名，没有“市”
	 */
	public static String getShortCityByAreaCode(String areaCode) {
		String city = "";
		switch (areaCode) {
			case "100002":
				city = "深圳";
				break;
			case "100010":
				city = "广州";
				break;
		}
		return city;
	}

	/**
	 * 加密手机号，保护隐私信息
	 */
	public static String mobileEncrypt(String mobilePhone) {
		if (isEmpty(mobilePhone) || mobilePhone.length() < 11) {
			return mobilePhone;
		}

		String mid = mobilePhone.substring(3, 3 + 4);
		return StringUtils.replaceOnce(mobilePhone, mid, "****");
	}


	/**
	 * 比较用户使用的版本号是否不低于后台配置的可使用的最低版本
	 *
	 * @param userVersion   用户当前的版本号
	 * @param limitVersions 限制下单的最低版本，Android与iOS限制版本号可能不同，因此分开比较
	 * @return 允许下单则返回true
	 */
	public static boolean versionCompare(String userVersion, String[] limitVersions) throws Exception {
		boolean result = true;

		String limitVersion;
		if (userVersion.contains("安卓版本：")) {
			limitVersion = limitVersions[0];
			userVersion = userVersion.replace("安卓版本：", "");
		} else {
			limitVersion = limitVersions[1];
		}

		// 由于版本号不能直接比较，因此需要将每个数字拆分从大版本开始比较
		String[] userVersionArray = userVersion.split("\\.");
		String[] limitVersionArray = limitVersion.split("\\.");
		for (int i = 0; i < userVersionArray.length; i++) {
			// 当第一位更大时，直接停止比较，肯定为true
			if (Integer.valueOf(userVersionArray[i]) > Integer.valueOf(limitVersionArray[i])) {
				break;
			} else if (Integer.valueOf(userVersionArray[i]) < Integer.valueOf(limitVersionArray[i])) {
				result = false;
				break;
			}
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println(mobileEncrypt("13800138000"));
	}

}
