package com.ancun.core.utils;


/**
 * 扩展字符窜工具
 * <p> 
 * @author  <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-3-29 上午11:03:29
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/**
	 * 
	 * 取文本长度,中文算两个
	 * 
	 * @param text
	 *
	 *
	 */
	public static int lengthForChinese(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}
	
	/**
	 * 
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-9-30 下午04:30:20 <br>
	 * @param text
	 *
	 */
	public static String convertCtrlNAndBlankToHtml(String text){
		if(isNotBlank(text)){
			text = text.replace("\n", "<br/>");
		}
		return text;
	}
	
	/**
	 * 
	* @param text
	* @param len
	*
	* <p>
	* author: <a href="mailto:maochaowu@ancun.com">chaowu.mao</a><br>
	* create at: 2014年10月11日 下午3:16:06
	 */
	public static String subStr(String text,int len) {
		int length = text.length();
		if(length<len) 
			return text.substring(0,length);
		else	
			return text.substring(0,len)+"...";
	}
}
