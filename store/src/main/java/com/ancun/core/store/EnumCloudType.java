package com.ancun.core.store;

import java.util.HashMap;
import java.util.Map;

/**
 * EnumCloudType
 *
 * @author <a href="mailto:shenwei@ancun.com">Evan.shen</a>
 * @date 16/9/16
 */
public enum EnumCloudType {

    OSS("oss", 1),
    BOS("bos", 2);

    private String name;
    private int value;


    private static Map<Integer, EnumCloudType> pool = new HashMap<Integer, EnumCloudType>();

    static {
        for (EnumCloudType each : EnumCloudType.values()) {
            EnumCloudType defined = pool.get(each.getValue());
            if (null != defined) {
                throw new java.lang.IllegalArgumentException(defined.toString()
                        + " defined as same code with " + each.toString());
            }
            pool.put(each.getValue(), each);
        }
    }
    EnumCloudType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static EnumCloudType valueOf(int value) {
        return pool.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
