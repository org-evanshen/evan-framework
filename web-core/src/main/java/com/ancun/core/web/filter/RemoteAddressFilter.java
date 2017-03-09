package com.ancun.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * �����������ͻ���ip�����ص�����
 * 
 * @author fish
 * 
 */
public class RemoteAddressFilter implements Filter {

	private static final String ForwardParameterName = "ForwardParameter";

	private String forwardParameter = "X-Forwarded-For";// ȱʡ�� ���� remote address

	// ip ��http head
	public void init(FilterConfig fc) throws ServletException {
		String get = fc.getInitParameter(ForwardParameterName);
		if (get != null) {
			forwardParameter = get.trim();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			// ��http servlet request ,������
			fc.doFilter(request, response);
			return;
		}
		if (request instanceof RemoteIpRequestWrapper) {
			// �Ѿ������,������
			fc.doFilter(request, response);
			return;
		}
		HttpServletRequest hsr = (HttpServletRequest) request;
		final String forward = hsr.getHeader(forwardParameter);
		if (forward == null) {
			fc.doFilter(request, response);
			return;
		}
		fc.doFilter(new RemoteIpRequestWrapper(hsr, forward), response);
	}

	public void destroy() {
	}

	private static class RemoteIpRequestWrapper extends
			HttpServletRequestWrapper {
		private String remoteIp;

		public RemoteIpRequestWrapper(HttpServletRequest request, String ip) {
			super(request);
			this.remoteIp = ip;
		}

		@Override
		public String getRemoteAddr() {
			return remoteIp;
		}
	}

}
