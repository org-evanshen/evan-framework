package com.ancun.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version Date: 2010-10-16 上午11:23:38
 * @since
 */
public class DateUtils {
	
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	/**
	 * 获得默认的 date pattern
	 *  String
	 */
	public static String getDatePattern() {
		return FORMAT_SHORT;
	}

	/**
	 * 根据预设格式返回当前日期
	 *  String
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 根据用户格式返回当前日期
	 *  String
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期
	 * 
	 * @param date
	 *  String
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用用户格式格式化日期
	 * @param date 日期
	 * @param pattern 日期格式
	 *  String
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 使用预设格式提取字符串日期
	 * @param strDate 日期字符串
	 *  Date
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用用户格式提取字符串日期
	 * @param strDate 日期字符串
	 * @param pattern 日期格式
	 *  Date
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 在日期上增加数个整月
	 * @param date 日期
	 * @param n 要增加的月数
	 *  Date
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 * @param date 日期
	 * @param n 要增加的天数
	 *  Date
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加小时数
	 * @param date 日期
	 * @param n 要增加的小时数
	 *  Date
	 */
	public static Date addHour(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, n);
		return cal.getTime();
	}

	/**
	 *
	 * @param bigDate
	 * @param date
	 * @return
	 */
	public static int diffHour(Date bigDate, Date date) {
		return (int) ((bigDate.getTime() - date.getTime())/(1000*3600));
	}

	/**
	 * 获取时间戳
	 *  String
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 获取日期年份
	 * @param date 日期
	 *  String
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 按默认格式的字符串距离今天的天数
	 * @param date 日期字符串
	 *  int
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 * @param date 日期字符串
	 * @param format 日期格式
	 *  int
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 验证是否是按默认格式的时间格式
	 *  boolean
	 */
	public static boolean valid(String str) {
		if (str != null && str.trim().length() > 0) {
			boolean convertSuccess = true;
			SimpleDateFormat format = new SimpleDateFormat(getDatePattern());
			try {
				format.setLenient(false);
				format.parse(str);
			} catch (ParseException e) {
				convertSuccess = false;
			}
			return convertSuccess;
		}
		return false;
	}
	
	/**
	 * 验证是否是按用户格式的时间格式
	 *  boolean
	 */
	public static boolean valid(String str, String format) {
		if (str != null && str.trim().length() > 0) {
			boolean convertSuccess = true;
			SimpleDateFormat df = new SimpleDateFormat(format);
			try {
				df.setLenient(false);
				df.parse(str);
			} catch (ParseException e) {
				convertSuccess = false;
			}
			return convertSuccess;
		}
		return false;
	}

	/**
	 * 获取当前Date
	 *  date
	 */
	public static Date getCurrentDate() {
		Date today = new Date();
		return today;
	}

	/**
	 * 获取当前Calendar
	 *  当前Calendar
	 */
	public static Calendar getCurrentCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}

	/**
	 * @param date
	 *  Calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal;
	}

	/**
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-6 下午03:32:25 <br>
	 * @param formatString
	 * @param strDate
	 * @throws ParseException 
	 */
	public static Date convertStringToDate(String formatString, String strDate) throws ParseException{
		Assert.isTrue(StringUtils.isNotBlank(strDate), "arg strDate[" + strDate + "] format is wrong");
		Assert.isTrue(StringUtils.isNotBlank(formatString),"arg strDate[" + strDate + "] format is wrong");
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		Date date = null;
		date = df.parse(strDate);
		return (date);
	}


	/**
	 * 根据当前时间得到一个月前的时间 只包括 年月日
	*
	* <p>
	* author: xuyuanyang<br>
	* create at: 2014年4月16日上午12:34:54
	 */
	public static String getDateBefore1M(){
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		int date = now.get(Calendar.DAY_OF_MONTH);
		int[] smallMonths = {2,4,6,9,11};
		if(month-1<=0){ //跨年
			year = year -1;
			month = 12;
		}
		if((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0) && month==3){ //如果1月前是闰年的2月，那么该月的最大天数是28
			month=2;
			date = date>28 ? 28 : date;
		}else if(Arrays.asList(smallMonths).contains(month-1)){//如果1月前是小月，那么最大的天数是30
			month=month-1;
			date = date>30 ? 30 : date;
		}else{
			month = month-1;
		}
		String months = String.valueOf(month).length()==1 ? "0"+month : ""+ month;
		String dates = String.valueOf(date).length()==1 ? "0"+date : ""+ date; 
		return year+"-"+months+"-"+dates;
	}
	
	/**
	 * 根据传入的月份获取月初和月末时间
	 * create at 2014年8月25日 上午10:13:57
	 *
	 */
	public static String getMaxMonthDate(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	public static Date getStartOfDay(Date date) {
//        if(date == null) {
//            return null;
//        }
//        DateTime dateTime = new DateTime(date);
//        return dateTime.withTimeAtStartOfDay().toDate();
		throw  new UnsupportedOperationException();
    }
	
	public static String dateToString(Date date,String format) {
		String strTemp = new SimpleDateFormat(format).format(date);
		strTemp = strTemp.replace("-", "");
		strTemp = strTemp.replace(".", "");
		strTemp = strTemp.replace(" ", "");
		strTemp = strTemp.replace(":", "");
		return strTemp;
	}
	
	public static String stringToString(String str) {
		str = str.replace("-", "");
		str = str.replace(".", "");
		str = str.replace(" ", "");
		str = str.replace(":", "");
		str = str.replace("年", "");
		str = str.replace("月", "");
		str = str.replace("日", "");
		return str;
	}
}
