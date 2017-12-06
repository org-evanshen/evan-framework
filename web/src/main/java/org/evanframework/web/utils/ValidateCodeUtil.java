package org.evanframework.web.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.evanframework.cache.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 验证工具类
 *
 * @author evan.shen
 * @since 1.0
 */
public class ValidateCodeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateCodeUtil.class);

    private static final String OBJECT_TYPE_KEY = "ValidateCode";

    private RedisUtil redisUtil;


    /**
     * 生成校验码，字母数字混合
     *
     * @param validateCodeLength 长度
     * @param cacheKey           放入缓存key
     * @param expireSeconds      过期秒
     * @return
     */
    public String create(int validateCodeLength, String cacheKey, Integer expireSeconds) {
        String validateCode = generateValidateCode(validateCodeLength);
        redisUtil.put(OBJECT_TYPE_KEY, cacheKey, validateCode, expireSeconds);

        LOGGER.info("生成验证码：validateCode[{}],cacheKey[{}]", validateCode, cacheKey);

        return validateCode;
    }

    /**
     * 生产校验码，字母数字混合，过期900秒
     *
     * @param validateCodeLength 长度
     * @param cacheKey           放入缓存key
     * @return
     */
    public String create(int validateCodeLength, String cacheKey) {
        return create(validateCodeLength, cacheKey, 900);
    }

    /**
     * 生成校验码，仅数字
     *
     * @param min           最小值
     * @param max           最大值
     * @param cacheKey      放入缓存key
     * @param expireSeconds 过期秒
     * @return
     */
    public String create(int min, int max, String cacheKey, Integer expireSeconds) {
        int length = (max + "").length();
        String validateCode = RandomUtils.nextInt(min, max) + "";
        if (validateCode.length() < length) {
            validateCode = StringUtils.leftPad(validateCode, length);
        }

        redisUtil.put(OBJECT_TYPE_KEY, cacheKey, validateCode, expireSeconds);

        LOGGER.info("生成验证码：validateCode[{}],cacheKey[{}]", validateCode, cacheKey);

        return validateCode;
    }

    /**
     * 生产校验码,过期900秒
     *
     * @param min      最小值
     * @param max      最大值
     * @param cacheKey 放入缓存key
     * @return
     */
    public String create(int min, int max, String cacheKey) {
        return create(min, max, cacheKey, 900);
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
            LOGGER.warn("验证码不正确：validateCode[{}],cacheKey[{}]", validateCode, cacheKey);
        }
        return result;
    }

    /**
     * 生成字母数字混合校验码
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

            while (itmp == 48 || itmp == 79) {// 不要0和O，看不清
                itmp = random.nextInt(10) + 48;
            }

            sRand.append((char) itmp);
        }
        return sRand.toString();
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
