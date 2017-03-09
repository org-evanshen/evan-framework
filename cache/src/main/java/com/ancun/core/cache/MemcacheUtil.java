package com.ancun.core.cache;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whalin.MemCached.MemCachedClient;

/**
 * Memcache工具类
 *  
 * <p>
 * create at 2014年4月18日 下午5:39:29 
 * @author  <a href="mailto:shenwei@ancun.com">ShenWei</a> 
 * @version %I%, %G%
 *
 */
public class MemcacheUtil {

	private static final Logger log = LoggerFactory.getLogger(MemcacheUtil.class);

	private MemCachedClient memCachedClient;

	public void put(Serializable key, Object o) {
		put(key, o, null);	
	}	

	public void put(Serializable key, Object o, Date expiry) {
		if (key != null && o != null) {

			if (log.isTraceEnabled()) {
				log.trace("put memcache key is [" + key + "],value is [" + o + "]");
			}
			if (expiry == null) {
				memCachedClient.set(key.toString(), o);
			} else {
				memCachedClient.set(key.toString(), o, expiry);
			}

		} else {
			if (log.isTraceEnabled()) {
				log.trace("no memcache put");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Serializable key, Class<T> c) {
		T returnO = null;
		if (key != null) {
			Object o = memCachedClient.get(key.toString());
			if (o != null) {
				returnO = (T) o;
				if (log.isTraceEnabled()) {
					log.trace("get memcache key is [" + key + "],value is [" + returnO + "]");
				}
			} else {
				if (log.isTraceEnabled()) {
					log.trace("not find memcache key is [" + key + "]");
				}
			}
		}

		return returnO;
	}

	public void remove(Serializable key) {
		if (key != null) {
			memCachedClient.delete(key.toString());
		}
	}

	public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}
}
