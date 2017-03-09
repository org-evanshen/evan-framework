package com.ancun.core.sysparameter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ancun.core.sysparameter.model.SysParameter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath*:sysconfig-bean.xml", //
        "classpath*:cache-bean.xml", //
        "classpath*:persistence-bean.xml", //
        "classpath*:sysparameter-bean.xml", //
        "classpath*:test-bean.xml"})
public class SysParameterServiceTest {

    @Autowired
    private SysParameterService sysParameterService;

    @Test
    public void testGet() {
        SysParameter v = sysParameterService.getByKey("notarization.system.url");
        System.out.println(v);
    }
}