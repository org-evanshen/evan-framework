package com.ancun.core.exception;

import com.ancun.core.service.ServiceResultCode;
import com.ancun.core.service.ServiceResultCodeEnum;

/**
 * 业务异常 <br>
 * 当业务需要抛出异常时，抛出该异常 <br>
 * 如果自定义业务异常，继承本异常
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-6-17 下午2:43:15
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 3583566093089790852L;

	private int code = ServiceResultCode._1000002.getCode();

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(ServiceResultCodeEnum codeEnum, Object... args) {
		this(String.format(codeEnum.getMsg(), args));
		this.code = codeEnum.getCode();
	}	

	public ServiceException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
