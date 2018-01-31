package com.kingdee.hrp.sms.common.exception;


import com.kingdee.hrp.sms.common.domain.StatusCode;

/**
 * 业务错误
 * 
 * @ClassName BusinessLogicRunTimeException
 * @Description 业务错误提示
 * @author yadda
 * @date 2017-04-15 22:28:04 星期六
 */
public class BusinessLogicRunTimeException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public int getErrCode() {
		return StatusCode.BUSINESS_LOGIC_ERROR;
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

	public BusinessLogicRunTimeException(Throwable cause) {
		super(cause);
	}

}
