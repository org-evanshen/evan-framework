package com.ancun.core.web.exception;

/**
 * 
 * 
 * <p>
 * create at 2015年12月21日 下午3:57:29
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public class CsrfException extends WebException {
	private static final long serialVersionUID = -5794812859104110263L;

	public CsrfException() {
		super("请求来源不正确或已过期");
	}
}
