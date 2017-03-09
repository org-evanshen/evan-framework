package com.ancun.core.web.session;

import javax.servlet.http.HttpServletRequest;

import com.ancun.core.model.OperatorAgent;

/**
 * 
 * 
 * <p>
 * create at 2014年4月15日 上午12:33:42
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */

public abstract class AbstractOperatorAgentSession<T extends OperatorAgent> implements OperatorAgentSession {

	@Override
	public abstract T get(HttpServletRequest request);
}
