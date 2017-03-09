package com.ancun.core.datadict.model;

import java.util.HashMap;
import java.util.Map;

public enum PubDataDictionaryColumns {
    /**
     * 分组
     */
    DICT_GROUP("dictGroup", "DICT_GROUP"),
    /**
     * 数据字典的值
     */
    DICT_VALUE("dictValue", "DICT_VALUE"),
    /***/
    GMT_CREATE("gmtCreate", "GMT_CREATE"),
    /**
     * 上级数据字典值
     */
    PARENT_VALUE("parentValue", "PARENT_VALUE"),
    /**
     * 文本颜色
     */
    TEXT_COLOR("textColor", "TEXT_COLOR"),
    /**
     * 数据字典的显示文本
     */
    DICT_TEXT("dictText", "DICT_TEXT"),
    /**
     * 扩展1
     */
    EXTEND1("extend1", "EXTEND1"),
    /**
     * 排序
     */
    SORT_NUM("sortNum", "SORT_NUM"),
    /**
     * 扩展3
     */
    EXTEND3("extend3", "EXTEND3"),
    /**
     * 扩展2
     */
    EXTEND2("extend2", "EXTEND2"),
    /***/
    GMT_MODIFY("gmtModify", "GMT_MODIFY"),
    /**
     * 1 正常 -1 已删除
     */
    STATUS("status", "STATUS"),
    /**
     * 数据字典所子系统 多个以','分割
     */
    SUB_SYSTEM("subSystem", "SUB_SYSTEM"),;

    private static Map<String, PubDataDictionaryColumns> pool = new HashMap<String, PubDataDictionaryColumns>();

    static {
        for (PubDataDictionaryColumns each : PubDataDictionaryColumns.values()) {
            PubDataDictionaryColumns defined = pool.get(each.getProperty());
            if (null != defined) {
                throw new java.lang.IllegalArgumentException(defined.toString() + " defined as same code with " + each.toString());
            }
            pool.put(each.getProperty(), each);
        }
    }

    private String property;
    private String column;
    private String alias;

    private PubDataDictionaryColumns(String property, String column) {
        this.property = property;
        this.column = "pdd." + column;
        this.alias = this.column + " as PUB_DATA_DICTIONARY_" + column;
    }

    public static PubDataDictionaryColumns valueOfProperty(String property) {
        return pool.get(property);
    }

    /**
     * 表别名.列名
     */
    public String getColumn() {
        return column;
    }

    public String getProperty() {
        return property;
    }

    /**
     * 表别名.列名 as 表名_列名
     */
    public String getAlias() {
        return alias;
    }
}