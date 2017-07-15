package org.evanframework.dto;

/**
 * 公用的返回类型，各子系统各业务模块可以继承该类
 * Created by shen.wei on 2017/4/24.
 */
public interface OperateCommonResultType {
    /**
     * 成功
     */
    OperateResultType SUCCESS = new OperateResultType("SUCCESS", "成功");
    /**
     * 系统出错
     */
    OperateResultType ERROR = new OperateResultType("ERROR", "系统出错");
    /**
     * 参数不正确
     */
    OperateResultType PARAMETER_INVALID = new OperateResultType("PARAMETER_INVALID", "参数不正确");
    /**
     * 请求地址不正确
     */
    OperateResultType HTTP_URL_INVALID = new OperateResultType("HTTP_URL_INVALID", "请求地址不正确");
    /**
     * 请求方式不正确
     */
    OperateResultType HTTP_METHOD_INVALID = new OperateResultType("HTTP_METHOD_INVALID", "请求方式不正确");
    /**
     * 请求方式不正确
     */
    OperateResultType HTTP_MEDIA_TYPE_INVALID = new OperateResultType("HTTP_MEDIA_TYPE_INVALID", "请求格式不正确");
    /**
     * 没有权限
     */
    OperateResultType NO_ACCESS = new OperateResultType("NO_ACCESS", "非常抱歉，您没有权限使用该功能！");
    /**
     * 请求的数据不存在或已删除
     */
    OperateResultType DATA_NOT_FIND = new OperateResultType("DATA_NOT_FIND", "请求的数据不存在或已删除");
    /**
     * 登录失败，用户名密码错误
     */
    OperateResultType ACCOUNT_OR_PWD_WRONG = new OperateResultType("ACCOUNT_OR_PWD_WRONG", "登录失败，用户名密码错误");
    /**
     * 登录失败，账户被冻结
     */
    OperateResultType ACCOUNT_FROZENED = new OperateResultType("ACCOUNT_FROZENED", "登录失败，账户被冻结");

}
