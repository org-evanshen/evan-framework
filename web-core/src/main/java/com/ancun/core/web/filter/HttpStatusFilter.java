package com.ancun.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.ancun.core.web.http.HttpStatusServletResponse;

public class HttpStatusFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpStatusServletResponse response2 = new HttpStatusServletResponse((HttpServletResponse) response);
		chain.doFilter(request, response2);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
