package com.ancun.core.web.velocity.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.ancun.core.web.utils.CsrfTokenUtils;

public class CsrfTokenTool {
	private CsrfTokenUtils csrfTokenUtils;

	public void init(Object obj) {
		WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		csrfTokenUtils = context.getBean(CsrfTokenUtils.class);
	}

	public String[] getToken(HttpServletRequest request,HttpServletResponse response) {
		return csrfTokenUtils.getToken(request,response);
	}
}
