package com.ancun.core.web.exception;

import com.ancun.core.exception.ServiceException;
import com.ancun.core.web.webapi.AncunApiResponseCode;
import com.ancun.core.web.webapi.AncunApiResponseCodeEnum;

public class WebException extends ServiceException {

	private static final long serialVersionUID = 358356609308978512L;

	private int code = AncunApiResponseCode._1000002.getCode();

	public WebException(AncunApiResponseCodeEnum codeEnum, Object... args) {
		this(String.format(codeEnum.getMsg(), args));
		this.code = codeEnum.getCode();
	}

	public WebException(String message) {
		super(message);
	}

	public WebException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
