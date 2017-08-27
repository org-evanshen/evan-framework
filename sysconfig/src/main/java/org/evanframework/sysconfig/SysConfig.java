package org.evanframework.sysconfig;

/**
 * 系统参数服务接口
 * <p>
 * 
 * @author shen.wei
 * @version 2013-9-2 上午10:26:37
 */
public interface SysConfig {
	enum Profile {
		DEV, TEST, PRODUCT
	}

	/**
	 * 根据key获取参数值<br>
	 * 参数可以定义在*.properties文件中，也可以定义在系统参数表中
	 */
	String get(String key);

	/** http编码 */
	String getWebEncoding();

	/** 开发环境 | 测试环境 | 生产环境 */
	Profile getProfile();

	Boolean isDebug();

	/** 应用编号 */
	String getAppCode();
}
