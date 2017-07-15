package org.evanframework.web.utils;

import org.evanframework.store.StoreConstants;
import org.evanframework.sysconfig.SysConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>WebContextUtils</code>是Webapp处理资源路径的工具类<br>
 * <p>
 * <p>
 * create at 2014年5月2日 下午10:03:28
 *
 * @author shen.wei
 * @version %I%, %G%
 */
public class WebContextUtils {
    private final static Logger log = LoggerFactory.getLogger(WebContextUtils.class);

    private final static int DEFAULT_CACHE_TIME_TO_LIVE_SECONDS = 300;

    private UrlPathHelper urlPathHelper;
    private Map<String, String> servletPathMappingToSystemCode = new HashMap<String, String>();
    private Map<String, String> servletPathMappingToSystemUrlCache = new ConcurrentHashMap<String, String>();
    private boolean isRun = false;
    private int cacheTimeToLiveSeconds = DEFAULT_CACHE_TIME_TO_LIVE_SECONDS;

    private Thread cleanCacheThread;

    private SysConfig sysConfig;

    public void init() {
        if (urlPathHelper == null) {
            urlPathHelper = new UrlPathHelper();
        }

        log.info("WebContextUtils inited");

        isRun = true;
        cleanCacheThread = new Thread(new CleanCache());
        cleanCacheThread.setDaemon(true);
        cleanCacheThread.start();

        log.info("WebContextUtils " + cleanCacheThread + " Started");
    }

    public void close() {
        cleanCacheThread.interrupt();
        log.info("Stop " + cleanCacheThread);
        isRun = false;
    }

    /**
     * 获取当前系统的地址
     *
     * @param request <p>
     *                author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     *                create at: 2014年5月2日下午10:15:14
     */
    public String getAppServer(HttpServletRequest request) {
        String appServer = request.getHeader("app_host");// app_host 配置在nginx中
        if (StringUtils.isBlank(appServer)) {
            appServer = sysConfig.get("app.host");// app_host  配置在配置文件中

            if (StringUtils.isBlank(appServer)) {
                appServer = HttpUtils.getAppURL(request);
            }
        }
        return appServer;
    }

    /**
     * 获取web资源的部署服务器的url
     * <p>
     * <p>
     * author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     * create at: 2014年5月2日下午10:15:44
     */
    public String getWebresourcesUrl(HttpServletRequest request) {
        String url = request.getHeader("webresources_host");// webresources_host 配置在nginx中

        if (StringUtils.isBlank(url)) {
            url = sysConfig.get("web.resources.url");
            if (StringUtils.isBlank(url)) {
                url = getAppServer(request);
            }
        }

        return url;
    }


    public String getStoreUrl(HttpServletRequest request) {
        String url = request.getHeader("store_host");// store_host
        // 配置在nginx中

        if (StringUtils.isBlank(url)) {
            url = sysConfig.get(StoreConstants.STORE_SERVER_URL);
        }

        return url;
    }


    /**
     * 获取web组件部署的服务器的url
     * <p>
     * <p>
     * author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     * create at: 2014年5月2日下午10:16:17
     */
    public String getComponentUrl(HttpServletRequest request) {
        String url = request.getHeader("component_host");// store_host
        // 配置在nginx中

        if (StringUtils.isBlank(url)) {
            url = sysConfig.get("web.component.url");
            if (StringUtils.isBlank(url)) {
                url = getAppServer(request) + "/_component";
            }
        }

        return url;
    }

    /**
     * 获取当前请求的Servlet Url
     *
     * @param request <p>
     *                author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     *                create at: 2014年4月16日上午12:34:54
     */
    public String getServletUrl(HttpServletRequest request) {
        return getAppServer(request) + urlPathHelper.getPathWithinServletMapping(request);
    }

    public void setServletPathMappingToSystemCode(Map<String, String> servletPathMappingToSystemCode) {
        if (log.isDebugEnabled()) {
            log.debug("Init servlet path mapping to system code {}", servletPathMappingToSystemCode);
        }

        this.servletPathMappingToSystemCode = servletPathMappingToSystemCode;
    }

    private class CleanCache implements Runnable {

        @Override
        public void run() {
            while (isRun) {
                try {
                    Thread.sleep(cacheTimeToLiveSeconds * 1000);
                } catch (InterruptedException e) {
                    log.info("process is closed");
                }

                if (!servletPathMappingToSystemUrlCache.isEmpty()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Clean servlet path mapping to system url cache");
                    }
                    servletPathMappingToSystemUrlCache.clear();
                }
            }
        }
    }

    public void setCacheTimeToLiveSeconds(int cacheTimeToLiveSeconds) {
        this.cacheTimeToLiveSeconds = cacheTimeToLiveSeconds;
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }
}