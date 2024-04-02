/*
 * Copyright (c) 2006-2008, Humpic Organization. All rights reserved.
 * 
 * $HeadURL: http://humpic.googlecode.com/svn/trunk/humpic-framework/src/java/core/com/humpic/framework/helper/java $
 * $Id: java 303 2008-10-31 08:18:37Z subchen $
 * 
 * This library is free. You can redistribute it and/or modify it.
 * Support by http://www.humpic.com/
 */
package com.figure.core.helper;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

	public static final long SECOND = 1000;
	public static final long MINITE = SECOND * 60;
	public static final long HOUR = MINITE * 60;
	public static final long DAY = HOUR * 24;

	public static final SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");

	public static final SimpleDateFormat timeSdf = new SimpleDateFormat("HHmmss");

	/**
	 * 说明：将DateTime中的Date转换成Integer
	 * @param date
	 * @return
	 */
	public static final int getDateInt(Date date){
		return Integer.valueOf(dateSdf.format(date));
	}

	/**
	 * 说明：将DateTime中的Time转换成Integer
	 * @param date
	 * @return
	 */
	public static final int getTimeInt(Date date){
		return Integer.valueOf(timeSdf.format(date));
	}


	// order is like this because the SimpleDateFormat.parse does not fail with
	// exception
	// if it can parse a valid date out of a substring of the full string given
	// the mask
	// so we have to check the most complete format first, then it fails with
	// exception
	private static final String[] RFC822_MASKS = { 
		"EEE, dd MMM yy HH:mm:ss z", 
		"EEE, dd MMM yy HH:mm z", 
		"dd MMM yy HH:mm:ss z", 
		"dd MMM yy HH:mm z" 
	};

	// order is like this because the SimpleDateFormat.parse does not fail with
	// exception
	// if it can parse a valid date out of a substring of the full string given
	// the mask
	// so we have to check the most complete format first, then it fails with
	// exception
	public static final String[] W3CDATETIME_MASKS = { 
		"yyyy-MM-dd'T'HH:mm:ss.SSSz", 
		"yyyy-MM-dd't'HH:mm:ss.SSSz", 
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", 
		"yyyy-MM-dd't'HH:mm:ss.SSS'z'",
		"yyyy-MM-dd'T'HH:mm:ssz", 
		"yyyy-MM-dd't'HH:mm:ssz", 
		"yyyy-MM-dd'T'HH:mm:ss'Z'", 
		"yyyy-MM-dd't'HH:mm:ss'z'", 
		"yyyy-MM-dd'T'HH:mmz", // together with logic in the parseW3CDateTime they
		"yyyy-MM'T'HH:mmz", // handle W3C dates without time forcing them to be GMT
		"yyyy'T'HH:mmz", 
		"yyyy-MM-dd't'HH:mmz", 
		"yyyy-MM-dd'T'HH:mm'Z'", 
		"yyyy-MM-dd't'HH:mm'z'", 
		"yyyy-MM-dd", 
		"yyyy-MM", 
		"yyyy" 
	};
	
	/**
	 * "yyyy-MM-dd HH:mm:ss" //2
	 * "yyyy-MM-dd" //4
	 * "HH:mm:ss" //12
	 */
	public static final String[] patterns_masks = { 
		"yyyy-MM-dd HH:mm:ss,SSS", 
		"yyyy-MM-dd HH:mm:ss.SSS", 
		"yyyy-MM-dd HH:mm:ss", //2
		"yyyy-MM-dd HH:mm", 
		"yyyy-MM-dd", //4
			"yyyy/MM/dd HH:mm:ss,SSS",
			"yyyy/MM/dd HH:mm:ss.SSS",
			"yyyy/MM/dd HH:mm:ss",
			"yyyy/MM/dd HH:mm",
			"yyyy/MM/dd",
			"yyyyMMddHHmmss",
			"yyyyMMdd",
			"HH:mm:ss",//12
			"hh:mm:ss,SSS",
			"hh:mm:ss.SSS",
			"hh:mm:ss",
			"yyyyMMddHHmmssSSS",//16
	};

	/**
	 * 修正 new Date() 函数返回的时间是北京时间.
	 * @since 2008-10-31
	 */
	public static void fixedChinaTimeZone() {
		System.out.println("start to fixedChinaTimeZone....................");
		TimeZone userTimeZone = TimeZone.getTimeZone(System.getProperty("user.timezone"));
		TimeZone chinaTimeZone = TimeZone.getTimeZone("Asia/Shanghai");

		if (!chinaTimeZone.hasSameRules(userTimeZone)) {
			System.out.println("finish fixedChinaTimeZone....................");
			TimeZone.setDefault(chinaTimeZone);
		}
	}

	/**
	 * get date string use pattern
	 * 
	 * @param pattern
	 *            see {@link SimpleDateFormat}
	 * @return a date string
	 */
	public static String format(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * get date string use pattern
     *
     * @param pattern
     *            see {@link SimpleDateFormat}
     * @return a date string
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(pattern);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 2008-02-22 fixed by subchen
        return dateFormater.format(date);
    }

    /**
     * get date string use  efault pattern
     *
     * @param date see {@link SimpleDateFormat}
     * @return a date string
     */
    public static String format(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(patterns_masks[2]);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 2008-02-22 fixed by subchen
        return dateFormater.format(date);
    }

    /**
     * get current date and time string
     *
     * @return a local datetime string
     */
    public static String getNowStr() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS");
    }

	/**
	 * get current date string
	 * 
	 * @return a local date string
	 */
	public static String getDateStr() {
		return format(new Date(), "yyyy-MM-dd");
	}

	/**
	 * get current time string
	 * 
	 * @return a local time string
	 */
	public static String getTimeStr() {
		return format(new Date(), "HH:mm:ss");
	}

	/**
	 * @since 2008-01-02
	 */
	public static Calendar getCalendar(long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		return c;
	}

	/**
	 * @since 2008-01-02
	 */
	public static Calendar getCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * @since 2008-01-02
	 */
	public static Date add(Date date, int field, int diff) {
		Calendar c = getCalendar(date);
		c.add(field, diff);
		return c.getTime();
	}

	/**
	 * @since 2008-01-02
	 */
	public static Date add(int field, int diff) {
		return add(new Date(), field, diff);
	}

	/**
	 * 得到与当前时间相差 diff 年的时间
	 */
	public static Date nextYears(int diff) {
		return add(new Date(), Calendar.YEAR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 年的时间
	 */
	public static Date nextYears(Date date, int diff) {
		return add(date, Calendar.YEAR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 年的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextYears(String sDate, int diff) {
		return format(nextYears(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 月的时间
	 */
	public static Date nextMonths(int diff) {
		return add(new Date(), Calendar.MONTH, diff);
	}

	/**
	 * 得到与当前时间相差 diff 月的时间
	 */
	public static Date nextMonths(Date date, int diff) {
		return add(date, Calendar.MONTH, diff);
	}

	/**
	 * 得到与当前时间相差 diff 月的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextMonths(String sDate, int diff) {
		return format(nextMonths(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 天的时间
	 */
	public static Date nextDays(int diff) {
		return add(new Date(), Calendar.DATE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 天的时间
	 */
	public static Date nextDays(Date date, int diff) {
		return add(date, Calendar.DATE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 天的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextDays(String sDate, int diff) {
		return format(nextDays(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 小时的时间
	 */
	public static Date nextHours(int diff) {
		return add(new Date(), Calendar.HOUR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 小时的时间
	 */
	public static Date nextHours(Date date, int diff) {
		return add(date, Calendar.HOUR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 小时的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextHours(String sDate, int diff) {
		return format(nextHours(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 分的时间
	 */
	public static Date nextMinutes(int diff) {
		return add(new Date(), Calendar.MINUTE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 分的时间
	 */
	public static Date nextMinutes(Date date, int diff) {
		return add(date, Calendar.MINUTE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 分的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextMinutes(String sDate, int diff) {
		return format(nextMinutes(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 秒的时间
	 */
	public static Date nextSeconds(int diff) {
		return add(new Date(), Calendar.SECOND, diff);
	}

	/**
	 * 得到与当前时间相差 diff 秒的时间
	 */
	public static Date nextSeconds(Date date, int diff) {
		return add(date, Calendar.SECOND, diff);
	}

	/**
	 * 得到与当前时间相差 diff 秒的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextSeconds(String sDate, int diff) {
		return format(nextSeconds(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 清除时间，得到日期
	 * @since 2007-12-10
	 */
	public static Date clearTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 清除时间，得到日期
	 * @since 2007-12-10
	 */
	public static Date clearDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.YEAR, 0);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 0);
		return c.getTime();
	}

	/**
	 * 计算 d1 - d2 的天数差
	 * @since 2007-12-10
	 */
	public static long diffDays(Date d1, Date d2) {
		d1 = clearTime(d1);
		d2 = clearTime(d2);
		return (d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算 d1 - d2 的毫秒差
	 * @since 2007-12-10
	 */
	public static long diffMillis(Date d1, Date d2) {
		return d1.getTime() - d2.getTime();
	}

	/**
	 * 得到 Calendar 类中的 Field Value.
	 * @see Calendar
	 * @since 2007-12-10
	 */
	public static int getCalendarField(Date date, int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	/**
	 * 得到当前时间
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp getTimestamp(Date date) {
		return date == null ? null : new Timestamp(date.getTime());
	}

	/**
	 * 用指定的格式解析日期时间.
	 * 
	 * @param datetime
	 *            时间字符串
	 * @param pattern
	 *            see {@link SimpleDateFormat}
	 * @throws UnhandledException
	 */
	public static Date parse(String datetime, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(true);
		try {
			ParsePosition pp = new ParsePosition(0);
			Date d = sdf.parse(datetime, pp);
			if (pp.getIndex() != datetime.length()) {
				d = null;
			}
			return d;
		} catch (Throwable e) {
			throw new NestableRuntimeException(e);
		}
	}

	/**
	 * Parses a Date out of a string using an array of masks. <p/> It uses the
	 * masks in order until one of them succedes or all fail. <p/>
	 * 
	 * @param masks
	 *            array of masks to use for parsing the string
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given string using one of the given
	 *         masks. It returns <b>null</b> if it was not possible to parse
	 *         the the string with any of the masks.
	 */
	private static Date parseUsingMask(String[] masks, String sDate) {
		sDate = (sDate != null) ? sDate.trim() : null;
		ParsePosition pp = null;
		Date d = null;
		for (int i = 0; d == null && i < masks.length; i++) {
			DateFormat df = new SimpleDateFormat(masks[i]);
			// df.setLenient(false);
			df.setLenient(true);
			try {
				pp = new ParsePosition(0);
				d = df.parse(sDate, pp);
				if (pp.getIndex() != sDate.length()) {
					d = null;
				}
				// 
				// s["+sDate+"m["+masks[i]+"] d["+d+"]");
			} catch (Exception e) {
				// 
				// try next pattern
			}
		}
		return d;
	}

	/**
	 * Parses a Date out of a String with a date in RFC822 format. <p/> It
	 * parsers the following formats:
	 * <ul>
	 * <li>"EEE, dd MMM yyyy HH:mm:ss z"</li>
	 * <li>"EEE, dd MMM yyyy HH:mm z"</li>
	 * <li>"EEE, dd MMM yy HH:mm:ss z"</li>
	 * <li>"EEE, dd MMM yy HH:mm z"</li>
	 * <li>"dd MMM yyyy HH:mm:ss z"</li>
	 * <li>"dd MMM yyyy HH:mm z"</li>
	 * <li>"dd MMM yy HH:mm:ss z"</li>
	 * <li>"dd MMM yy HH:mm z"</li>
	 * </ul>
	 * <p/> Refer to the java.text.SimpleDateFormat javadocs for details on the
	 * format of each element. <p/>
	 * 
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given RFC822 string. It returns
	 *         <b>null</b> if it was not possible to parse the given string
	 *         into a Date.
	 * 
	 */
	public static Date parseRFC822(String sDate) {
		int utIndex = sDate.indexOf(" UT");
		if (utIndex > -1) {
			String pre = sDate.substring(0, utIndex);
			String post = sDate.substring(utIndex + 3);
			sDate = pre + " GMT" + post;
		}
		return parseUsingMask(RFC822_MASKS, sDate);
	}

	/**
	 * Parses a Date out of a String with a date in W3C date-time format. <p/>
	 * It parsers the following formats:
	 * <ul>
	 * <li>"yyyy-MM-dd'T'HH:mm:ssz"</li>
	 * <li>"yyyy-MM-dd'T'HH:mmz"</li>
	 * <li>"yyyy-MM-dd"</li>
	 * <li>"yyyy-MM"</li>
	 * <li>"yyyy"</li>
	 * </ul>
	 * <p/> Refer to the java.text.SimpleDateFormat javadocs for details on the
	 * format of each element. <p/>
	 * 
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given W3C date-time string. It
	 *         returns <b>null</b> if it was not possible to parse the given
	 *         string into a Date.
	 * 
	 */
	public static Date parseW3CDateTime(String sDate) {
		// if sDate has time on it, it injects 'GTM' before de TZ displacement
		// to
		// allow the SimpleDateFormat parser to parse it properly
		int tIndex = sDate.indexOf("T");
		if (tIndex > -1) {
			if (sDate.endsWith("Z")) {
				sDate = sDate.substring(0, sDate.length() - 1) + "+00:00";
			}
			int tzdIndex = sDate.indexOf("+", tIndex);
			if (tzdIndex == -1) {
				tzdIndex = sDate.indexOf("-", tIndex);
			}
			if (tzdIndex > -1) {
				String pre = sDate.substring(0, tzdIndex);
				int secFraction = pre.indexOf(",");
				if (secFraction > -1) {
					pre = pre.substring(0, secFraction);
				}
				String post = sDate.substring(tzdIndex);
				sDate = pre + "GMT" + post;
			}
		} else {
			sDate += "T00:00GMT";
		}
		return parseUsingMask(W3CDATETIME_MASKS, sDate);
	}

	/**
	 * Parses a Date out of a String with a date in W3C date-time format or in a
	 * RFC822 format or in a humpic-default format. <p/> 用尝试多种格式解析日期时间,
	 * 修改自：http://www.koders.com/java/fidDBC85D14D02AA458CE8B8A25256E176EAC6EA748.aspx
	 * <p>
	 * 
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given W3C date-time string. It
	 *         returns <b>null</b> if it was not possible to parse the given
	 *         string into a Date.
	 */
	public static Date parse(String sDate) {
		Date d = parseW3CDateTime(sDate);
		if (d == null) {
			d = parseRFC822(sDate);
		}
		if (d == null) {
			d = parseUsingMask(patterns_masks, sDate);
		}
		if (d == null) {
			try {
				d = DateFormat.getInstance().parse(sDate);
			} catch (ParseException e) {
				d = null;
			}
		}
		return d;
	}

	/**
	 * create a RFC822 representation of a date. <p/> Refer to the
	 * java.text.SimpleDateFormat javadocs for details on the format of each
	 * element. <p/>
	 * 
	 * @param date
	 *            Date to parse
	 * @return the RFC822 represented by the given Date It returns <b>null</b>
	 *         if it was not possible to parse the date.
	 */
	public static String formatRFC822(Date date) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormater.format(date);
	}

	/**
	 * create a W3C Date Time representation of a date. <p/> Refer to the
	 * java.text.SimpleDateFormat javadocs for details on the format of each
	 * element. <p/>
	 * 
	 * @param date
	 *            Date to parse
	 * @return the W3C Date Time represented by the given Date It returns
	 *         <b>null</b> if it was not possible to parse the date.
	 */
	public static String formatW3CDateTime(Date date) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormater.format(date);
	}


	public static String format(long ms) {//将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;

		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		return strDay + "天" + strHour + "小时" + strMinute + "分钟" ;//+ strSecond + " " + strMilliSecond;
	} 

	public static boolean isDateBefore(String date2)   
	{   
		try{   
			Date date1 = new Date();   
			DateFormat df = DateFormat.getDateTimeInstance();  
			return date1.before(df.parse(date2));    
		}catch(ParseException e){   
			return false;   
		}   
	}   


	public static boolean isDateAfter(String date2){   
		try{   
			Date date1 = new Date();   
			DateFormat df = DateFormat.getDateTimeInstance();   
			return date1.after(df.parse(date2));    
		}catch(ParseException e){   
			return false;   
		}  
	}
	
	
	 /**
	 * 比较时间与系统当前时间的大小
	 * @param time1 比较的时间
	 * @return time1小于当前时间返回-1否则返回1 等于返回0
	 */
	public  static int compareTime(String time1) {
		
		//定义时间格式
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		String time2 = new Timestamp(System.currentTimeMillis()).toString();
		try {
			c1.setTime(df.parse(time1));
			c2.setTime(df.parse(time2));
		} catch (ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);
		return result;
	}
	
	
	
	 /**
	 * 判断当前日期转换成星期后是否在字符串内
	 * @param weekStr 字符串
	 * @return 如果在则返回true,否则返回false
	 */
	public  static Boolean isInWeekStr(String weekStr) {
		Calendar c = Calendar.getInstance();
        return weekStr.indexOf(String.valueOf(c.get(Calendar.DAY_OF_WEEK))) != -1;
	}
	

	/**
	 * 将数字转换为对应的星期
	 * @param weekForShow
	 * @return weekForShow
	 */
	public static String weekChange(String weekForShow){
		weekForShow=weekForShow.replace('1', '日');
		weekForShow=weekForShow.replace('2', '一');
		weekForShow=weekForShow.replace('3', '二');
		weekForShow=weekForShow.replace('4', '三');
		weekForShow=weekForShow.replace('5', '四');
		weekForShow=weekForShow.replace('6', '五');
		weekForShow=weekForShow.replace('7', '六');
		return weekForShow;
	}
	
	/** * 获取指定日期是星期几
	  * 参数为null时表示获取当前日期是星期几
	  * @param date
	  * @return
	*/
	public static int getWeekOfDate(Date date) {      
	    int[] weekOfDays = {7, 1, 2,3,4,5,6};       
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	         calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return weekOfDays[w];    
	}
	
	
	public static boolean checkTimeCross(Date startDate1,Date endDate1,Date startDate2,Date endDate2){
		return ((startDate1.getTime() >= startDate2.getTime())   
                && startDate1.getTime() < endDate2.getTime())  
        ||  
        ((startDate1.getTime() > startDate2.getTime())   
                && startDate1.getTime() <= endDate2.getTime())  
        ||  
        ((startDate2.getTime() >= startDate1.getTime())   
                && startDate2.getTime() < endDate1.getTime())  
        ||  
        ((startDate2.getTime() > startDate1.getTime())   
                && startDate2.getTime() <= endDate1.getTime());
	}
	
	public static Interval calculateCorssTime(Date datetime1,Date datetime2,Date datetime3,Date datetime4) {
		Integer corssTime = 0;
		DateTime time1 = new DateTime(datetime1);
		DateTime time2 = new DateTime(datetime2);
		DateTime time3 = new DateTime(datetime3);
		DateTime time4 = new DateTime(datetime4);
		Interval interval1 = new Interval(time1, time2);
		Interval interval2 = new Interval(time3, time4);
		
		Interval between = interval1.overlap(interval2);
//		if(between!=null) {
//			Duration duration = new Duration(between.getStart(), between.getEnd());
//			corssTime = (int)duration.getStandardSeconds();
//		}
		return between;
	}
	
	public static Date getDateByTwoDateTime(Date datetime1,Date datetime2) {
		return parse(format(datetime1, patterns_masks[4])
				+" "+format(datetime2, patterns_masks[12]), patterns_masks[2]);
	}
	
	public static Date getDateTimeByZeroTime(Date datetime) {
		return parse(format(datetime, patterns_masks[4])+" 00:00:00", patterns_masks[2]);
	}
	
	public static Date getDateTimeByEndTime(Date datetime) {
		return parse(format(datetime, patterns_masks[4])+" 23:59:59", patterns_masks[2]);
	}
	
	public static boolean isSameDay(Date datetime1,Date datetime2) {
		return (getDateTimeByZeroTime(datetime1).getTime()==getDateTimeByZeroTime(datetime2).getTime());
	}


	public static Date getYear(Date startTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		int yearInt = cal.get(Calendar.YEAR);
		String yearStr = yearInt+"-01-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date year = null;
		try {
			year =  sdf.parse(yearStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return year;
	}

	public static Date getNextYear(Date startTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		int yearInt = cal.get(Calendar.YEAR)+1;
		String yearStr = yearInt+"-01-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date year = null;
		try {
			year =  sdf.parse(yearStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return year;
	}

	public static void main(String[] args) {
		System.out.println(getYear(new Date()));
	}

	public static int getIntYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static Date getDateByYear(int yearint) {
		String yearStr = yearint+"-01-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date year = null;
		try {
			year =  sdf.parse(yearStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return year;
	}

	public static Date getNextMonth(Date startTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		int month = cal.get(Calendar.MONTH) + 2;
		int year = cal.get(Calendar.YEAR);
		String yearStr = year+"-"+(month>9?String.valueOf(month):"0"+month)+"-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date monthDate = null;
		try {
			monthDate =  sdf.parse(yearStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monthDate;
	}

	public static Date getMonth(Date startTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String yearStr = year+"-"+(month>9?String.valueOf(month):"0"+month)+"-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date monthDate = null;
		try {
			monthDate =  sdf.parse(yearStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monthDate;
	}

	public static String dateToStr(Date startTime) {
		String str="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			str =  sdf.format(startTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String dateToStr1(Date startTime) {
		String str="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			str =  sdf.format(startTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static Date addDay(Date date,int day) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.DAY_OF_MONTH, day);
		return cl.getTime();
	}


    public static String defaultFormat(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patterns_masks[2]);
		return dateFormat.format(date);
	}

    public static Date strToDate(String date_str) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date =  sdf.parse(date_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
    }
}