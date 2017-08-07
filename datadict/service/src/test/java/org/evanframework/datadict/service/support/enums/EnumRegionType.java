package org.evanframework.datadict.service.support.enums;

import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.dto.DataDictionaryEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 地区类型
 */
public enum EnumRegionType implements DataDictionaryEnum {
    /**
     * 国家
     */
    COUNTRY(1, "国家"),
    /**
     * 省份
     */
    PROVINCE(2, "省份"),
    /**
     * 城市
     */
    CITY(3, "城市"),
    /**
     * 地区
     */
    REGION(4, "地区");

    private static final Map<Integer, EnumRegionType> pool = new HashMap<Integer, EnumRegionType>();

    static {
        for (EnumRegionType each : EnumRegionType.values()) {
            EnumRegionType defined = pool.get(each.getValue());
            if (null != defined) {
                throw new IllegalArgumentException(defined.toString() + " defined as same code with " + each.toString());
            }
            pool.put(each.getValue(), each);
        }
    }

    private Integer value;
    private String text;
    private DataDictionary dataDictionary;

    EnumRegionType(Integer value, String text) {
        this.value = value;
        this.text = text;
        dataDictionary = new DataDictionary();
        dataDictionary.setValue(value.toString());
        dataDictionary.setText(text);
    }

    public Integer getValue() {
        return this.value;
    }

    public String getText() {
        return text;
    }

    public static EnumRegionType valueOf(int value) {
        return pool.get(value);
    }

    @Override
    public DataDictionary getDataDictionary() {
        return dataDictionary;
    }
}
