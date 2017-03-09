package com.ancun.core.web.model.ext;

import java.io.Serializable;

public class ComboBoxItem implements Serializable {

	private static final long serialVersionUID = 7487072157180309256L;

	public ComboBoxItem(String value, String text) {
		this.value = value;
		this.text = text;
	}

	String text;
	String value;

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

}
