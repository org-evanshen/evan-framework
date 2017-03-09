package com.ancun.core.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * Json 工具
 * 
 * <p>
 * create at 2014年10月30日 下午5:06:19
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public class JsonUtils {

	/**
	 * 将javaBean转换成Json字符串
	 * 
	 * @param o
	 *  Json字符串
	 */
	public static String beanToJSON(Object o) {
		if (o == null) {
			return null;
		}
		return JSON.toJSONString(o);
	}

	/**
	 * 将Json字符串转换成javaBean
	 * 
	 * @param json
	 * @param c
	 *  javaBean
	 */
	public static <T> T jsonToBean(String json, Class<T> c) {
		if (StringUtils.isBlank(json)) {
			return null;
		}

		return JSON.parseObject(json, c);
	}
}
