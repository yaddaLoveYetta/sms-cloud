package com.kingdee.hrp.sms.common.exception;


import com.kingdee.hrp.sms.common.model.StatusCode;

/**
 * 插件逻辑阻止业务继续运行错误类
 * 
 * @author yadda
 *
 */
public class PlugInRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public int getErrCode() {
		return StatusCode.PLUGIN_ERROR;
	}

	public PlugInRuntimeException() {
		super("插件错误！");
	}

	public PlugInRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlugInRuntimeException(String message) {
		super(message);
	}

	public PlugInRuntimeException(Throwable cause) {
		super(cause);
	}
}
