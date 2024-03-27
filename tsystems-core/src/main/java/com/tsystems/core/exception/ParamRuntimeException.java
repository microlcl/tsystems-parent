package com.tsystems.core.exception;

import com.tsystems.core.constant.ResponseCode;

/**
 * 
 * @author lcl
 *
 */
public class ParamRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = 3683494310458128606L;

	public ParamRuntimeException() {
		super();
	}

	public ParamRuntimeException(Throwable cause) {
		super(cause);
		this.errorCode = ResponseCode.RC301.getCode();
		this.errorMessage = ResponseCode.RC301.getMessage();
	}

	public ParamRuntimeException(String message) {
		super();
		this.errorCode = ResponseCode.RC301.getCode();
		this.errorMessage = message;
	}
	
}

