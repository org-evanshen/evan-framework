package com.ancun.core.web.model.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */
@SuppressWarnings("rawtypes")
public class ExtPageResult implements Serializable {
	private static final long serialVersionUID = -1285956019790798731L;

	private long recordCount;
	private List data = new ArrayList();

	public long getRecordCount() {
		return recordCount;
	}

	public List getData() {
		return data;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	@SuppressWarnings("unchecked")
	public void setData(List data) {
		this.data.addAll(data);
	}
}
