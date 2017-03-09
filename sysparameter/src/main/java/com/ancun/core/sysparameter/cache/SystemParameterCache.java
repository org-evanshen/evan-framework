package com.ancun.core.sysparameter.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ancun.core.cache.CacheSupport;
import com.ancun.core.cache.EHCacheUtil;
import com.ancun.core.sysparameter.model.SysParameter;

import net.sf.ehcache.CacheManager;

public class SystemParameterCache extends CacheSupport<SysParameter> {

    public static final String CACHE_ID = SystemParameterCache.class.getSimpleName();

    @Autowired
    @Qualifier("ehCacheManagerSysparameter")
    private CacheManager ehCacheManager;

    @Override
    public String getCacheId() {
        return CACHE_ID;
    }

    @PostConstruct
    public void init() {
        ehCacheUtil = new EHCacheUtil(ehCacheManager, CACHE_ID);
    }

    @PreDestroy
    public void destroy() {
        ehCacheUtil = null;
    }
}
