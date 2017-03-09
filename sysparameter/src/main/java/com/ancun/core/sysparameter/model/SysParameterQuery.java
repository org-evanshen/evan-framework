package com.ancun.core.sysparameter.model;

import java.io.Serializable;
import java.util.Arrays;

import com.ancun.core.query.AbstractQueryParam;
import com.ancun.core.query.QueryParam;

/**
 * 系统参数表查询类
 */
public class SysParameterQuery extends AbstractQueryParam implements QueryParam, Serializable {
    private static final long serialVersionUID = 13801582950791L;

    private String paramKey;
    private String[] paramKeyArray;//参数key
    private String isReadonly;//是否只读, 0否 1是，只读的不能通过管理端修改，只能直接改数据库
    private String paramValue;//参数值
    private String paramName;//参数说明
    private String paramValueDebug;//参数值,用于调试环境

    /***
     * 参数key
     */
    public String[] getParamKeyArray() {
        return paramKeyArray;
    }

    /***
     * 参数key
     */
    public void setParamKeyArray(String... paramKeyArray) {
        this.paramKeyArray = paramKeyArray;
    }

    /***
     * 是否只读, 0否 1是，只读的不能通过管理端修改，只能直接改数据库
     */
    public String getIsReadonly() {
        return isReadonly;
    }

    /***
     * 是否只读, 0否 1是，只读的不能通过管理端修改，只能直接改数据库
     */
    public void setIsReadonly(String isReadonly) {
        this.isReadonly = isReadonly;
    }

    /***
     * 参数值
     */
    public String getParamValue() {
        return paramValue;
    }

    /***
     * 参数值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /***
     * 参数说明
     */
    public String getParamName() {
        return paramName;
    }

    /***
     * 参数说明
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /***
     * 参数值,用于调试环境
     */
    public String getParamValueDebug() {
        return paramValueDebug;
    }

    /***
     * 参数值,用于调试环境
     */
    public void setParamValueDebug(String paramValueDebug) {
        this.paramValueDebug = paramValueDebug;
    }

    @Override
    public String toString() {
        return "SysParameterQuery [paramKey=" + paramKey + ", paramKeyArray=" + Arrays.toString(paramKeyArray) + ", isReadonly="
                + isReadonly + ", paramValue=" + paramValue + ", paramName=" + paramName + ", paramValueDebug=" + paramValueDebug + "]";
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }
}
