package com.xwtec.xwserver.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * 对于日期类型的操作工具类 <br>
 * <p>
 * Copyright: Copyright (c) 2014-1-16 上午11:52:47
 * <p>
 * Company: 欣网视讯
 * <p>
 * 
 * @author caozhenxing@mail.xwtec.cn
 * @version 1.0.0
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	private static final Logger log = Logger.getLogger(DateUtils.class);

	// 一天的毫秒数 60*60*1000*24
	private final static long DAY_MILLIS = 86400000;

	// 日期格式化串
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	// 日期月份格式化
	private final static SimpleDateFormat monthFmt = new SimpleDateFormat("yyyyMM");

	/**
	 * 方法描述:计算两个日期之间相差多少天
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final int getDayDistance(long start, long end) {
		long temp = end - start;
		int distance = (int) (temp / DAY_MILLIS);
		return distance;
	}

	/**
	 * 方法描述:判断两个时间是否在同一个星期内
	 * 
	 * @param beginDate第一个时间
	 *            endDate第二个时间
	 * @return boolean true 在同一个星期内 false不在同一个星期内
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final boolean isSameWeek(String beginDate, String endDate) {
		try {
			Date begin = DATE_FORMAT.parse(beginDate);
			Date end = DATE_FORMAT.parse(endDate);
			Calendar cBegin = Calendar.getInstance();
			Calendar cEnd = Calendar.getInstance();
			cBegin.setTime(begin);
			cEnd.setTime(end);
			int distance = getDayDistance(cBegin.getTimeInMillis(), cEnd.getTimeInMillis());// 计算两个日期之间相差多少天
			if (distance < 7 && cBegin.get(Calendar.WEEK_OF_YEAR) == cEnd.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		}
		catch (ParseException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return false;
	}

	/**
	 * 方法描述:判断两个时间是否在同一个月里面
	 * 
	 * @param beginDate第一个时间
	 *            endDate第二个时间
	 * @return boolean true 在同一个星期内 false不在同一个星期内
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final boolean isSameMonth(String beginDate, String endDate) {
		try {
			Date begin = DATE_FORMAT.parse(beginDate);
			Date end = DATE_FORMAT.parse(endDate);
			Calendar cBegin = Calendar.getInstance();
			Calendar cEnd = Calendar.getInstance();
			cBegin.setTime(begin);
			cEnd.setTime(end);
			if (cBegin.get(Calendar.YEAR) == cEnd.get(Calendar.YEAR) && cBegin.get(Calendar.MONTH) == cEnd.get(Calendar.MONTH)) {// 同一年同一个月
				return true;
			}
		}
		catch (ParseException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return false;
	}

	/**
	 * 方法描述:判断两个时间是否在同一年内
	 * 
	 * @param beginDate第一个时间
	 *            endDate第二个时间
	 * @return boolean true 在同一个星期内 false不在同一个星期内
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static boolean isSameYear(String beginDate, String endDate) {
		try {
			Date begin = DATE_FORMAT.parse(beginDate);
			Date end = DATE_FORMAT.parse(endDate);
			Calendar cBegin = Calendar.getInstance();
			Calendar cEnd = Calendar.getInstance();
			cBegin.setTime(begin);
			cEnd.setTime(end);
			if (cBegin.get(Calendar.YEAR) == cEnd.get(Calendar.YEAR)) { // 同一年
				return true;
			}
		}
		catch (ParseException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return false;
	}

	/**
	 * 方法描述:将给定的字符串日期格式转换为java.util.Date对象
	 * 
	 * @param date
	 *            日期 formatPattern 格式
	 * @return java.util.Date
	 * @throws ParseException
	 *             抛出异常
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final Date parse(String date, String formatPattern) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(formatPattern);

		Date _date = null;

		try {
			_date = dateFormat.parse(date);
		}
		catch (ParseException pe) {
			throw pe;
		}

		return _date;
	}

	/**
	 * 方法描述:将给定的字符串日期转换为另一种指定的格式(yyyyMMddHHmmss)
	 * 
	 * @param date
	 *            日期 sourcePattern 原格式 formatPattern 目标格式
	 * @return java.lang.String
	 * @throws ParseException
	 *             抛出异常
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String format(String date, String sourcePattern, String formatPattern) throws ParseException {
		Date _date = null;

		try {
			_date = parse(date, sourcePattern);
		}
		catch (ParseException pe) {
			throw pe;
		}

		DateFormat dateFormat = new SimpleDateFormat(formatPattern);
		return dateFormat.format(_date);
	}

	/**
	 * 方法描述:将给定的日期格式化成指定的格式字符串
	 * 
	 * @param date
	 *            日期 formatPattern 格式
	 * @return java.lang.String
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String format(Date date, String formatPattern) {
		DateFormat dateFormat = new SimpleDateFormat(formatPattern);
		return dateFormat.format(date);
	}

	/**
	 * 方法描述:将给定的字符串日期转换为十四位格式(yyyyMMddHHmmss)
	 * 
	 * @param date
	 *            日期 sourcePattern 原格式
	 * @return java.lang.String
	 * @throws ParseException
	 *             抛出异常
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String formatChar14(String date, String sourcePattern) throws ParseException {
		return format(date, sourcePattern, "yyyyMMddHHmmss");
	}

	/**
	 * 方法描述:将给定的字符串日期转换为八位格式(yyyyMMdd)
	 * 
	 * @param date
	 *            日期 sourcePattern 原格式
	 * @return java.lang.String
	 * @throws ParseException
	 *             抛出异常
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String formatChar8(String date, String sourcePattern) throws ParseException {
		return format(date, sourcePattern, "yyyyMMdd");
	}

	/**
	 * 方法描述:将给定的日期转换为十四位的字符串(yyyyMMddHHmmss)
	 * 
	 * @param date
	 *            日期
	 * @return java.lang.String
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String formatChar14(Date date) {
		return format(date, "yyyyMMddHHmmss");
	}

	/**
	 * 方法描述:将给定的日期转换为八位的字符串(yyyyMMdd)
	 * 
	 * @param date
	 *            日期
	 * @return java.lang.String
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String formatChar8(Date date) {
		return format(date, "yyyyMMdd");
	}

	/**
	 * 方法描述:判断afterDate是否在beforeDate之后
	 * 
	 * @param beforeDate
	 *            之前的日期 afterDate 之后的日期 allowEquals 是否允许日期相等
	 * @return true beforeDate在afterDate之后 false beforeDate不在afterDate之后
	 * @throws Exception
	 *             抛出异常
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final boolean isAfter(Date afterDate, Date beforeDate, boolean allowEquals) throws Exception {
		return allowEquals ? afterDate.after(beforeDate) || afterDate.compareTo(beforeDate) == 0 : afterDate.after(beforeDate);
	}

	/**
	 * 方法描述:获取指定区域的系统当前时间
	 * 
	 * @param locale
	 *            区域
	 * @return java.util.Calendar
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static synchronized final Calendar getCurrent(Locale locale) {
		return Calendar.getInstance(locale);
	}

	/**
	 * 方法描述:获取默认区域的系统当前时间
	 * 
	 * @return java.util.Calendar
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final Calendar getCurrent() {
		return getCurrent(Locale.getDefault());
	}

	/**
	 * 方法描述:获取默认区域的当前时间的十四位字符串格式
	 * 
	 * @return java.lang.String
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String getCurrent14() {
		return formatChar14(getCurrent().getTime());
	}

	/**
	 * 方法描述:获取默认区域的当前时间的八位字符串格式
	 * 
	 * @return java.lang.String
	 * @date:2014-1-16
	 * @add by: caozhenxing@mail.xwtec.cn
	 */
	public static final String getCurrent8() {
		return formatChar8(getCurrent().getTime());
	}

	/**
	 * 返回年月周yyyyMMw
	 * 
	 * @param date
	 *            yyyyMMdd
	 * @return date:2014-02-19 add by: chenchao@xwtec.cn
	 */
	public static String getWeekOfMonth(String date) {
		int y = Integer.parseInt(date.substring(0, 4));
		int m = Integer.parseInt(date.substring(4, 6));
		int d = Integer.parseInt(date.substring(6));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, y);
		cal.set(Calendar.MONTH, m - 1);
		cal.set(Calendar.DAY_OF_MONTH, d);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		// 取本月第一天是星期几
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, y);
		cal2.set(Calendar.MONTH, m - 1);
		cal2.set(Calendar.DAY_OF_MONTH, 1);
		cal2.set(Calendar.HOUR, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int week = cal2.get(Calendar.DAY_OF_WEEK);

		// 判断本月第一天是否是周一，如果是则周数不需要-1，否则周数需要-1
		if (week == Calendar.MONDAY) {
			return monthFmt.format(cal.getTime()) + cal.get(Calendar.WEEK_OF_MONTH);
		} else {
			return monthFmt.format(cal.getTime()) + (cal.get(Calendar.WEEK_OF_MONTH) - 1);
		}
	}

	/**
	 * 获取指定年月的最后一天的日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 指定年月的最后一天的日期 返回格式yyyyMMdd
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 */
	public static String getEndDateByMonth(String year, String month) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");

		Calendar calendar = Calendar.getInstance();

		try {
			Date date = format.parse(year + month);

			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		}
		catch (ParseException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return format2.format(calendar.getTime());
	}

	/**
	 * 将字符型的日期转化为日期型的日期
	 * 
	 * @param dateValue
	 *            将要转化的字符串
	 * @return 日期型的日期 返回格式yyyyMMdd
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date strToDate(String dateValue) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.parse(dateValue);
	}

	/**
	 * 获取指定年月的第一天的日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 指定年月的第一天的日期 （如：20130601）
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 */
	public static String getStartDateByMonth(String year, String month) {

		return year + month + "01";
	}

	/**
	 * 根据一个日期，返回数字的星期几
	 * 
	 * @param dateValue
	 *            日期
	 * @return 数字的星期几 （1：星期日 7：星期六，其他类推）
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 * @throws ParseException
	 */
	public static int getWeekByDate(String dateValue) throws ParseException {
		// 再转换为时间
		Date date = strToDate(dateValue);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 距离最近的星期一有几天
	 * 
	 * @param day
	 *            星期几（ 1：星期日 7：星期六，其他类推）
	 * @return date:2014-02-19 add by: chenchao@xwtec.cn
	 */
	public static int nextMondayDays(int day) {
		int days = 0;
		switch (day) {
			case 1:
				days = 1;
				break;
			case 2:
				days = 0;
				break;
			case 3:
				days = 6;
				break;
			case 4:
				days = 5;
				break;
			case 5:
				days = 4;
				break;
			case 6:
				days = 3;
				break;
			case 7:
				days = 2;
				break;
			default:
				break;
		}
		return days;
	}

	/**
	 * 根据传进的日期,以及X得到X天后的日期
	 * 
	 * @return 日期 格式：yyyyMMdd
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 */
	public static String getAfterDate(String date, int several) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(date));
		long days = several;
		long timeone = cal.getTimeInMillis();
		long result = timeone + days * 24 * 60 * 60 * 1000;
		Date getDate = new Date(result);
		return sdf.format(getDate).toString();
	}

	/**
	 * 获取每个月的第一个星期一的日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 第一个星期一的日期（yyyyMMdd）
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 * @throws Exception
	 *             转换异常
	 */
	public static String getFirstMondyOfMonth(String year, String month) throws Exception {
		String date = getStartDateByMonth(year, month);
		return getAfterDate(date, nextMondayDays(getWeekByDate(date)));
	}

	/**
	 * 获取指定年月的第几周的开始日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param week
	 *            日
	 * @return 开始日期（yyyyMMdd）
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 * @throws Exception
	 *             异常
	 */
	public static String getStartDayByWeek(String year, String month, String week) {
		String firstMondayDate = "";
		String date = "";
		try {
			firstMondayDate = getFirstMondyOfMonth(year, month);
			date = getAfterDate(firstMondayDate, (Integer.parseInt(week) - 1) * 7);
		}
		catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return date;
	}

	/**
	 * 获取指定年月的第几周的结束日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param week
	 *            日
	 * @return 结束日期（yyyyMMdd）
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 * @throws Exception
	 *             异常
	 */
	public static String getEndDayByWeek(String year, String month, String week) {
		String firstMondayDate = "";
		String date = "";
		try {
			firstMondayDate = getFirstMondyOfMonth(year, month);
			date = getAfterDate(firstMondayDate, (Integer.parseInt(week) - 1) * 7 + 6);
		}
		catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return date;
	}

	/**
	 * 返回一个月有几周
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 一个月有几周（整数，如：4）
	 * @date:2014-02-19 add by: chenchao@xwtec.cn
	 */
	public static int getWeeksOfMonth(String year, String month) {

		// 默认一个月有4周
		int count = 4;

		try {
			String mondayDate = getAfterDate(getFirstMondyOfMonth(year, month), 28);

			if (mondayDate.substring(4, 6).equals(month)) {
				count = 5;
			}
		}
		catch (Exception e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
		return count;

	}

	/**
	 * 获取上个月的日期（yyyyMM）
	 * 
	 * @return date:2014-02-19 add by: chenchao@xwtec.cn
	 */
	public static String getLastMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return monthFmt.format(c.getTime());
	}
}
