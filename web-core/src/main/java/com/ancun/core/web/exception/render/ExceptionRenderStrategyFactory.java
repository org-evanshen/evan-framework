package com.ancun.core.web.exception.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.sysconfig.SysConfig;
import com.ancun.core.utils.JsonUtils;
import com.ancun.core.web.WebCoreConstants;
import com.ancun.core.web.utils.HttpUtils;
import com.ancun.core.web.webapi.AncunApiResponse;

/***
 * 异常渲染
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-1-10 下午12:28:13
 */
public class ExceptionRenderStrategyFactory {
	private static SysConfig sysConfig;

	private final static Logger log = LoggerFactory.getLogger(ExceptionRenderStrategyFactory.class);

	/***
	 * 根据request中的内容，来取得对应的异常渲染器
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2013-1-10 下午12:29:14 <br>
	 * 
	 * @param request
	 *
	 */
	public ExceptionRenderStrategy getStrategy(HttpServletRequest request) {
		ExceptionRenderStrategy exceptionRenderStrategy = null;
		String callbackJavascriptFunction = request
				.getParameter(JavascriptFunctionExceptionRender.JAVASCRIPT_FUNCTION_REQUEST_KEY);
		if (StringUtils.isNotBlank(callbackJavascriptFunction)) {
			exceptionRenderStrategy = JavascriptFunctionExceptionRender.getInstance();
		} else if (StringUtils.isNotBlank(request.getParameter("isExtjsForm"))) {// ext
																					// 表单提交
			exceptionRenderStrategy = ExtjsFormExceptionRender.getInstance();
		} else if (HttpUtils.isAjaxRequest(request) || HttpUtils.isFormPost(request)) {
			exceptionRenderStrategy = AjaxExceptionRender.getInstance();
		} else {
			exceptionRenderStrategy = NormalExceptionRender.getInstance();
		}

		log.info("Exception render is [" + exceptionRenderStrategy.getClass() + "]");

		return exceptionRenderStrategy;
	}

	/**
	 * 普通页面渲染
	 * <p>
	 * 
	 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
	 * @version 2013-1-10 下午12:29:56
	 */
	private static class NormalExceptionRender implements ExceptionRenderStrategy {
		private final static ExceptionRenderStrategy exceptionRenderStrategy = new NormalExceptionRender();

		private NormalExceptionRender() {
		}

		/**
		 * 获取实例,这里为单例模式
		 * <p>
		 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
		 * version: 2013-1-10 下午12:31:54 <br>
		 * 
		 *
		 */
		public static ExceptionRenderStrategy getInstance() {
			return exceptionRenderStrategy;
		}

		@Override
		public void render(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request,
						   HttpServletResponse response, Exception ex) {
			if (StringUtils.isBlank(mv.getViewName())) {
				mv.setViewName(WebCoreConstants.PAGE_MSG_VM_PATH);
				mv.addObject(WebCoreConstants.PAGE_MSG_MODEL_KEY, msg);
				// mv.setViewName("forword:/"+CoreConstants.URL_MSG);
			}
		}
	}

	/**
	 * ajax渲染
	 * <p>
	 * 
	 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
	 * @version 2013-1-10 下午12:30:09
	 */
	private static class AjaxExceptionRender implements ExceptionRenderStrategy {
		private final static ExceptionRenderStrategy exceptionRenderStrategy = new AjaxExceptionRender();

		private AjaxExceptionRender() {
		}

		/**
		 * 获取实例,这里为单例模式
		 * <p>
		 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
		 * version: 2013-1-10 下午12:31:54 <br>
		 * 
		 *
		 */
		public static ExceptionRenderStrategy getInstance() {
			return exceptionRenderStrategy;
		}

		@Override
		public void render(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request,
				HttpServletResponse response, Exception ex) {
			response.setHeader(WebCoreConstants.EXCEPTION_RESPONSE_HEAD_KEY, msg.getCode() + "");
			// response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			HttpUtils.printJson(JsonUtils.beanToJSON(msg), response, sysConfig.getWebEncoding());
		}
	}

	/**
	 * Extjs异常渲染
	 * <p>
	 * 
	 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
	 * @version 2013-1-10 下午12:30:21
	 */
	private static class ExtjsFormExceptionRender implements ExceptionRenderStrategy {
		private final static ExceptionRenderStrategy exceptionRenderStrategy = new ExtjsFormExceptionRender();

		private ExtjsFormExceptionRender() {
		}

		/**
		 * 获取实例,这里为单例模式
		 * <p>
		 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
		 * version: 2013-1-10 下午12:31:54 <br>
		 * 
		 *
		 */
		public static ExceptionRenderStrategy getInstance() {
			return exceptionRenderStrategy;
		}

		@Override
		public void render(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request,
				HttpServletResponse response, Exception ex) {
			HttpUtils.printJson("{success:false,msg:" + JsonUtils.beanToJSON(msg) + "}", response,
					sysConfig.getWebEncoding());
		}
	}

	/**
	 * 用javascript回调函数来渲染异常
	 * <p>
	 * 
	 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
	 * @version 2013-1-10 下午12:30:37
	 */
	private static class JavascriptFunctionExceptionRender implements ExceptionRenderStrategy {
		private final static ExceptionRenderStrategy exceptionRenderStrategy = new JavascriptFunctionExceptionRender();
		public final static String JAVASCRIPT_FUNCTION_REQUEST_KEY = "callbackJavascriptFunction";

		private JavascriptFunctionExceptionRender() {
		}

		/**
		 * 获取实例,这里为单例模式
		 * <p>
		 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
		 * version: 2013-1-10 下午12:31:54 <br>
		 * 
		 *
		 */
		public static ExceptionRenderStrategy getInstance() {
			return exceptionRenderStrategy;
		}

		@Override
		public void render(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request,
				HttpServletResponse response, Exception ex) {
			String callbackJavascriptFunction = request.getParameter(JAVASCRIPT_FUNCTION_REQUEST_KEY);
			String json = JsonUtils.beanToJSON(msg);
			HttpUtils.printHtml("<script>" + callbackJavascriptFunction + "("+json+");</script>", response, sysConfig.getWebEncoding());
		}
	}

	@Autowired
	public void setSysConfig(SysConfig sysConfig) {
		ExceptionRenderStrategyFactory.sysConfig = sysConfig;
	}
}
