package com.ancun.core.web.controller;


import org.springframework.beans.factory.annotation.Autowired;

import com.ancun.core.sysconfig.SysConfig;
import com.ancun.core.web.utils.ControllerUtils;

public class ControllerSupport {
	@Autowired
	protected ControllerUtils controllerUtils;

	@Autowired
	protected SysConfig sysConfig;
	
}
