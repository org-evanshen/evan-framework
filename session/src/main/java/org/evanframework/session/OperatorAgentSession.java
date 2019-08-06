package org.evanframework.session;

import javax.servlet.http.HttpServletRequest;

import org.evanframework.model.result.OperatorAgent;

/**
 *
 */
public interface OperatorAgentSession {
	OperatorAgent get(HttpServletRequest request);
}
