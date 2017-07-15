package org.evanframework.cache;

import org.evanframework.cache.support.Demo;
import org.evanframework.cache.support.DemoData;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by evan.shen on 2017/3/17.
 */
public class CacheUtilTest {
    private final static Logger logger = LoggerFactory.getLogger(CacheUtilTest.class);

    private CacheUtil cacheUtil;

    @Before
    public void init() {
        //Configuration defaultConf = new  Configuration();
        CacheManager cacheManager = new CacheManager();
        CacheConfiguration conf1 = new CacheConfiguration();
        conf1.name("cache1");
        EHCacheUtil cacheUtil1 = new EHCacheUtil(cacheManager,conf1);

        cacheUtil = new CacheUtil(null,cacheUtil1);
    }

    @Test
    public void test() throws InterruptedException {
        cacheUtil.put("aa", DemoData.randomDto(),20);
        cacheUtil.put("bb",DemoData.randomDto(),20);
        Thread.sleep(8000);

        logger.info("aa=" + cacheUtil.get("aa",Demo.class));
        logger.info("bb=" + cacheUtil.get("bb",Demo.class));

        Thread.sleep(22000);

        logger.info("aa=" + cacheUtil.get("aa",Demo.class));
        logger.info("bb=" + cacheUtil.get("bb",Demo.class));

    }
}
