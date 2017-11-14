package org.evanframework.sysconfig.impl;

import org.apache.commons.lang3.StringUtils;
import org.evanframework.sysconfig.SysConfig;
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
    private final static String DEFAULT_WEB_ENCODING = "UTF-8";
    //private final static String KEY_IS_WRITE_TO_OSS = "isWriteToOss";
    private final static String KEY_PROFILE = "profile";
    private final static String KEY_PROFILE2 = "spring.profiles.active";
    private final static String KEY_APP_CODE1 = "spring.application.name";
    private final static String KEY_APP_CODE2 = "app.code";
    private static Logger LOGGER = LoggerFactory.getLogger(SysConfigImpl.class);
    //private AppPropertyPlaceholderConfigurer propertyConfigurer;
    private String appCode;
    private SysConfig.Profile profile = Profile.PRODUCTION;

    private String webEncoding = DEFAULT_WEB_ENCODING;
    private Environment environment;

    public void init() {
        if (environment == null) {
            throw new IllegalStateException("Environment is not inited, please call method [setEnvironment]");
        }

        String profile = environment.getProperty(KEY_PROFILE2);
        LOGGER.info(">>>>>>>> Environment [{}] is [{}]", KEY_PROFILE2, profile);
        if (StringUtils.isBlank(profile)) {
            profile = environment.getProperty(KEY_PROFILE);
            LOGGER.info("Environment [{}] is [{}]", KEY_PROFILE, profile);
        }
        if (StringUtils.isBlank(profile)) {
            profile = environment.getProperty("app." + KEY_PROFILE);
            LOGGER.info("Environment [{}] is [{}]", "app." + KEY_PROFILE, profile);
        }
        if (StringUtils.isNotBlank(profile)) {
            try {
                this.profile = Profile.valueOf(profile.toUpperCase());
            } catch (Exception ex) {
                LOGGER.error(">>>>>>>> String [" + profile + "] can't convert to enum [" + Profile.class + "]", ex);
            }
        }
//		String tmp = environment.getProperty(SysConfigImpl.KEY_IS_WRITE_TO_OSS);
//		if (StringUtils.isNotBlank(tmp)) {
//			writeToOss = Boolean.valueOf(tmp);
//		}
        appCode = environment.getProperty(KEY_APP_CODE1);
        if (StringUtils.isBlank(appCode)) {
            appCode = environment.getProperty(KEY_APP_CODE2);
        }

        LOGGER.info("加载系统配置读取组件，Class is [{}],appCode is [{}],profile is [{}]", this.getClass(), appCode, profile);
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

    @Override
    public Boolean isDebug() {
        return Profile.DEV.equals(profile);
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
