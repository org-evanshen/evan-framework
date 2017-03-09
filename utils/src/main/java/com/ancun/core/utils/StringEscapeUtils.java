package com.ancun.core.utils;

import java.util.regex.Pattern;

public class StringEscapeUtils extends org.apache.commons.lang3.StringEscapeUtils {
	public static String escapeHtml(String source) {
		if (source == null) {
			return "";
		}
		String html = "";
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
			case '<':
				buffer.append("&lt;");
				break;
			case '>':
				buffer.append("&gt;");
				break;
			case '&':
				buffer.append("&amp;");
				break;
			case '"':
				buffer.append("&quot;");
				break;
			case 10:
			case 13:
				break;
			default:
				buffer.append(c);
			}
		}
		html = buffer.toString();
		return html;
	}

	public static String extractText(String inputString) {
		String htmlStr = inputString;
		if (htmlStr == null) {
			return htmlStr;
		}
		String textStr = "";
		Pattern p_script;
		java.util.regex.Matcher m_script;
		Pattern p_style;
		java.util.regex.Matcher m_style;
		Pattern p_html;
		java.util.regex.Matcher m_html;
		Pattern p_html1;
		java.util.regex.Matcher m_html1;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 瀹氫箟script鐨勬鍒欒〃杈惧紡{鎴?script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 瀹氫箟style鐨勬鍒欒〃杈惧紡{鎴?style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 瀹氫箟HTML鏍囩鐨勬鍒欒〃杈惧紡
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 杩囨护script鏍囩

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 杩囨护style鏍囩

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 杩囨护html鏍囩

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 杩囨护html鏍囩

			Pattern space = Pattern.compile("&nbsp;", Pattern.CASE_INSENSITIVE);
			java.util.regex.Matcher m_space = space.matcher(htmlStr);
			htmlStr = m_space.replaceAll("");

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 杩斿洖鏂囨湰瀛楃涓?
	}
}
