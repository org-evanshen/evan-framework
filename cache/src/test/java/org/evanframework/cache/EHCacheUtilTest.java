package org.evanframework.cache;

import org.evanframework.cache.support.Demo;
import org.evanframework.cache.support.DemoData;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by evan.shen on 2017/3/16.
 */
public class EHCacheUtilTest {
    private final static Logger logger = LoggerFactory.getLogger(EHCacheUtilTest.class);

    private EHCacheUtil cacheUtil1;
    private EHCacheUtil cacheUtil2;

    @Before
    public void init() {
        //Configuration defaultConf = new  Configuration();

        CacheManager cacheManager = new CacheManager();

        CacheConfiguration conf1 = new CacheConfiguration();
        conf1.name("cache1");
        cacheUtil1 = new EHCacheUtil(cacheManager,conf1);

        CacheConfiguration conf2 = new CacheConfiguration();
        conf2.name("cache2");
        cacheUtil2 = new EHCacheUtil(cacheManager,conf2);
    }

    @Test
    public void test() throws InterruptedException {
        cacheUtil1.put("aa", DemoData.randomDto());
        cacheUtil1.put("bb",DemoData.randomDto());
        Thread.sleep(5000);
        logger.info("aa=" + cacheUtil1.get("aa",Demo.class));
        logger.info("bb=" + cacheUtil1.get("bb",Demo.class));
        logger.info("aa=" + cacheUtil2.get("aa",Demo.class));

        Thread.sleep(21000);

        logger.info("aa=" + cacheUtil1.get("aa",Demo.class));
        logger.info("bb=" + cacheUtil1.get("bb",Demo.class));

    }

    @After
    public void destory(){

    }
}
