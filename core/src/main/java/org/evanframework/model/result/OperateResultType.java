package org.evanframework.model.result;

/**
 * 单个操作结果类型
 * Created by evan.shen on 2017/4/24.
 */
public class OperateResultType {
    private String code;
    private String msg;

    public OperateResultType(){

    }


    public OperateResultType(String code, String msg) {
        this.msg = msg;
        this.code = code;
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


    @Override
    public String toString() {
        return "OperateResultType{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperateResultType that = (OperateResultType) o;

        return code.equals(that.getCode());
    }
}
