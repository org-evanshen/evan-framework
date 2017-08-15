package org.evanframework.datadict.service;

import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.dto.DataDictionaryEnum;
import org.evanframework.datadict.dto.DataDictionaryList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 用枚举作为数据字典的创建器
 * <p/>
 *
 * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
 * @version 2012-9-20 下午3:41:17
 */
@Component
public class DataDictionaryEnumLoader implements ApplicationContextAware {
    private final static Logger log = LoggerFactory.getLogger(DataDictionaryEnumLoader.class);

    private final static Map<String, DataDictionaryList> dataDictionaryDatas = new HashMap<String, DataDictionaryList>();
    private final static Map<String, Map<String, DataDictionary>> dataDictionaryName = new HashMap<String, Map<String, DataDictionary>>();
    /**
     * 记录已加载的枚举构建器
     */
    private final static Set<String> dataDictionaryEnumBuildorsLoaded = new HashSet<String>();

    private ApplicationContext applicationContext;

    /**
     * 作为数据字典的枚举，在这里创建
     */
    @PostConstruct
    public void init() {
        Map<String, DataDictionaryEnumBuildor> dataDictionaryEnumBuildors = applicationContext
                .getBeansOfType(DataDictionaryEnumBuildor.class);

        Map<String, DataDictionaryEnum[]> map = new HashMap<String, DataDictionaryEnum[]>();

        if (dataDictionaryEnumBuildors != null && !dataDictionaryEnumBuildors.isEmpty()) {
            for (Entry<String, DataDictionaryEnumBuildor> e : dataDictionaryEnumBuildors.entrySet()) {
                DataDictionaryEnumBuildor buildor = e.getValue();
                if (!dataDictionaryEnumBuildorsLoaded.contains(buildor.getClass().getName())) {
                    map.clear();
                    map.putAll(buildor.getEnums());
                    for (Map.Entry<String, DataDictionaryEnum[]> enter : map.entrySet()) {
                        addByKey(enter.getKey(), enter.getValue());
                    }

                    log.info("Inited [" + buildor.getClass() + "] [" + map.size() + "] dataDictionary enums");

                    dataDictionaryEnumBuildorsLoaded.add(buildor.getClass().getName());
                }
            }
        }
    }

    /**
     * 用枚举创建数据字典
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2012-9-20 下午3:42:06 <br>
     *
     * @param key
     * @param values
     */
    private void addByKey(String key, DataDictionaryEnum[] values) {
        DataDictionaryList list = new DataDictionaryList();
        Map<String, DataDictionary> map = new HashMap<String, DataDictionary>();
        for (DataDictionaryEnum enumdd : values) {
            DataDictionary dd = enumdd.getDataDictionary();
            list.add(dd);
            map.put(dd.getValue(), dd);
        }
        dataDictionaryDatas.put(key, list);
        dataDictionaryName.put(key, map);
    }

    /**
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2012-9-20 下午3:42:28 <br>
     *
     * @param key
     */
    public DataDictionaryList getList(String key) {
        DataDictionaryList list = dataDictionaryDatas.get(key);
        // if (log.isDebugEnabled()) {
        // if (list == null) {
        // log.debug("not find enum data dictionary list the key is :[" + key +
        // "]");
        // } else {
        // log.debug("find enum data dictionary list the key is :[" + key +
        // "]");
        // }
        // }
        return list;
    }

    /**
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2012-9-20 下午3:42:35 <br>
     *
     * @param key
     * @param value
     */
    public DataDictionary getOne(String key, String value) {
        DataDictionary dd = null;
        Map<String, DataDictionary> map = dataDictionaryName.get(key);
        if (map != null) {
            dd = map.get(value);
        }
        // if (log.isDebugEnabled()) {
        // if (dd == null) {
        // log.debug("not find enum data dictionary object the key is :[" + key
        // + "],value is: [" + value + "]");
        // } else {
        // log.debug("find enum data dictionary object the key is :[" + key +
        // "],value is: [" + value + "]");
        // }
        // }
        return dd;
    }

    // public Map<String, Map<String, DataDictionary>> getAll() {
    // return dataDictionaryName;
    // }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PreDestroy
    public void destroy() {
        dataDictionaryEnumBuildorsLoaded.clear();
    }
}
