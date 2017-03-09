package com.ancun.core.web.model.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComboBox implements Serializable {
	private static final long serialVersionUID = -2360902041160756132L;

	List<ComboBoxItem> data;
	int recordCount;

	public ComboBox() {
		this.data = new ArrayList<ComboBoxItem>();
	}

	public ComboBox add(String value, String text) {
		this.data.add(new ComboBoxItem(value, text));
		return this;
	}

	/**
	 *  the data
	 */
	public List<ComboBoxItem> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<ComboBoxItem> data) {
		this.data.addAll(data);
	}

	/**
	 *  the recordCount
	 */
	public int getRecordCount() {
		return data.size();
	}
}
