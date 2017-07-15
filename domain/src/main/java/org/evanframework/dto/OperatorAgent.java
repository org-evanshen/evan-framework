package org.evanframework.dto;


/**
 * 
 * 当前登录用户
 * 
 * @author shen.wei
 * @version 2012-8-1 下午6:40:47
 * @since 1.0
 */
public interface OperatorAgent {

	Long getId();

	String getType();

	String getAccount();

	String getIp();

}
