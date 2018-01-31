package com.kingdee.hrp.sms.common.exception;


import com.kingdee.hrp.sms.common.domain.StatusCode;

public class SessionLostRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public int getErrCode() {

		return StatusCode.SESSION_LOST;
	}

	public SessionLostRuntimeException() {
		super();
	}

	public SessionLostRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SessionLostRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionLostRuntimeException(String message) {
		super(message);
	}

	public SessionLostRuntimeException(Throwable cause) {
		super(cause);
	}

}
