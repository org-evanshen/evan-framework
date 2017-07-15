package org.evanframework.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 缓存支持类，内部实现了radis和ehcache的封装，各业务模块定义缓存时继承该类，T为要缓存的对象类型，例如：
 * <p>
 * <pre>
 * public class MyCache extends AbstractCache<MyDataType>{
 *      private final static String CACHE_NAME = MyCache.class.getSimpleName();
 *      private final static int REDIS_DATABASE_INDEX = 2;
 *      {@code @Autowired}
 *      private CacheManager cacheManager; //如果要同时缓存到ehcache,注入CacheManager
 *      {@code @Autowired
 *      private RedisTemplateCreator redisTemplateCreator;
 *      {@code @PostConstruct}
 *      public void init(){
 *          super.init(CACHE_NAME, redisTemplateCreator, REDIS_DATABASE_INDEX, cacheManager);
 *      }
 * }
 * <pre>
 * redis默认过期时间7200秒，子类可以通过setRedisTimeToLiveSeconds()方法设置过期时间
 * <p>create at 2014年7月17日 上午9:52:55
 *
 * @author shen.wei
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class AbstractCache<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCache.class);

    private static final int DEFAULT_REDIS_TIME_TO_LIVE_SECONDS = 7200;//默认失效时间
    private int redisTimeToLiveSeconds = DEFAULT_REDIS_TIME_TO_LIVE_SECONDS;
    private Class<T> cacheClass;

    private CacheUtil cacheUtil;

    protected void init(String cacheName, RedisTemplateCreator redisTemplateCreator, int redisDatabaseIndex, CacheManager ehCacheManager) {
        //如果要同时缓存到ehcache,这初始化EHCacheUtil
        CacheConfiguration conf = new CacheConfiguration();
        conf.setName(cacheName);//缓存名，全局唯一，可以用类名
        EHCacheUtil ehCacheUtil = new EHCacheUtil(ehCacheManager, conf);
        RedisUtil redisUtil = new RedisUtil(redisTemplateCreator.getRedisTemplate(redisDatabaseIndex));
        //初始化CacheUtil
        cacheUtil = new CacheUtil(redisUtil, ehCacheUtil);
        initLog(cacheName, redisDatabaseIndex);
    }

    protected void init(String cacheName, RedisTemplateCreator redisTemplateCreator, int redisDatabaseIndex) {
        RedisUtil redisUtil = new RedisUtil(redisTemplateCreator.getRedisTemplate(redisDatabaseIndex));
        //如果不需要用 EHCache
        cacheUtil = new CacheUtil(redisUtil, cacheName);
        initLog(cacheName, redisDatabaseIndex);
    }

    private void initLog(String cacheName, int redisDatabaseIndex) {
        if(logger.isDebugEnabled()) {
            logger.debug("Cache [cacheName:{},cacheClass:{},redisDatabaseIndex:{}] inited", cacheName, cacheClass.getName(), redisDatabaseIndex);
        }
    }

//    /**
//     * 设置cacheUtil，由子类来实现
//     */
//    protected abstract CacheUtil getCacheUtil();

    @SuppressWarnings("unchecked")
    public AbstractCache() {
        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        cacheClass = (Class<T>) types[0];
    }

    public void put(Serializable key, T o) {
        cacheUtil.put(key, (Serializable) o, redisTimeToLiveSeconds);
    }

    public T get(Serializable key) {
        T o = cacheUtil.get(key, cacheClass);
        return o;
    }

    public void remove(Serializable key) {
        cacheUtil.remove(key);
    }

    public boolean has(Serializable key) {
        return cacheUtil.has(key);
    }

    /**
     * 设置 redis 的失效时间，单位秒，默认7200
     *
     * @param redisTimeToLiveSeconds
     */
    protected void setRedisTimeToLiveSeconds(int redisTimeToLiveSeconds) {
        this.redisTimeToLiveSeconds = redisTimeToLiveSeconds;
    }
}
