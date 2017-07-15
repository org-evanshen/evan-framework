package org.evanframework.web.utils;

import org.evanframework.datadict.service.dto.DataDictionary;
import org.evanframework.web.WebCoreConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class ControllerUtils {
    private static final String REDIRECT_MSG_COOKIE_KEY = "redirectMsg";

    private PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 直接输出纯HTML.
     */
    public void printHtml(String text, HttpServletResponse response, String encoding) {
        HttpUtils.printHtml(text, response, encoding);
    }

    /**
     * 直接输出纯字符串.
     */
    public void printText(String text, HttpServletResponse response, String encoding) {
        HttpUtils.printText(text, response, encoding);
    }

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

    public static String createSuccessResult() {
        return "{type:success}";
    }

    public void responseRedirectMsg(String msg, HttpServletResponse response, String encoding) {
        CookieUtil.save(REDIRECT_MSG_COOKIE_KEY, msg, -1, response, encoding);
    }

    public void requestRedirectMsg(String msg, HttpServletRequest request, String encoding) {
        CookieUtil.read(REDIRECT_MSG_COOKIE_KEY, request, encoding);
    }

    public boolean isListBack(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return pathMatcher.match("/**/" + WebCoreConstants.LIST_BACK_ACTION_NAME, requestUri);
    }

    public void saveQueryToCookie(Object query, HttpServletResponse response, String encoding) {
        CookieUtil.save("queryCookie", query, -1, response, encoding);
    }

    public <T> T getQueryFromCookie(Class<T> c, HttpServletRequest request, String encoding) {
        T query = CookieUtil.read(c, "queryCookie", request, encoding);

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
