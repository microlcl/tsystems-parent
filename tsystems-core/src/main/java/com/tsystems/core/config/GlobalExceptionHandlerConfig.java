package com.tsystems.core.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsystems.core.api.ResponseData;
import com.tsystems.core.constant.LogConstant;
import com.tsystems.core.constant.ResponseCode;
import com.tsystems.core.exception.BaseRuntimeException;
import com.tsystems.core.http.MyHttpRequestHolder;
import com.tsystems.core.utils.StringUtil;


/**
 * 异常处理拦截器
 *
 * @date: Sep 20, 2018 5:05:58 PM
 * @author lichunei
 */
@ControllerAdvice
@ResponseBody
//@Component
public class GlobalExceptionHandlerConfig {

	//其他错误
	@ExceptionHandler({Exception.class})
	public Object exception(Exception ex) {
		MyHttpRequestHolder.setAttribute(LogConstant.ERRCODE_HEADER,ResponseCode.RC500.getCode());
		MyHttpRequestHolder.setAttribute(LogConstant.ERRDESC_HEADER, StringUtil.getMsgOrClzName(ex));
		MyHttpRequestHolder.setAttribute(LogConstant.LASTEXCEPTION_HEADER,ex);

		ex.printStackTrace();
//		return ResponseData.fail(ResponseCode.RC508.getCode(), StringUtil.getMsgOrClzName(ex));
		return ResponseData.fail(ResponseCode.RC500.getCode(), ResponseCode.RC500.getMessage() + ex.getMessage(), StringUtil.getMsgOrClzName(ex));
	}

	//自定义异常捕获
	@ExceptionHandler({BaseRuntimeException.class})
	public Object myException(BaseRuntimeException ex) {
		MyHttpRequestHolder.setAttribute(LogConstant.ERRCODE_HEADER,ex.getErrorCode());
		MyHttpRequestHolder.setAttribute(LogConstant.ERRDESC_HEADER, ex.getMessage());
		MyHttpRequestHolder.setAttribute(LogConstant.LASTEXCEPTION_HEADER,ex);
		ex.printStackTrace();
		return ResponseData.fail(ex.getErrorCode(), ex.getErrorMessage(), ex.getContext());
		

	}

}
