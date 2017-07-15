package org.evanframework.datadict.service.support.enums;

import org.evanframework.datadict.service.dto.DataDictionary;
import org.evanframework.datadict.service.dto.DataDictionaryEnum;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum EnumRegionStatus implements DataDictionaryEnum {
    /**
     * 正常
     */
    NORMAL(1, "正常", "green"),
    /**
     * 失效
     */
    DELETED(-1, "失效", "red");

    private Integer value;
    private String text;
    private String color;

    private DataDictionary dataDictionary;

    private static final Map<Integer, EnumRegionStatus> pool = new HashMap<Integer, EnumRegionStatus>();

    static {
        for (EnumRegionStatus each : EnumRegionStatus.values()) {
            EnumRegionStatus defined = pool.get(each.getValue());
            if (null != defined) {
                throw new IllegalArgumentException(defined.toString() + " defined as same code with " + each.toString());
            }
            pool.put(each.getValue(), each);
        }
    }

    EnumRegionStatus(int value, String text, String color) {
        this.value = value;
        this.text = text;
        this.color = color;
        dataDictionary = new DataDictionary();
        dataDictionary.setValue(value + "");
        dataDictionary.setText(text);
        dataDictionary.setColor(color);
    }

    @Override
    public DataDictionary getDataDictionary() {
        return dataDictionary;
    }

    public int getValue() {
        return this.value;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public static EnumRegionStatus valueOf(int value) {
        return pool.get(value);
    }
}
