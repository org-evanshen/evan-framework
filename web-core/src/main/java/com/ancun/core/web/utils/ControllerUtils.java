package com.ancun.core.web.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.PathMatcher;

import com.ancun.core.datadict.DataDictionaryService;
import com.ancun.core.model.DataDictionary;
import com.ancun.core.sysconfig.SysConfig;
import com.ancun.core.web.WebCoreConstants;

// @Component
public class ControllerUtils {

	private static final String REDIRECT_MSG_COOKIE_KEY = "redirectMsg";

	@Autowired
	private SysConfig sysConfig;
	@Autowired(required = false)
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private PathMatcher pathMatcher;

	/**
	 * 直接输出纯HTML.
	 */
	public void printHtml(String text, HttpServletResponse response) {
		HttpUtils.printHtml(text, response, sysConfig.getWebEncoding());
	}

	/**
	 * 直接输出纯字符串.
	 */
	public void printText(String text, HttpServletResponse response) {
		HttpUtils.printText(text, response, sysConfig.getWebEncoding());
	}

	// /**
	// *
	// */
	// public void printSuccess(HttpServletResponse response, String
	// dataJsonStr) {
	// WebUtils.printHtml( getSuccess(dataJsonStr), response,
	// systemParameterService.getWebEncoding());
	// }

	public static List<String> convertRequestParamsToList(HttpServletRequest request, String key, String... defaults) {
		String[] values = request.getParameterValues("key");
		if (values == null || values.length == 0) {
			values = defaults;
		}

		List<String> list = new ArrayList<String>(values.length);

		for (String v : values) {
			list.add(v);
		}
		return list;
	}

	// @Deprecated
	// public static void putPageParameterFromCookyjar(PageParameter
	// pageParameter, Cookyjar cookyjar) {
	// PageCookie pageCookie = (PageCookie)
	// cookyjar.getObject(PageCookie.class); // 从cookie中读取返回前的page
	// if (pageCookie != null) {
	// PageParameter pageParameter1 = pageCookie.getPage();
	// pageParameter.setPageNo(pageParameter1.getPageNo());
	// pageParameter.setPageSize(pageParameter1.getPageSize());
	// pageParameter.setSort(pageParameter1.getSort());
	// pageParameter.clear();
	// pageParameter.putAll(pageParameter1);
	// }
	// }
	//
	// @Deprecated
	// public static PageParameter getPageParameterFromCookyjar(Cookyjar
	// cookyjar) {
	// PageCookie pageCookie = (PageCookie)
	// cookyjar.getObject(PageCookie.class); // 从cookie中读取返回前的page
	// PageParameter pageParameter = QueryParameterFactory.getPageParameter();
	// if (pageCookie != null) {
	// PageParameter pageParameter1 = pageCookie.getPage();
	// pageParameter.setPageNo(pageParameter1.getPageNo());
	// pageParameter.setPageSize(pageParameter1.getPageSize());
	// pageParameter.setSort(pageParameter1.getSort());
	// pageParameter.clear();
	// pageParameter.putAll(pageParameter1);
	// }
	// return pageParameter;
	// }

	/**
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2012-11-12 下午3:23:05 <br>
	 * 
	 * @param model
	 */
	public void renderDatadict(Model model, String... groups) {
		if (dataDictionaryService != null) {

			StringBuilder s = new StringBuilder(2048);
			List<DataDictionary> dds = null;
			for (String g : groups) {
				dds = dataDictionaryService.getByGroup(g);
				s.append("App.DataDictArray['" + g + "']=" + getByGroupForValueTextArray(dds) + ";");
				s.append("App.DataDictMap['" + g + "']=" + getByGroupForValueToText(dds) + ";");
			}

			model.addAttribute("screen_dataDictData", s);

		}
	}	

	public static String createSuccessResult() {
		return "{type:success}";
	}

	public void responseRedirectMsg(String msg, HttpServletResponse response) {
		CookieUtil.save(REDIRECT_MSG_COOKIE_KEY, msg, -1, response, sysConfig.getWebEncoding());
	}

	public void requestRedirectMsg(String msg, HttpServletRequest request) {
		CookieUtil.read(REDIRECT_MSG_COOKIE_KEY, request, sysConfig.getWebEncoding());
	}

	public boolean isListBack(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		return pathMatcher.match("/**/" + WebCoreConstants.LIST_BACK_ACTION_NAME, requestUri);
	}

	public void saveQueryToCookie(Object query, HttpServletResponse response) {
		CookieUtil.save("queryCookie", query, -1, response, sysConfig.getWebEncoding());
	}

	public <T> T getQueryFromCookie(Class<T> c, HttpServletRequest request) {
		T query = CookieUtil.read(c, "queryCookie", request, sysConfig.getWebEncoding());

		if (query == null) {
			try {
				query = c.newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}

		return query;
	}

	public void removeQueryCookie(HttpServletRequest request, HttpServletResponse response) {
		CookieUtil.remove("queryCookie", request, response);

	}

	private String getByGroupForValueTextArray(List<DataDictionary> dds) {
		StringBuilder s = new StringBuilder(512);
		s.append('[');
		if (dds != null) {
			int i = 0;
			for (DataDictionary o : dds) {
				if (i > 0) {
					s.append(",");
				}
				s.append("{'value':'" + o.getValue() + "','text':'" + o.getText() + "'}");
				i++;
			}
		}
		s.append(']');
		return s.toString();
	}

	private String getByGroupForValueToText(List<DataDictionary> dds) {
		StringBuilder s = new StringBuilder(512);
		s.append('{');
		if (dds != null) {
			int i = 0;
			for (DataDictionary o : dds) {
				if (i > 0) {
					s.append(",");
				}
				s.append("'" + o.getValue() + "':");
				s.append("{'text':'" + o.getText() + "'");

				if (StringUtils.isNotBlank(o.getColor())) {
					s.append(",'color':'" + o.getColor() + "'");
				}
				s.append("}");
				i++;
			}
		}
		s.append('}');
		return s.toString();
	}

}
