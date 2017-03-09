package com.ancun.core.web.velocity.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

public class FixedVelocityConfigurer extends VelocityConfigurer {
	private static final String VELOCIMACRO_LIBRARY = "velocimacro.library";

	// private VelocityConfigurer velocityConfigurer;

	private String velocimacroLibrary;

	public FixedVelocityConfigurer(String velocimacroLibrary) {
		this.velocimacroLibrary = velocimacroLibrary;
	}

	public FixedVelocityConfigurer() {
	}

	public void setVelocityProperties(Properties velocityProperties) {
		if (this.velocimacroLibrary != null) {

			String current = velocityProperties.getProperty(VELOCIMACRO_LIBRARY);
			String value = null;
			if (StringUtils.isNotBlank(current)) {
				value = current + "," + velocimacroLibrary;
			} else {
				value = velocimacroLibrary;
			}
			velocityProperties.setProperty(VELOCIMACRO_LIBRARY, value);
		}
		super.setVelocityProperties(velocityProperties);
	}

	// public void setVelocityConfigurer(VelocityConfigurer velocityConfigurer)
	// {
	// this.velocityConfigurer = velocityConfigurer;
	// }
}
