package com.ancun.core.web.authority;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.ancun.core.Excludor;
import com.ancun.core.exception.PermissionDeniedException;
import com.ancun.core.model.OperatorAgent;
import com.ancun.core.utils.PathUtils;
import com.ancun.core.web.exception.NoLoginException;

/**
 * 用户权限控制拦截器,用途 :<br>
 * 1、判断是否登录 <br>
 * 2、判断权限<br>
 * <p>
 * <p>
 * <p>
 * <p>
 * create at 2014年5月2日 下午9:13:25
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public abstract class OperatorAuthInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(OperatorAuthInterceptor.class);

    /**
     * 没有配置权限控制注解的Methed缓存
     */
    protected Set<Method> noAuthCaches = Collections.synchronizedSet(new HashSet<Method>());

    protected Excludor excludor;

    @Autowired
    protected UrlPathHelper urlPathHelper;

    public abstract boolean pass(OperatorAgent user, Method handlerMethod);

    public abstract OperatorAgent getLoginUser();

    public void init() {
        if (excludor == null) {
            excludor = new Excludor();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = urlPathHelper.getPathWithinServletMapping(request);

        if (PathUtils.matches(requestPath, excludor.getExcludes())) {
            return true;
        }

        if (HandlerMethod.class.isInstance(handler)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (log.isDebugEnabled()) {
                log.debug("validate login user in servlet [" + requestPath + "]");
            }
            OperatorAgent loginUser = getLoginUser();
            if (loginUser == null) {
                if (log.isDebugEnabled()) {
                    log.debug("No login in servlet [" + requestPath + "]");
                }
                throw new NoLoginException();
            }
            if (!pass(loginUser, method)) {
                log.warn("user:{id:" + loginUser.getId() + ",account:" + loginUser.getAccount()
                        + "} no permission in servlet [" + requestPath + "]");
                throw new PermissionDeniedException();
            }
            return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    public void setExcludor(Excludor excludor) {
        this.excludor = excludor;
    }
}
