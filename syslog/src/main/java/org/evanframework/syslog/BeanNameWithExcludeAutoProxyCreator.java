package org.evanframework.syslog;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fish
 */
public class BeanNameWithExcludeAutoProxyCreator extends
        BeanNameAutoProxyCreator {
    private static final long serialVersionUID = 5275221709260639502L;

    private Set<String> exclude;

    public void setExclude(String[] names) {
        Assert.notEmpty(names, "'beanNames' must not be empty");
        this.exclude = new HashSet<String>();
        for (String n : names) {
            exclude.add(n);
        }
    }

    @Override
    protected boolean isMatch(String beanName, String mappedName) {
        if (this.exclude.contains(beanName)) {
            return false;
        }
        return super.isMatch(beanName, mappedName);
    }

}
