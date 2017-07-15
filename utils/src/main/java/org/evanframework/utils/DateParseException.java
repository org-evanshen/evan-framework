package org.evanframework.utils;

/**
 * Created by evan.shen on 2017/3/18.
 */
public class DateParseException extends RuntimeException {

    public DateParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateParseException(String message) {
        super(message);
    }

    public DateParseException(Throwable cause) {
        super("日期解析错误", cause);
    }
}
