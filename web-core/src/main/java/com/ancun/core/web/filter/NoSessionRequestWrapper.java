package com.ancun.core.web.filter;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fish
 * 
 */
public class NoSessionRequestWrapper extends HttpServletRequestWrapper {

	private static final Logger log = LoggerFactory
			.getLogger(NoSessionRequestWrapper.class);

	private static HttpSession empty = new EmptyHttpSession();

	private HttpServletRequest request;

	public NoSessionRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public HttpSession getSession() {
		if (log.isTraceEnabled()) {
			log.trace("HttpServletRequest.getSession method is invoking in:"
					+ request.getRequestURI());
		}
		return empty;
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		if (log.isTraceEnabled()) {
			log.trace("HttpServletRequest.getSession method is invoking in:"
					+ request.getRequestURI());
		}
		return empty;
	}

	private static class EmptyHttpSession implements HttpSession {

		public Object getAttribute(String arg0) {
			return null;
		}

		public Enumeration getAttributeNames() {
			return null;
		}

		public long getCreationTime() {
			return 0;
		}

		public String getId() {
			return null;
		}

		public long getLastAccessedTime() {
			return 0;
		}

		public int getMaxInactiveInterval() {
			return 0;
		}

		public ServletContext getServletContext() {
			return null;
		}

		public HttpSessionContext getSessionContext() {
			return null;
		}

		public Object getValue(String arg0) {
			return null;
		}

		public String[] getValueNames() {
			return null;
		}

		public void invalidate() {

		}

		public boolean isNew() {
			return false;
		}

		public void putValue(String arg0, Object arg1) {

		}

		public void removeAttribute(String arg0) {

		}

		public void removeValue(String arg0) {

		}

		public void setAttribute(String arg0, Object arg1) {

		}

		public void setMaxInactiveInterval(int arg0) {

		}

	}

}
