package com.ancun.core.web.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fish
 * 
 */
public class NoSessionFilter implements Filter {

	private final String AlreadFilteredTag = NoSessionFilter.class.getName()
			+ ".FILTERED";

	public void init(FilterConfig cfg) throws ServletException {

	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request.getAttribute(AlreadFilteredTag) == null) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			NoSessionRequestWrapper wrapper = new NoSessionRequestWrapper(
					httpRequest);
			try {
				chain.doFilter(wrapper, response);
			} finally {
				request.removeAttribute(AlreadFilteredTag);
			}
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

}