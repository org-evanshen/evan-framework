package com.ancun.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UrlPathHelper;

import com.ancun.core.Excludor;
import com.ancun.core.utils.PathUtils;
import com.ancun.core.web.utils.WebContextUtils;

/**
 * 
 * 
 * <p>
 * create at 2014年4月18日 下午5:25:10
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public abstract class AbstractOperatorContextFilter implements Filter {

	private Excludor excludor = new Excludor();

	@Autowired
	protected UrlPathHelper urlPathHelper;

	@Autowired
	protected WebContextUtils webContextUtils;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		//String requestPath = urlPathHelper.getPathWithinApplication(request);		
		String requestPath = urlPathHelper.getPathWithinServletMapping(request);

		if (!PathUtils.matches(requestPath, excludor.getExcludes())) {
			process(request);
		}

		chain.doFilter(request, response);

	}

	public abstract void process(HttpServletRequest request);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	public void setExcludor(Excludor excludor) {
		this.excludor = excludor;
	}
}
