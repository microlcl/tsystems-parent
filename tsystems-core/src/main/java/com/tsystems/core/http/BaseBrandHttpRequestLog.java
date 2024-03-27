package com.tsystems.core.http;


/**
 * 
 * @author LCL
 *
 */
public class BaseBrandHttpRequestLog extends BaseHttpRequestLog {

	private static final long serialVersionUID = 824522570934159189L;

	private String serviceIp;

	private String reqFlag;
	private String clientIP;
	public String getServiceIp() {
		return serviceIp;
	}
	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	public String getReqFlag() {
		return reqFlag;
	}
	public void setReqFlag(String reqFlag) {
		this.reqFlag = reqFlag;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

}
