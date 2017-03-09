package com.ancun.core.web.pagemeta;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * PageMetas 参数解析器
 * <p>
 * 作用： 用于渲染页面的meta
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-4-8 上午09:04:49
 */
public class PageMetasArgumentResolver implements WebArgumentResolver {

	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(PageMetas.class)) {
			PageMetas webParams = new PageMetasArray();
			return webParams;
		}
		return UNRESOLVED;
	}
}
