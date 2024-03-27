package com.tsystems.core.api;


/**
 * 
 * @author Lichunlei
 *
 */
public class ResponseData<T> {
  /** 结果状态 ,具体状态码参见ResultData.java*/
  private String status;
  private String message;
  private T data;
  private long timestamp ;
  
  public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public T getData() {
		return data;
	}


	public void setData(T data) {
		this.data = data;
	}


	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	  

	public ResponseData (){
	    this.timestamp = System.currentTimeMillis();
	}


	public static <T> ResponseData<T> success(T data) {
		ResponseData<T> resultData = new ResponseData<>();
		resultData.setStatus(ResponseCode.RC100.getCode());
		resultData.setMessage(ResponseCode.RC100.getMessage());
		resultData.setData(data);
		return resultData;
	}
	
	public static <T> ResponseData<T> success() {
		ResponseData<T> resultData = new ResponseData<>();
		resultData.setStatus(ResponseCode.RC100.getCode());
		resultData.setMessage(ResponseCode.RC100.getMessage());
		return resultData;
	}

	public static <T> ResponseData<T> fail(String code, String message) {
		ResponseData<T> resultData = new ResponseData<>();
		resultData.setStatus(code);
		resultData.setMessage(message);
		return resultData;
	}
	
	public static <T> ResponseData<T> fail(String code, String message, T context) {
		ResponseData<T> resultData = new ResponseData<>();
		resultData.setStatus(code);
		resultData.setMessage(message);
		resultData.setData(context);
		return resultData;
	}



  

}
