package org.evanframework.web.velocity.tools;

import org.evanframework.web.csrf.CsrfTokenUtils;
import org.evanframework.web.velocity.resolver.FixedVelocityLayoutView;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @since 1.0
 */
public class CsrfTokenTool {
    private CsrfTokenUtils csrfTokenUtils;

    public void init(Object obj) {
        ViewToolContext ctx = (ViewToolContext) obj;
        ApplicationContext applicationContext = (ApplicationContext) ctx.get(FixedVelocityLayoutView.APPLICATIONCONTEXT_KEY);
        csrfTokenUtils = applicationContext.getBean(CsrfTokenUtils.class);

    }

    public String[] getToken(HttpServletRequest request, HttpServletResponse response) {
        return csrfTokenUtils.getToken(request, response);
    }
}
