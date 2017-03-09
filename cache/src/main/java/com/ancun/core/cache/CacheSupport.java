package com.ancun.core.cache;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * 缓存支持类，各业务缓存继承该类，内部实现的radis和ehcache的封装， T 为要缓存的对象类型,
 * radis默认过期时间7200秒，子类可以通过重写getRedisTimeToLiveSeconds()方法设置过期时间，
 * <p/>
 * 如果要同时缓存到ehcache,在子类中加上以下代码
 * <p/>
 * <pre>
 * <code>@Autowired</code> <code>@Qualifier</code>("ehCacheManagerXXX")
 * //和ehcache配置文件中的相同 private CacheManager ehCacheManager;
 *
 * <code>@PostConstruct</code> public void init() { ehCacheUtil = new
 * EHCacheUtil(ehCacheManager, CACHE_ID); }
 *
 * <pre>
 * create at 2014年7月17日 上午9:52:55
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public abstract class CacheSupport<T> {

    /**
     * 默认过期时间
     */
    private static final int DEFAULT_REDIS_TIME_TO_LIVE_SECONDS = 7200;

    private int redisTimeToLiveSeconds = DEFAULT_REDIS_TIME_TO_LIVE_SECONDS;

    private RedisUtil redisUtil;

    protected EHCacheUtil ehCacheUtil;

    private Class<T> cacheClass;

    @SuppressWarnings("unchecked")
    public CacheSupport() {
        ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

        cacheClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract String getCacheId();

    public void put(Serializable key, T o) {
        Date expiry = null;
        if (getRedisTimeToLiveSeconds() > 0) {
            expiry = DateUtils.addSeconds(new Date(), getRedisTimeToLiveSeconds());
        }
        if (key == null || o == null) {
            throw new IllegalArgumentException("Param [key] and [o] can't empty");
        }
        redisUtil.put(getCacheId(), key, o, expiry);
        if (ehCacheUtil != null) {
            ehCacheUtil.put(key, o);
        }
    }

    public T get(Serializable key) {
        Assert.notNull(key, "param [key] can't empty");

        T o = null;
        if (ehCacheUtil != null) {
            o = ehCacheUtil.get(key, cacheClass);
        }
        if (o == null) {
            o = redisUtil.get(getCacheId(), key, cacheClass);
            if (o != null) {
                if (ehCacheUtil != null) {
                    ehCacheUtil.put(key, o);
                }
            }
        }
        return o;
    }

    public void remove(Serializable key) {
        Assert.notNull(key, "param [key] can't empty");

        redisUtil.remove(getCacheId(), key);
        if (ehCacheUtil != null) {
            ehCacheUtil.remove(key);
        }
    }

    public boolean has(Serializable key) {
        boolean returnV = false;
        if (ehCacheUtil != null) {
            returnV = ehCacheUtil.has(key);
        }
        if (!returnV) {
            returnV = redisUtil.has(getCacheId(), key);
        }
        return returnV;
    }

    /**
     * 子类可覆盖该方法，设置过期时间
     */
    protected int getRedisTimeToLiveSeconds() {
        return redisTimeToLiveSeconds;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
