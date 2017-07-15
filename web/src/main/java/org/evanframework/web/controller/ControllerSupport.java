package org.evanframework.web.controller;


import org.evanframework.sysconfig.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;

import org.evanframework.web.utils.ControllerUtils;

public class ControllerSupport {
	@Autowired
	protected ControllerUtils controllerUtils;

	@Autowired
	protected SysConfig sysConfig;
	
}
