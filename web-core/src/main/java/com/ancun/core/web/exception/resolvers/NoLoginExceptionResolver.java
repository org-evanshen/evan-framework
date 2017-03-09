package com.ancun.core.web.exception.resolvers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.ancun.core.sysconfig.SysConfig;
import com.ancun.core.web.utils.HttpUtils;
import com.ancun.core.web.utils.WebContextUtils;
import com.ancun.core.web.webapi.AncunApiResponse;
import com.ancun.core.web.webapi.AncunApiResponseCode;

/**
 * 登录失效异常解决器
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2012-12-20 下午2:10:43
 */
public class NoLoginExceptionResolver implements ExceptionResolverStrategy {
	private String loginSubSystemCode;
	private String loginUrl;
	private String loginReturnParameterKey;
	private String defaultLoginServletPath = "";

	private Map<String, String> loginServlets = new HashMap<String, String>(4);

	@Autowired
	private WebContextUtils webContextUtils;
	@Autowired
	private SysConfig sysConfig;
	@Autowired
	private PathMatcher pathMatcher;
	@Autowired
	private UrlPathHelper urlPathHelper;

	@Override
	public void resolver(ModelAndView mv, AncunApiResponse msg, HttpServletRequest request,
						 HttpServletResponse response, Exception ex) {
		String loginUrl = getSubSystemLoginUrl(request);
		if (HttpUtils.isAjaxRequest(request)) {
			msg.setResponseCode(AncunApiResponseCode._1000123);
			msg.setData(loginUrl);
		} else {
			if (StringUtils.isNotBlank(loginReturnParameterKey)) {
				String returnUrl = getReturnUrl(request);
				mv.addObject(loginReturnParameterKey, returnUrl);
				// try {
				// loginUrl += "?" + loginReturnParameterKey + "=" +
				// URLEncoder.encode(getReturnUrl(request),"UTF-8");
				// } catch (UnsupportedEncodingException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}

			mv.setViewName("redirect:" + loginUrl);
		}
	}

	private String getSubSystemLoginUrl(HttpServletRequest request) {
		String loginUrl1 = null;

		if (this.loginUrl != null) {
			loginUrl1 = this.loginUrl;
		} else {
			String loginSubSystemCode = null;
			String loginServletPath = null;
			if (loginServlets.isEmpty()) {
				loginSubSystemCode = this.loginSubSystemCode;
				loginServletPath = this.defaultLoginServletPath;
			} else {
				String requestUri = urlPathHelper.getPathWithinServletMapping(request);
				for (Entry<String, String> entry : loginServlets.entrySet()) {
					if (pathMatcher.match(entry.getKey(), requestUri)) {
						loginSubSystemCode = entry.getKey();
						loginServletPath = entry.getValue();
					}
				}
			}
			// if (StringUtils.isBlank(loginSubSystemCode)) {
			// throw new IllegalArgumentException("重定向登录页面失败，系统没有配置登录子系统");
			// }
			
			if (loginServletPath == null && !request.getRequestURI().equals(loginUrl)) {
				loginUrl1 = loginUrl + request.getContextPath();
			} else {
				loginUrl1 = loginUrl + request.getContextPath() + "/" + loginServletPath;
			}
		}

		return loginUrl1;
	}

	private String getReturnUrl(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder(256);
		sb.append(request.getRequestURL());
		appendRequestParameters(sb, request);
		return sb.toString();
		// try {
		// return URLEncoder.encode(sb.toString(), sysConfig.getWebEncoding());
		// } catch (UnsupportedEncodingException e) {
		// return null;
		// }
	}

	private void appendRequestParameters(StringBuilder sb, HttpServletRequest request) {
		Enumeration<?> en = request.getParameterNames();
		if (!en.hasMoreElements()) {
			return;
		}
		sb.append('?');
		while (en.hasMoreElements()) {
			String name = (String) en.nextElement();
			String[] values = request.getParameterValues(name);
			if (values == null || values.length == 0) {
				continue;
			}
			for (String v : values) {
				try {
					v = URLEncoder.encode(v, sysConfig.getWebEncoding());
				} catch (UnsupportedEncodingException ignore) {
				}
				sb.append(name).append('=').append(v).append('&');
			}
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setLoginReturnParameterKey(String loginReturnParameterKey) {
		this.loginReturnParameterKey = loginReturnParameterKey;
	}

	public void setLoginSubSystemCode(String loginSubSystemCode) {
		this.loginSubSystemCode = loginSubSystemCode;
	}

	public void setDefaultLoginServletPath(String loginServletPath) {
		this.defaultLoginServletPath = loginServletPath;
	}

	public void setLoginServlets(Map<String, String> loginServlets) {
		this.loginServlets = loginServlets;
	}
}
