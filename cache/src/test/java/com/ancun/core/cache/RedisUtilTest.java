package com.ancun.core.cache;


import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ancun.core.cache.support.Demo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:sysconfig-bean.xml", "classpath*:cache-bean.xml"})
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test() throws InterruptedException {
        Demo demo = new Demo(1L);
        System.out.println(demo);
        redisUtil.put(Demo.class.getSimpleName(), "1", demo, 1L);

        System.out.println(redisUtil.has(Demo.class.getSimpleName(),"1"));
        Demo demo1 = redisUtil.get(Demo.class.getSimpleName(), "1", Demo.class);
        System.out.println(demo1);

        Thread.sleep(2540);

        System.out.println(redisUtil.has(Demo.class.getSimpleName(),"1"));
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
        redisUtil.remove("Demo");
//		fail("Not yet implemented");
    }

    @Test
    public void testKeys() {
        Set keys = redisUtil.keys("*");
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(keys);
    }

    @Test
    public void testScan() {
        Set<?> scan = redisUtil.scan(10, "*");
        Iterator<?> iterator = scan.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(scan.size());
    }

    @Test
    public void testGetByRedisKey() {
        Map<?, ?> map = redisUtil.getAll("Demo");
        Iterator<?> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(map.get(iterator.next()));
        }
        System.out.println(map.size());
    }

    @Test
    public void testFlushAll() {
        redisUtil.flushAll();
    }
}
