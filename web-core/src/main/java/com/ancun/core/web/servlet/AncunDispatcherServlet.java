package com.ancun.core.web.servlet;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.util.UrlPathHelper;

import com.ancun.core.web.WebCoreConstants;
import com.ancun.core.web.resolver.ViewPathExtendResolver;
import com.ancun.core.web.utils.WebContextUtils;

public class AncunDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = 3353459049895961290L;

	private static final Logger log = LoggerFactory.getLogger(AncunDispatcherServlet.class);

	private static final String VIEW_PATH_EXTEND_RESOLVER_BEAN_NAME = "viewPathExtendResolver";

	private ViewPathExtendResolver viewPathExtendResolver;
	private LocaleResolver localeResolver;

	// private PathMatcher pathMatcher;
	private WebContextUtils webContextUtils;
	private UrlPathHelper urlPathHelper;

	protected void initStrategies(ApplicationContext context) {
		super.initStrategies(context);
		initViewPathExtendResolver(context);
		initLocaleResolver(context);
		this.webContextUtils = context.getBean("webContextUtils", WebContextUtils.class);
		this.urlPathHelper = context.getBean("urlPathHelper", UrlPathHelper.class);
	}

	private void initViewPathExtendResolver(ApplicationContext context) {
		this.viewPathExtendResolver = null;

		try {
			viewPathExtendResolver = context.getBean(VIEW_PATH_EXTEND_RESOLVER_BEAN_NAME, ViewPathExtendResolver.class);
			if (log.isInfoEnabled()) {
				log.debug("Using ViewPathExtendResolver [" + this.viewPathExtendResolver + "]");
			}
		} catch (NoSuchBeanDefinitionException ex) {
			if (log.isInfoEnabled()) {
				log.info("Unable to locate ViewPathExtendResolver with name '" + VIEW_PATH_EXTEND_RESOLVER_BEAN_NAME
						+ "'");
			}
		}
	}

	private void initLocaleResolver(ApplicationContext context) {
		try {
			this.localeResolver = context.getBean(LOCALE_RESOLVER_BEAN_NAME, LocaleResolver.class);
			if (logger.isDebugEnabled()) {
				logger.debug("Using LocaleResolver [" + this.localeResolver + "]");
			}
		} catch (NoSuchBeanDefinitionException ex) {
			// We need to use the default.
			this.localeResolver = getDefaultStrategy(context, LocaleResolver.class);
			if (logger.isDebugEnabled()) {
				logger.debug("Unable to locate LocaleResolver with name '" + LOCALE_RESOLVER_BEAN_NAME
						+ "': using default [" + this.localeResolver + "]");
			}
		}
	}

	@Override
	protected View resolveViewName(String viewName, Map<String, Object> model, Locale locale, HttpServletRequest request)
			throws Exception {

		if (!StringUtils.startsWith(viewName, "redirect:") && !StringUtils.startsWith(viewName, "forword:")) {
			if (viewPathExtendResolver != null) {
				String url = request.getPathInfo();

				if (viewPathExtendResolver.viewIsMatch(viewName)) {
					String viewPathPrefix = viewPathExtendResolver.getViewPathPrefix(url);
					if (StringUtils.isNotBlank(viewPathPrefix)) {
						viewName = viewPathPrefix + "/" + viewName;
					}
				}

				String layout = viewPathExtendResolver.getLayout(url);
				if (StringUtils.isNotBlank(layout)) {
					if (log.isDebugEnabled()) {
						log.debug("Servlet[" + url + "],use layout [" + layout + "]");
					}
					model.put(WebCoreConstants.LAYOUT_KEY, layout);
				}
			}
			if (webContextUtils != null) {
				pubContextValues(request, model);
			}
		}

		View view = super.resolveViewName(viewName, model, locale, request);
		return view;
	}

	@Override
	protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			super.render(mv, request, response);
		} catch (ServletException ex) {
			log.error(ex.getMessage(), ex);
			mv.setViewName(WebCoreConstants.PAGE_NOT_FIND_PATH);//如果view找不到，则用404页面来渲染
			super.render(mv, request, response);
		}
	}

	private void pubContextValues(HttpServletRequest request, Map<String, Object> model) {
		String appServer = webContextUtils.getAppServer(request);

		String webresourcesUrl = webContextUtils.getWebresourcesUrl(request);
		String refUrl = webContextUtils.getRefUrl(request);

		pubContextValue(model, "appServer", appServer);
		pubContextValue(model, "webresourcesUrl", webresourcesUrl);
		pubContextValue(model, "refUrl", refUrl);

		if (log.isDebugEnabled()) {
			String str = MessageFormat.format("Servlet [" + appServer + urlPathHelper.getLookupPathForRequest(request)
					+ "], set context values [appServer={0},webresourcesUrl={1},refUrl={2}]", appServer,
					webresourcesUrl, refUrl);
			log.debug(str);
		}
	}

	private void pubContextValue(Map<String, Object> model, String key, Object value) {
		model.put("context_" + key, value);
		if (log.isTraceEnabled()) {
			log.trace("set context value [context_" + key + "=" + value + "]");
		}
	}
}
