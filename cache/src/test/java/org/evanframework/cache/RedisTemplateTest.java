package org.evanframework.cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author evan.shen
 */
public class RedisTemplateTest {
    private final static int DATABASE_COUNT = 16;//模拟16个库测试

    private String host = "localhost";
    private int port = 6379;
    private int timeout = 3000;
    private String passowrd = null;
    private int maxIdle = 512;
    private int maxTotal = 512;

    private List<RedisTemplate> redisTemplates = new ArrayList<RedisTemplate>(DATABASE_COUNT);

    @Before
    public void init() {
        for (int i = 0; i < DATABASE_COUNT; i++) {
            // 获取初始配置
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(maxIdle);
            config.setMaxTotal(maxTotal);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            JedisConnectionFactory factory = new JedisConnectionFactory();
            factory.setHostName(host);
            factory.setDatabase(i);
            factory.setPort(port);
            factory.setTimeout(timeout);
            factory.setPassword(passowrd);
            factory.setPoolConfig(config);
            factory.afterPropertiesSet();

            RedisTemplate redisTemplate = new RedisTemplate();
            redisTemplate.setConnectionFactory(factory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

            redisTemplate.afterPropertiesSet();

            redisTemplates.add(redisTemplate);
        }
    }

    @Test
    public void test() {
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        long end;
        long begin1 = 0;
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < DATABASE_COUNT; j++) {
                RedisTemplate redisTemplate = redisTemplates.get(j);
                redisTemplate.execute(new RedisCallback() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {

                        return null;
                    }
                });
                redisTemplate.opsForValue().set("key" + i + "-" + j, "value" + i + "-" + j);
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
}
