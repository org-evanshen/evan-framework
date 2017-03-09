package com.ancun.core.exception;

import com.ancun.core.service.ServiceResultCode;
import com.ancun.core.service.ServiceResultCodeEnum;

/**
 * 没有权限异常
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2010-12-12 下午05:14:55
 */
public class PermissionDeniedException extends ServiceException {

    private static final long serialVersionUID = -19517187915506429L;
    private static final ServiceResultCode DEFAULT_CODE = ServiceResultCode._1000122;

    public PermissionDeniedException(int code, String message) {
        super(code, message);
    }

    public PermissionDeniedException() {
        this(DEFAULT_CODE.getCode(), DEFAULT_CODE.getMsg());
    }

    public PermissionDeniedException(ServiceResultCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
    }
}
