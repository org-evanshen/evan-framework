package org.evanframework.web.csrf;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.evanframework.cache.RedisUtil;
import org.evanframework.utils.DigestsUtils;
import org.evanframework.web.utils.CookieUtil;
import org.evanframework.web.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * 跨站伪造攻击处理工具
 *
 * @since 1.0
 */
public class CsrfTokenUtils {

    private RedisUtil redisUtil;
    private final static String CSRF_COOKIE_KEY = "csrf_tokens";
    private static final String OBJECT_TYPE_KEY = "E12123dsasdasToken";
    public static final String CSRF_TOKEN_KEY_PREFIX = "csrf_token_";
    private final static int TIME_OUT = 1800;
    private static Logger logger = LoggerFactory.getLogger(CsrfTokenUtils.class);


    /**
     * @param request
     * @param response
     * @return String[] { tokenKey, tokenValue }
     */
    public String[] getToken(HttpServletRequest request, HttpServletResponse response) {
        String tokenKey = CSRF_TOKEN_KEY_PREFIX + UUID.randomUUID().toString();
        String tokenValue = UUID.randomUUID().toString();


        String ip = HttpUtils.getRemoteAddr(request);
        logger.info("Csrf ip is [{}]", ip);
        String cookValue = tokenValue + "_" + ip;
        String sessionCookieValue = DigestsUtils.md5Hex(cookValue.getBytes());
        logger.info("Csrf sessionCookieValue is [{}]", cookValue);
        Date expiry = DateUtils.addSeconds(new Date(), TIME_OUT);

        CookieUtil.save(CSRF_COOKIE_KEY, sessionCookieValue, TIME_OUT, response, "UTF-8");
        redisUtil.put(OBJECT_TYPE_KEY, tokenKey, tokenValue, TIME_OUT);

        return new String[]{tokenKey, tokenValue};

    }

    /**
     * @param tokenKey
     * @param tokenValue
     * @param request
     * @return
     */
    public boolean validateToken(String tokenKey, String tokenValue, HttpServletRequest request) {
        if (StringUtils.isBlank(tokenKey) || StringUtils.isBlank(tokenValue)) {
            return false;
        }
        String tokenValue1 = redisUtil.get(OBJECT_TYPE_KEY, tokenKey, String.class);

        String ip = HttpUtils.getRemoteAddr(request);
        logger.info("Csrf validateToken ip is [{}]", ip);
        String cookValue = tokenValue + "_" + ip;
        String cookieValue = DigestsUtils.md5Hex(cookValue.getBytes());
        logger.info("Csrf validateToken generate cookieValue is [{}]", cookieValue);
        String sessionCookieName = CookieUtil.read(CSRF_COOKIE_KEY, request, "UTF-8");
        logger.info("cookie read cookieValue is [{}]", sessionCookieName);
        return cookieValue.equals(sessionCookieName) && tokenValue.equals(tokenValue1);
    }

    /**
     * @param tokenKey
     * @param request
     * @param response
     */
    public void remove(String tokenKey, HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(CSRF_COOKIE_KEY, request, response);
        redisUtil.remove(OBJECT_TYPE_KEY, tokenKey);
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
