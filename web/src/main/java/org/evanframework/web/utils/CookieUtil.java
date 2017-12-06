package org.evanframework.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.evanframework.utils.JsonUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * cookie工具
 * 
 * @author shen.wei
 * @version Date: 2010-10-14 下午01:01:39
 * @since 1.0
 * @deprecated 2.1
 */
@Deprecated
public class CookieUtil {
	/**
	 * cookie名称的前缀
	 */
	public final static String COOKIE_NAME_PREFIX = "";

	/**
	 * 读取Cookie,返回加入该Cookie的value
	 * <p>
	 * author: shen.wei<br>
	 * version: 2010-12-10 上午10:24:56 <br>
	 * 
	 * @param cookieName
	 * @param request
	 */
	public static String read(String cookieName, HttpServletRequest request, String webencoding) {
		Cookie[] cookies = request.getCookies();
		String returnV = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookie.getName(), COOKIE_NAME_PREFIX + cookieName)) {// 判断是否存在cookie
					returnV = cookie.getValue();
					try {
						returnV = URLDecoder.decode(returnV, webencoding);
					} catch (UnsupportedEncodingException e) {
						throw new IllegalStateException(e);
					}
					break;
				}
			}
		}
		return returnV;
	}

	/**
	 * 读取Cookie,将该Cookie的value转换层对象在返回
	 * <p>
	 * author: shen.wei<br>
	 * version: 2010-12-10 上午10:24:45 <br>
	 * 
	 * @param c
	 * @param cookieName
	 * @param request
	 */
	public static <T> T read(Class<T> c, String cookieName, HttpServletRequest request, String webencoding) {
		String cookieValue = read(cookieName, request, webencoding);
		String json = null;
		T returnO = null;
		if (cookieValue != null) {
			try {
				json = URLDecoder.decode(cookieValue, webencoding);
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException(e);
			}
			returnO = JsonUtils.jsonToBean(json, c);
		}

		return returnO;
	}

	/**
	 * 
	 * <p>
	 * author: shen.wei<br>
	 * version: 2010-12-10 上午10:26:35 <br>
	 * 
	 * @param cookieName
	 * @param request
	 * @param response
	 */
	public static void remove(String cookieName, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(COOKIE_NAME_PREFIX + cookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 将对象保存到cookie
	 * <p>
	 * author: shen.wei<br>
	 * version: 2010-12-10 上午10:22:31 <br>
	 * 
	 * @param cookieName
	 * @param obj
	 * @param expiry
	 *            有效期
	 * @param webencoding
	 *            有编码
	 * @param response
	 */
	public static void save(String cookieName, Object obj, int expiry, HttpServletResponse response, String webencoding) {
		String value = null;
		try {
			if (obj instanceof String || obj instanceof Integer || obj instanceof Long || obj instanceof BigDecimal) {
				value = obj.toString();
			} else {
				value = JsonUtils.beanToJSON(obj);
			}
			value = URLEncoder.encode(value, webencoding);
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException("String [" + value + "] encode error", e);
		}

		Cookie cookie = new Cookie(COOKIE_NAME_PREFIX + cookieName, value);
		cookie.setMaxAge(expiry);
		cookie.setPath("/");		

		response.addCookie(cookie);
	}
}
