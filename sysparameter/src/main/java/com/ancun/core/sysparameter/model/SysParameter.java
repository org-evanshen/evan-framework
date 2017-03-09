package com.ancun.core.sysparameter.model;

import java.io.Serializable;

public class SysParameter implements Serializable {
    private static final long serialVersionUID = 231311112311L;

    private String paramKey;//参数key
    private String paramValue;//参数值
    private String paramName;//参数说明
    private String paramValueDebug;//参数值,用于调试环境
    private boolean readOnly;//是否只读, 0否 1是，只读的不能通过管理端修改，只能直接改数据库

    public SysParameter() {
    }

    /**
     * @param paramKey -- 参数key
     */
    public SysParameter(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * 参数key
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * 参数key
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * 参数值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 参数值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 参数说明
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 参数说明
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 参数值,用于调试环境
     */
    public String getParamValueDebug() {
        return paramValueDebug;
    }

    /**
     * 参数值,用于调试环境
     */
    public void setParamValueDebug(String paramValueDebug) {
        this.paramValueDebug = paramValueDebug;
    }

    /**
     * 是否只读,只读的不能通过管理端修改，只能直接改数据库
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * 是否只读, 只读的不能通过管理端修改，只能直接改数据库
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public String toString() {
        return "PubParameter [paramKey=" + paramKey + ", paramValue=" + paramValue + ", paramName=" + paramName + ", paramValueDebug="
                + paramValueDebug + "]";
    }
}
