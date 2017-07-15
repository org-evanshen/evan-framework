package org.evanframework.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * EHCache工具类, Created by evan.shen on 2017/3/16.
 * @since 1.0
 */
public class EHCacheUtil {
    private static final Logger logger = LoggerFactory.getLogger(EHCacheUtil.class);
    //private static final ConcurrentHashMap<String, EHCacheUtil> ehCacheUtilPool = new ConcurrentHashMap<String, EHCacheUtil>(128);

    private Cache cache = null;
    private String cacheName = null;

    /**
     *
     * @param cacheManager CacheManager通过构造函数传入，外部CacheManager保持单例
     * @param conf maxEntriesLocalHeap默认5000，timeToIdleSeconds默认20，timeToLiveSeconds默认20
     */
    public EHCacheUtil(CacheManager cacheManager, CacheConfiguration conf) {
        if (conf.getName() ==null || "".equals(conf.getName().trim())) {
            throw new IllegalArgumentException("EHCache name can not empty!");
        }

        this.cacheName = conf.getName();
        if (conf.getMaxEntriesLocalHeap() == 0) {
            conf.maxEntriesLocalHeap(5000);//最大的缓存数量
        }
        if (conf.getMemoryStoreEvictionPolicy() == null) {
            conf.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU);//设置失效策略
        }
        if (conf.getTimeToIdleSeconds() == 0) {
            conf.setTimeToIdleSeconds(20);
        }
        if (conf.getTimeToLiveSeconds() == 0) {
            conf.setTimeToLiveSeconds(20);
        }
        cache = new Cache(conf);
        try {
            cacheManager.addCache(cache);
            if(logger.isDebugEnabled()) {
                logger.debug("EHCache [{}] init success!", cacheName);
            }
        } catch (ObjectExistsException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

//    public static EHCacheUtil getInstance(String cacheName) {
//        EHCacheUtil ehCacheUtil = ehCacheUtilPool.get(cacheName);
//        if (ehCacheUtil == null) {
//            ehCacheUtil = new EHCacheUtil(cacheName);
//            ehCacheUtilPool.put(cacheName, ehCacheUtil);
//        }
//        return ehCacheUtil;
//    }

    /**
     *
     * @param key
     * @param value
     */
    public void put(Serializable key, Serializable value) {
        cache.put(new Element(key, value));
    }

    /**
     *
     * @param key
     * @param c
     * @param <T>
     * @return
     */
    public <T> T get(Serializable key, Class<T> c) {
        T returnV = null;
        Element element = cache.get(key);
        if (element != null) {
            Object tmp = element.getObjectValue();
            if (tmp != null) {
                returnV = (T) tmp;
            }
        }

        return returnV;
    }

    /**
     * 移除
     *
     * @param key
     */
    public void remove(Serializable key) {
        cache.remove(key);
    }

    /**
     * 判断是否存在
     *
     * @param key
     * @return
     */
    public boolean has(Serializable key) {
        return cache.isKeyInCache(key);
    }

    public String getCacheName() {
        return cacheName;
    }
}
