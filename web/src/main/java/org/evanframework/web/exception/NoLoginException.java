package org.evanframework.web.exception;

import org.evanframework.exception.ServiceException;

/**
 * 会话失效异常
 * <p>
 *
 * @author ShenWei
 * @version
 * @since 1.0
 */
public class NoLoginException extends ServiceException {
    private static final long serialVersionUID = 3583566093089790852L;

    private static final String CODE = "NO_LOGIN";

    public NoLoginException(String msg) {
        super(CODE, msg);
    }

    public NoLoginException() {
        super(CODE, "没有登录或登录超时");
    }
}
