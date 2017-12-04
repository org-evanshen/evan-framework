package org.evanframework.utils;

/**
 * @author evan.shen
 * @version 2017/12/4
 * @since 2.1
 */
public class AESException extends Exception {
    private static final long serialVersionUID = -4132765618689655059L;

    public AESException(String message, Throwable cause) {
        super(message, cause);
    }

    public AESException(String message) {
        super(message);
    }
}
