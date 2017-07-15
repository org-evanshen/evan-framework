package org.evanframework.datadict.service.support.enums;

import org.evanframework.datadict.service.dto.DataDictionary;
import org.evanframework.datadict.service.dto.DataDictionaryEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别
 */
public enum EnumSex implements DataDictionaryEnum {
    /**
     * 男
     */
    MAN(1, "男"),
    /**
     * 女
     */
    WOMAN(2, "女");

    private static Map<Integer, EnumSex> pool = new HashMap<Integer, EnumSex>();

    static {
        for (EnumSex each : EnumSex.values()) {
            EnumSex defined = pool.get(each.getValue());
            if (null != defined) {
                throw new IllegalArgumentException(defined.toString()
                        + " defined as same code with " + each.toString());
            }
            pool.put(each.getValue(), each);
        }
    }

    private int value;
    private String text;
    private DataDictionary dataDictionary;

    EnumSex(int value, String text) {
        this.value = value;
        this.text = text;
        dataDictionary = new DataDictionary();
        dataDictionary.setValue(this.value + "");
        dataDictionary.setText(this.text);
    }

    public int getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }

    public static EnumSex valueOf(int value) {
        return pool.get(value);
    }

    //	public String toString() {
    //		return JsonUtils.beanToJSON(this);
    //	}

    @Override
    public DataDictionary getDataDictionary() {
        return this.dataDictionary;
    }
}
