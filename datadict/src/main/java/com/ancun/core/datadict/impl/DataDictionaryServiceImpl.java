package com.ancun.core.datadict.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ancun.core.datadict.DataDictionaryService;
import com.ancun.core.datadict.cache.DataDictionaryCacheForData;
import com.ancun.core.datadict.cache.DataDictionaryCacheForName;
import com.ancun.core.datadict.dto.DataDictionaryList;
import com.ancun.core.datadict.model.PubDataDictionaryEntity;
import com.ancun.core.model.DataDictionary;

/**
 * 数据字典服务接口实现
 * <p/>
 * create at 2014年4月17日 下午9:05:04
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public class DataDictionaryServiceImpl implements DataDictionaryService {
    private final static Logger log = LoggerFactory.getLogger(DataDictionaryServiceImpl.class);

    @Resource
    private DataDictionaryCacheForData dataDictionaryCacheForData;
    @Resource
    private DataDictionaryCacheForName dataDictionaryCacheForName;
    @Resource
    private DataDictionaryEnumLoader dataDictionaryEnumLoader;
    @Autowired(required = false)
    private PubDataDictionaryDao pubDataDictionaryDao;

    /**
     * 获取分组获取数据字典
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-2-26 下午10:58:04 <br>
     */
    @Override
    public DataDictionaryList getByGroup(String group) {
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

        // 从数据库中取
        List<PubDataDictionaryEntity> list2 = pubDataDictionaryDao.getByGroup(group, false);
        if (list2 != null && !list2.isEmpty()) {
            dds = new DataDictionaryList();

            for (PubDataDictionaryEntity o : list2) {
                dds.add(o.toDTO());
            }

            if (log.isTraceEnabled()) {
                log.trace(
                        "get data dictionary from database, the key is :[" + group + "], size is [" + dds.size() + "]");
                log.trace("put data dictionary to cache, the group is [" + group + "], size: [" + dds.size() + "]");
            }
            dataDictionaryCacheForData.put(group, dds);
        }
        return dds;
    }

    /**
     * 获取分组和parentValue获取数据字
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-2-26 下午10:58:04 <br>
     */
    @Override
    public DataDictionaryList getByGroupAndParentValue(String group, String parentValue) {
        if (StringUtils.isBlank(group) || StringUtils.isBlank(parentValue)) {
            return null;
        }
        String key = null;
        if (StringUtils.isEmpty(parentValue)) {
            key = group;
        } else {
            key = group + DATA_GROUP_AND_VALUE_SPLIT + parentValue;
        }
        // 先从缓存中取
        DataDictionaryList dds = dataDictionaryCacheForData.get(key);

        if (dds == null) {
            List<PubDataDictionaryEntity> list2 = pubDataDictionaryDao.getByGroupAndParentValue(group, parentValue,
                    false);
            if (!list2.isEmpty()) {
                dds = new DataDictionaryList();
                for (PubDataDictionaryEntity o : list2) {
                    dds.add(o.toDTO());
                }
                dataDictionaryCacheForData.put(key, dds);
            }
        }

        return dds;
    }

    /**
     * 根据获取分组和值获取单个数据字典
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-2-27 上午01:13:34 <br>
     *
     * @param group
     * @param value
     */

    @Override
    public DataDictionary getByValue(String group, String value) {
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

        Map<String, PubDataDictionaryEntity> map2 = pubDataDictionaryDao.getByGroupForMap(group);
        if (!map2.isEmpty()) {
            if (log.isTraceEnabled()) {
                log.trace("find data dictionary the key is :[" + group + "],value is: [" + value + "] in database");
                log.trace("put data dictionary the key is [" + group + "], value is: [" + value + "]");
            }
            Map<String, DataDictionary> map = new HashMap<String, DataDictionary>();
            for (Map.Entry<String, PubDataDictionaryEntity> entry : map2.entrySet()) {
                map.put(entry.getKey(), entry.getValue().toDTO());
            }
            dd = map.get(value);
            if (dd != null) {
                dataDictionaryCacheForName.put(group + DATA_GROUP_AND_VALUE_SPLIT + value, dd);
            }
        }

        return dd;
    }

    @Override
    public DataDictionary getByValue(String group, String value, Map<String, DataDictionary> localCache) {
        if (StringUtils.isBlank(group) || StringUtils.isBlank(value)) {
            return null;
        }

        DataDictionary dd = null;

        if (localCache != null) {
            dd = localCache.get(group + DATA_GROUP_AND_VALUE_SPLIT + value);
        }

        if (dd == null) {
            dd = getByValue(group, value);
            if (localCache != null && dd != null) {
                localCache.put(group + DATA_GROUP_AND_VALUE_SPLIT + value, dd);
            }
        }
        return dd;
    }

    @Override
    public String getTextByValue(String group, String value, Map<String, String> localCache) {
        if (StringUtils.isBlank(group) || StringUtils.isBlank(value)) {
            return null;
        }
        String returnV = null;
        if (localCache != null) {
            returnV = localCache.get(group + DATA_GROUP_AND_VALUE_SPLIT + value);
        }
        if (StringUtils.isBlank(returnV)) {
            DataDictionary dd = getByValue(group, value);
            if (dd != null) {
                returnV = dd.getText();
                if (localCache != null) {
                    localCache.put(group + DATA_GROUP_AND_VALUE_SPLIT + value, returnV);
                }
            }
        }
        return returnV;
    }
}
