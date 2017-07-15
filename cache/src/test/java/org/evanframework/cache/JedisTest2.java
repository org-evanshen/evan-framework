package org.evanframework.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建16个jedisPool，每个数据库对应1个jedis
 * @author evan.shen
 */
public class JedisTest2 {
    private final static int DATABASE_COUNT = 16;//模拟16个库测试

    private String host = "localhost";
    private int port = 6379;
    private int timeout = 3000;
    private String passowrd = null;
    private int maxIdle = 512;
    private int maxTotal = 512;

    private List<JedisPool> jedisPools = new ArrayList<JedisPool>(DATABASE_COUNT);

    @Before
    public void init() {
        for (int i = 0; i < DATABASE_COUNT; i++) {
            // 获取初始配置
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(maxIdle);
            config.setMaxTotal(maxTotal);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            JedisPool jedisPool = new JedisPool(config, host, port, timeout, passowrd, i);
            jedisPools.add(jedisPool);
        }
    }

    @Test
    public void test() {
        JedisPool jedisPool = null;
        //Jedis jedis = jedisPool.getResource();
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        long end;
        long begin1 = 0;
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < DATABASE_COUNT; j++) {
                jedisPool = jedisPools.get(j);
                Jedis jedis= jedisPool.getResource();
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
        //jedis.close();
    }

    @After
    public void close() {
        for (int i = 0; i < 16; i++) {
            jedisPools.get(i).close();
            jedisPools.get(i).destroy();
        }
    }
}
