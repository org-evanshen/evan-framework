package com.ancun.core.web.exception.resolvers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.ancun.core.web.webapi.AncunApiResponse;

public interface ExceptionResolverStrategy {
	void resolver(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request, HttpServletResponse response,
				  Exception ex);
}
