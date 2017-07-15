package org.evanframework.cache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author evan.shen
 */
public class RedisTemplateCreatorTest {

    private RedisTemplateCreator redisTemplateCreator;

    @Before
    public void init() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = new JedisPoolConfig();
        factory.setPoolConfig(config);
        redisTemplateCreator = new RedisTemplateCreator(factory);
    }

    @Test
    public void test() {
        int[] databaseIndexes = new int[]{1, 10, 6, 9, 2, 5, 4, 3, 7, 8, 12, 15, 11, 14};

        long begin = System.currentTimeMillis();
        System.out.println(begin);
        long end;
        long begin1 = 0;
        for (int i = 0; i < 100000; i++) {
            for (int j : databaseIndexes) {
                RedisTemplate redisTemplate = redisTemplateCreator.getRedisTemplate(j);
                redisTemplate.opsForValue().set("key" + i + "_" + j, "value" + i + "_" + j);
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
