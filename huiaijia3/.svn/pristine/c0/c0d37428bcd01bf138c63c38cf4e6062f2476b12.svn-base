/**
 * 
 */
package com.flf.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间日期工具类
 * 
 * @author SevenWong<br>
 * @date 2016年8月10日下午3:37:13
 */
public class DateUtil {

	private final static Logger log = Logger.getLogger(DateUtil.class);

	public static SimpleDateFormat sdf = new SimpleDateFormat();
	public static Calendar calendar;

	public static final int SECONDS_IN_DAY = 60 * 60 * 24;
	public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

	/**
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月10日下午3:21:02
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String datetime2Str(Date date) {
		if (date == null) {
			return null;
		}
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月10日下午3:35:14
	 * @param date
	 * @param millisecond
	 *            为true时返回毫秒值
	 * @return yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd HH:mm:ss:SSS
	 */
	public static String datetime2Str(Date date, boolean millisecond) {
		if (millisecond) {
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss:SSS");
			return sdf.format(date);
		} else {
			return datetime2Str(date);
		}
	}

	/**
	 * 返回中文格式日期时间(yyyy年MM月dd日HH时mm分ss秒)
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月10日下午3:27:17
	 * @param date
	 * @return
	 */
	public static String datetime2StrZH_CN(Date date) {
		sdf.applyPattern("yyyy年MM月dd日HH时mm分ss秒");
		return sdf.format(date);
	}

	/**
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月10日下午3:23:23
	 * @param date
	 * @return 返回指定日期的yyyy-MM-dd格式
	 */
	public static String date2Str(Date date) {
		if (date == null) {
			return null;
		}
		sdf.applyPattern("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String time2Str(Date date) {
		sdf.applyPattern("HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 
	 * 多个类中有调用此方法，因此未弃用，建议使用date2Str(Date date)
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月10日下午3:23:23
	 * @param date
	 * @return 返回指定日期的yyyy-MM-dd格式
	 */
	public static String dateToString(Date date) {
		return date2Str(date);
	}

	/**
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月10日下午3:20:57
	 * @param date
	 * @param separator
	 *            为true时返回yyyy-MM-dd，否则返回yyyyMMdd
	 * @return
	 */
	public static String date2Str(Date date, boolean separator) {
		if (separator) {
			return date2Str(date);
		} else {
			sdf.applyPattern("yyyyMMdd");
			return sdf.format(date);
		}
	}

	/**
	 * 下单时获取预计送达时间
	 * 
	 * @return
	 */
	public static String getDeliveryTime() {
		sdf.applyPattern("yyyy年MM月dd日 7点0分");
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		return sdf.format(calendar.getTime());
	}

	public static String dateformat(Date date, String fmt) {
		sdf.applyPattern(fmt);
		return sdf.format(date);
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获得指定日期的后几天
	 */
	public static Date getDayAfter(int days) {
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, days);
		return calendar2.getTime();
	}

	public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
		final long interval = ms1 - ms2;
		return interval < MILLIS_IN_DAY && interval > -1L * MILLIS_IN_DAY && toDay(ms1) == toDay(ms2);
	}

	private static long toDay(long millis) {
		return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
	}

	public static boolean isToday(String date1, String date2) {
		boolean resultflag = false;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt1 = df.parse(date1);
			long time1 = dt1.getTime();

			Date dt2 = df.parse(date2);
			long time2 = dt2.getTime();
			resultflag = isSameDayOfMillis(time1, time2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultflag;
	}

	public static int compare_date(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				log.info("当前时间" + date1 + " 在取消时间" + date2 + "之后");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				log.info("当前时间" + date1 + " 在取消时间" + date2 + "之前");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		/*
		 * Date date = new Date(); // 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date
		 * 对象表示的毫秒数。 long time = date.getTime();
		 * 
		 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 * 
		 * try { Date dt1 = df.parse("2016-11-20 09:55:20"); long time1 =
		 * dt1.getTime();
		 * 
		 * System.out.println(time); System.out.println(time1);
		 * System.out.println(isSameDayOfMillis(time, time1)); } catch
		 * (ParseException e) { e.printStackTrace(); }
		 */

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, 0);
		System.out.println(calendar2.getTime());

	}

	/**
	 * 日期字符串转格式
	 * @param date
	 * @return
	 */
	public static String datetime2StrZHCN(String date) {
		if (date!=null) {
			String[] arr = date.split("-");
			for (int i = 0;i<arr.length;i++) {
				return arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
			}
		}
		return null;
	}

}
