package org.evanframework.datadict.service.support.enums;

import org.evanframework.datadict.service.dto.DataDictionary;
import org.evanframework.datadict.service.dto.DataDictionaryEnum;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum EnumDataDictStatus implements DataDictionaryEnum {
    /**
     * 正常
     */
    NORMAL(1, "正常", "green"),
    /**
     * 失效
     */
    DEPARTURE(-1, "已删除", "red");

    private int value;
    private String text;
    private String color;

    private DataDictionary dataDictionary;

    private static Map<Integer, EnumDataDictStatus> pool = new HashMap<Integer, EnumDataDictStatus>();

    static {
        for (EnumDataDictStatus each : EnumDataDictStatus.values()) {
            EnumDataDictStatus defined = pool.get(each.getValue());
            if (null != defined) {
                throw new IllegalArgumentException(defined.toString() + " defined as same code with "
                        + each.toString());
            }
            pool.put(each.getValue(), each);
        }
    }

    EnumDataDictStatus(int value, String text, String color) {
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

    public static EnumDataDictStatus getByValue(String value) {
        return pool.get(value);
    }
}
