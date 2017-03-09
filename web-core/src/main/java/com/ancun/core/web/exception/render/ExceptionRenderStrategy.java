package com.ancun.core.web.exception.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.web.webapi.AncunApiResponse;

/**
 * 异常渲染策略
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-2-4 上午9:43:17
 */
public interface ExceptionRenderStrategy {
	void render(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request, HttpServletResponse response, Exception ex);
}
