package org.evanframework.web.monitor;

import org.evanframework.syslog.TimeProfiler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class MonitorIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (TimeProfiler.isProfileEnable()) {
            if (HandlerMethod.class.isInstance(handler)) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                Class<?> clazz = method.getDeclaringClass();
                StringBuilder log = new StringBuilder(String.format("UrlMapping: Url[%s],params[%s],method[%s.%s]",
                        request.getRequestURI(),
                        request.getParameterMap() + "",
                        clazz.getName(),
                        method.getName()));

//                if (modelAndView != null) {
//                    log.append(modelAndView + "");
//                }
                TimeProfiler.beginTask(log.toString());
            } else {
                TimeProfiler.beginTask(handler.toString());
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (TimeProfiler.isProfileEnable()) {
            TimeProfiler.endTask();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }
}
