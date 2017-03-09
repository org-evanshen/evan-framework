package com.ancun.core.sysconfig;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:sysconfig-bean.xml"})
public class SysConfigTest {

    @Autowired
    private SysConfig sysConfig;

    @Test
    public void testGet() {
        String v = sysConfig.get("app.profile");
        System.out.println(v);
    }
}