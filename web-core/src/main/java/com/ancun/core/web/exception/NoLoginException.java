package com.ancun.core.web.exception;

/**
 * 没有登录异常
 * <p> 
 * @author  <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2010-12-12 下午05:14:55
 */
public class NoLoginException extends RuntimeException {
	private static final long serialVersionUID = 3583566093089790852L;

	public NoLoginException() {
		super("没有登录");
	}
}
