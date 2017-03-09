package com.ancun.core.datadict.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ancun.core.cache.CacheSupport;
import com.ancun.core.cache.EHCacheUtil;
import com.ancun.core.datadict.dto.DataDictionaryList;

import net.sf.ehcache.CacheManager;

/**
 * <p/>
 * create at 2014年4月15日 上午12:33:42
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public class DataDictionaryCacheForData extends CacheSupport<DataDictionaryList> {
    private static final String CACHE_ID = DataDictionaryCacheForData.class.getSimpleName();

    @Autowired
    @Qualifier("ehCacheManagerDataDict")
    private CacheManager ehCacheManager;

    @Override
    public String getCacheId() {
        return CACHE_ID;
    }

    @PostConstruct
    public void init() {
        ehCacheUtil = new EHCacheUtil(ehCacheManager, CACHE_ID);
    }

    protected int getRedisTimeToLiveSeconds() {
        return 3600;
    }

    @PreDestroy
    public void destroy() {
        ehCacheUtil = null;
    }
}
