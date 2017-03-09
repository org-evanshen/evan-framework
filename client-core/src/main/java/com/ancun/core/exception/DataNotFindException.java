package com.ancun.core.exception;

import com.ancun.core.service.ServiceResultCode;
import com.ancun.core.service.ServiceResultCodeEnum;

public class DataNotFindException extends ServiceException {
	private static final long serialVersionUID = -951778798106425369L;
	private static final ServiceResultCode DEFAULT_CODE = ServiceResultCode._1000121;

	public DataNotFindException(int code, String message) {
		super(code, message);
	}

	public DataNotFindException() {
		this(DEFAULT_CODE.getCode(), DEFAULT_CODE.getMsg());
	}
	
	public DataNotFindException(ServiceResultCodeEnum codeEnum, Object... args) {
		super(codeEnum, args);
	}	
	
}
