package com.ancun.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author fish
 * 
 */
public class TrimParametersFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException {
		if (request instanceof TrimParametersRequestWrapper) {
			fc.doFilter(request, response);
			return;
		}
		if (!(request instanceof HttpServletRequest)
				|| !(response instanceof HttpServletResponse)) {
			throw new ServletException(
					"TrimParametersFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		TrimParametersRequestWrapper trimRequest = new TrimParametersRequestWrapper(
				httpRequest);
		fc.doFilter(trimRequest, response);
	}

}
