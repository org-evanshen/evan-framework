package org.evanframework.cache;

import org.evanframework.cache.support.Demo;
import org.evanframework.cache.support.DemoCache;
import net.sf.ehcache.CacheManager;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author evan.shen
 */
public class DemoCacheTest {

    private final static Logger logger = LoggerFactory.getLogger(CacheUtilTest.class);

    private DemoCache demoCache;

    @Before
    public void init() {
        demoCache = new DemoCache();
        CacheManager cacheManager = new CacheManager();

        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = new JedisPoolConfig();
        factory.setPoolConfig(config);
        RedisTemplateCreator redisTemplateCreator = new RedisTemplateCreator(factory);

        demoCache.setCacheManager(cacheManager);
        demoCache.setRedisTemplateCreator(redisTemplateCreator);

        demoCache.init();
    }

    @Test
    public void test() {
        Demo demo = new Demo();
        demo.setId(100L);
        demoCache.put("aa", demo);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Demo demo1 = demoCache.get("aa");
        logger.info(demo1 + "");
    }
}
