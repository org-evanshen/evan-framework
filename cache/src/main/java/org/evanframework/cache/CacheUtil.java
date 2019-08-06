package org.evanframework.cache;

import org.evanframework.ehcache.EHCacheUtil;
import org.evanframework.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 缓存工具类，Created by evan.shen on 2017/3/17.
 * <p>内部实现了radis和ehcache的封装，各业务模块定义缓存时继承该类，例如：
 * <pre>
 *
 * public class MyCacheUtil extends CacheUtil {
 *      private final static String CACHE_NAME = MyCacheUtil.class.getSimpleName();
 *      private final static int REDIS_DATABASE_INDEX = 2;
 *
 *      {@code @Autowired} //如果要同时缓存到ehcache,注入CacheManager
 *      private CacheManager cacheManager;
 *
 *      public MyCacheUtil() {
 *          super();
 *      }
 *
 *      {@code @PostConstruct}
 *      public void init(){
 *          //如果要同时缓存到ehcache,这初始化EHCacheUtil
 *          CacheConfiguration conf = new CacheConfiguration();
 *          conf.setName(CACHE_NAME);
 *          RedisUtil redisUtil = new RedisUtil(redisTemplateCreator.getRedisTemplate(REDIS_DATABASE_INDEX));
 *          super.init(redisUtil, new EHCacheUtil(cacheManager,conf));
 *
 *          //super.init(redisUtil, CACHE_NAME);//如果不缓存到 EHCache，需要传入CACHE_NAME
 *      }
 * }
 * </pre>
 *
 * @since 1.0
 */
public class CacheUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheUtil.class);
    //private static final ConcurrentHashMap<String, CacheUtil> cacheUtilPool = new ConcurrentHashMap<String, CacheUtil>(128);

    private RedisUtil redisUtil;
    private EHCacheUtil ehCacheUtil;
    private String cacheName;

    protected CacheUtil() {
    }

    protected void init(RedisUtil RedisUtil, EHCacheUtil ehCacheUtil) {
        this.redisUtil = RedisUtil;
        this.ehCacheUtil = ehCacheUtil;
        this.cacheName = ehCacheUtil.getCacheName();

        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("CacheUtil inited,cacheName is [{}]", cacheName);
        }
    }

    public CacheUtil(RedisUtil redisUtil, EHCacheUtil ehCacheUtil) {
        init(redisUtil, ehCacheUtil);
    }

    public CacheUtil(RedisUtil redisUtil, String cacheName) {
        this.redisUtil = redisUtil;
        this.cacheName = cacheName;
    }

    public void put(Serializable key, Serializable o, Integer redisExpireSeconds) {
        checkCacheUtils();

        if (key == null || o == null) {
            //throw new IllegalArgumentException("Param [key] and [o] can't empty");
            LOGGER.warn("CacheUtil.put(Serializable key, Serializable o), Param [key] can't empty");
            return;
        }
        if (key == null || o == null) {
            //throw new IllegalArgumentException("Param [key] and [o] can't empty");
            LOGGER.warn("CacheUtil.put(Serializable key, Serializable o), Param [o] can't empty");
            return;
        }

        if (redisUtil != null) {
            redisUtil.put(cacheName, key, o, redisExpireSeconds);
        }
        if (ehCacheUtil != null) {
            ehCacheUtil.put(key + "", o);
        }
    }

    public <T> T get(Serializable key, Class<T> cacheClass) {
        checkCacheUtils();

        if (key == null) {
            //throw new IllegalArgumentException("Param [key] can't empty");
            LOGGER.warn("CacheUtil.get(Serializable key, Class<T> cacheClass), Param [o] can't empty");
        }

        T o = null;
        if (ehCacheUtil != null) {
            o = ehCacheUtil.get(key + "", cacheClass);
        }
        if (o == null && redisUtil != null) {
            o = redisUtil.get(cacheName, key, cacheClass);
            if (o != null) {
                if (ehCacheUtil != null) {
                    ehCacheUtil.put(key + "", (Serializable) o);
                }
            }
        }
        return o;
    }

    public void remove(Serializable key) {
        checkCacheUtils();

        if (key == null) {
            //throw new IllegalArgumentException("Param [key] can't empty");
            LOGGER.warn("CacheUtil.remove(Serializable key), Param [o] can't empty");
        }
        if (redisUtil != null) {
            redisUtil.remove(cacheName, key);
        }
        if (ehCacheUtil != null) {
            ehCacheUtil.remove(key + "");
        }
    }

    public boolean has(Serializable key) {
        checkCacheUtils();

        boolean returnV = false;
        if (ehCacheUtil != null) {
            returnV = ehCacheUtil.has(key);
        }
        if (!returnV && redisUtil != null) {
            returnV = redisUtil.has(cacheName, key);
        }
        return returnV;
    }

    private void checkCacheUtils() {
        if (ehCacheUtil == null && redisUtil == null) {
            throw new IllegalArgumentException("CacheUtils init failed, EHCacheUtil and RedisUtil can not all null");
        }
    }
}
