package org.evanframework.sysconfig.impl;

import org.evanframework.sysconfig.SysConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * 系统配置统一读取器
 * <p>
 * <p>
 * create at 2016年3月20日 下午4:42:30
 *
 * @author shen.wei
 * @version %I%, %G%
 */
public class SysConfigImpl implements SysConfig {
    private static Logger logger = LoggerFactory.getLogger(SysConfigImpl.class);

    private final static String DEFAULT_WEB_ENCODING = "UTF-8";
    //private final static String KEY_IS_WRITE_TO_OSS = "isWriteToOss";
    private final static String KEY_PROFILE = "profile";
    private final static String KEY_APP_CODE1 = "spring.application.name";
    private final static String KEY_APP_CODE2 = "app.code";

    //private AppPropertyPlaceholderConfigurer propertyConfigurer;
    private String appCode;
    private SysConfig.Profile profile = Profile.PRODUCT;
    private String webEncoding = DEFAULT_WEB_ENCODING;
    private Environment environment;

    public void init() {
        if (environment == null) {
            throw new IllegalStateException("Environment is not inited, please call method [setEnvironment]");
        }

        String profile = environment.getProperty(KEY_PROFILE);
        if (StringUtils.isBlank(profile)) {
            profile = environment.getProperty("app." + KEY_PROFILE);
        }
        if (StringUtils.isNotBlank(profile)) {
            try {
                this.profile = Profile.valueOf(profile.toUpperCase());
            } catch (Exception ex) {
                logger.error("String [" + profile + "] can't convert to enun [Profile]", ex);
            }
        }
//		String tmp = environment.getProperty(SysConfigImpl.KEY_IS_WRITE_TO_OSS);
//		if (StringUtils.isNotBlank(tmp)) {
//			writeToOss = Boolean.valueOf(tmp);
//		}
        appCode = environment.getProperty(KEY_APP_CODE1);
        if (StringUtils.isBlank(profile)) {
            appCode = environment.getProperty(KEY_APP_CODE2);
        }

        logger.info("系统配置读取组件加载成功，Class is [{}],appCode is [{}],profile is [{}]", this.getClass(), appCode, this.profile);
    }

    /**
     * 根据key获取参数值<br>
     */
    @Override
    public String get(String key) {
        String v = environment.getProperty(key);
        return v;
    }

    /**
     * http编码
     */
    @Override
    public String getWebEncoding() {
        if (webEncoding == null) {
            webEncoding = environment.getProperty("web.encoding");
            if (StringUtils.isBlank(webEncoding)) {
                webEncoding = DEFAULT_WEB_ENCODING;
            }
        }
        return webEncoding;
    }

    /**
     * 是否开发模式
     */
    @Override
    public Profile getProfile() {
        return profile;
    }

    /**
     * 应用编号
     */
    @Override
    public String getAppCode() {
        return appCode;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
