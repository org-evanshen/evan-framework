package com.ancun.core.datadict;

import java.util.Map;

import com.ancun.core.datadict.dto.DataDictionaryList;
import com.ancun.core.model.DataDictionary;

public interface DataDictionaryService {
    String DATA_GROUP_AND_VALUE_SPLIT = "$_$";

    /**
     * 获取分组获取数据字典
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-2-26 下午10:58:04 <br>
     */
    DataDictionaryList getByGroup(String group);

    /**
     * 获取分组和parentValue获取数据字
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-2-26 下午10:58:04 <br>
     */
    DataDictionaryList getByGroupAndParentValue(String group, String parentValue);

    /**
     * 根据获取分组和值获取单个数据字典
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-2-27 上午01:13:34 <br>
     *
     * @param group
     * @param value
     */
    DataDictionary getByValue(String group, String value);

    /**
     * 根据获取分组和值获取单个数据字典,并加入本地缓存
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-2-27 上午01:13:34 <br>
     *
     * @param group
     * @param value
     */
    DataDictionary getByValue(String group, String value, Map<String, DataDictionary> localCache);

    /**
     * 根据获取分组和值获取单个数据字典的文本
     *
     * @param group
     * @param value
     * @param localCache 如果是循环调用该方法，可使用本地线程缓存 new HashMap<String, String>();
     *                   <p/>
     *                   <p/>
     *                   author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     *                   create at: 2014年4月18日下午2:05:00
     */
    String getTextByValue(String group, String value, Map<String, String> localCache);
}
