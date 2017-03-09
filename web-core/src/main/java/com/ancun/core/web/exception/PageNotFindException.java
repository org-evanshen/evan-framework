package com.ancun.core.web.exception;

/**
 * 页面找不到 <br>
 * 业务异常的一种
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-9-2 12:46:55
 *  com.ancun.products.core.modules.exception.ServiceException
 */
public class PageNotFindException extends RuntimeException {
	private static final long serialVersionUID = 3583566093089790852L;

	public PageNotFindException() {
		super("非常抱歉,您所访问的页面不存在!");
	}

	public PageNotFindException(String msg) {
		super(msg);
	}
}
