package com.ancun.core.web.argumentresolver;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public abstract class AbstractArgumentResolver<T> implements WebArgumentResolver {
	private static final Log log = LogFactory.getLog(AbstractArgumentResolver.class);

	private Class<T> classT;
	
	private String sortKey = "sort";

	@SuppressWarnings("unchecked")
	public AbstractArgumentResolver() {
		classT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(classT)) {

			T t = createT();

			Map<String, String[]> parameterMap = webRequest.getParameterMap();
			String[] values = null;// 每个参数值	
			for (Map.Entry<String, String[]> e : parameterMap.entrySet()) {
				values = e.getValue();
				if (values != null && values.length > 0) {
					pubValueToPageParameter(t, getValue(values), e.getKey());
				}
			}

			return t;
		}
		return UNRESOLVED;
	}

	protected abstract T createT();

	protected abstract void setValue(T t, String key, String value);

	private void pubValueToPageParameter(T t, String value, String key) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();

			if (log.isDebugEnabled()) {
				log.debug("pub query param [" + key + "=" + value + "]");
			}

			setValue(t, key, value);
		}
	}

	/**
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2012-7-30 下午1:26:56 <br>
	 * 
	 * @param values
	 */
	protected String getValue(String[] values) {
		StringBuilder value = new StringBuilder(64);
		int i = 0;
		for (String v : values) {
			if (i > 0) {
				value.append(",");
			}
			value.append(v);
			i++;
		}
		return value.toString();
	}

	protected String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	// String mothed = null;
	// if (ServletWebRequest.class.isInstance(webRequest)) {
	// ServletWebRequest servletWebRequest = (ServletWebRequest)
	// webRequest;
	// mothed = servletWebRequest.getRequest().getMethod();
	// }
}
