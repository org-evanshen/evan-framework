package org.evanframework.web.http;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 在 http 返回上加上状态码
 * @since 1.0
 */
public class HttpStatusServletResponse extends HttpServletResponseWrapper {
	private int httpStatus;

	public HttpStatusServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void sendError(int sc) throws IOException {
		httpStatus = sc;
		super.sendError(sc);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		httpStatus = sc;
		super.sendError(sc, msg);
	}

	@Override
	public void setStatus(int sc) {
		httpStatus = sc;
		super.setStatus(sc);
	}

	public int getStatus() {
		return httpStatus;
	}

}
