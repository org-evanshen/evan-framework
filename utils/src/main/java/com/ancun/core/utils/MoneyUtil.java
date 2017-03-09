package com.ancun.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <p> 
 * @author  <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-8-6 下午1:47:22
 */
public class MoneyUtil {

	//private static final int DEF_DIV_SCALE = 2;

	public MoneyUtil() {
	}

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public static double add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue();
	}

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	public static double sub(String v1, String v2) {
		if (v1 == null || v1.equals("")) {
			v1 = "0";
		}
		if (v2 == null || v2.equals("")) {
			v2 = "0";
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).doubleValue();
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	public static double mul(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double v1, double v2) {
		return div(v1, v2, 2);
	}

	public static double div(String v1, String v2) {
		if (v1 == null || v1.equals("")) {
			v1 = "0";
		}
		if (v2 == null || v2.equals("")) {
			v2 = "0";
		}
		return div(Double.parseDouble(v1), Double.parseDouble(v2), 2);
	}

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.divide(b2, scale, 4).doubleValue();
		}
	}

	public static double div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.divide(b2, scale, 4).doubleValue();
		}
	}

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else {
			BigDecimal b = new BigDecimal(Double.toString(v));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, 4).doubleValue();
		}
	}

	public static double round(String v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else {
			BigDecimal b = new BigDecimal(v);
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, 4).doubleValue();
		}
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param strMoney
	 *
	 */
	public static String getFormatMoney(String strMoney) {
		return getFormatMoney(strMoney, null);
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param strMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 *
	 */
	public static String getFormatMoney(String strMoney, String formatStr) {
		return getFormatMoney(strMoney, formatStr, Locale.US);
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param strMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 * @param locale
	 *            使用哪国的数字格式,如美国的千分位来表示数字
	 *
	 *
	 */
	public static String getFormatMoney(String strMoney, String formatStr,
			Locale locale) {
		Double doubleMoney;
		if (strMoney == null || strMoney.trim().equals("")) {
			strMoney = "0";
		}
		try {
			doubleMoney = Double.valueOf(strMoney);
		} catch (Exception e) {
			return strMoney;
		}
		return getFormatMoney(doubleMoney, formatStr, locale);
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param intMoney
	 *
		*
	 */
	public static String getFormatMoney(Integer intMoney) {
		return getFormatMoney(intMoney, null);
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param intMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 *
	 */
	public static String getFormatMoney(Integer intMoney, String formatStr) {
		return getFormatMoney(intMoney, formatStr, Locale.US);
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param intMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 * @param locale
	 *            使用哪国的数字格式,如美国的千分位来表示数字
	 *
	 */
	public static String getFormatMoney(Integer intMoney, String formatStr,
			Locale locale) {
		if (intMoney == null) {
			intMoney = Integer.parseInt("0");
		}
		if (null == formatStr || formatStr.trim().equals("")) {
			formatStr = "￥#,##0.00";
		}
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
		df.applyPattern(formatStr);// 设置应用金额格式
		return df.format(intMoney);
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param doubleMoney
	 *
	 */
	public static String getFormatMoney(Double doubleMoney) {
		return getFormatMoney(doubleMoney, null);
	}

	/**
	 * 转换成金钱的字符串,带格式
	 *
	 * @param doubleMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 *
	 */
	public static String getFormatMoney(Double doubleMoney, String formatStr) {
		if (doubleMoney == null) {
			doubleMoney = Double.valueOf(0);
		}
		return getFormatMoney(doubleMoney, formatStr, Locale.US);
	}

	/**
	 * 转换成金钱的字符串,带格式
	 *
	 * @param doubleMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 * @param locale
	 *            使用哪国的数字格式,如美国的千分位来表示数字
	 *
	 */
	public static String getFormatMoney(Double doubleMoney, String formatStr,
			Locale locale) {
		if (doubleMoney == null) {
			doubleMoney = Double.valueOf(0);
		}
		if (null == formatStr || formatStr.trim().equals("")) {
			formatStr = "￥#,##0.00";
		}
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
		df.applyPattern(formatStr);// 设置应用金额格式
		return df.format(doubleMoney);
	}

	/**
	 * 转换成金钱的字符串,带格式(两位小数,千分位)
	 *
	 * @param bigDecimalMoney
	 *
	 */
	public static String getFormatMoney(BigDecimal bigDecimalMoney) {
		return getFormatMoney(bigDecimalMoney, null);
	}

	/**
	 * 转换成金钱的字符串,带格式
	 *
	 * @param bigDecimalMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 */
	public static String getFormatMoney(BigDecimal bigDecimalMoney,
			String formatStr) {
		return getFormatMoney(bigDecimalMoney, formatStr, Locale.US);
	}

	/**
	 * 转换成金钱的字符串,带格式
	 *
	 * @param bigDecimalMoney
	 * @param formatStr
	 *            格式(例如: ###.00 元),
	 *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
	 *            ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
	 * @param locale
	 *            使用哪国的数字格式,如美国的千分位来表示数字
	 *
	 */
	public static String getFormatMoney(BigDecimal bigDecimalMoney,
			String formatStr, Locale locale) {
		if (bigDecimalMoney == null) {
			bigDecimalMoney = BigDecimal.valueOf(0.00);
		}
		if (null == formatStr || formatStr.trim().equals("")) {
			formatStr = "￥#,##0.00";
		}
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
		df.applyPattern(formatStr);// 设置应用金额格式
		return df.format(bigDecimalMoney);
	}

	/**
	 * 价格基金转换，除100，取整数
	 */
	public static int getMoneyPoint(double amount1, double amount2) {
		int point = 0;
		if (amount1 + amount2 > 0.0) {
			point = (int) ((amount1 + amount2) / 100);
		}
		return point;
	}

	public static int getMoneyPointGive(double amount1, double amount2,
			double amount3) {
		int point = 0;
		if ((amount1 + amount2 - amount3) > 0.0) {
			point = (int) ((amount1 + amount2 - amount3) / 100);
		}
		return point;
	}

	/**
	 * 积分转换成价格，除100，取2位小数
	 */
	public static double getPointToMoney(double amount1) {
		double point = 0.0;
		if (amount1 > 0.0) {
			point = ((amount1) / 100);
		}
		return point;
	}

	/**
	 * 积分计算2数值相加整数结果
	 */
	public static int integerAdd(double point1, double point2) {
		int point = 0;
		if ((point1 + point2) > 0.0) {
			point = (int) (point1 + point2);
		}
		return point;
	}

	/**
	 * 积分计算2数值相乘整数结果
	 */
	public static int integerMul(double point1, int point2) {
		int point = 0;
		String acount = "" + point1 * point2;
		if ((point1 * point2) > 0.0) {
			BigDecimal dd = new BigDecimal(acount).setScale(0,
					BigDecimal.ROUND_HALF_UP);
			point = dd.intValue();
		}
		return point;
	}

	/**
	 * 字符转换成浮点
	 *
	 * @param money
	 *
	 */
	public static double StringToDouble(String money) {
		return Double.parseDouble(money) / 100;
	}

	/**
	 * 浮点金额显示
	 *
	 * @param money
	 *
	 */
	public static double DoubleMoney(Double money) {
		if (null == money) {
			return 0;
		}
		return money / 100;
	}

	/*
	 * 自助缴费使用，金额/100
	 */
	public static String getFormatMoneyByPay(Object money) {
		if (money == null || "".equals(money)) {
			money = "0";
		}
		Double temp = Double.parseDouble(money.toString()) / 100;
		return getFormatMoney(temp.toString(), "#,##0.00");
	}

	/*
	 * 自助缴费使用，金额/100
	 */
	public static String getFormatMoneyByPay2(Object money) {
		if (money == null || "".equals(money)) {
			money = "0";
		}
		Double temp = Double.parseDouble(money.toString()) * 100;
		return getFormatMoney(temp.toString(), "#,##0.00");
	}
	
	/**
	 * 类目首页最近浏览商品
	 *
	 */
	public static String getFormatMoneyCategory(Double money) {
		String formatMoney=getFormatMoney(money, "#,##0.00");
		String[] array=StringUtils.split(formatMoney, ".");
		if(array[1].equals("00")){
			return getFormatMoney(money, "#,##0");
		}else if(array[1].endsWith("0")){
			return getFormatMoney(money, "#,##0.0");
		}else{
			return formatMoney;
		}
	}
	
	/**
	 * 类目首页最近浏览商品
	 *
	 */
	public static String getFormatMoneyCategory(String money) {
		try{
			return getFormatMoneyCategory(Double.parseDouble(money));
		}catch (Exception e){
			return "";
		}
	}
	
	public static void main(String args[]) {
//		System.out.println(mul(add(5D, 663.85000000000002D), 1.01D));
		System.out.println(getFormatMoneyCategory(2.20));

	}
	
	/**
	 * 计算拍卖商品冻结买家保证金 固定比例 
	 * @param bids  proportion以%为单位
	 *
	 */
	public static double getAuctionBidsMargin(double bids,double proportion) {
		try{
    		BigDecimal marginBig = new BigDecimal(""+(bids*proportion/100)).setScale(0, BigDecimal.ROUND_HALF_UP);
    		int margin = marginBig.intValue();
    		if(margin<=5){
    			return 5;
    		}
    		return margin;
		}catch(Exception e){
		    return 5;
		}
	}

	
	
}