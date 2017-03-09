package com.ancun.core.web.model.ext;

/**
 * ExtJs的form提交的处理结果
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-2-19 下午1:41:29
 * @param <T>
 */
public class ExtJsFormPostResult<T> {
	private boolean success;
	private T data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
