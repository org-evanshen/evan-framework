package com.ancun.core.web.session;

import javax.servlet.http.HttpServletRequest;

import com.ancun.core.model.OperatorAgent;

public interface OperatorAgentSession {
	OperatorAgent get(HttpServletRequest request);
}
