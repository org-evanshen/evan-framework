package org.evanframework.sysconfig;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBeansConfig.class)
public class SysConfigTest {
    @Autowired
    private SysConfig sysConfig;

    @Test
    public void testGet() {
        String v = sysConfig.get("profile");
        System.out.println(v);
    }
}