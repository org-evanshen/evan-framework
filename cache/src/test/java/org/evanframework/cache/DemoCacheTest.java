package org.evanframework.cache;

import net.sf.ehcache.CacheManager;
import org.evanframework.cache.support.Demo;
import org.evanframework.cache.support.DemoCache;
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
    public void test() throws InterruptedException {
        Demo demo = new Demo();
        demo.setId(100L);
        demoCache.put("aa", demo);


        Thread t1 = new Thread(new ThreadGet());
        Thread t2 = new Thread(new ThreadGet());
        Thread t3 = new Thread(new ThreadGet());

        Thread.sleep(2000);
        t1.start();

        Thread.sleep(4000);
        t2.start();

        Thread.sleep(6000);
        t3.start();
    }
}

class ThreadGet implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(CacheUtilTest.class);
    private DemoCache demoCache;

    public ThreadGet() {
        demoCache = new DemoCache();

        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = new JedisPoolConfig();
        factory.setPoolConfig(config);
        RedisTemplateCreator redisTemplateCreator = new RedisTemplateCreator(factory);

        demoCache.setRedisTemplateCreator(redisTemplateCreator);

        demoCache.init();
    }

    @Override
    public void run() {
        logger.info(">>>>>>> {}", demoCache.get("aa"));
    }
}
