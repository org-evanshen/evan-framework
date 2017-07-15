package org.evanframework.datadict.service.support.enums;

import org.evanframework.datadict.service.dto.DataDictionary;
import org.evanframework.datadict.service.dto.DataDictionaryEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员状态
 */
public enum EnumAdminUserStatus implements DataDictionaryEnum {
    /**
     * 正常
     */
    NORMAL(1, "正常", "green"),
    /**
     * 冻结
     */
    DEPARTURE(2, "冻结", "#ff6600"),
    /**
     * 锁定
     */
    LOCK(3, "锁定", "red");

    //	/** 外派 */
    //ASSIGNMENT(4, "外派", "blue");
    //	/** 冻结 */
    //FREEZE(5, "冻结1", "red")


    private int value;
    private String text;
    private String color;

    private DataDictionary dataDictionary;

    private static Map<Integer, EnumAdminUserStatus> pool = new HashMap<Integer, EnumAdminUserStatus>();

    static {
        for (EnumAdminUserStatus each : EnumAdminUserStatus.values()) {
            EnumAdminUserStatus defined = pool.get(each.getValue());
            if (null != defined) {
                throw new IllegalArgumentException(defined.toString() + " defined as same code with "
                        + each.toString());
            }
            pool.put(each.getValue(), each);
        }
    }

    EnumAdminUserStatus(int value, String text, String color) {
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

    public static EnumAdminUserStatus valueOf(int value) {
        return pool.get(value);
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return this.value;
    }

    public String getText() {
        return text;
    }
}
