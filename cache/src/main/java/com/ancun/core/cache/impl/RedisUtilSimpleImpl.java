package com.ancun.core.cache.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ancun.core.cache.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redisUtils Jedis原生态实现
 * <p/>
 * <p/>
 * create at 2016年4月26日 下午8:11:54
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public class RedisUtilSimpleImpl implements RedisUtil {

    // aliRedis服务器IP
    private String host = "localhost";
    // aliRedis的端口号
    private int port = 6379;
    // aliRedis的连接超时
    private int timeout = 3000;
    // aliRedis的密码
    private String passowrd = "";
    private int maxIdle = 200;
    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private int maxTotal = 512;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private boolean testOnBorrow = true;
    private boolean testOnReturn = true;
    private JedisPoolConfig config;
    private JedisPool jedisPool = null;
    private Jedis jedis;

    /**
     * 初始化Redis配置
     */
    void init() {
//        host = propertyConfigurer.getProperty("redis.host");
//        port = Integer.parseInt(propertyConfigurer.getProperty("redis.port"));
//        timeout = Integer.parseInt(propertyConfigurer.getProperty("redis.timeout"));
//        passowrd = propertyConfigurer.getProperty("redis.passowrd");
//        maxIdle = Integer.parseInt(propertyConfigurer.getProperty("redis.maxidle"));
//        maxTotal = Integer.parseInt(propertyConfigurer.getProperty("redis.maxtotal"));
//        testOnBorrow = Boolean.parseBoolean(propertyConfigurer.getProperty("redis.testonborrow"));
//        testOnReturn = Boolean.parseBoolean(propertyConfigurer.getProperty("redis.testonreturn"));

        // 获取初始配置
        config = new JedisPoolConfig();
        // 最大空闲连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
        config.setMaxIdle(maxIdle);
        // 最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        jedisPool = new JedisPool(config, host, port, timeout, passowrd);
        jedis = jedisPool.getResource();
    }

    /**
     * 释放jedis资源
     */
    public void destory() {
        if (jedis != null) {
            // jedisPool.returnResource(jedis);//jedis 2.5/2.6
            jedis.close();// jedis 2.7
        }
        jedisPool.destroy();// jedis 2.7
    }

    /**
     * 清空所有数据
     * <p/>
     * <p/>
     * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     * create at: 2016年3月24日 下午2:39:21
     */
    public void removeAll() {
        jedis.flushAll();
    }

    @Override
    public void put(Serializable objectTypeKey, Serializable objectKey, Object o, Date expiry) {
        jedis.set(objectTypeKey + "" + objectKey, o.toString());
        if (expiry != null) {
            jedis.expire(objectTypeKey + "" + objectKey, expiry.getSeconds());
        }
    }

    @Override
    public <T> T get(Serializable objectTypeKey, Serializable objectKey, Class<T> c) {
        return (T) jedis.get(objectTypeKey + "" + objectKey);
    }

    @Override
    public void remove(Serializable objectTypeKey, Serializable objectKey) {
        jedis.del(objectTypeKey + "" + objectKey);
    }

    @Override
    public void put(Serializable objectTypeKey, Serializable objectKey, Object o, Long expireSeconds) {
        // TODO Auto-generated method stub
    }

    public <T> Set<String> keys(String pattern) {
        return jedis.keys(pattern);
    }

    @Override
    public void remove(String redisKey) {
        jedis.del(redisKey);
    }

    @Override
    public <T> Set<?> scan(long count, String pattern) {
        Set<String> set = new HashSet<>();
        set.addAll(jedis.scan("0").getResult());
        return set;
    }

    @Override
    public Map<?, ?> getAll(String key) {
        return jedis.hgetAll(key);
    }

    @Override
    public void flushAll() {
        jedis.flushAll();
    }

    @Override
    public boolean has(Serializable objectTypeKey, Serializable objectKey) {
        return false;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
}
