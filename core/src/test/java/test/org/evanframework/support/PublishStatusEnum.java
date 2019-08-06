package test.org.evanframework.support;

import java.util.HashMap;
import java.util.Map;


/**
 * 发布状态
 */
public enum PublishStatusEnum {
    /**
     * 未发布
     */
    NO_PUBLISH(1, "未发布", "#ff6600"),
    /**
     * 已发布
     */
    PUBLISHED(2, "已发布", "green");

    private static final Map<Integer, PublishStatusEnum> pool = new HashMap<Integer, PublishStatusEnum>();

    static {
        for (PublishStatusEnum each : PublishStatusEnum.values()) {
            PublishStatusEnum defined = pool.get(each.getValue());
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


    PublishStatusEnum(Integer value, String text, String color) {
        this.value = value;
        this.text = text;
        this.color = color;

    }

    public static PublishStatusEnum valueOf(int value) {
        return pool.get(value);
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
}
