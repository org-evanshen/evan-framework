package org.evanframework.dto;

import java.io.Serializable;
import java.util.List;

import org.evanframework.query.QueryParam;

/**
 * 分页结果
 * @since 1.0
 * @param <T>
 * @author shen.wei
 */
public class PageResult<T> implements Serializable {
	private static final long serialVersionUID = 808498493080717152L;

	private long recordCount; // 总记录数
	private int pageCount; // 总页数
	private List<T> data;
	private int pageNo = 1;// 当前是第几页，从1开始记数
	private int pageSize; // 每页多少条	
	private QueryParam query;

	private PageResult() {
	}

	public static <T> PageResult<T> create(QueryParam query, List<T> data, long recordCount) {
		PageResult<T> result = new PageResult<T>(query);
		result.setData(data);
		result.setRecordCount(recordCount);
		return result;
	}

	private PageResult(QueryParam query) {
		pageNo = query.getPageNo();
		pageSize = query.getPageSize();
		this.query = query;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	//@JsonIgnore
	public int getFirst() {
		return (pageNo - 1) * pageSize + 1;
	}

	/**
	 * 记录总数
	 */
	public long getRecordCount() {
		return this.recordCount;
	}

	/**
	 * 记录总数
	 */
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;

		if (pageCount == 0 && pageSize > 0) {
			pageCount = (int) (recordCount / pageSize);
			pageCount += recordCount % pageSize > 0 ? 1 : 0;
		}
	}

	/**
	 * 页数
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 是否还有下一页.
	 */
	//@JsonIgnore
	public boolean isHasNext() {
		return pageNo < pageCount;
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	//@JsonIgnore
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	//@JsonIgnore
	public boolean isHasPre() {
		return pageNo > 1;
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	//@JsonIgnore
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 结果数据集
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 结果数据集
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	//@JsonIgnore
	public QueryParam getQuery() {
		return query;
	}

	@Override
	public String toString() {
		return "PageResult [recordCount=" + recordCount + ", pageCount=" + pageCount + ", data=" + data
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", query=" + query + "]";
	}
}
