package com.ancun.core.utils;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 路径工具类
 * 
 * <p>
 * create at 2015年5月19日 下午12:23:08
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 * @since 1.0.0
 */
public class PathUtils {

	private static PathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 路径匹配
	 * 
	 * @param lookupPath
	 * @param excludes
	 *  <p>
	 *         author: ShenWei<br>
	 *         create at 2015年5月19日 下午12:23:59
	 */
	public static boolean matches(String lookupPath, Set<String> excludes) {
		if (excludes != null) {
			for (String pattern : excludes) {
				if (pathMatcher.match(pattern, lookupPath)) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 拼接路径
	 * 
	 * @param paths
	 *  <p>
	 *         author: ShenWei<br>
	 *         create at 2015年5月19日 下午12:31:33 eg
	 * 
	 *         <pre>
	 * concat('a','b','c') = /a/b/c;
	 * concat('a/','/b','c') = /a/b/c;
	 * </pre>
	 * 
	 */
	public static String concat(String... paths) {
		StringBuilder sb = new StringBuilder(128);
		for (String path : paths) {
			if (StringUtils.isNotBlank(path)) {
				if (!StringUtils.startsWith(path, "/") && !StringUtils.contains(path, ":")) {
					sb.append("/");
				}
				sb.append(path);
				char last = sb.charAt(sb.length()-1);
				if(StringUtils.equals("/", String.valueOf(last))){
					sb.deleteCharAt(sb.length()-1);
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 路径约定，所有文件夹路径末尾都要加"/",那么在路径相加的时候，就不会出现没有"/"而字符串直接相加的情况
	 * 路径相加都调用此方法，消除"//"即可
	* @param paths
	*
	* <p>
	* author: xyy
	* create at 2015年5月22日 上午11:10:49
	 */
	public static String concatDeleteRepeat(String... paths){
		StringBuilder sb = new StringBuilder(128);
		for (String path : paths) {
			if (StringUtils.isNotBlank(path)) {
				if (path.lastIndexOf("/") + 1 != path.length())
					sb.append(path).append("/");
				else
					sb.append(path);
			}
		}
		String result = sb.toString();
		result = result.replace("//", "/");
		result = result.substring(0,result.length()-1);
		return result;
	}
}