package com.ancun.core.web.model.ext;

import com.ancun.core.query.AbstractQueryParam;

public class ExtPageParameter {
	private int limit;
	private int start;
	private int dir;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public void merginToQuery(AbstractQueryParam query) {
		query.setPageSize(limit);
		query.setPageNo(start / limit + 1);
		query.setSort(query.getSort() + " " + dir);
	}
}
