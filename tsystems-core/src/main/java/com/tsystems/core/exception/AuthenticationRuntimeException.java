package com.tsystems.core.exception;

import com.tsystems.core.constant.ResponseCode;

/**
 * 
 * @author lcl
 *
 */
public class AuthenticationRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = 3683494310458128606L;

	public AuthenticationRuntimeException() {
		super();
	}

	public AuthenticationRuntimeException(String message) {
		super(message);
		this.errorCode = ResponseCode.RC301.getCode();
	}

	public AuthenticationRuntimeException(Throwable cause) {
		super(cause);
		this.errorCode = ResponseCode.USERNAME_OR_PASSWORD_ERROR.getCode();
		this.errorMessage = ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMessage();
	}

	public AuthenticationRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = ResponseCode.USERNAME_OR_PASSWORD_ERROR.getCode();

	}
	
	public AuthenticationRuntimeException(String message, Throwable cause, String context) {
		super(message, cause);
		this.context = context;
		this.errorCode = ResponseCode.RC301.getCode();
	}
	
	public AuthenticationRuntimeException(ResponseCode responseCode, Throwable cause, String context) {
		super(cause);
		this.errorCode = responseCode.getCode();
		this.errorMessage =  responseCode.getMessage();
		this.context = context;
		
	}
	
	public AuthenticationRuntimeException(ResponseCode responseCode, String message) {
		this.errorCode = responseCode.getCode();
		this.errorMessage = message;
		this.context = message;
	}

}

