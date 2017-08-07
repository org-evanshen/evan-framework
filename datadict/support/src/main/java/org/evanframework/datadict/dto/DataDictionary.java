package org.evanframework.datadict.dto;

import java.io.Serializable;

/**
 * 数据字典(基础数据)
 */
public class DataDictionary implements Serializable {

	private static final long serialVersionUID = 5776429860449356562L;

	private String value;
	private String text;
	private String color;
	private boolean leaf = true;	
	private String extend1;//扩展1
	private String extend3;//扩展3
	private String extend2;//扩展2

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	public String toString() {
		return "DataDictionary [value=" + value + ", text=" + text + "]";
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
}
