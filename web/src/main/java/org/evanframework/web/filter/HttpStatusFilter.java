package org.evanframework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.evanframework.web.http.HttpStatusServletResponse;

/**
 * Http输出状态码过滤器
 * @since 1.0
 */
public class HttpStatusFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {


	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpStatusServletResponse response2 = new HttpStatusServletResponse((HttpServletResponse) response);
		chain.doFilter(request, response2);
	}

	@Override
	public void destroy() {

	}
}
