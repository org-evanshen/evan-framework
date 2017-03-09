package com.ancun.core.utils;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=mailto:zhangxufeng@ancun.com>ZhangXufeng</a>
 * @create 2016-10-27 10:58
 */
public class AesUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(AesUtilsTest.class);
    
    @Test
    public void testEncryptAndDecrpt() throws Exception {

        String originContent = AesUtilsTest.class.getCanonicalName();
        logger.info("加密前:"+originContent);
        String password = "123456";
        String encryptContent = AESUtils.encrypt(originContent, password);
        logger.info("加密后:"+originContent);
        String decryptContent = AESUtils.decrypt(encryptContent, password);
        logger.info("解密后:"+originContent);
        Assert.assertTrue(originContent.equals(decryptContent));

    }

}
