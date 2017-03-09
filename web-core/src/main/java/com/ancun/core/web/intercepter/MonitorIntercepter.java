package com.ancun.core.web.intercepter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.profiler.TimeProfiler;

public class MonitorIntercepter implements HandlerInterceptor {

	//private static final Logger log = LoggerFactory.getLogger(LogIntercepter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//		if (HandlerMethod.class.isInstance(handler)) {
		//			HandlerMethod handlerMethod = (HandlerMethod) handler;
		//			Method method = handlerMethod.getMethod();
		//			if (log.isDebugEnabled()) {
		//				log.debug("===Controller method:" + method);
		//				//log.debug("Method:"+ method.get);
		//			}
		//
		//		}

		if (TimeProfiler.isProfileEnable()) {
			invokeWithProfile(handler);
		}

		return true;
	}

	private void invokeWithProfile(Object handler) {
		if (HandlerMethod.class.isInstance(handler)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Class<?> clazz = method.getDeclaringClass();
			String className = ClassUtils.getShortClassName(clazz);
			TimeProfiler.beginTask(new StringBuilder(className).append(':').append(method.getName())
					.toString());
		} else {
			TimeProfiler.beginTask(handler.toString());
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//		if (log.isDebugEnabled()) {
		//			log.debug("===Controller model:" + modelAndView.getModel());
		//			log.debug("===Controller view:" + modelAndView.getViewName());
		//			//log.debug("Method:"+ method.get);
		//		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws Exception {
		if (TimeProfiler.isProfileEnable()) {
			TimeProfiler.endTask();
		}
	}
}
