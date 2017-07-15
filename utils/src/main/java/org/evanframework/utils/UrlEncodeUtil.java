package org.evanframework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URI编码和解码工具
 * 
 * <p>
 * create at 2015年5月27日 下午3:28:34
 * @author shen.wei
 * @version %I%, %G%
 *
 */
public class UrlEncodeUtil {
	private  static final String CHARSET = "UTF-8";
	
	/**
	 * URL编码
	 */
	public static String encode(String str) {
		return encode(str, CHARSET);
	}

	/**
	 * URL编码
	 */
	public static String encode(String str, String enc)	{
		if(StringUtils.isBlank(enc)){
			enc = CHARSET;
		}		
		String returnV = null;
		if(StringUtils.isNotBlank(str)){
			try {
				returnV = URLEncoder.encode(str, enc);
			} catch (UnsupportedEncodingException e) {				
				throw new UnsupportedOperationException("Encode[" + enc + "] is not not supporte", e);
			}
		}else{
			returnV = "";
		}

		return returnV;
	}

	/**
	 * URL编码(解码)
	 * 
	 */
	public static String decode(String str)  {
		return decode(str, CHARSET);
	}

	/**
	 * URL编码(解码)
	 * 
	 */
	public static String decode(String str, String enc) {
		if(StringUtils.isBlank(enc)){
			enc = CHARSET;
		}
		
		String returnV = null;
		if(StringUtils.isNotBlank(str)){
			try {
				returnV = URLDecoder.decode(str, enc);
			} catch (UnsupportedEncodingException e) {
				throw new UnsupportedOperationException("Encode[" + enc + "] is not not supporte", e);
			}
		}else{
			returnV = "";
		}

		return returnV;
	}
}
