package com.ancun.core.web.exception.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.web.webapi.AncunApiResponse;
import com.ancun.core.web.webapi.AncunApiResponseCode;

/***
 * 系统异常解决器
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-12-20 下午2:11:24
 */

public class SystemExceptionResolver implements ExceptionResolverStrategy {
	private final Logger logger = LoggerFactory.getLogger(SystemExceptionResolver.class);

	@Override
	public void resolver(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request, HttpServletResponse response, Exception ex) {
		logger.error(ex.getMessage(), ex);

		msg.setMsg("非常抱歉，系统出错，请稍候再试！");
		msg.setCode(AncunApiResponseCode._1000002.getCode());
	}	
}
