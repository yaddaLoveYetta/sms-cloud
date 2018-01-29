package com.kingdee.hrp.sms.common.exception;

/**
 * 错误基础类
 *
 * @author yadda
 * @ClassName BaseRuntimeException
 * @Description 增加系统错误代码
 * @date 2017-04-15 22:29:23 星期六
 */
public abstract class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public abstract int getErrCode();

    public BaseRuntimeException() {
        super();
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

}
