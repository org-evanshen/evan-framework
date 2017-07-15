package org.evanframework.web.velocity.tools;

import org.evanframework.sysconfig.SysConfig;
import org.evanframework.web.velocity.resolver.FixedVelocityLayoutView;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @since 1.0
 */
public class SysConfigTool implements ApplicationContextAware {
    private SysConfig sysConfig;

    public void init(Object obj) {
        ViewToolContext ctx = (ViewToolContext) obj;
        ApplicationContext applicationContext = (ApplicationContext) ctx.get(FixedVelocityLayoutView.APPLICATIONCONTEXT_KEY);
        sysConfig = applicationContext.getBean("sysConfig", SysConfig.class);
    }

    public String get(String key) {
        return sysConfig.get(key);
    }

    public String getProfile() {
        return sysConfig.getProfile().name();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
