package org.evanframework.cache;


import org.evanframework.cache.support.Demo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

import static org.junit.Assert.fail;

public class RedisUtilTest {

    private RedisUtil redisUtil;

    @Before
    public void init() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.afterPropertiesSet();

        RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.afterPropertiesSet();

        redisUtil = new RedisUtil(redisTemplate);
    }

    @Test
    public void test() throws InterruptedException {
        Demo demo = new Demo(1L);
        System.out.println(demo);
        redisUtil.put(Demo.class.getSimpleName(), "1", demo, 3);

        System.out.println(redisUtil.has(Demo.class.getSimpleName(), "1"));
        Demo demo1 = redisUtil.get(Demo.class.getSimpleName(), "1", Demo.class);
        System.out.println(demo1);

        Thread.sleep(4000);

        System.out.println(redisUtil.has(Demo.class.getSimpleName(), "1"));
        demo1 = redisUtil.get(Demo.class.getSimpleName(), "1", Demo.class);
        System.out.println(demo1);
    }

    @Test
    public void testPutSerializableObjectDate() {
        fail("Not yet implemented");
    }

    @Test
    public void testGet() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemove() {
        redisUtil.remove(Demo.class.getSimpleName(),"1");
//		fail("Not yet implemented");
    }
}
