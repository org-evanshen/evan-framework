package com.ancun.core.service;

import java.io.Serializable;
import java.util.Map;

/**
 * 业务服务层操作结果封装
 * 
 * <p>
 * create at 2016年4月3日 下午3:25:51
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 * @since 1.0.14
 *
 */
public class ServiceResult<T> implements Serializable {
	private static final long serialVersionUID = 2941991018140190488L;

	public static final String SUCCESS = "success";
	public static final String WARN = "warn";
	public static final String ERROR = "error";

	/** 返回信息编号 */
	private int code = ServiceResultCode._1000000.getCode();
	/** 返回信息 */
	private String msg = ServiceResultCode._1000000.getMsg();
	/** 服务端版本号 */
	private String serversion;
	private T data;

	public static <T> ServiceResult<T> create() {
		ServiceResult<T> ancunApiResponse = new ServiceResult<T>();
		return ancunApiResponse;
	}

	public static <T> ServiceResult<T> create(T data) {
		return createInner(data);
	}

	public static <T> ServiceResult<T> create(ServiceResultCode responseCode) {
		return create(responseCode.getCode(), responseCode.getMsg());
	}

	public static <T> ServiceResult<T> create(int code, String msg) {
		ServiceResult<T> aap = ServiceResult.create();
		aap.setCode(code);
		aap.setMsg(msg);

		return aap;
	}

	public static <T> ServiceResult<T> create(Map<String, ?> map) {
		return (ServiceResult<T>) createInner(map);
	}

	private static <T> ServiceResult<T> createInner(T obj0) {
		ServiceResult<T> aap = ServiceResult.create();

		if (obj0 != null) {
			aap.setData(obj0);
		}

		return aap;
	}

	public void setCodeAndMsg(int code, String msg) {
		setCode(code);
		setMsg(msg);
	}

	public void setResponseCode(ServiceResultCodeEnum responseCode, Object... args) {
		setCode(responseCode.getCode());
		setMsg(String.format(responseCode.getMsg(), args));
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getServersion() {
		return serversion;
	}

	public String getType() {
		if (code == ServiceResultCode._1000000.getCode()) {
			return SUCCESS;
		} else if (code == ServiceResultCode._1000001.getCode() //
				|| code == ServiceResultCode._1000002.getCode()) {
			return ERROR;
		} else {
			return WARN;
		}
	}

	public boolean isSuccess() {
		return code == ServiceResultCode._1000000.getCode();
	}

	public void setServersion(String serversion) {
		this.serversion = serversion;
	}
}
