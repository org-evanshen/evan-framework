package org.evanframework.datadict.service.cache;

import org.evanframework.cache.AbstractCache;
import org.evanframework.cache.RedisTemplateCreator;
import org.evanframework.datadict.service.dto.DataDictionary;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 数据字典缓存<p>
 * 用途：根据group和key获取单个数据字典
 */
@Component
public class DataDictionaryCacheForName extends AbstractCache<DataDictionary> {

    private static final String CACHE_NAME = DataDictionaryCacheForName.class.getSimpleName();

    private final static int REDIS_DATABASE_INDEX = 8;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RedisTemplateCreator redisTemplateCreator;

    @PostConstruct
    public void init() {
        super.init(CACHE_NAME, redisTemplateCreator, REDIS_DATABASE_INDEX, cacheManager);
    }
}