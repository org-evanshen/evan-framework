package com.ancun.core.web.velocity.tools;

import java.util.Map;

import com.ancun.core.utils.BeanUtils;

public class BeanTool {
	public Map<String, ?> beanToMap(Object bean) {
		return BeanUtils.beanToMap(bean);
	}
}
