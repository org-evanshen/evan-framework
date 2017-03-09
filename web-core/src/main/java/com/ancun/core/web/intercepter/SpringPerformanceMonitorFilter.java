package com.ancun.core.web.intercepter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import com.ancun.core.profiler.TimeProfiler;
import com.ancun.core.sysconfig.SysConfig;

public class SpringPerformanceMonitorFilter extends GenericFilterBean implements Filter {
	private int threshold = 100;

	@Autowired
	private SysConfig sysConfig;

	public void init() {
		String threshold = sysConfig.get("performance.monitor.threshold");
		if (StringUtils.isNotBlank(threshold)) {
			this.threshold = Integer.valueOf(threshold);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (TimeProfiler.isProfileEnable()) {
			doFilterWithProfile(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	private void doFilterWithProfile(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hs = (HttpServletRequest) request;
		// hs.setCharacterEncoding(encoding);
		String profilerId = getProfilerId(hs);
		TimeProfiler profiler = TimeProfiler.start(profilerId, threshold);
		try {
			chain.doFilter(request, response);
		} finally {
			profiler.release(getProfilerName(hs));
		}
	}

	private String getProfilerId(HttpServletRequest hs) {
		StringBuffer id = hs.getRequestURL();
		return id.toString();
	}

	private String getProfilerName(HttpServletRequest hs) {
		StringBuffer id = hs.getRequestURL();
		id.append(' ');
		Enumeration<String> enumer = hs.getParameterNames();
		while (enumer.hasMoreElements()) {
			String name = enumer.nextElement().toString();
			String value = hs.getParameter(name);
			id.append('[').append(name).append('=').append(value).append("] ");
		}
		return id.toString();
	}

}
