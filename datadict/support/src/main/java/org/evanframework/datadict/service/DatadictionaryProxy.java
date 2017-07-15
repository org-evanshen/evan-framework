package org.evanframework.datadict.service;

import org.evanframework.datadict.service.dto.DataDictionary;
import org.evanframework.datadict.service.dto.DataDictionaryList;

import java.util.Map;

/**
 * Created by evan.shen on 2017/5/1.
 */
public interface DatadictionaryProxy {
    /**
     * 根据分组取该父节点的字节点
     */
    DataDictionaryList getByGroup(String group, boolean isIncludeDeleted);

    DataDictionaryList getByGroupAndParentValue(String group, String parentValue,
                                                boolean isIncludeDeleted);

    /**
     * 根据分组获取该分组下的数据字典，以Map<group, PubDataDictionary>方式返回
     *
     * @param group
     * @return
     */
    public Map<String, DataDictionary> getByGroupForMap(String group);
}
