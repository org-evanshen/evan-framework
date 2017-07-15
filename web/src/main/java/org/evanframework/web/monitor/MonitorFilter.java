package org.evanframework.web.monitor;

import org.evanframework.sysconfig.SysConfig;
import org.evanframework.syslog.TimeProfiler;
import org.evanframework.utils.PathUtils;
import org.evanframework.web.support.Excludor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class MonitorFilter extends GenericFilterBean implements Filter {
    private int threshold = 50;

    private SysConfig sysConfig;
    private Excludor excludor;
    private UrlPathHelper urlPathHelper;

    public void init() {
        if (sysConfig != null) {
            String threshold = sysConfig.get("performance.monitor.threshold");
            if (StringUtils.isNotBlank(threshold)) {
                this.threshold = Integer.valueOf(threshold);
            }
        }
        if (excludor == null) {
            excludor = new Excludor();
        }
        if (urlPathHelper == null) {
            urlPathHelper = new UrlPathHelper();
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestPath = urlPathHelper.getPathWithinApplication(request);

        if (!PathUtils.matches(requestPath, excludor.getExcludes()) && TimeProfiler.isProfileEnable()) {
            doFilterWithProfile(servletRequest, servletResponse, chain);
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    private void doFilterWithProfile(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest hs = (HttpServletRequest) request;
        // hs.setCharacterEncoding(encoding);
        String profilerId = getProfilerId(hs);
        TimeProfiler profiler = TimeProfiler.start(profilerId, threshold);
        try {
            chain.doFilter(request, response);
        } finally {
            profiler.release(getProfilerName(hs));
        }
    }

    private String getProfilerId(HttpServletRequest hs) {
        StringBuffer id = hs.getRequestURL();
        return id.toString();
    }

    private String getProfilerName(HttpServletRequest hs) {
        StringBuffer id = hs.getRequestURL();
        id.append(' ');
        Enumeration<String> enumer = hs.getParameterNames();
        while (enumer.hasMoreElements()) {
            String name = enumer.nextElement().toString();
            String value = hs.getParameter(name);
            id.append('[').append(name).append('=').append(value).append("] ");
        }
        return id.toString();
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
    }

    public void setExcludor(Excludor excludor) {
        this.excludor = excludor;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }
}
