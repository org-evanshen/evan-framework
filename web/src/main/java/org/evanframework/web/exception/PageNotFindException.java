package org.evanframework.web.exception;

import org.evanframework.exception.ServiceException;

/**
 * 页面找不到 <br>
 * 业务异常的一种
 * <p>
 *
 * @author shen.wei
 * @version 2011-9-2 12:46:55
 */
public class PageNotFindException extends ServiceException {
    private static final long serialVersionUID = 3583566093089790852L;

    private static final String CODE = "PAGE_NOT_FIND";

    public PageNotFindException() {
        super(CODE, "非常抱歉,您所访问的页面不存在!");
    }

    public PageNotFindException(String msg) {
        super(CODE, msg);
    }
}
