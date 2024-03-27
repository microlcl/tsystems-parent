package com.tsystems.core.exception;


import com.tsystems.core.constant.ResponseCode;
import com.tsystems.core.utils.StringUtil;



public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7118344898584330772L;

	protected String errorCode;
	
	protected String errorMessage;
	
	protected String context;
	

	public BaseRuntimeException() {
		super();
	}

	public BaseRuntimeException(String message) {
		super(message);
	}

	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}

	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseRuntimeException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}


	public BaseRuntimeException(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public BaseRuntimeException(String errorCode, String message, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorMessage = message;
	}
	
	public BaseRuntimeException(ResponseCode responseCode, Throwable cause, String context) {
		super(cause);
		this.errorCode = responseCode.getCode();
		this.errorMessage = responseCode.getMessage();
		this.context = context;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



	@Override
	public String getMessage() {
		return super.getMessage() + (this.errorCode == null ? "" : "; Error Code(" + this.errorCode + ")");
	}

	public String getMessageWithoutErroCode() {
		return super.getMessage();
	}

	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getFullStackTrace() {
		return StringUtil.getFullStackTrace(this);
	}

}

