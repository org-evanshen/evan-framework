package com.ancun.core.web.velocity.tools;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.ancun.core.sysconfig.SysConfig;

public class SysConfigTool {
	private SysConfig sysConfig;

	public void init(Object obj) {
		WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		sysConfig = context.getBean("sysConfig", SysConfig.class);
	}

	public String get(String key) {
		return sysConfig.get(key);
	}

	public String getProfile() {
		return sysConfig.getProfile().name();
	}

}
