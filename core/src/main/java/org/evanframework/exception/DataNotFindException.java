package org.evanframework.exception;


import org.evanframework.model.result.OperateCommonResultType;

/**
 * @since 1.0
 */
public class DataNotFindException extends ServiceException {
    private static final long serialVersionUID = -951778798106425369L;

    public DataNotFindException() {
        super(OperateCommonResultType.DATA_NOT_FIND);
    }

    public DataNotFindException(String message) {
        super(OperateCommonResultType.DATA_NOT_FIND.getCode(), message);
    }
}
