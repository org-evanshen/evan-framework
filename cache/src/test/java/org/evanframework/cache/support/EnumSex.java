package org.evanframework.cache.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别
 */
public enum EnumSex {
	/** 男 */
	MAN(1, "男"),
	/** 女 */
	WOMAN(2, "女");

	private static Map<Integer, EnumSex> pool = new HashMap<Integer, EnumSex>();

	static {
		for (EnumSex each : EnumSex.values()) {
			EnumSex defined = pool.get(each.getValue());
			if (null != defined) {
				throw new java.lang.IllegalArgumentException(defined.toString()
						+ " defined as same code with " + each.toString());
			}
			pool.put(each.getValue(), each);
		}
	}

	private int value;
	private String text;

	EnumSex(int value, String text) {
		this.value = value;
		this.text = text;

	}

	public static EnumSex valueOf(int value) {
		return pool.get(value);
	}

	public int getValue() {
		return this.value;
	}

	public String getText() {
		return this.text;
	}

}
