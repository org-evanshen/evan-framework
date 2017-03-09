package com.ancun.core.cache.impl;

import com.ancun.core.cache.RedisUtil;
import com.ancun.core.sysconfig.SysConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p/>
 * create at 2016年4月26日 下午8:03:14
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public class RedisUtilSpringImpl implements RedisUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisUtilSpringImpl.class);

    private RedisTemplate<Serializable, Serializable> redisTemplate;

    private StringRedisTemplate stringRedisTemplate;

    private HashOperations<Serializable, Object, Object> hashOperations;
    private ValueOperations<Serializable, Serializable> valueOperations;
    private ListOperations<Serializable, Serializable> listOperations;
    private SetOperations<Serializable, Serializable> setOperations;

    private SysConfig sysConfig;
    private String redisKeyPrefix;

    public void init() {
        hashOperations = redisTemplate.opsForHash();
        valueOperations = redisTemplate.opsForValue();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();

        String temp = sysConfig.get("redis.key.prefix");
        if (StringUtils.isNotBlank(temp)) {
            redisKeyPrefix = temp + "_";
        } else {
            redisKeyPrefix = "";
        }

        log.info("Redis client inited,use[org.springframework.data.redis]，redisKeyPrefix["+redisKeyPrefix+"]");
    }

    public void destroy() {
        hashOperations = null;
        valueOperations = null;
        listOperations = null;
        setOperations = null;

    }

    public void put(Serializable objectTypeKey, Serializable objectKey, final Object o, Date expiry) {


        if (objectKey != null && o != null) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Put redis objectTypeKey is [%s],objectKey is [%s], value is [%s]",
                        objectTypeKey, objectKey, o));
            }
            String fullObjectTypeKey = getFullObjectTypeKey(objectTypeKey);
            try {
                if (expiry != null) {
                    redisTemplate.expireAt(fullObjectTypeKey, expiry);
                }
                hashOperations.put(fullObjectTypeKey, String.valueOf(objectKey), o);
            } catch (RuntimeException ex) {
                log.error("Put redis error,fullObjectTypeKey is [" + fullObjectTypeKey + "],objectKey is [" + objectKey
                        + "]," + ex.getMessage(), ex);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("No redis put");
            }
        }
    }

    public void put(Serializable objectTypeKey, Serializable objectKey, final Object o, Long expireSeconds) {
        if (objectKey != null && o != null) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Put redis objectTypeKey is [%s],objectKey is [%s], value is [%s]",
                        objectTypeKey, objectKey, o));
            }
            String fullObjectTypeKey = getFullObjectTypeKey(objectTypeKey);
            try {
                if (expireSeconds != null) {
                    redisTemplate.expire(fullObjectTypeKey, expireSeconds, TimeUnit.SECONDS);
                }
                hashOperations.put(fullObjectTypeKey, String.valueOf(objectKey), o);
            } catch (RuntimeException ex) {
                log.error("Put redis error,fullObjectTypeKey is [" + fullObjectTypeKey + "],objectKey is [" + objectKey
                        + "]," + ex.getMessage(), ex);
                // exceptionNotify.send(ex);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("No redis put");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Serializable objectTypeKey, Serializable objectKey, Class<T> c) {
        T returnO = null;
        if (objectKey != null) {

            String fullObjectTypeKey = getFullObjectTypeKey(objectTypeKey);
            Object o = null;
            try {
                o = hashOperations.get(getFullObjectTypeKey(objectTypeKey), String.valueOf(objectKey));
            } catch (RuntimeException ex) {
                log.error("Get redis error,fullObjectTypeKey is [" + fullObjectTypeKey + "],objectKey is [" + objectKey
                        + "]," + ex.getMessage(), ex);
            }

            if (o != null) {
                returnO = (T) o;
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Get Put redis objectTypeKey is	[%s],objectKey is [%s],value is [%s]",
                            objectTypeKey, objectKey, returnO));
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("Not find redis objectTypeKey is [%s],objectKey is [%s]", objectTypeKey,
                            objectKey));
                }
            }
        }
        return returnO;
    }

    public void remove(Serializable objectTypeKey, Serializable objectKey) {
        if (objectKey != null) {
            String fullObjectTypeKey = getFullObjectTypeKey(objectTypeKey);
            try {
                hashOperations.delete(fullObjectTypeKey, String.valueOf(objectKey));
            } catch (RuntimeException ex) {
                log.error("Delete redis error,fullObjectTypeKey is [" + fullObjectTypeKey + "],objectKey is ["
                        + objectKey + "]," + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public <T> Set<String> keys(String pattern) {
        if (pattern != null) {
            try {
                Set<String> keys = stringRedisTemplate.keys(pattern);
                return removeFullObjectTypeKey(keys);
            } catch (RuntimeException ex) {
                log.error("keys redis error,pattern is [" + pattern + "]" + ex.getMessage(), ex);
            }
        }
        return new HashSet<String>();
    }

    @Override
    public void remove(String redisKey) {
        if (redisKey != null) {
            try {
                String fullObjectTypeKey = getFullObjectTypeKey(redisKey);
                stringRedisTemplate.delete(fullObjectTypeKey);
            } catch (RuntimeException ex) {
                log.error("Delete redis error,redisKey is [" + redisKey + "]" + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public <T> Set<?> scan(long count, String pattern) {
        try{
            log.info("RedisUtilSpringImpl.scan start........");
            Cursor<byte[]> cursor = doScan(pattern);
            Set<byte[]> set = new HashSet<byte[]>();
            while (cursor.hasNext()) {
                set.add(cursor.next());
            }
            try {
                cursor.close();
            } catch (IOException e) {
                log.error("Scan redis error,cursor is close error" + e.getMessage(), e);
            }
            log.info("RedisUtilSpringImpl.scan start   1111111111........");
            Set<?> keys = null != redisTemplate.getKeySerializer() ? SerializationUtils.deserialize(set, redisTemplate.getKeySerializer()) : set;
            return removeFullObjectTypeKey(keys);
        }catch (RuntimeException ex){
            log.error("Scan redis error,count is [" + count + "] , pattern is [" + pattern + "]" + ex.getMessage(), ex);
        }
        return new HashSet<>();
    }

    @Override
    public Map<?, ?> getAll(String key) {
        try{
            if (StringUtils.isBlank(key))
                return null;
            String fullObjectTypeKey = getFullObjectTypeKey(key);
            Map<byte[], byte[]> fields = doGetAll(fullObjectTypeKey);
            return null != redisTemplate.getHashKeySerializer() && null != redisTemplate.getHashValueSerializer()
                    ? SerializationUtils.deserialize(fields, redisTemplate.getHashKeySerializer(), redisTemplate.getHashValueSerializer()) : fields;
        }catch (RuntimeException ex){
            log.error("getAll redis error,redisKey is [" + key + "] " + ex.getMessage(), ex);
        }
        return new HashMap<>();
    }

    @Override
    public void flushAll() {
        try{
            redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) {
                    connection.flushAll();
                    return null;
                }
            }, true);
        }catch (RuntimeException ex){
            log.error("flushAll redis error " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean has(Serializable objectTypeKey, Serializable objectKey) {
        String fullObjectTypeKey = getFullObjectTypeKey(objectTypeKey);
        return hashOperations.hasKey(fullObjectTypeKey,String.valueOf(objectKey));
    }

    private Cursor<byte[]> doScan(String pattern) {
        log.info("RedisUtilSpringImpl.doScan start   1111111111........");
        ScanOptions.ScanOptionsBuilder optionsBuilder = new ScanOptions.ScanOptionsBuilder();
        if (StringUtils.isNotBlank(pattern)) {
            optionsBuilder.match(pattern);
        }
        log.info("RedisUtilSpringImpl.doScan start   22222222222........");
        final ScanOptions options = optionsBuilder.build();
        Cursor<byte[]> cursor = redisTemplate.execute(new RedisCallback<Cursor<byte[]>>() {
            public Cursor<byte[]> doInRedis(RedisConnection connection) {
                return connection.scan(options);
            }
        }, true);

        log.info("RedisUtilSpringImpl.doScan start   3333333333333333333........");
        return cursor;
    }

    private Map<byte[], byte[]> doGetAll(final String key) {
        return redisTemplate.execute(new RedisCallback<Map<byte[], byte[]>>() {
            public Map<byte[], byte[]> doInRedis(RedisConnection connection) {
                return connection.hGetAll(key.getBytes());
            }
        }, true);
    }

    private String getFullObjectTypeKey(Serializable objectTypeKey) {
        return redisKeyPrefix + String.valueOf(objectTypeKey);
    }

    private String removeFullObjectTypeKey(String redisKey) {
        if (redisKey == null) return null;
        if (redisKey.startsWith(redisKeyPrefix))
            return redisKey.substring(redisKeyPrefix.length(), redisKey.length());
        return redisKey;
    }

    private Set<String> removeFullObjectTypeKey(Set<?> redisKeys) {
        if (redisKeys == null) return new HashSet<String>();
        Set<String> keys = new HashSet<String>();
        Iterator<?> keyIterator = redisKeys.iterator();
        while (keyIterator.hasNext()) {
            keys.add(removeFullObjectTypeKey(String.valueOf(keyIterator.next())));
        }
        return keys;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
    }
}