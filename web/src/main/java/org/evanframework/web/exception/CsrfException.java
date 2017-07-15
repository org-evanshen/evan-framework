package org.evanframework.web.exception;

import org.evanframework.exception.ServiceException;

/**
 * <p>
 * create at 2015年12月21日 下午3:57:29
 *
 * @author shen.wei
 * @version %I%, %G%
 */
public class CsrfException extends ServiceException {
    private static final long serialVersionUID = -5794812859104110263L;

    private static final String CODE = "CSRF_EXCEPTION";

    public CsrfException(String msg) {
        super(CODE, msg);
    }

    public CsrfException() {
        super(CODE, "请求来源不正确或已过期");
    }
}
