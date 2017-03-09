package com.ancun.core.web.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UrlPathHelper;

import com.ancun.core.store.StoreConstants;
import com.ancun.core.sysconfig.SysConfig;

/**
 * <code>WebContextUtils</code>是Webapp处理资源路径的工具类<br>
 * 
 * <p>
 * create at 2014年5月2日 下午10:03:28
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public class WebContextUtils {
	private final static Logger log = LoggerFactory.getLogger(WebContextUtils.class);

	private final static int DEFAULT_CACHE_TIME_TO_LIVE_SECONDS = 300;

	private SysConfig sysConfig;
	
	@Autowired
	private UrlPathHelper urlPathHelper;
	private Map<String, String> servletPathMappingToSystemCode = new HashMap<String, String>();
	private Map<String, String> servletPathMappingToSystemUrlCache = new ConcurrentHashMap<String, String>();
	private boolean isRun = false;
	private int cacheTimeToLiveSeconds = DEFAULT_CACHE_TIME_TO_LIVE_SECONDS;


	private Thread cleanCacheThread;

	public void init() {
		isRun = true;
		cleanCacheThread = new Thread(new CleanCache());
		cleanCacheThread.setDaemon(true);
		cleanCacheThread.start();
		log.info("Start " + cleanCacheThread);
	}

	public void close() {
		cleanCacheThread.interrupt();
		log.info("Stop " + cleanCacheThread);
		isRun = false;
	}

	/**
	 * 获取当前系统的地址
	 * 
	 * @param request
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年5月2日下午10:15:14
	 */
	public String getAppServer(HttpServletRequest request) {
		String appServer = request.getHeader("app_host");// app_host 配置在nginx中
		if (StringUtils.isBlank(appServer)) {
			appServer = sysConfig.get("app_host");// app_host
																// 配置在配置文件中
			if (StringUtils.isBlank(appServer)) {
				appServer = HttpUtils.getAppURL(request);
			}
		}
		return appServer;
	}

	/**
	 * 获取web资源的部署服务器的url
	 * 
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年5月2日下午10:15:44
	 */
	public String getWebresourcesUrl(HttpServletRequest request) {
		String url = request.getHeader("webresources_host");// webresources_host
															// 配置在nginx中

		if (StringUtils.isBlank(url)) {
			url = sysConfig.get("webresources.url");
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
	 * 
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年5月2日下午10:16:17
	 */
	public String getRefUrl(HttpServletRequest request) {
		String url = request.getHeader("component_host");// store_host
		// 配置在nginx中

		if (StringUtils.isBlank(url)) {
			url = sysConfig.get("component.url");
		}

		return url;

	}	

	/**
	 * 获取当前请求的Servlet Url
	 * 
	 * @param request
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年4月16日上午12:34:54
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
					if (log.isErrorEnabled()) {
						log.error("process is closed");
					}
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
	
}

// if (appServer == null || StringUtils.contains(appServer,
// "localhost")) {
// appServer = WebUtils.getAppURL(request);
// }
//
// if (sysConfig.isProxypass()) {
// return appServer;
// } else {
// return appServer + request.getServletPath();
// }

// String systemUrl = null;
// if (servletPathMappingToSystemCode.isEmpty()) {
// systemUrl = servletPathMappingToSystemUrlCache.get("appServer");
// if (StringUtils.isBlank(systemUrl)) {
// systemUrl = WebUtils.getAppURL(request);
// if (!StringUtils.contains(systemUrl, "localhost")) {
// servletPathMappingToSystemUrlCache.put("appServer", systemUrl);
// }
// }
// } else {
// String servletPath = request.getServletPath();
// systemUrl = servletPathMappingToSystemUrlCache.get(servletPath);
// if (StringUtils.isBlank(systemUrl)) {
// String systemCode = servletPathMappingToSystemCode.get(servletPath);
// SysSubSystem system = null;
// if (StringUtils.isNotBlank(systemCode)) {
// system = subSystemService.getByCode(systemCode);
// }
//
// if (system != null) {
// systemUrl = system.getSystemUrl();
// if (log.isDebugEnabled()) {
// log.debug("Servlet path {} mapping System  {}", servletPath, system);
// }
// } else {
// systemUrl = WebUtils.getAppURL(request);
// if (log.isDebugEnabled()) {
// log.debug("Servlet path {} mapping System  {}", servletPath,
// systemUrl);
// }
// }
//
// servletPathMappingToSystemUrlCache.put(servletPath, systemUrl);
// }
// }

// return systemUrl;
