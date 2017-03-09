package com.ancun.core.service;

/**
 * 返回信息枚举
 * <p>
 * create at 2014年10月28日 下午5:15:54
 * 
 * @author <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a>
 * @version %I%, %G%
 *
 */
public enum ServiceResultCode  implements ServiceResultCodeEnum {
	/** 成功 */
	_1000000("成功"),
	/** 系统维护中 */
	_1000001("系统维护中"),
	/** 未知错误 */
	_1000002("未知错误"),
	/** 参数不正确 */
	_1000003("参数不正确"),
	/** 没有权限 */
	_1000122("非常抱歉，您没有权限使用该功能！"),
	/** 请求的数据不存在或已删除 */
	_1000121("请求的数据不存在或已删除"),

	;

	private int code;
	private String msg;

	private ServiceResultCode(String msg) {
		this.msg = msg;
		this.code = Integer.valueOf(this.name().substring(1));
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
