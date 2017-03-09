package com.ancun.core.cache;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * EhCache工具类
 * <p/>
 * create at 2014年4月18日 下午5:39:16
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public class EHCacheUtil {

    private static final Logger log = LoggerFactory.getLogger(EHCacheUtil.class);

    private Ehcache ehCache;

    public EHCacheUtil(Ehcache ehcache) {
        this.ehCache = ehcache;
    }

    public EHCacheUtil(CacheManager ehCacheManager, String cacheName) {
        this.ehCache = ehCacheManager.getEhcache(cacheName);
        log.info("EHCache[{}] inited! ", cacheName);
    }

    public void put(Serializable key, Object o) {
        if (key != null && o != null) {
            if (log.isTraceEnabled()) {
                log.trace("Put local cache key is [" + key + "],value is [" + o + "] ");
            }

            Element e = new Element(key, o);
            ehCache.put(e);

        } else {
            if (log.isTraceEnabled()) {
                log.trace("No object put to local cache");
            }
        }
    }

    /**
     * 带过期时间的缓存
     *
     * @param key               键
     * @param o                 值
     * @param timeToIdleSeconds 闲置超时时间
     * @param timeToLiveSeconds 缓存过期时间
     *                          <p/>
     *                          author: <a href="mailto:caozhenfei@ancun.com">CaoZhenfei</a><br>
     *                          create at: 2016年6月26日 下午1:33:52
     */
    public void put(Serializable key, Object o, int timeToIdleSeconds, int timeToLiveSeconds) {
        if (key != null && o != null) {
            if (log.isTraceEnabled()) {
                log.trace("Put local cache key is [" + key + "],value is [" + o
                        + "] ,timeToIdleSeconds is [" + timeToIdleSeconds
                        + "] ,timeToLiveSeconds is [" + timeToLiveSeconds
                        + "] ");
            }
            Element e = new Element(key, o);
            e.setTimeToIdle(timeToIdleSeconds);
            e.setTimeToLive(timeToLiveSeconds);
            ehCache.put(e);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("No object put to local cache");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Serializable key, Class<T> c) {
        T returnO = null;
        if (key != null) {
            Element e = ehCache.get(key);
            if (e != null) {
                returnO = (T) e.getValue();
                if (log.isTraceEnabled()) {
                    log.trace("Get local cache [" + ehCache.getName() + "] key is [" + key + "],value is [" + returnO
                            + "] ");
                }
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Not find local cache [" + ehCache.getName() + "] key is [" + key + "]");
                }
            }
        }

        return returnO;
    }

    /**
     * 移除
     * @param key
     */
    public void remove(Serializable key) {
        ehCache.remove(key);
    }

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    public boolean has(Serializable key) {
        return ehCache.isKeyInCache(key);
    }
}
