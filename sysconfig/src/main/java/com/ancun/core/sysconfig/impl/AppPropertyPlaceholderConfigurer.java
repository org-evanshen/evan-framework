package com.ancun.core.sysconfig.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import static org.springframework.beans.factory.config.PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX;
import static org.springframework.beans.factory.config.PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_SUFFIX;

/**
 * 扩展的属性PlaceholderConfigurer
 * <P>
 * 可将*.properties文件中的属性全部加载到Properties中
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-5-4 上午8:50:39
 */
public class AppPropertyPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

	protected Properties properties;

	@Override
	public void setLocations(Resource[] locations) {
		super.setLocations(locations);
		try {
			this.properties = super.mergeProperties();

			Iterator<Entry<Object, Object>> itr = properties.entrySet().iterator();

			String innerKey, value = null;
			while (itr.hasNext()) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Entry<String, String> e = (Entry) itr.next();

				int p1 = e.getValue().indexOf(DEFAULT_PLACEHOLDER_PREFIX);
				int p2 = e.getValue().indexOf(DEFAULT_PLACEHOLDER_SUFFIX);

				if (p1 > -1 && p2 > -1) {
					innerKey = e.getValue().substring(p1 + DEFAULT_PLACEHOLDER_PREFIX.length(), p2);
					value = e.getValue().replace(DEFAULT_PLACEHOLDER_PREFIX + innerKey + DEFAULT_PLACEHOLDER_SUFFIX,
							this.properties.get(innerKey).toString());
				} else {
					value = e.getValue();
				}

				properties.put(e.getKey(), value);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
