package com.ancun.core.utils;

/**
 * 地区工具
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-11-14 下午2:00:12
 */
public class RegionUtils {

	/**
	 * 根据地区代码获取其上级代码<br>
	 * 
	 * <pre>
	 * RegionUtils.getParentCode("330000")      = ""
	 * RegionUtils.getParentCode("330100")      = "330000"
	 * RegionUtils.getParentCode("330102")      = "3301"
	 * </pre>
	 * 
	 * <p>
	 * <a href="mailto:shenwei@ancun.com">ShenWei</a> create at 2013-11-5 下午2:05:45
	 * </p>
	 * 
	 * @param code
	 *
	 */
	public static String getParentCode(String code) {
		String returnV = null;
		if (StringUtils.isNotBlank(code)) {
			code = fix6(code);
			if (code.endsWith("0000")) {
				returnV = "";
			} else if (code.endsWith("00")) {
				returnV = StringUtils.left(code, 2) + "0000";
			} else {
				returnV = StringUtils.left(code, 4) + "00";
			}
		}
		return returnV;
	}

	/**
	 * 
	 * <p>
	 * <a href="mailto:shenwei@ancun.com">ShenWei</a> create at 2013-11-5 下午1:59:15
	 * </p>
	 * 
	 * @param code
	 *
	 */
	private static String fix6(String code) {
		if (code.length() > 6) {
			code = StringUtils.left(code, 6);
		} else if (code.length() < 6) {
			code = StringUtils.rightPad(code, 6, '0');
		}
		return code;
	}

	/**
	 * <pre>
	 * RegionUtils.getParentCode("330000")      = "33"
	 * RegionUtils.getParentCode("330100")      = "3301"
	 * RegionUtils.getParentCode("330102")      = "330102"
	 * </pre>
	 */
	public static String getParentCodePrefix(String parentCode) {
		String returnV = null;

		if (StringUtils.isNotBlank(parentCode)) {
			parentCode = fix6(parentCode);

			if (parentCode.endsWith("0000")) {
				returnV = StringUtils.left(parentCode, 2);
			} else if (parentCode.endsWith("00")) {
				returnV = StringUtils.left(parentCode, 4);
			} else {
				returnV = StringUtils.left(parentCode, 6);
			}
		}

		return returnV;
	}

	public static String getCodeFullPath(String code) {
		StringBuilder s = new StringBuilder(32);
		s.append("/" + code);
		aa(s, code);
		return s.toString();
	}

	private static void aa(StringBuilder s, String code) {
		String pcode = getParentCode(code);
		if (StringUtils.isNotBlank(pcode)) {
			s.insert(0, "/" + pcode);
			aa(s, pcode);
		}
	}
}
