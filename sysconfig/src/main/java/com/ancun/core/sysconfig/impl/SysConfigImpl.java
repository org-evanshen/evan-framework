package com.ancun.core.sysconfig.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.sysconfig.SysConfig;

/**
 * 系统配置统一读取器
 * 
 * <p>
 * create at 2016年3月20日 下午4:42:30
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public class SysConfigImpl implements SysConfig {
	private static Logger logger = LoggerFactory.getLogger(SysConfigImpl.class);

	private final static String DEFAULT_WEB_ENCODING = "UTF-8";
	private final static String KEY_IS_WRITE_TO_OSS = "isWriteToOss";

	private AppPropertyPlaceholderConfigurer propertyConfigurer;
	private String appCode;
	private SysConfig.Profile profile = Profile.PRODUCT;
	private String domain;
	private boolean writeToOss = false;
	private String webEncoding;

	public void init() {
		String profile = propertyConfigurer.getProperty("app.profile");
		if (StringUtils.isNotBlank(profile)) {
			try {
				this.profile = Profile.valueOf(profile.toUpperCase());
			} catch (Exception ex) {
				logger.error("String [" + profile + "] can't convert to enun [Profile]", ex);
			}
		}

		String tmp = propertyConfigurer.getProperty(SysConfigImpl.KEY_IS_WRITE_TO_OSS);
		if (StringUtils.isNotBlank(tmp)) {
			writeToOss = Boolean.valueOf(tmp);
		}

		appCode = propertyConfigurer.getProperty("app.code");

		logger.info("系统配置读取组件加载成功，Class is [{}],appCode is [{}],profile is [{}]",this.getClass(),appCode,profile);
	}

	/**
	 * 根据key获取参数值<br>
	 */
	@Override
	public String get(String key) {	
		String v = propertyConfigurer.getProperty(key);		
		return v;
	}

	/** 应用主域名 */
	@Override
	public String getDomain() {
		if (StringUtils.isBlank(domain)) {
			domain = propertyConfigurer.getProperty("app.devMode");
		}
		return domain;
	}

	/** http编码 */
	@Override
	public String getWebEncoding() {
		if (webEncoding == null) {
			webEncoding = propertyConfigurer.getProperty("web.encoding");
			if (StringUtils.isBlank(webEncoding)) {
				webEncoding = DEFAULT_WEB_ENCODING;
			}
		}
		return webEncoding;
	}

	/** 是否开发模式 */
	@Override
	public Profile getProfile() {
		return profile;
	}

	/** 应用编号 */
	@Override
	public String getAppCode() {
		return appCode;
	}

	@Override
	public boolean isWriteToOss() {
		return this.writeToOss;
	}

	public void setPropertyConfigurer(AppPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	public AppPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}
}
