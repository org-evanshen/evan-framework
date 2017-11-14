package org.evanframework.web.velocity.tools;

import org.evanframework.sysconfig.SysConfig;
import org.evanframework.web.utils.WebContextUtils;
import org.evanframework.web.velocity.resolver.FixedVelocityLayoutView;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @since 1.0
 */
public class WebContextTool {
    // private static final Logger logger =
    // LoggerFactory.getLogger(WebContextTool.class);

    private SysConfig sysConfig;
    private WebContextUtils webContextUtils;

    public void init(Object obj) {
        ViewToolContext ctx = (ViewToolContext) obj;
        ApplicationContext applicationContext = (ApplicationContext) ctx.get(FixedVelocityLayoutView.APPLICATIONCONTEXT_KEY);
        sysConfig = applicationContext.getBean("sysConfig", SysConfig.class);
        webContextUtils = applicationContext.getBean(WebContextUtils.class);
    }

    private String appServer = null;

    public String getAppServer(HttpServletRequest request) {
        if (appServer == null || StringUtils.contains(appServer, "localhost")) {
            appServer = webContextUtils.getAppServer(request);
        }
        return appServer;
    }

//    //public String getAppDomain(HttpServletRequest request) {
//        return sysConfig.getDomain();
//    }

    public String getUploadUrl(HttpServletRequest request) {
        String key = "store.dir";
        Object uploadUrl = request.getAttribute(key);
        if (uploadUrl == null) {
            uploadUrl = webContextUtils.getStoreUrl(request);
            if (uploadUrl != null) {
                request.setAttribute(key, uploadUrl);
            }
        }
        return uploadUrl.toString();
    }

    // private String webresourcesUrl = null;
    //
    // public String getWebresourcesUrl(HttpServletRequest request) {
    // String key = SysConfig.KEY_WEBRESOURCE_URL;
    // if (StringUtils.isBlank(webresourcesUrl)) {
    // webresourcesUrl = sysConfig.get(key);
    // }
    // return webresourcesUrl;
    //
    // // Object webresourcesUrl = request.getAttribute(key);
    // // if (webresourcesUrl == null) {
    // // webresourcesUrl = sysConfig.getValueByKey(key);
    // // if (webresourcesUrl != null) {
    // // request.setAttribute(key, webresourcesUrl);
    // // if (logger.isDebugEnabled()) {
    // //
    // logger.debug("get webresources url from system parameter service the value is ["
    // // + webresourcesUrl + "]");
    // // }
    // // }
    // // } else {
    // // if (logger.isDebugEnabled()) {
    // //
    // logger.debug("get webresources url from request service the value is ["
    // // + webresourcesUrl + "]");
    // // }
    // // }
    // // return webresourcesUrl.toString();
    // }

    // public String getWebresourcesUrlAppRes(HttpServletRequest request) {
    // return getWebresourcesUrl(request) +
    // CoreConstants.WEB_RESOURCES_APP_SUB_URL;
    // }

    // public String getWebresourcesUrlRef(HttpServletRequest request) {
    // return getWebresourcesUrl(request) +
    // CoreConstants.WEB_RESOURCES_REF_SUB_URL;
    // }

    public String getWebEncoding() {
        return sysConfig.getWebEncoding();
    }

    public String getFileEncoding() {
        return sysConfig.getWebEncoding();
    }

    public String getAppCode() {
        return sysConfig.getAppCode();
    }

    public boolean isDebug() {
        return SysConfig.Profile.DEV.equals(sysConfig.getProfile());
    }

    public boolean isProduct() {
        return SysConfig.Profile.PRODUCTION.equals(sysConfig.getProfile());
    }

    public String getConfigByKey(HttpServletRequest request, String key) {
        String keyInRequest = "systemParameter_" + key;
        Object value = request.getAttribute(keyInRequest);
        String returnV = null;
        if (value == null) {
            value = sysConfig.get(key);
            if (value != null) {
                request.setAttribute(keyInRequest, value);
                returnV = value.toString();
            }
        }
        return returnV;
    }
}
