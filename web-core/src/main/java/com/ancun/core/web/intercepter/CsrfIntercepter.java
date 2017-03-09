package com.ancun.core.web.intercepter;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.web.annotation.CsrfValidate;
import com.ancun.core.web.exception.CsrfException;
import com.ancun.core.web.utils.CsrfTokenUtils;

public class CsrfIntercepter implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(CsrfIntercepter.class);

	private CsrfTokenUtils csrfTokenUtils;

	protected Set<Method> methodCache = Collections.synchronizedSet(new HashSet<Method>());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (RequestMethod.GET.name().equals(request.getMethod())) {
			return true;
		}

		if (HandlerMethod.class.isInstance(handler)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			boolean isNeedCsrfValidate = false;
			if (!methodCache.contains(method)) {
				if (method.isAnnotationPresent(CsrfValidate.class)) {
					methodCache.add(method);
					isNeedCsrfValidate = true;
				}
			} else {
				isNeedCsrfValidate = true;
			}

			if (isNeedCsrfValidate) {
				logger.info("Csrf validate, request is [{}], contronller is [{}] ", request.getRequestURI(), method);

				@SuppressWarnings("unchecked")
				Enumeration<String> paramNames = request.getParameterNames();

				boolean isCsrfPass = false;
				while (paramNames.hasMoreElements()) {
					String key = paramNames.nextElement();

					if (StringUtils.startsWith(key, CsrfTokenUtils.CSRF_TOKEN_KEY_PREFIX)) {
						request.setAttribute("csrf_token_form_item_key", key);
						String tokenValue = request.getParameter(key);
						logger.info("Csrf validate, csrf token key is [{}], value is [{}] ", key, tokenValue);
						isCsrfPass = csrfTokenUtils.validateToken(key, tokenValue,request);
						break;
					}
				}

				if (!isCsrfPass) {
					throw new CsrfException();
				}
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (RequestMethod.GET.name().equals(request.getMethod())) {
			return;
		}
		
		Object csrfTokenFormItemKey = request.getAttribute("csrf_token_form_item_key");
		if (csrfTokenFormItemKey != null) {
			csrfTokenUtils.remove(csrfTokenFormItemKey.toString(),request,response);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public void setCsrfTokenUtils(CsrfTokenUtils csrfTokenUtils) {
		this.csrfTokenUtils = csrfTokenUtils;
	}

}
