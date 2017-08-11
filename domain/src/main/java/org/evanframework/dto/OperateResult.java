package org.evanframework.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 业务服务层操作结果封装
 * <p>
 * create at 2016年4月3日 下午3:25:51
 *
 * @author shen.wei
 * @version %I%, %G%
 * @since 1.0
 */
public class OperateResult<T> implements Serializable {
    private static final long serialVersionUID = 2941991018140190488L;

    private static final OperateResultType DEFAULT_RESULT = OperateCommonResultType.SUCCESS;

    private static final String DEFAULT_CODE = DEFAULT_RESULT.getCode();
    private static final String DEFAULT_MSG = DEFAULT_RESULT.getMsg();

    private String code = DEFAULT_CODE;
    private String msg = DEFAULT_MSG;
    private String serversion;
    private T data;

    protected OperateResult() {
    }

    protected OperateResult(String code, String msg, T data) {
        //this.result = result == null ? EnumOperateResult.SUCCESS : result;
        if (code != null && !"".equals(code.trim())) {
            this.code = code;
        }
        if (msg != null && !"".equals(msg.trim())) {
            this.msg = msg;
        }
        this.data = data;
    }

    public static <T> OperateResult<T> create(String code, String msg, T data) {
        return new OperateResult(code, msg, data);
    }

    public static <T> OperateResult<T> create() {
        OperateResult<T> operateResult = new OperateResult<T>();
        return operateResult;
    }

    public static <T> OperateResult<T> create(String code, String msg) {
        return new OperateResult(code, msg, null);
    }

    public static <T> OperateResult<T> create(T data) {
        return createInner(data);
    }

    public static OperateResult create(Map<String, Object> map) {
        return createInner(map);
    }

    public static <T> OperateResult<T> createInner(T obj0) {
        OperateResult<T> operateResult = OperateResult.create();

        if (obj0 != null) {
            operateResult.setData(obj0);
        }

        return operateResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCodeAndMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(OperateResultType operateResultType) {
        this.code = operateResultType.getCode();
        this.msg = operateResultType.getMsg();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return DEFAULT_CODE.equals(this.code);
    }

    @Override
    public String toString() {
        return "OperateResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", serversion='" + serversion + '\'' +
                ", data=" + data +
                '}';
    }
}
