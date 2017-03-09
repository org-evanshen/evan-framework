package com.ancun.core.cache;

import java.util.concurrent.ExecutionException;

import com.schooner.MemCached.PlainCallbackHandler;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;

public class AliOcsUtil {

    // ocs 控制台上的“内网地址”
    private static String host;
    // ocs 默认端口 11211，不用改
    private static String port;
    // ocs 控制台上的“访问账号”
    private static String username;
    // ocs 邮件中提供的“密码”
    private static String password;
    private static MemcachedClient cache = null;

    /**
     * 获取OCS实例
     */
    private synchronized static MemcachedClient getCache() {
        try {
            AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                    new PlainCallbackHandler(username, password));

            cache = new MemcachedClient(
                    new ConnectionFactoryBuilder().setProtocol(Protocol.BINARY).setAuthDescriptor(ad).build(),
                    AddrUtil.getAddresses(host + ":" + port));
            if (cache != null) {
                return cache;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放OCS资源
     */
    private static void returnResource(final MemcachedClient cache) {
        if (cache != null) {
            cache.shutdown();
        }
    }

    /**
     * 新增aliyun OCS key
     *
     * @param key
     * @param value
     * @param expireTime 过期时间(单位：秒)
     *                   <p/>
     *                   author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *                   create at: 2016年3月23日 下午7:32:00
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void put(String key, Object value, int expireTime) {
        MemcachedClient cache = getCache();
        cache.set(key, expireTime, value);
        returnResource(cache);
    }

    /**
     * 获取aliyun OCS value by key
     *
     * @param key <p/>
     *            author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *            create at: 2016年3月23日 下午7:40:58
     */
    public Object get(String key) {
        MemcachedClient cache = getCache();
        Object value = cache.get(key);
        returnResource(cache);
        return value;
    }

    /**
     * 删除aliyun OCS value by key
     *
     * @param key <p/>
     *            author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     *            create at: 2016年3月23日 下午7:41:24
     */
    public void remove(String key) {
        MemcachedClient cache = getCache();
        cache.delete(key);
        returnResource(cache);
    }

    /**
     * 清空Memcached数据
     * <p/>
     * <p/>
     * author: <a href="mailto:xiangzhitong@ancun.org">xiangzhitong</a><br>
     * create at: 2016年3月24日 下午2:46:54
     */
    public void removeAll() {
        MemcachedClient cache = getCache();
        cache.flush();
        returnResource(cache);
    }

    public static void setHost(String host) {
        AliOcsUtil.host = host;
    }

    public static void setPort(String port) {
        AliOcsUtil.port = port;
    }

    public static void setUsername(String username) {
        AliOcsUtil.username = username;
    }

    public static void setPassword(String password) {
        AliOcsUtil.password = password;
    }
}
