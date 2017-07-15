package org.evanframework.web.session;

import javax.servlet.http.HttpServletRequest;

import org.evanframework.dto.OperatorAgent;

/**
 *
 */
public interface OperatorAgentSession {
	OperatorAgent get(HttpServletRequest request);
}
