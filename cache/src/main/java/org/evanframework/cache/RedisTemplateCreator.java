package org.evanframework.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Field;
import java.util.Vector;

/**
 * RedisTemplate 创建器
 * 根据 DatabaseIndex 创建对应的RedisTemplate，可对 redis 的16个数据库进行操作
 *
 * @author shenwei
 * @since 1.0
 */
public class RedisTemplateCreator {
    private final static Logger LOGGER = LoggerFactory.getLogger(RedisTemplateCreator.class);

    private final static int DATABASE_COUNT = 16;//Redis16个库
    //支持多数据库，redis共16个数据库
    private static final Vector<RedisTemplate> redisTemplates = new Vector<RedisTemplate>(DATABASE_COUNT);

    private JedisConnectionFactory connectionFactory;
    private RedisSentinelConfiguration sentinelConfig;

    public RedisTemplateCreator(JedisConnectionFactory connectionFactory) {
        for (int i = 0; i < DATABASE_COUNT; i++) {
            redisTemplates.add(null);
        }

        this.connectionFactory = connectionFactory;
        //this.poolConfig = poolConfig;

        try {
            Field field = JedisConnectionFactory.class.getDeclaredField("sentinelConfig");
            field.setAccessible(true);
            Object o = field.get(connectionFactory);
            if (o != null) {
                sentinelConfig = (RedisSentinelConfiguration) o;
            }
        } catch (NoSuchFieldException ex) {
            throw new IllegalStateException("RedisTemplateCreator init fail," + ex.getMessage(), ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("RedisTemplateCreator init fail," + ex.getMessage(), ex);
        }

        LOGGER.info("RedisTemplateCreator inited,{},{}", connectionFactory, sentinelConfig);
    }

    public RedisTemplate getRedisTemplate(int databaseIndex) {
        if (databaseIndex >= DATABASE_COUNT || databaseIndex < 0) {
            throw new IllegalArgumentException("Redis databaseIndex mush >= 0 and < 16!");
        }

        RedisTemplate redisTemplate = null;

        if (redisTemplates.get(databaseIndex) == null) { //如果没有，新创建一个redisTemplate
//            JedisPoolConfig poolConfig1 = new JedisPoolConfig();
//            poolConfig1.setMaxIdle(poolConfig.getMaxIdle());
//            poolConfig1.setMinIdle(poolConfig.getMinIdle());
//            poolConfig1.setMaxTotal(poolConfig.getMaxTotal());
//            poolConfig1.setMaxWaitMillis(poolConfig.getMaxWaitMillis());
//            poolConfig1.set
            JedisConnectionFactory connectionFactory1 = null;
            if (this.sentinelConfig == null) {
                connectionFactory1 = new JedisConnectionFactory(connectionFactory.getPoolConfig());
            } else {
                connectionFactory1 = new JedisConnectionFactory(this.sentinelConfig, connectionFactory.getPoolConfig());
            }
            connectionFactory1.setPassword(connectionFactory.getPassword());
            connectionFactory1.setPort(connectionFactory.getPort());
            connectionFactory1.setTimeout(connectionFactory.getTimeout());
            //connectionFactory1.setClientName(connectionFactory.getClientName());
            connectionFactory1.setHostName(connectionFactory.getHostName());
            connectionFactory1.setShardInfo(connectionFactory.getShardInfo());
            connectionFactory1.setUsePool(connectionFactory.getUsePool());
            connectionFactory1.setConvertPipelineAndTxResults(connectionFactory.getConvertPipelineAndTxResults());
            connectionFactory1.setDatabase(databaseIndex);
            connectionFactory1.afterPropertiesSet();

            redisTemplate = new RedisTemplate();
            redisTemplate.setConnectionFactory(connectionFactory1);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
            redisTemplate.afterPropertiesSet();
            redisTemplates.set(databaseIndex, redisTemplate);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("RedisTemplate inited, dababase is [{}]", databaseIndex);
            }
        } else {
            redisTemplate = redisTemplates.get(databaseIndex);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("get RedisTemplate, dababase is [{}]", databaseIndex);
            }
        }
        return redisTemplate;
    }

    public void destroy() {
        for (int i = 0; i < DATABASE_COUNT; i++) {
            RedisTemplate redisTemplate = redisTemplates.get(i);
            if (redisTemplate != null) {
                JedisConnectionFactory JedisConnectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
                JedisConnectionFactory.destroy();
            }
        }
    }


//    private Jedis getJedis(int dbIndex) {
//        JedisConnection jedisConnection = (JedisConnection) redisTemplate.getConnectionFactory().getConnection();
//        Jedis jedis = jedisConnection.getNativeConnection();
//        jedis.select(dbIndex);
//        return jedis;
//    }
//
//    public void flushDB(int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        jedis.flushDB();
//    }
//
//    public Boolean exists(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.exists(key);
//    }
//
//    public void set(String key, String value, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        jedis.set(key, value);
//    }
//
//    public void setObject(String key, Object value, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        Serializable serializeble = (Serializable) value;
//        jedis.set(SerializationUtils.serialize(key.hashCode()), SerializationUtils.serialize(serializeble));
//    }
//
//    public String get(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.get(key);
//    }
//
//    public Object getObject(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return SerializationUtils.deserialize(jedis.get(SerializationUtils
//                .serialize(key.hashCode())));
//    }
//
//    public Long incr(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.incr(key);
//    }
//
//    public Long del(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.del(key);
//    }
//
//    public Long delObject(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.expire(SerializationUtils.serialize(key.hashCode()), 0);
//    }
//
//    public Long expire(String key, int seconds, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.expire(key, seconds);
//    }
//
//    public Long expireObject(String key, int seconds, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.expire(SerializationUtils.serialize(key.hashCode()), seconds);
//    }
//
//    public Long ttl(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.ttl(key);
//    }
//
//    public Long ttlObject(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.ttl(SerializationUtils.serialize(key.hashCode()));
//    }
//
//    public Long hset(String key, String field, String xvalue, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hset(key, field, xvalue);
//    }
//
//    public String hget(String key, String field, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hget(key, field);
//    }
//
//    public Long hdel(String key, int dbIndex, String... fields) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hdel(key, fields);
//    }
//
//    public Long hincrBy(String key, String field, long value, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hincrBy(key, field, value);
//    }
//
//    public Double hincrByFloat(String key, String field, double value, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hincrByFloat(key, field, value);
//    }
//
//    public Long hlen(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hlen(key);
//    }
//
//    public Long hsetnx(String key, String field, String value, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hsetnx(key, field, value);
//    }
//
//    public Boolean hexists(String key, String field, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hexists(key, field);
//    }
//
//    public Map<String, String> hexists(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.hgetAll(key);
//    }
//
//    public Long lpush(String key, int dbIndex, String... values) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.lpush(key, values);
//    }
//
//    public Long lpushx(String key, int dbIndex, String... values) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.lpushx(key, values);
//    }
//
//    public Long rpush(String key, int dbIndex, String... values) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.rpush(key, values);
//    }
//
//    public Long rpushx(String key, int dbIndex, String... values) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.rpushx(key, values);
//    }
//
//    public String lpop(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.lpop(key);
//    }
//
//    public String rpop(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.rpop(key);
//    }
//
//    public List<String> lrange(String key, long start, long end, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.lrange(key, start, end);
//    }
//
//    public Long llen(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.llen(key);
//    }
//
//    public String ltrim(String key, long start, long end, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.ltrim(key, start, end);
//    }
//
//    public Long lrem(String key, long count, String value, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.lrem(key, count, value);
//    }
//
//    public String lindex(String key, long index, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.lindex(key, index);
//    }
//
//    public Long sadd(String key, int dbIndex, String... members) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.sadd(key, members);
//    }
//
//    public Long srem(String key, int dbIndex, String... members) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.srem(key, members);
//    }
//
//    public String spop(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.spop(key);
//    }
//
//    public Set<String> sdiff(int dbIndex, String... keys) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.sdiff(keys);
//    }
//
//    public Set<String> sunion(int dbIndex, String... keys) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.sunion(keys);
//    }
//
//    public String srandmember(String key, int dbIndex) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.srandmember(key);
//    }
//
//    public List<String> srandmember(String key, int dbIndex, int count) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.srandmember(key, count);
//    }
//
//    public Long zadd(String key, int dbIndex, double score, String member) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.zadd(key, score, member);
//    }
//
//    public Set<String> zrange(String key, int dbIndex, long start, long end) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.zrange(key, start, end);
//    }
//
//    public Set<String> zrange(String key, int dbIndex, String min, String max) {
//        Jedis jedis = getJedis(dbIndex);
//        return jedis.zrangeByLex(key, min, max);
//    }
//
//    public void setRedisTemplate(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
}
