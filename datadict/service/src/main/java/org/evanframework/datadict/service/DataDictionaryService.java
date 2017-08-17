package org.evanframework.datadict.service;

import org.apache.commons.lang3.StringUtils;
import org.evanframework.datadict.DatadictionaryProxy;
import org.evanframework.datadict.cache.DataDictionaryCacheForData;
import org.evanframework.datadict.cache.DataDictionaryCacheForName;
import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.dto.DataDictionaryList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典服务类，支持枚举和数据库整合的方式
 *
 * @author <a href="mailto:277469513@qq.com">Evan.shen</a>
 * @date 16/8/12
 */
@Service
public class DataDictionaryService {
    private final static Logger log = LoggerFactory.getLogger(DataDictionaryService.class);

    private final static String DATA_GROUP_AND_VALUE_SPLIT = "&_&";

    @Autowired
    private DataDictionaryCacheForData dataDictionaryCacheForData;
    @Autowired
    private DataDictionaryCacheForName dataDictionaryCacheForName;
    @Autowired
    private DataDictionaryEnumLoader dataDictionaryEnumLoader;
    @Autowired(required = false)
    private DatadictionaryProxy datadictionaryProxy;

    /**
     * 获取分组获取数据字典
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2011-2-26 下午10:58:04 <br>
     */
    public DataDictionaryList getForList(String group) {
        if (StringUtils.isBlank(group)) {
            return null;
        }

        // 先从枚举中取
        DataDictionaryList dds = dataDictionaryEnumLoader.getList(group);// 先从枚举中取
        if (dds != null) {
            if (log.isTraceEnabled()) {
                log.trace("get data dictionary from enums, the key is [" + group + "] size is [" + dds.size() + "]");
            }
            return dds;
        }

        // 从缓存中取
        dds = dataDictionaryCacheForData.get(group);
        if (dds != null) {
            if (log.isTraceEnabled()) {
                log.trace("get data dictionary from cache, the key is [" + group + "] size is [" + dds.size()
                        + "] in cache");
            }
            return dds;
        }

        if (datadictionaryProxy != null) {
            // 从数据字典服务中取
            dds = datadictionaryProxy.getForList(group, false);
            if (dds != null && !dds.isEmpty()) {
                if (log.isTraceEnabled()) {
                    log.trace(
                            "get data dictionary from database, the key is :[" + group + "], size is [" + dds.size() + "]");
                    log.trace("put data dictionary to cache, the group is [" + group + "], size: [" + dds.size() + "]");
                }
                dataDictionaryCacheForData.put(group, dds);
            }
        }
        return dds;
    }

    /**
     * 获取分组和parentValue获取数据字
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2011-2-26 下午10:58:04 <br>
     */

    public DataDictionaryList getForList(String group, String parentValue) {
        if (StringUtils.isBlank(group)){
            return null;
        }

        if(StringUtils.isBlank(parentValue)){
            return getForList(group);
        }

        String key = group + DATA_GROUP_AND_VALUE_SPLIT + parentValue;
        // 先从缓存中取
        DataDictionaryList dds = dataDictionaryCacheForData.get(key);

        if (datadictionaryProxy != null) {
            // 从数据字典服务中取
            if (dds == null) {
                List<DataDictionary> list = datadictionaryProxy.getForList(group, parentValue,
                        false);
                if (!list.isEmpty()) {
                    dds = new DataDictionaryList(list);
                    dataDictionaryCacheForData.put(key, dds);
                }
            }
        }

        return dds;
    }

    /**
     * 根据获取分组和值获取单个数据字典
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2011-2-27 上午01:13:34 <br>
     *
     * @param group
     * @param value
     */
    public DataDictionary getForObject(String group, String value) {
        if (StringUtils.isBlank(group) || StringUtils.isBlank(value)) {
            return null;
        }

        // 从枚举中获取
        DataDictionary dd = dataDictionaryEnumLoader.getOne(group, value);

        if (dd != null) {
            if (log.isTraceEnabled()) {
                log.trace("get from enums, key is [" + group + "] value is " + dd);
            }
            return dd;
        }

        // 从缓存中获取
        dd = dataDictionaryCacheForName.get(group + DATA_GROUP_AND_VALUE_SPLIT + value);
        if (dd != null) {
            if (log.isTraceEnabled()) {
                log.trace("get from cache, key is [" + group + "] value is " + dd);
            }
            return dd;
        }

        if (datadictionaryProxy != null) {
            dd = datadictionaryProxy.getForObject(group, value);
            if (dd != null) {
                if (log.isTraceEnabled()) {
                    log.trace("find data dictionary the key is :[" + group + "],value is: [" + value + "] from base service");
                    log.trace("put data dictionary the key is [" + group + "], value is: [" + value + "]");
                }
                dataDictionaryCacheForName.put(group + DATA_GROUP_AND_VALUE_SPLIT + value, dd);
            }
        }
        return dd;
    }

    /**
     * group/DataDictionary的本地缓存用法，用于一个请求中，列表循环根据group获取DataDictionary的场景，请求结束缓存即刻消失
     *
     * @param group
     * @param value
     * @param localCache
     * @return
     */
    public DataDictionary getForObject(String group, String value, Map<String, DataDictionary> localCache) {
        if (StringUtils.isBlank(group) || StringUtils.isBlank(value)) {
            return null;
        }

        DataDictionary dd = null;

        if (localCache != null) {
            dd = localCache.get(group + DATA_GROUP_AND_VALUE_SPLIT + value);
        }

        if (dd == null) {
            dd = getForObject(group, value);
            if (localCache != null && dd != null) {
                localCache.put(group + DATA_GROUP_AND_VALUE_SPLIT + value, dd);
            }
        }
        return dd;
    }

    /**
     * group/dictdictText的本地缓存用法，用于一个请求中，列表循环根据group获取DataDictionary.getText()的场景，请求结束缓存即刻消失
     *
     * @param group
     * @param value
     * @param localCache
     * @return
     */
    public String getForString(String group, String value, Map<String, String> localCache) {
        if (StringUtils.isBlank(group) || StringUtils.isBlank(value)) {
            return null;
        }
        String returnV = null;
        if (localCache != null) {
            returnV = localCache.get(group + DATA_GROUP_AND_VALUE_SPLIT + value);
        }
        if (StringUtils.isBlank(returnV)) {
            DataDictionary dd = getForObject(group, value);
            if (dd != null) {
                returnV = dd.getText();
                if (localCache != null) {
                    localCache.put(group + DATA_GROUP_AND_VALUE_SPLIT + value, returnV);
                }
            }
        }
        return returnV;
    }

    /**
     * @param group
     * @return
     */
    public Map<String, DataDictionary> getForMap(String group) {
        Map<String, DataDictionary> map = new HashMap<>();
        DataDictionaryList dataDictionaryList = getForList(group);
        if (dataDictionaryList != null && !dataDictionaryList.isEmpty()) {
            for (DataDictionary e : dataDictionaryList) {
                map.put(e.getValue(),e);
            }
        }
        return map;
    }
}
