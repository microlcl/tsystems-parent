package com.tsystems.core.http;

import java.io.Serializable;
import java.util.Date;

public class RequestLog extends BaseBrandHttpRequestLog implements Serializable {

	private static final long serialVersionUID = 4697785061394529734L;

	private String uri;
	private String queryString;
	private String body;

	private Date endTime;

	private String errorCode;
	private String errCode;
	private String errMsg;

	private String lastExceptionMsg;
	private String lastExceptionStack;

	@Override
	public Long getCostMS() {
		return (this.endTime != null && getCallTime() != null) ? (this.endTime.getTime() - getCallTime().getTime()) : null;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getQueryString() {
		return this.queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getLastExceptionMsg() {
		return this.lastExceptionMsg;
	}

	public void setLastExceptionMsg(String lastExceptionMsg) {
		this.lastExceptionMsg = lastExceptionMsg;
	}

	public String getLastExceptionStack() {
		return this.lastExceptionStack;
	}

	public void setLastExceptionStack(String lastExceptionStack) {
		this.lastExceptionStack = lastExceptionStack;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



}
