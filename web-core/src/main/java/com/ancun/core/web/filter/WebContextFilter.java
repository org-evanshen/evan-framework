package com.ancun.core.web.filter;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.ancun.core.Excludor;
import com.ancun.core.utils.PathUtils;
import com.ancun.core.web.WebCoreConstants;
import com.ancun.core.web.utils.HttpUtils;
import com.ancun.core.web.utils.WebContextUtils;

public class WebContextFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(WebContextFilter.class);

	@Autowired
	private PathMatcher pathMatcher;
	@Autowired
	private WebContextUtils webContextUtils;
	@Autowired
	private UrlPathHelper urlPathHelper;

	private Map<String, String> urlPrefixMap = new HashMap<String, String>(8);
	private Map<String, String> layouts = new HashMap<String, String>(8);
	private Excludor excludor = new Excludor();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		if (!HttpUtils.isAjaxRequest(request) && !HttpUtils.isFormPost(request)) {
			//String requestUri = WebUtils.getRequestURI(request, CoreConstants.SERVLET_PATH_SUFFIX);
			String requestPath = urlPathHelper.getPathWithinApplication(request);
			if (!PathUtils.matches(requestPath, excludor.getExcludes())) {
				process(request);
			}
		}

		chain.doFilter(servletRequest, response);
	}

	/**
	 * 
	 * <p>
	 * <a href="mailto:shenwei@ancun.com">ShenWei</a> create at 2013-10-2
	 * 下午2:25:16
	 * </p>
	 * 
	 * @param request
	 *
	 */
	protected void process(HttpServletRequest request) {
		pubContextValues(request);
		
		putLayout(request);
	}

	private void pubContextValues(HttpServletRequest request) {
		String appServer = webContextUtils.getAppServer(request);

		//		log.debug("urlPathHelper.getRequestUri():" + urlPathHelper.getRequestUri(request));
		//		log.debug("urlPathHelper.getContextPath():" + urlPathHelper.getContextPath(request));
		//		log.debug("urlPathHelper.getLookupPathForRequest():" + urlPathHelper.getLookupPathForRequest(request));
		//		log.debug("urlPathHelper.getPathWithinApplication():" + urlPathHelper.getPathWithinApplication(request));
		//		log.debug("urlPathHelper.getPathWithinServletMapping():" + urlPathHelper.getPathWithinServletMapping(request));
		//		log.debug("urlPathHelper.getOriginatingQueryString():" + urlPathHelper.getOriginatingQueryString(request));

		String webresourcesUrl = webContextUtils.getWebresourcesUrl(request);
		String refUrl = webContextUtils.getRefUrl(request);

		pubContextValue(request, "appServer", appServer);
		pubContextValue(request, "webresourcesUrl", webresourcesUrl);
		pubContextValue(request, "refUrl", refUrl);

		if (log.isDebugEnabled()) {
			String str = MessageFormat.format(
					"Servlet [" + appServer + urlPathHelper.getLookupPathForRequest(request)
							+ "], set context values [appServer={0},webresourcesUrl={1},refUrl={2}]",
					appServer, webresourcesUrl, refUrl);
			log.debug(str);
		}
	}
	
	private void putLayout(HttpServletRequest request) {
		String requestUri = request.getRequestURI();

		for (Entry<String, String> entry : layouts.entrySet()) {
			if (pathMatcher.matchStart(entry.getKey(), "/" + requestUri)) {
				if (log.isDebugEnabled()) {
					log.debug("Servlet [" + webContextUtils.getAppServer(request)
							+ urlPathHelper.getPathWithinServletMapping(request) + "], set " + WebCoreConstants.LAYOUT_KEY
							+ " [" + entry.getValue() + "]");
				}
				request.setAttribute(WebCoreConstants.LAYOUT_KEY, entry.getValue());
			}
		}
	}

	private void pubContextValue(HttpServletRequest request, String key, Object value) {
		request.setAttribute("context_" + key, value);
		if (log.isTraceEnabled()) {
			log.trace("set context value [context_" + key + "=" + value + "]");
		}
	}

	@Override
	public void destroy() {
	}

	public void setExcludor(Excludor excludor) {
		this.excludor = excludor;
	}

	public void setUrlPrefixMap(Map<String, String> urlPrefixMap) {
		this.urlPrefixMap = urlPrefixMap;

		if (log.isDebugEnabled()) {
			for (Entry<String, String> entry : this.urlPrefixMap.entrySet()) {
				log.debug("Inited url prefix mapper [app code:" + entry.getKey() + ", url prefix:"
						+ entry.getValue() + "]");
			}
		}
	}

	public void setLayouts(Map<String, String> layouts) {
		this.layouts = layouts;

		if (log.isDebugEnabled()) {
			for (Entry<String, String> entry : this.layouts.entrySet()) {
				log.debug("Inited layout mapper [servlet:" + entry.getKey() + ", layout:" + entry.getValue()
						+ "]");
			}
		}
	}
}
