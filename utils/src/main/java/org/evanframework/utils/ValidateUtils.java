package org.evanframework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p>
 * 
 * @author shen.wei
 * @version 2011-7-5 上午11:43:05
 */
public class ValidateUtils {
	
	/**
	 * 整数
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-7-18 下午05:10:14 <br>
	 * @param str
	 *
	 */
	public static boolean isInteger(String str) {
		return regexValidate("^\\d{1,10}$",str);
	}
	
	/**
	 * 正则校验
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-7-18 下午05:10:28 <br>
	 * @param regex
	 * @param validateStr
	 *
	 */
	public static boolean regexValidate(String regex,String validateStr){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(validateStr);

		return matcher.find();
	}
	
	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 *
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 *
	 */
	public static boolean checkMobile(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(14[^4,\\D])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
