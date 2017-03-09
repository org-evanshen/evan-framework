package com.ancun.core.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * web工具
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version Date: 2010-9-13 下午01:39:40
 * @since
 */
@SuppressWarnings({ "unchecked" })
public class WebUtils extends org.springframework.web.util.WebUtils {

	private final static Log log = LogFactory.getLog(WebUtils.class);

	/**
	 * 重载Spring WebUtils中的函数,作用如函数名所示 加入泛型转换,改变输入参数为request 而不是session
	 * 
	 * @param name
	 *            session中变量名称
	 * @param clazz
	 *            session中变量的类型
	 */
	public static <T> T getOrCreateSessionAttribute(HttpServletRequest request, String name, Class<T> clazz) {
		return (T) org.springframework.web.util.WebUtils.getOrCreateSessionAttribute(request.getSession(), name, clazz);
	}

}
