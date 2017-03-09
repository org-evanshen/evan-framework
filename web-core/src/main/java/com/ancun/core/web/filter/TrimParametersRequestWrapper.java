package com.ancun.core.web.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

/**
 * ��http�����е�value trim
 * 
 * @author fish
 * 
 */
public class TrimParametersRequestWrapper extends HttpServletRequestWrapper {

	private Map parameterMap;

	public TrimParametersRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String[] ss = this.getParameterValues(name);
		if (ss == null || ss.length == 0) {
			return null;
		}
		return ss[0];
	}

	@Override
	public String[] getParameterValues(String name) {
		return (String[]) this.getParameterMap().get(name);
	}

	@Override
	public Map getParameterMap() {
		if (this.parameterMap == null) {
			initParameterMap(this.getRequest());
		}
		return this.parameterMap;
	}

	private void initParameterMap(ServletRequest request) {
		Map ps = request.getParameterMap();
		this.parameterMap = new HashMap(ps.size());
		for (Iterator it = ps.entrySet().iterator(); it.hasNext();) {
			Entry en = (Entry) it.next();
			String[] values = (String[]) en.getValue();
			if (values == null) {
				this.parameterMap.put(en.getKey(), null);
				continue;
			}
			if (values.length == 0) {
				this.parameterMap.put(en.getKey(), values);
				continue;
			}
			String[] trims = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				trims[i] = StringUtils.trim(values[i]);
			}
			this.parameterMap.put(en.getKey(), trims);
		}
	}
}
