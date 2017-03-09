package com.ancun.core.utils.support;

/**
 * 性别
 */
public enum EnumSex {
	/** 男 */
	MAN(1, "男"),
	/** 女 */
	WOMAN(2, "女");

	private int value;
	private String text;


	EnumSex(int value, String text) {
		this.value = value;
		this.text = text;

	}

	public int getValue() {
		return this.value;
	}

	public String getText() {
		return this.text;
	}

}
