package org.evanframework.exception;


import org.evanframework.model.result.OperateResultType;

/**
 * 业务异常，当业务需要抛出异常时，抛出该异常，如果自定义业务异常，继承本异常
 *
 * @author shenWei
 * @version 2012-6-17 下午2:43:15
 * @since 1.0
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 3583566093089790852L;

    private String code;

    protected ServiceException() {
        super();
    }

    public ServiceException(OperateResultType operateResultType) {
        this(operateResultType.getCode(), operateResultType.getMsg());
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
