package com.ancun.core.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.utils.BeanUtils;
import com.ancun.core.utils.Encodes;

/**
 * http请求和响应的工具类，主要处理字符编码和其它一些信息
 * 
 * @author tao.wangt
 * @version $Id: HttpUtils.java,v 0.1 2009-12-28 涓嬪崍12:56:41 tao.wangt Exp $
 */
@SuppressWarnings("rawtypes")
public class HttpUtils {
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	private static final String CONTENT_ATTRIBUTE = "javax.servlet.content";

	/**
	 * 获取可修改的http请求参数列表
	 * 
	 * @param request
	 *
	 */
	public static Map<String, String> getRequestParameters(HttpServletRequest request) {
		Map<String, String> paraMap = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			if (null != values && values.length > 0) {
				StringBuilder valueSb = new StringBuilder();
				for (int i = 0; i < values.length; i++) {
					valueSb.append(values[i]).append(",");
				}
				valueSb.deleteCharAt(valueSb.length() - 1);
				if (valueSb.length() > 0 && StringUtils.isNotBlank(name)) {
					paraMap.put(name, valueSb.toString());
				}
			}

		}
		return paraMap;
	}

	public static Map<String, String> getRequestURLDecodeParameters(HttpServletRequest request, String encoding) {
		Map<String, String> paraMap = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);

			if (null != values && values.length > 0) {
				StringBuilder valueSb = new StringBuilder();
				for (int i = 0; i < values.length; i++) {
					valueSb.append(values[i]).append(",");
				}
				valueSb.deleteCharAt(valueSb.length() - 1);
				if (valueSb.length() > 0 && StringUtils.isNotBlank(name)) {
					String valueStr = valueSb.toString();
					valueStr = Encodes.urlEncode(valueStr, encoding);
					paraMap.put(name, valueStr);
				}
			}
		}
		return paraMap;
	}

	/**
	 * 获取http get方式经编码后的请求串
	 * 
	 * @param queryMap
	 * @param encoding
	 *
	 */
	// public static String getEncodedQueryString(Map<String, String[]>
	// queryMap, String encoding) {
	// StringBuilder sb = new StringBuilder();
	// if (null != queryMap && 0 != queryMap.size() &&
	// StringUtils.isNotBlank(encoding)) {
	// for (String key : queryMap.keySet()) {
	// String[] values = queryMap.get(key);
	// if (values != null && values.length > 0) {
	// String val = values[0];
	// if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(val)) {
	// sb.append(sb.length() > 0 ? "&" : "");
	// key = Encodes.urlEncode(key, encoding);
	// val = Encodes.urlEncode(val, encoding);
	// sb.append(key + "=" + val);
	// }
	// }
	// }
	// }
	// return sb.toString();
	// }

	public static String getEncodedQueryString(Map<String, String> queryMap, String encoding) {
		StringBuilder sb = new StringBuilder();
		if (null != queryMap && 0 != queryMap.size() && StringUtils.isNotBlank(encoding)) {
			for (String key : queryMap.keySet()) {
				String val = queryMap.get(key);
				if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(val)) {
					sb.append(sb.length() > 0 ? "&" : "");
					key = Encodes.urlEncode(key, encoding);
					val = Encodes.urlEncode(val, encoding);
					sb.append(key + "=" + val);
				}
			}
		}
		return sb.toString();
	}

	public static String getEncodedQueryString(Serializable arg0, final String encoding) {
		final StringBuilder sb = new StringBuilder();
		BeanUtils.eachProperties(arg0, new BeanUtils.EachPropertiesHandler() {
			@Override
			public void handler(String property, Object value) {
				sb.append(sb.length() > 0 ? "&" : "");
				String val = null;
				if (value != null) {
					val = Encodes.urlEncode(value.toString(), encoding);
				}
				if (StringUtils.isNotBlank(val)) {
					sb.append(property + "=" + val);
				}
			}
		});

		return sb.toString();
	}

	// public static String getEncodedUrl(String queryUrl, Map<String, String[]>
	// queryMap, String encoding) {
	// String queryString = getEncodedQueryString(queryMap, encoding);
	// return merginUrl(queryUrl, queryString);
	// }

	private static String merginUrl(String queryUrl, String queryString) {
		int queryIndex = queryUrl.indexOf("?");
		if (-1 != queryIndex) {
			if (StringUtils.isBlank(queryString)) {
				queryUrl = queryUrl.substring(0, queryIndex);
			} else {
				queryUrl = queryUrl + queryString;
			}
		} else {
			if (StringUtils.isNotBlank(queryString)) {
				queryUrl = queryUrl + "?" + queryString;
			}
		}
		return queryUrl;
	}

	public static String getEncodedUrl(String queryUrl, Serializable arg0, String encoding) {
		String queryString = getEncodedQueryString(arg0, encoding);
		return merginUrl(queryUrl, queryString);
	}

	/**
	 * 判断网络是否可用,等同于访问网络
	 * 
	 * @param url
	 *
	 */
	public static boolean isCanConnection(String url) {
		try {
			URL urlInfo = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlInfo.openConnection();
			int state = conn.getResponseCode();
			if (state == 200) {
				return true;
			}
		} catch (Exception e) {
			log.error("the url:" + url + " is can't connection", e);
		}
		return false;
	}

	/**
	 * 是否ajax
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2012-5-9 下午4:03:41 <br>
	 * 
	 * @param request
	 *
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String xRequestedWith = request.getHeader("X-Requested-With");
		return StringUtils.equalsIgnoreCase("XMLHttpRequest", xRequestedWith);
	}

	/**
	 * 是够表单提交
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2012-6-5 上午9:36:58 <br>
	 * 
	 * @param request
	 *
	 */
	public static boolean isFormPost(HttpServletRequest request) {
		String contentType = request.getHeader("Content-Type");
		// return StringUtils.equalsIgnoreCase(contentType,
		// "application/x-www-form-urlencoded");
		return StringUtils.containsIgnoreCase(contentType, "application/x-www-form-urlencoded");
	}

	/**
	 * 输出HttpServletRequest的信息，包括header部分
	 * 
	 * @param request
	 *            <p>
	 *            author: ShenWei<br>
	 *            create at 2014年9月24日 上午11:28:40
	 */
	public static void printRequest(HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("request.getContentType():" + request.getContentType());
			log.debug("request.getContentLength():" + request.getContentLength());
			log.debug("request.getLocalAddr():" + request.getLocalAddr());
			log.debug("request.getLocalName():" + request.getLocalName());
			log.debug("request.getLocalPort():" + request.getLocalPort());
			log.debug("request.getPathInfo():" + request.getPathInfo());
			log.debug("request.getPathTranslated():" + request.getPathTranslated());
			log.debug("request.getProtocol():" + request.getProtocol());
			log.debug("request.getRemoteAddr():" + request.getRemoteAddr());
			log.debug("request.getRemoteHost():" + request.getRemoteHost());
			log.debug("request.getRemotePort():" + request.getRemotePort());
			log.debug("request.getRequestURI():" + request.getRequestURI());
			log.debug("request.getServletPath():" + request.getServletPath());
			log.debug("request.getScheme():" + request.getScheme());
			log.debug("request.getServerName():" + request.getServerName());
			log.debug("request.getServerPort():" + request.getServerPort());
			log.debug("request.getUserPrincipal():" + request.getUserPrincipal());

			@SuppressWarnings("unchecked")
			Enumeration<String> names = request.getHeaderNames();
			for (Enumeration<String> e = names; e.hasMoreElements();) {
				String thisName = e.nextElement().toString();
				String thisValue = request.getHeader(thisName);
				log.debug("request.getHeader(" + thisName + "):" + thisValue);
			}
		}

	}

	/**
	 * 直接输出纯Json.
	 */
	public static void printJson(String text, HttpServletResponse response, String webencoding) {
		doPrint(text, "application/json;charset=" + webencoding, response);
	}

	/**
	 * 直接输出纯XML.
	 */
	public static void printXML(String text, HttpServletResponse response, String webencoding) {
		doPrint(text, "application/xml;charset=" + webencoding, response);
	}

	/**
	 * 直接输出纯HTML.
	 */
	public static void printHtml(String text, HttpServletResponse response, String webencoding) {
		doPrint(text, "text/html;charset=" + webencoding, response);
	}

	/**
	 * 直接输出纯字符串.
	 */
	public static void printText(String text, HttpServletResponse response, String webencoding) {
		doPrint(text, "text/plain;charset=" + webencoding, response);
	}

	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后
	 */
	public static void doPrint(String text, String contentType, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType(contentType);

		try {
			out = response.getWriter();
			out.write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		} finally {
			if (out != null) {
				out.print("");
				out.close();
			}
		}
	}

	/**
	 * 获取请求参数
	 * 
	 * @param request
	 *  <p>
	 *         author: <a href="mailto:chenggang@ancun.com">chenggang</a><br>
	 *         create at: 2014年10月22日 下午5:39:34
	 */
	public static String getParametersAndValue(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		String queryString = "";
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				queryString += key + "=" + value + "&";
			}
		}
		// 去掉最后一个空格
		if (StringUtils.isNotBlank(queryString)) {
			queryString = queryString.substring(0, queryString.length() - 1);
		}
		return queryString;
	}

	public static String getRequestContent(HttpServletRequest request) throws IOException {
		String returnV = null;
		Object o = request.getAttribute(CONTENT_ATTRIBUTE);
		if (o == null) {
			// HttpInputMessage inputMessage = new
			// ServletServerHttpRequest(request);

			StringBuffer sb = new StringBuffer();
			PushbackInputStream pbis = new PushbackInputStream(request.getInputStream());	
			InputStreamReader isr = new InputStreamReader(pbis);
			BufferedReader br = new BufferedReader(isr);
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			returnV = sb.toString();
			request.setAttribute(CONTENT_ATTRIBUTE, returnV);
		} else {
			returnV = o.toString();
		}
		return returnV;
	}

	/**
	 * 获取URL和参数
	 * 
	 * @param request
	 *  <br>
	 *         create by <a href="mailto:shenwei@ancun.com">ShenWei</a> as
	 *         2010-11-4
	 */
	public static String getRequestURLAndQuery(HttpServletRequest request) {
		StringBuilder url = new StringBuilder();

		url.append(getAppURL(request));
		url.append(request.getPathInfo());

		if (request.getQueryString() != null) {
			url.append("?" + request.getQueryString());
		}

		return url.toString();
	}

	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *            内容的类型 <br>
	 *            输出json: text/x-json;charset= <br>
	 *            输出纯字符串: text/plain;charset= <br>
	 *            输出html: text/html;charset= <br>
	 *            输出xml: text/xml;charset=
	 */
	public static void render(String text, String contentType, HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType(contentType);

		try {
			out = response.getWriter();
			out.write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (out != null) {
				out.print("");
				out.close();
			}
		}
	}

	/**
	 * 获取客户端Ip <br>
	 * 当通过apache代理方式部署时，request.getRemoteAddr()方法无法获取真实的ip
	 * 
	 * @param request
	 *  客户端Ip
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){  
                //根据网卡取本机配置的IP  
                InetAddress inet=null;  
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (UnknownHostException e) {  
                	log.error(e.getMessage(), e);
                }  
                ip= inet.getHostAddress();  
            }  
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
		if(ip!=null && ip.indexOf(",")>0){
			ip =ip.substring(0, ip.indexOf(","));
		}
		return ip;
	}

	/**
	 * Convenience method to get the application's URL based on request
	 * variables.
	 * 
	 * @param request
	 *            the current request
	 *  URL to application
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		url.append(request.getServletPath());
		return url.toString();
	}
}
