package org.evanframework.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期工具
 *
 * @author shen.wei
 * @version Date: 2010-10-16 上午11:23:38
 */
public class DateUtils {

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static final String FORMAT_SHORT_STRING = "yyyy-MM-dd";

    public static final SimpleDateFormat FORMAT_SHORT = new SimpleDateFormat(FORMAT_SHORT_STRING);
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static final String FORMAT_LONG_STRING = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String FORMAT_FULL_STRING = "yyyy-MM-dd HH:mm:ss.S";

    public static final SimpleDateFormat FORMAT_FULL = new SimpleDateFormat(FORMAT_FULL_STRING);
    /**
     * 中文简写 如：2010年12月01日
     */
    public static final String FORMAT_SHORT_STRING_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static final String FORMAT_LONG_STRING_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static final String FORMAT_FULL_STRING_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static final ConcurrentHashMap<String, DateFormat> formatCache = new ConcurrentHashMap<String, DateFormat>();

    /**
     * 获得默认的 date pattern
     * String
     */
    public static String getDatePattern() {
        return FORMAT_SHORT_STRING;
    }

    /**
     * 根据预设格式返回当前日期
     * String
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     * String
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date String
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     *                String
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = getDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat df = (SimpleDateFormat) formatCache.get(pattern);
        if (df == null) {
            df = new SimpleDateFormat(pattern);
            formatCache.put(pattern, df);
        }
        return df;
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     *                Date
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     *                Date
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = getDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            throw  new DateParseException("输入日期[" + strDate + "]日期格式不正确,正确的格式为[" + pattern + "]", e);
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     *             Date
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     *             Date
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加小时数
     *
     * @param date 日期
     * @param n    要增加的小时数
     *             Date
     */
    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }

    /**
     * @param bigDate
     * @param date
     * @return
     */
    public static int diffHour(Date bigDate, Date date) {
        return (int) ((bigDate.getTime() - date.getTime()) / (1000 * 3600));
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     *             String
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     *             int
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
     *
     * @param date   日期字符串
     * @param format 日期格式
     *               int
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
     * boolean
     */
    public static boolean valid(String str) {
        if (str != null && str.trim().length() > 0) {
            boolean convertSuccess = true;
            try {
                FORMAT_SHORT.setLenient(false);
                FORMAT_SHORT.parse(str);
            } catch (ParseException e) {
                convertSuccess = false;
            }
            return convertSuccess;
        }
        return false;
    }

    /**
     * 验证是否是按用户格式的时间格式
     * boolean
     */
    public static boolean valid(String str, String format) {
        if (str != null && str.trim().length() > 0) {
            boolean convertSuccess = true;
            SimpleDateFormat df = getDateFormat(format);
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
     * 获取当前Calendar
     * 当前Calendar
     */
    public static Calendar getCurrentCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    /**
     * @param date Calendar
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }


    /**
     * 根据当前时间得到一个月前的时间 只包括 年月日
     * <p>
     * <p>
     * author: xuyuanyang<br>
     * create at: 2014年4月16日上午12:34:54
     */
    public static String getDateBefore1M() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DAY_OF_MONTH);
        int[] smallMonths = {2, 4, 6, 9, 11};
        if (month - 1 <= 0) { //跨年
            year = year - 1;
            month = 12;
        }
        if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0) && month == 3) { //如果1月前是闰年的2月，那么该月的最大天数是28
            month = 2;
            date = date > 28 ? 28 : date;
        } else if (Arrays.asList(smallMonths).contains(month - 1)) {//如果1月前是小月，那么最大的天数是30
            month = month - 1;
            date = date > 30 ? 30 : date;
        } else {
            month = month - 1;
        }
        String months = String.valueOf(month).length() == 1 ? "0" + month : "" + month;
        String dates = String.valueOf(date).length() == 1 ? "0" + date : "" + date;
        return year + "-" + months + "-" + dates;
    }

    /**
     * 根据传入的月份获取月初和月末时间
     * create at 2014年8月25日 上午10:13:57
     */
    public static String getMaxMonthDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = getDateFormat("yyyyMMdd");
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
        throw new UnsupportedOperationException();
    }
}
