package com.lifeng.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * <p>
 * Title: 日期工具集合类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author shulin.feng
 * @version 1.0
 * @createtime 2006-10-17
 */
public class DateFormatUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将日期型转换为yyyy-MM-dd HH:mm:ss式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getSimpleStringDate(Date date) {
		return sdf.format(date);
	}

	/**
	 * 将字符串解析成java.util.Date
	 *
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date toDate(String strDate, String pattern) {
		Date reVal = null;
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			reVal = df.parse(strDate);
		} catch (ParseException e) {
			reVal = null;
			e.printStackTrace();
		}
		return reVal;
	}

	/**
	 * 基本功能:获得某个时间段的月数
	 * <p/>
	 *
	 * @param startDate
	 * @param endDate
	 * @return int
	 */
	public static int getDaysMonth(Date startDate, Date endDate) {
		int end = getMonth(endDate);
		int start = getMonth(startDate);
		int months = 0;
		if (end < start) {
			months = end + 12 - start;
		} else {
			months = end - start;
		}
		return months;
	}

	/**
	 * 基本功能:得到月份，行如：MM
	 * <p/>
	 *
	 * @param date
	 * @return String
	 */
	public static int getMonth(Date date) {
		DateFormat df = new SimpleDateFormat("MM");
		return Integer.parseInt(df.format(date));
	}

	/**
	 * 基本功能:返回本月最后一天
	 * <p/>
	 *
	 * @param date
	 * @return Date
	 */
	public static Date getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}

	/**
	 * 基本功能:获得某个月的最后一天
	 * <p/>
	 *
	 * @param year
	 * @param month
	 * @return int
	 */
	public static int getLastMonthDay(Date date) {
		int lastmonth;
		int lastmonthday = 0;
		int year = Integer.parseInt(getYear(date));
		try {
			lastmonth = getMonth(date);
			if (lastmonth == 0) {
				lastmonth = 12;
				year = year - 1;
			}
			switch (lastmonth) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				lastmonthday = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				lastmonthday = 30;
				break;
			case 2:
				if ((year % 4) == 0)
					lastmonthday = 29;
				else
					lastmonthday = 28;
				break;
			}

		} catch (Exception e) {
			return 0;
		}
		return lastmonthday;
	}

	/**
	 * 基本功能:得到日期，行如：dd
	 * <p/>
	 *
	 * @param date
	 * @return String
	 */
	public static int getOneDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 返回当前时间
	 *
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(pattern);
		String currentdate = "";
		try {
			currentdate = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentdate;
	}

	/**
	 * 基本功能:转换成SQL.date类型
	 * <p/>
	 *
	 * @param strDate
	 * @param pattern
	 * @return java.sql.Date
	 */
	public static java.sql.Date toSqlDate(String strDate, String pattern) {
		return new java.sql.Date(toDate(strDate, pattern).getTime());
	}

	/**
	 * 将日期类转化成字符串，格式为"yyyy-MM-dd".
	 *
	 * @param date
	 * @return
	 */
	public static String toNormalPatternString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 基本功能：返回日期的字符串到秒
	 * <p/>
	 *
	 * @param time
	 * @return String
	 */
	public static String getTimeAll(long time) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ' 'HH:mm:ss");

		return formatter.format(new Date(time));

	}

	/**
	 * 基本功能：返回指定格式日期字符串
	 * <p/>
	 *
	 * @param time
	 * @param format
	 * @return String
	 */
	public static String getFormatStr(String format, long time) {
		String formatTime = "";

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatTime = formatter.format(new Date(time));

		return formatTime;
	}

	/**
	 * 基本功能：返回格林威治时间（1970-1-1）到指定日期的毫秒数
	 * <p/>
	 *
	 * @param format
	 * @param datestr
	 * @return long
	 * @throws Exception
	 */
	public static long getLongFormatDate(String format, String datestr) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		// ParsePosition pos = new ParsePosition(0);
		Date day;
		try {
			day = formatter.parse(datestr);
			return day.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 基本功能：返回第一个时间与第二个时间相差的秒数
	 * <p/>
	 *
	 * @param first
	 * @param second
	 * @return long
	 * @throws Exception
	 */
	public static long getDistance(Date first, Date second) {
		return (first.getTime() - second.getTime()) / 1000;
	}

	/**
	 * 获得24小时制long型时间，形如:2007-07-01 01:23 PM
	 *
	 * @param datestr
	 * @return
	 */
	public static long getLongTime24(String datestr) {
		long long24time = 0;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US);
			Date dd = df.parse(datestr);
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String ddstr = df1.format(dd);
			long24time = getLongFormatDate("yyyy-MM-dd HH:mm", ddstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return long24time;
	}

	/**
	 * 基本功能：返回yyyy-MM-dd日期字符串
	 * <p/>
	 *
	 * @param time
	 * @return String
	 */
	public static String getTimeSimple(long time) {
		String formatTime = "";

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatTime = formatter.format(new Date(time));

		return formatTime;
	}

	/**
	 * 基本功能：将long转换为Date
	 * <p/>
	 *
	 * @param time
	 * @return java.sql.Date
	 */
	public static java.sql.Date getDateTimeSimple(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formatTime = formatter.format(new Date(time));
		return java.sql.Date.valueOf(formatTime);
	}

	/**
	 * 基本功能：将yyyy-MM-dd格式字符串转换为long
	 * <p/>
	 *
	 * @param time
	 * @return
	 * @throws Exception
	 *             long
	 */
	public static long getLongByTimeThreadStr(String time) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(time, pos);
		if (date == null)
			throw new Exception("the date/time string can not parse");
		return date.getTime();
	}

	/**
	 * 基本功能：将yyyy-MM-dd HH:mm:ss格式字符串转换为long
	 * <p/>
	 *
	 * @param time
	 * @return
	 * @throws Exception
	 *             long
	 */
	public static long getLongByFullTime(String time) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(time, pos);
		if (date == null)
			throw new Exception("the date/time string can not parse");
		return date.getTime();
	}

	/**
	 * 基本功能：将yyyy.MM.dd格式字符串转换为long
	 * <p/>
	 *
	 * @param time
	 * @param type
	 * @return
	 * @throws Exception
	 *             long
	 */
	public static long getLongByTimeDotStrTime(String time, int type) throws Exception {
		long formatTime = 0;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(time, pos);
		if (date == null)
			throw new Exception("the date/time string can not parse");
		formatTime = date.getTime();

		return formatTime;
	}

	/**
	 * 基本功能：将java日期类转化成sql Timestamp类型
	 * <p/>
	 *
	 * @param date
	 * @return java.sql.Timestamp
	 */
	public static java.sql.Timestamp toSqlTimestamp(Date date) {
		if (date == null)
			return null;
		else
			return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 基本功能:字符串转换为Timestamp
	 * <p/>
	 *
	 * @param str
	 * @return java.sql.Timestamp
	 */
	public static java.sql.Timestamp toTimestamp(String str) {
		if (!str.equals("")) {
			Date date = toDate(str, "yyyy-MM-dd HH:mm:ss");
			return new java.sql.Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * 基本功能：得到年月，行如：yyyy-MM
	 * <p/>
	 *
	 * @param date
	 * @return String
	 */
	public static String getYearMonth(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);
	}

	/**
	 * 基本功能：得到年，行如：yyyy
	 * <p/>
	 *
	 * @param date
	 * @return String
	 */
	public static String getYear(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy");
		return df.format(date);
	}

	/**
	 * 基本功能：得到日期，行如：yyyy-MM-dd
	 * <p/>
	 *
	 * @param date
	 * @return String
	 */
	public static String getDay(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 基本功能:将日期DATE转换为String
	 * <p/>
	 *
	 * @param date
	 * @param parteen
	 * @return String
	 */
	public static String dateToString(Date date, String parteen) {
		DateFormat df = new SimpleDateFormat(parteen);
		return df.format(date);
	}

	/**
	 * 基本功能：得到date所在周的第一天
	 * <p/>
	 *
	 * @param date
	 * @return String
	 */
	public static String getWeekStartDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.HOUR, -24 * (weekDay - 1));
		return getDay(calendar.getTime());
	}

	/**
	 * 基本功能：得到date所在周的最后一天
	 * <p/>
	 *
	 * @param date
	 * @return String
	 */
	public static String getWeekEndDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.HOUR, 24 * (7 - weekDay));
		return getDay(calendar.getTime());
	}

	/**
	 * 基本功能：得到date所在周的起始日期
	 * <p/>
	 *
	 * @param date
	 * @return String[]
	 */
	public static String[] getWeekStartEnd(Date date) {
		String[] str = new String[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.HOUR, -24 * (weekDay - 1));
		str[0] = getDay(calendar.getTime());

		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 24 * (7 - weekDay));
		str[1] = getDay(calendar.getTime());
		return str;
	}

	/**
	 * 基本功能：得到date所在月的起始结束日期
	 * <p/>
	 *
	 * @param date
	 * @return String[]
	 */
	public static String[] getMonthStartEnd(Date date) {
		String[] str = new String[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		str[0] = getDay(calendar.getTime());

		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		str[1] = getDay(calendar.getTime());
		return str;
	}

	/**
	 * 转换日期格式为long型
	 *
	 * @param date
	 * @return long
	 */
	public static long parseAnyDate(String date) {
		if (null != date) {
			List<SimpleDateFormat> list = new ArrayList<SimpleDateFormat>();
			list.add(new SimpleDateFormat("yyyy年MM月dd日 HH:mm"));
			list.add(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"));
			list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
			for (SimpleDateFormat format : list) {
				try {
					Date newdate = format.parse(date);
					return newdate.getTime();
				} catch (Exception e) {
					continue;
				}
			}
		}
		return 0L;
	}

	/**
	 * 转换日期格式为Date型
	 *
	 * @param date
	 * @return Date
	 */
	public static Date parseAnyDateToDate(String date) {
		if (null != date) {
			List<SimpleDateFormat> list = new ArrayList<SimpleDateFormat>();
			list.add(new SimpleDateFormat("yyyy年MM月dd日 HH:mm"));
			list.add(new SimpleDateFormat("yyyy年MM月dd日HH:mm"));
			list.add(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"));
			list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
			for (SimpleDateFormat format : list) {
				try {
					Date newdate = format.parse(date);
					return newdate;
				} catch (Exception e) {
					continue;
				}
			}
		}
		return new Date();
	}

	/**
	 * 日期的月份增加或减少
	 *
	 * @param String
	 *            2005-09-09 ; len=5
	 * @return 2006-02-09
	 */
	public static String addMonth(String datestr, int len) {
		StringTokenizer token = new StringTokenizer(datestr, "-");
		int year = Integer.parseInt(token.nextToken());
		int month = Integer.parseInt(token.nextToken());
		int day = Integer.parseInt(token.nextToken());
		Calendar date = Calendar.getInstance();
		month = month + len - 1;
		date.set(year, month, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime()).toString();
	}

	/**
	 * 按当前时间增加分钟
	 *
	 * @param datastr
	 * @param minute
	 * @return
	 */
	public static String addMinute(String datastr, int minute) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance(); // 当时的日期和时间
		String strtime = "";
		try {
			Date d = df.parse(datastr);
			c.setTime(d);
			long minutemillis = minute * 60 * 1000;
			c.setTimeInMillis(c.getTimeInMillis() + minutemillis);
			strtime = df.format(c.getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return strtime;
	}

	/**
	 * 按当前时间增加小时
	 *
	 * @param datastr
	 * @param minute
	 * @return
	 */
	public static String addHour(String datastr, int hour) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance(); // 当时的日期和时间
		String strtime = "";
		try {
			Date d = df.parse(datastr);
			c.setTime(d);
			long hourmillis = hour * 60 * 60 * 1000L;
			c.setTimeInMillis(c.getTimeInMillis() + hourmillis);
			strtime = df.format(c.getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return strtime;
	}

	/**
	 * 获取当前日期 yyyy-MM-dd HH:mm:ss
	 *
	 * @return String
	 */
	public static String getToday() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 获取当前日期 yyyy-MM-dd HH:mm:ss
	 *
	 * @return String
	 */
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	/**
	 * 获取当前日期年 yyyy
	 *
	 * @return String
	 */
	public static String getCurrentYear() {
		String time = getToday();
		return time.substring(0, time.indexOf("-"));
	}

	/**
	 * 改变日期 在当前时间上加天数
	 *
	 * @param aDate
	 *            Date
	 * @param days
	 *            int
	 * @return String
	 */
	public static String getChangeDate(Date aDate, int days) {
		String datestr = "";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(aDate);
			cal.add(Calendar.DATE, days);
			aDate = cal.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			datestr = formatter.format(aDate);
		} catch (Exception ex) {
		}
		return datestr;
	}

	public static String getChangeDate(String aDate, int days) {
		String datestr = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date d = formatter.parse(aDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, days);
			d = cal.getTime();
			datestr = formatter.format(d);

		} catch (Exception ex) {

		}
		return datestr;
	}

	public static String getChangeDateInfo(String aDate, int days) {
		String datestr = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = formatter.parse(aDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, days);
			d = cal.getTime();
			datestr = formatter.format(d);

		} catch (Exception ex) {

		}
		return datestr;
	}

	/**
	 * 把当前日期格式转换成微秒格式
	 *
	 * @param aDate
	 * @return
	 */
	public static String getMillisTime(String aDate) {
		String datestr = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = df.parse(aDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			datestr = datestr + cal.getTimeInMillis();
		} catch (Exception ex) {

		}
		return datestr;
	}

	/**
	 * 获取当前是周几--数字
	 *
	 * @param dt
	 * @return
	 */
	public static int getWeekOfDate(Date dt) {
		int[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取当前是周几--中文
	 *
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDateChinese(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * @Description 获取当前系统前一天最小日期（2016-11-15 00:00:00）
	 * @param date
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static Date getBeforeStartDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		date = calendar.getTime();
		return date;
	}

	/**
	 * @Description 获取当前系统前一天最大日期（2016-11-15 23:59:59）
	 * @param date
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static Date getBeforeEndDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * @Description UNIX时间戳
	 * @param strDate
	 * @param pattern
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static long getUnixTime(String strDate, String pattern) {
		Date date = toDate(strDate, pattern);
		long unixTime = date.getTime() / 1000;
		return unixTime;
	}
	
	/**
	 * @Description Unix时间戳转时间
	 * @param unixTime
	 * @param formats
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static Date unixTimeToDate(String unixTime, String formats) {
		Long timestamp = Long.parseLong(unixTime) * 1000;
		String date = new SimpleDateFormat(formats).format(new java.util.Date(timestamp));
		return toDate(date, formats);
	}
	
	public static void main(String[] args) {
		//1516951899
		Date time = unixTimeToDate("1516953643", "yyyy-MM-dd HH:mm:ss");
		System.out.println(time+"");
	}
}
