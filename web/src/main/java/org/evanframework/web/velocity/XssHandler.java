package org.evanframework.web.velocity;

import java.util.HashSet;
import java.util.Set;

import org.evanframework.utils.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.util.RuntimeServicesAware;


public class XssHandler implements ReferenceInsertionEventHandler, RuntimeServicesAware {
	private static final Log logger = LogFactory.getLog(XssHandler.class);
	private static final String DirectOutputVariableConfiguration = "reference.insertion.event.handler.direct.variable.names";

	private Set<String> directOutputVariables = new HashSet<String>();

	@Override
	public Object referenceInsert(String reference, Object value) {
		if (value == null) {
			return value;
		}
		if (directOutputVariables.contains(reference)) {
			return value;
		}
//		if (value instanceof DirectOutput) {
//			return value;
//		}
		if (!(value instanceof String)) {
			return value;
		}
		return StringEscapeUtils.escapeHtml((String) value);
	}

	@Override
	public void setRuntimeServices(RuntimeServices rs) {
		String[] temp = rs.getConfiguration().getStringArray(DirectOutputVariableConfiguration);
		if (temp != null && temp.length > 0) {
			for (String s : temp) {
				if (StringUtils.isNotBlank(s)) {
					directOutputVariables.add(s.trim());
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("init DirectOutputVariable with:" + directOutputVariables);
		}
	}
}
