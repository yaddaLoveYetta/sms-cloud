package com.kingdee.hrp.sms.common.exception;


import com.kingdee.hrp.sms.common.pojo.StatusCode;

/**
 * 业务错误
 *
 * @author yadda
 * @ClassName BusinessLogicRunTimeException
 * @Description 业务错误提示
 * @date 2017-04-15 22:28:04 星期六
 */
public class BusinessLogicRunTimeException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer errorCode = StatusCode.BUSINESS_LOGIC_ERROR;

    @Override
    public int getErrCode() {
        return errorCode;
    }

    public BusinessLogicRunTimeException() {
        super();
    }

    public BusinessLogicRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BusinessLogicRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessLogicRunTimeException(String message) {
        super(message);
    }

    public BusinessLogicRunTimeException(Integer code, String message) {
        super(message);
        this.errorCode = code;
    }

    public BusinessLogicRunTimeException(Throwable cause) {
        super(cause);
    }

}
