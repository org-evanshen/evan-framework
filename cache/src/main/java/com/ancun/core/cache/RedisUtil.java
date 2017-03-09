package com.ancun.core.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * redis 工具接口
 * <p/>
 * <p/>
 * create at 2016年4月26日 下午8:08:56
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public interface RedisUtil {
    /**
     * @param objectTypeKey
     * @param objectKey
     * @param o
     * @param expiry        <p/>
     *                      author: ShenWei<br>
     *                      create at 2016年4月26日 下午8:09:10
     */
    void put(Serializable objectTypeKey, Serializable objectKey, final Object o, Date expiry);

    /**
     * @param objectTypeKey
     * @param objectKey
     * @param o             <p/>
     *                      author: ShenWei<br>
     *                      create at 2016年4月26日 下午8:09:10
     */
    void put(Serializable objectTypeKey, Serializable objectKey, final Object o, Long expireSeconds);

    /**
     * @param objectTypeKey
     * @param objectKey
     * @param c             <p/>
     *                      author: ShenWei<br>
     *                      create at 2016年4月26日 下午8:09:21
     */
    <T> T get(Serializable objectTypeKey, Serializable objectKey, Class<T> c);

    /**
     * @param objectTypeKey
     * @param objectKey     <p/>
     *                      author: ShenWei<br>
     *                      create at 2016年4月26日 下午8:09:24
     */
    void remove(Serializable objectTypeKey, Serializable objectKey);

    /**
     * @param pattern 表达式
     *                <p/>
     *                auth: MouHaoNing<br>
     *                create at 2016年8月31日 下午3:38:24
     */
    @Deprecated
    <T> Set<?> keys(String pattern);

    /**
     * 使用stringRedisTemplate方式删除
     *
     * @param redisKey redis存储的key
     *                 <p/>
     *                 author: MouHaoNing<br>
     *                 create at 2016年9月8日 下午3:38:24
     */
    void remove(String redisKey);

    /**
     * 代替keys
     *
     * @param count scan 的条数
     * @param count scan 的表达式
     *              <p/>
     *              author: MouHaoNing<br>
     *              create at 2016年9月9日 上午9:38:24
     */
    <T> Set<?> scan(long count, String pattern);

    /**
     * 根据redisKey返回hash 的所有field 和 value
     *
     * @param redisKey redisKey
     *                 <p/>
     *                 author: MouHaoNing<br>
     *                 create at 2016年9月11日 下午7:56:24
     */
    Map<?, ?> getAll(String redisKey);

    /**
     * 清除Redis
     * <p/>
     * <p/>
     * author: MouHaoNing<br>
     * create at 2016年9月12日 下午5:41:24
     */
    void flushAll();

    /**
     * 判断缓存是否存在
     * @param objectTypeKey
     * @param objectKey
     * @return
     */
    boolean has(Serializable objectTypeKey, Serializable objectKey);
}