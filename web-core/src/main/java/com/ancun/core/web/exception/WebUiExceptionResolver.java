package com.ancun.core.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.web.exception.render.ExceptionRenderStrategy;
import com.ancun.core.web.exception.render.ExceptionRenderStrategyFactory;
import com.ancun.core.web.exception.resolvers.ExceptionResolverStrategy;
import com.ancun.core.web.exception.resolvers.ExceptionResolverStrategyFactory;
import com.ancun.core.web.webapi.AncunApiResponse;

/**
 * 异常解析类
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-3-25 下午03:38:45
 */
public class WebUiExceptionResolver implements HandlerExceptionResolver {

	private ExceptionResolverStrategyFactory exceptionResolverStrategyFactory;

	private ExceptionRenderStrategyFactory exceptionRenderStrategyFactory;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {

		ModelAndView mv = new ModelAndView();
		AncunApiResponse msg = new AncunApiResponse();

		ExceptionResolverStrategy exceptionResolverStrategy = exceptionResolverStrategyFactory.create(ex);
		resolver(exceptionResolverStrategy, mv, msg, request, response, ex);

		render(exceptionRenderStrategyFactory.getStrategy(request), mv, msg, request, response, ex);

		return mv;
	}

	/**
	 * 解决异常，策略模式
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2013-2-4 上午10:48:10 <br>
	 * 
	 * @param strategy
	 * @param mv
	 * @param msg
	 * @param request
	 * @param response
	 * @param ex
	 */
	private void resolver(ExceptionResolverStrategy strategy, ModelAndView mv, AncunApiResponse msg,
			HttpServletRequest request, HttpServletResponse response, Exception ex) {
		strategy.resolver(mv, msg, request, response, ex);
	}

	/**
	 * 渲染异常信息，策略模式
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2013-2-4 上午10:48:30 <br>
	 * 
	 * @param exceptionRenderStrategy
	 * @param mv
	 * @param msg
	 * @param request
	 * @param response
	 * @param ex
	 */
	private void render(ExceptionRenderStrategy exceptionRenderStrategy, ModelAndView mv, AncunApiResponse msg,
			HttpServletRequest request, HttpServletResponse response, Exception ex) {
		exceptionRenderStrategy.render(mv, msg, request, response, ex);
	}

	public void setExceptionResolverStrategyFactory(
			ExceptionResolverStrategyFactory exceptionResolverStrategyFactory) {
		this.exceptionResolverStrategyFactory = exceptionResolverStrategyFactory;
	}

	public void setExceptionRenderStrategyFactory(ExceptionRenderStrategyFactory exceptionRenderStrategyFactory) {
		this.exceptionRenderStrategyFactory = exceptionRenderStrategyFactory;
	}
}
