package org.evanframework.datadict.service.support.enums;

import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.dto.DataDictionaryEnum;

import java.util.HashMap;
import java.util.Map;


/**
 * 发布状态
 */
public enum EnumPublishStatus implements DataDictionaryEnum {
    /**
     * 未发布
     */
    NO_PUBLISH(1, "未发布", "#ff6600"),
    /**
     * 已发布
     */
    PUBLISHED(2, "已发布", "green");

    private static final Map<Integer, EnumPublishStatus> pool = new HashMap<Integer, EnumPublishStatus>();

    static {
        for (EnumPublishStatus each : EnumPublishStatus.values()) {
            EnumPublishStatus defined = pool.get(each.getValue());
            if (null != defined) {
                throw new IllegalArgumentException(defined.toString()
                        + " defined as same code with " + each.toString());
            }
            pool.put(each.getValue(), each);
        }
    }

    private Integer value;
    private String text;
    private String color;
    private DataDictionary dataDictionary;

    EnumPublishStatus(Integer value, String text, String color) {
        this.value = value;
        this.text = text;
        this.color = color;
        dataDictionary = new DataDictionary();
        dataDictionary.setValue(value + "");
        dataDictionary.setText(text);
        dataDictionary.setColor(color);
    }

    public Integer getValue() {
        return this.value;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    @Override
    public DataDictionary getDataDictionary() {
        return this.dataDictionary;
    }

    public static EnumPublishStatus valueOf(int value) {
        return pool.get(value);
    }
}
