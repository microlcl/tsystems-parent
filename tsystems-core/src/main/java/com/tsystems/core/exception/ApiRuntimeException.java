package com.tsystems.core.exception;

import com.tsystems.core.constant.ResponseCode;

/**
 * 
 * @author lcl
 *
 */
public class ApiRuntimeException extends BaseRuntimeException {


	private static final long serialVersionUID = -9077435642900752059L;

	public ApiRuntimeException() {
		super();
	}

	public ApiRuntimeException(Throwable cause) {
		super(cause);
		this.errorCode = ResponseCode.USERNAME_OR_PASSWORD_ERROR.getCode();
		this.errorMessage = ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMessage();
	}

	public ApiRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = ResponseCode.USERNAME_OR_PASSWORD_ERROR.getCode();

	}
	
	public ApiRuntimeException(String message, Throwable cause, String context) {
		super(message, cause);
		this.context = context;
		this.errorCode = ResponseCode.RC301.getCode();
	}
	
	public ApiRuntimeException(ResponseCode responseCode, Throwable cause, String context) {
		super(cause);
		this.errorCode = responseCode.getCode();
		this.errorMessage = responseCode.getMessage();
		this.context = context;
	}
	
	public ApiRuntimeException(String message) {
		this.errorCode = ResponseCode.RC501.getCode();
		this.errorMessage = message;
		this.context = ResponseCode.RC501.getMessage();
	}

}

