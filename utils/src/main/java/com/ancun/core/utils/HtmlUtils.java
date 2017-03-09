package com.ancun.core.utils;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.utils.html.parse.HTMLParser;

/**
 * @author fish
 * @version $Id: HtmlUtils.java,v 1.2 2009/05/22 06:20:07 fish Exp $
 */
public final class HtmlUtils {

	private static final Logger logger = LoggerFactory.getLogger(HtmlUtils.class);

	private static final Pattern scriptTag = Pattern
			.compile("(?i)(?s)(<script.+?</script>)|(on.+?=)");

	private static final String emptyString = "";

	/**
	 *
	 * @param s
	 *
	 */
	public static final String escapeForbiddenHtml(String s) {
		return escapeScriptTag(s);
	}

	/**
	 *
	 * @param s
	 *
	 */
	public static final String escapeScriptTag(String s) {
		if (s == null) {
			return null;
		}
		Matcher m = scriptTag.matcher(s);
		return m.replaceAll(emptyString);
	}

	/**
	 *
	 * @param s
	 *
	 */
	public static final String parseHtml(String s) {
		if (s == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			Reader r = new StringReader(s);
			HTMLParser parser = new HTMLParser(r);
			LineNumberReader reader = new LineNumberReader(parser.getReader());
			for (String l = reader.readLine(); l != null; l = reader.readLine()) {
				sb.append(l);
			}
			r.close();
		} catch (IOException e) {
			logger.error("error than parse html string", e);
		}
		return sb.toString();
	}

	/**
	 *
	 * @param s
	 * @param maxLength
	 *  s.length <= maxLength
	 */
	public static final String parseHtml(String s, int maxLength) {
		if (maxLength <= 0) {
			throw new IllegalArgumentException(
					"maxLength must greate than zero");
		}
		if (s == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		try {
			Reader r = new StringReader(s);
			HTMLParser parser = new HTMLParser(r);
			LineNumberReader reader = new LineNumberReader(parser.getReader());
			for (String l = reader.readLine(); l != null; l = reader.readLine()) {
				sb.append(l);
				if (sb.length() >= maxLength) {
					break;
				}
			}
			r.close();
		} catch (IOException e) {
			logger.error("error than parse html string", e);
		}
		if (sb.length() >= maxLength) {
			return sb.substring(0, maxLength);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @param length
	 */
	public static String split(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		if(length<1){
			length = Integer.MAX_VALUE;
		}
		// 去掉所有html元素,   
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}
}
