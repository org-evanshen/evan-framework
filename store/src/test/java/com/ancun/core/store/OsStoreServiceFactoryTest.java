package com.ancun.core.store;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * OsStoreServiceFactoryTest
 *
 * @author <a href="mailto:shenwei@ancun.com">Evan.shen</a>
 * @date 16/9/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:sysconfig-bean.xml","classpath*:store-bean.xml" })
public class OsStoreServiceFactoryTest {


    @Autowired
    private OsStoreServiceFactory osStoreServiceFactory;

    @Test
    public void testGetOsStoreService(){
        OsStoreService osStoreService = osStoreServiceFactory.getInstance(EnumCloudType.OSS);
        System.out.println("\r\nOsStoreService is [" + osStoreService+ "]"  );
    }
}
