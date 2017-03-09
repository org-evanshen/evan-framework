package com.ancun.core.web.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToDateConverter implements Converter<String, Date> {
	private DateFormat dateFormat;
	private DateFormat dateTimeFormat;
	private String dateFormatString = "yyyy-MM-dd";
	private String dateTimeFormatString = "yyyy-MM-dd HH:mm:ss";

	private boolean allowEmpty = true;

	private int exactDateLength = -1;

	public StringToDateConverter() {
		init();
	}

	public StringToDateConverter(String dateFormatString, String dateTimeFormatString) {
		this.dateFormatString = dateFormatString;
		this.dateTimeFormatString = dateTimeFormatString;
		init();
	}

	/**
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2012-8-6 下午2:09:52 <br>
	 */
	private void init() {
		dateFormat = new SimpleDateFormat(dateFormatString);
		dateTimeFormat = new SimpleDateFormat(dateTimeFormatString);
		dateFormat.setLenient(false);
		dateTimeFormat.setLenient(false);
	}

	@Override
	public Date convert(String text) {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			return null;
		} else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
			throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength
					+ "characters long");
		} else {
			try {
				Date date = null;

				if (text.length() > this.dateFormatString.length()) {
					date = this.dateTimeFormat.parse(text);
				} else {
					date = this.dateFormat.parse(text);
				}

				return date;
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	public boolean isAllowEmpty() {
		return allowEmpty;
	}

	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

}
