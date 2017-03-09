package com.ancun.core.query;

public interface QueryParam {
	/**
	 * 排序表达式
	 */
	String getSort();

	/**
	 * 排序标识，可将排序标识转换为排序表单式，对调用者不公开排序表达式
	 */
	String getSortCode();

	/**
	 * 当前页 从1开始
	 */
	int getPageNo();

	/**
	 * 每页记录数
	 */
	int getPageSize();

	/**
	 * 开始记录 从1开始
	 */
	int getStartRow();
	
	/**
	 * 开始记录 从0开始
	 */
	int getStartRowBegin0();

	/**
	 * 结束记录
	 */
	int getEndRow();

	/**
	 * select列
	 */
	String[] getColumns();

	void setPageSize(int pageSize);

	/**
	 * 是否按默认排序<br>
	 * <br>
	 * true -- 当排序表单式为空时，按默认排序 <br>
	 * false -- 当排序表单式为空时，不排序 <br>
	 * 默认 true
	 */
	boolean isSortByDefault();

	/**
	 * 是否包含已逻辑删除的数据<br>
	 * <br>
	 * 默认 false
	 */
	boolean isIncludeDeleted();
}
