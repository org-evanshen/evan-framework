package com.ancun.core.web.resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PathMatcher;

/**
 * 
 * request的url前缀和velocity路径前缀、布局前缀映射器
 * <p>
 * create at 2016年4月7日 下午12:38:14
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public class ViewPathExtendResolver {
	private static final Logger log = LoggerFactory.getLogger(ViewPathExtendResolver.class);

	private Map<String, String> requestUrlPrefixMappingViewPathPrefix = new HashMap<String, String>(8);

	private Map<String, String> requestUrlPrefixMappingLayout = new HashMap<String, String>(8);

	private Map<String, String> cache1 = new ConcurrentHashMap<String, String>(512);
	private Map<String, String> cache2 = new ConcurrentHashMap<String, String>(512);

	private String defaultViewPathPrefix;

	private Set<String> excludes;

	@Autowired
	private PathMatcher pathMatcher;

	public boolean viewIsMatch(String viewName) {
		boolean match = true;
		if (excludes != null) {
			for (String v : excludes) {
				if (pathMatcher.match("/" + v + "*", viewName)) {
					match = false;
					break;
				}
			}
		}
		return match;
	}

	public String getViewPathPrefix(String requestUrl) {
		String returnV = cache1.get(requestUrl);

		if (StringUtils.isBlank(returnV)) {
			for (Map.Entry<String, String> entry : requestUrlPrefixMappingViewPathPrefix.entrySet()) {
				if (pathMatcher.match(entry.getKey(), requestUrl)) {
					returnV = entry.getValue();
					cache1.put(requestUrl, returnV);
					break;
				}
			}
			if (returnV == null) {
				returnV = defaultViewPathPrefix;
				if (returnV == null) {
					returnV = "";
				}
				cache1.put(requestUrl, returnV);
			}
		}

		return returnV;
	}

	public String getLayout(String requestUrl) {
		String returnV = cache2.get(requestUrl);

		if (StringUtils.isBlank(returnV)) {
			for (Map.Entry<String, String> entry : requestUrlPrefixMappingLayout.entrySet()) {
				if (pathMatcher.match(entry.getKey(), requestUrl)) {
					returnV = entry.getValue();
					cache2.put(requestUrl, returnV);
					break;
				}
			}
		}

		return returnV;
	}

	public void setDefaultViewPathPrefix(String defaultViewPathPrefix) {
		this.defaultViewPathPrefix = defaultViewPathPrefix;

	}

	public void setRequestUrlPrefixMappingLayout(Map<String, String> requestUrlPrefixMappingLayout) {
		this.requestUrlPrefixMappingLayout = requestUrlPrefixMappingLayout;

		if (log.isDebugEnabled()) {
			for (Entry<String, String> entry : this.requestUrlPrefixMappingLayout.entrySet()) {
				log.debug("Inited requestUrlPrefixMappingLayout [path:" + entry.getKey() + ", Layout :"
						+ entry.getValue() + "]");
			}
		}
	}

	public void setRequestUrlPrefixMappingViewPathPrefix(Map<String, String> urlPrefixMappingViewPathPrefix) {
		this.requestUrlPrefixMappingViewPathPrefix = urlPrefixMappingViewPathPrefix;

		if (log.isDebugEnabled()) {
			for (Entry<String, String> entry : this.requestUrlPrefixMappingViewPathPrefix.entrySet()) {
				log.debug("Inited requestUrlPrefixMappingViewPathPrefix [path:" + entry.getKey()
						+ ", ViewPathPrefix:" + entry.getValue() + "]");
			}
		}
	}

	public void setExcludes(Set<String> excludes) {
		this.excludes = excludes;
	}
}
