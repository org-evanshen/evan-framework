package org.evanframework.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 创建1个jedisPool，动态选库
 * @author evan.shen
 */
public class JedisTest {


    private String host = "localhost";
    private int port = 6379;
    private int timeout = 3000;
    private String passowrd = null;
    private int maxIdle = 512;
    private int maxTotal = 512;

    private JedisPool jedisPool;

    @Before
    public void init() {
        // 获取初始配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        jedisPool = new JedisPool(config, host, port, timeout, passowrd);
    }

    @Test
    public void test() {
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        long end;
        long begin1 = 0;
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 16; j++) {
                Jedis jedis = jedisPool.getResource();
                jedis.select(j);
                jedis.set("key" + i + "-" + j, "value" + i + "-" + j);
                jedis.close();
            }
            if (i % 1000 == 0) {
                end = System.currentTimeMillis();
                System.out.println(end - begin1);
                begin1 = end;
            }
        }
        end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    @After
    public void close() {
        jedisPool.close();

        jedisPool.destroy();
    }
}
