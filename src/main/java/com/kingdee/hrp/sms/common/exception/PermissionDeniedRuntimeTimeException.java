package com.kingdee.hrp.sms.common.exception;


import com.kingdee.hrp.sms.common.model.StatusCode;

/**
 * 权限出错
 * 
 * @ClassName PermissionDeniedRuntimeTimeException
 * @Description 没有权限
 * @author yadda
 * @date 2017-04-15 22:24:27 星期六
 */
public class PermissionDeniedRuntimeTimeException extends BaseRuntimeException {

	private static final long serialVersionUID = -9015231003731531075L;

	@Override
	public int getErrCode() {
		return StatusCode.PERMISSION_INVALID;
	}

	public PermissionDeniedRuntimeTimeException() {
		super();
	}

	public PermissionDeniedRuntimeTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PermissionDeniedRuntimeTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionDeniedRuntimeTimeException(String message) {
		super(message);
	}

	public PermissionDeniedRuntimeTimeException(Throwable cause) {
		super(cause);
	}

}
