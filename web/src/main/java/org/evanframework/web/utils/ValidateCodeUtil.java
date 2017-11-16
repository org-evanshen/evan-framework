package org.evanframework.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.evanframework.cache.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 验证工具类
 * @author evan.shen
 * @since 1.0
 */
public class ValidateCodeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateCodeUtil.class);

    private static final String OBJECT_TYPE_KEY = "ValidateCode";

    private RedisUtil redisUtil;


    /**
     * 生产校验码
     */
    public String create(String cacheKey, int validateCodeLength, Integer expireSeconds) {
        String validateCode = generateValidateCode(validateCodeLength);
        redisUtil.put(OBJECT_TYPE_KEY, cacheKey, validateCode, expireSeconds);

        LOGGER.info("生成图片验证码：validateCode[{}],cacheKey[{}]", validateCode, cacheKey);

        return validateCode;
    }

    /**
     * 生产校验码
     */
    public String create(String cacheKey, int validateCodeLength) {
        return create(cacheKey, validateCodeLength, 1800);
    }

    /**
     * 校验码是否正确
     *
     * @param validateCode
     */
    public boolean validate(String cacheKey, String validateCode) {
        String validateCodeInImage = redisUtil.get(OBJECT_TYPE_KEY, cacheKey, String.class);
        if (validateCodeInImage == null) {
            return false;
        }
        boolean result = StringUtils.equalsIgnoreCase(validateCodeInImage, validateCode);
        if (result) {
            redisUtil.remove(OBJECT_TYPE_KEY, cacheKey);
        } else {
            LOGGER.warn("图片验证码不正确：validateCode[{}],cacheKey[{}]", validateCode, cacheKey);
        }
        return result;
    }

    /**
     * 生成校验码
     */
    private String generateValidateCode(int validateCodeLength) {
        Random random = new Random();
        StringBuilder sRand = new StringBuilder();
        int itmp = 0;
        for (int i = 0; i < validateCodeLength; i++) {
            if (random.nextInt(2) == 1) {
                itmp = random.nextInt(26) + 65; // 生成A~Z的字母
            } else {
                itmp = random.nextInt(10) + 48; // 生成0~9的数字
            }

            while (itmp == 48 || itmp == 79) {// 不要0和O
                itmp = random.nextInt(10) + 48;
            }

            sRand.append((char) itmp);

            // if("O".equals(String.valueOf((char) itmp))){
            // System.out.print(itmp);
            //
            // }
        }
        return sRand.toString();
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
