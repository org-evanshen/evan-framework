package org.evanframework.web.session;

import javax.servlet.http.HttpServletRequest;

import org.evanframework.dto.OperatorAgent;

/**
 *
 * create at 2014年4月15日 上午12:33:42
 * 
 * @author shen.wei
 * @version %I%, %G%
 *
 */
public abstract class AbstractOperatorAgentSession<T extends OperatorAgent> implements OperatorAgentSession {

	@Override
	public abstract T get(HttpServletRequest request);
}
