package org.evanframework.datadict;

import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.dto.DataDictionaryList;

import java.util.Map;

/**
 * Created by evan.shen on 2017/5/1.
 */
public interface DatadictionaryProxy {
    /**
     * 根据分组获取字典
     */
    DataDictionaryList getForList(String group, boolean isIncludeDeleted);

    /**
     * 根据分组和上级字典取子数据
     * @param group
     * @param parentValue
     * @param isIncludeDeleted
     * @return
     */
    DataDictionaryList getForList(String group, String parentValue,
                                  boolean isIncludeDeleted);

    /**
     * 根据分组和值获取数据字典对象
     *
     * @param group
     * @return
     */
    DataDictionary getForObject(String group,String value);

    /**
     * 根据分组获取该分组下的数据字典，以Map<group, DataDictionary>方式返回
     *
     * @param group
     * @return
     */
    Map<String, DataDictionary> getForMap(String group);
}
