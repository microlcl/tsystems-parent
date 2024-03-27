package com.tsystems.core.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tsystems.core.constant.LogConstant;
import com.tsystems.core.constant.ResponseCode;
import com.tsystems.core.exception.BaseRuntimeException;
import com.tsystems.core.http.MyHttpRequestHolder;
import com.tsystems.core.http.RequestLog;
import com.tsystems.core.utils.NetUtil;
import com.tsystems.core.utils.StringUtil;



/**
 * LCL
 * 日志 拦截器
 *
 */
public class RequestLogInterceptor implements AsyncHandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

	protected static final Logger logReqElkJson = LoggerFactory.getLogger("ELK_REQ_LOG");
	
	
	private static final int MAX_RESPONSE = 2000;


	/**
	 * 统一添加startDate
	 */
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		httpServletRequest.setAttribute("startDate", new Date());
		return true;
	}

	/**
	 * 处理完后清理mdc
	 */
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
		// Do nothing
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (ex != null) {
			if ( ex instanceof BaseRuntimeException) {
				BaseRuntimeException e = (BaseRuntimeException) ex;
				request.setAttribute(LogConstant.ERRCODE_HEADER,e.getErrorCode());
				request.setAttribute(LogConstant.ERRDESC_HEADER,e.getMessage());
			} else {
				request.setAttribute(LogConstant.ERRCODE_HEADER, ResponseCode.RC500.getCode());
				request.setAttribute(LogConstant.ERRDESC_HEADER,ex.getMessage());
			}
			request.setAttribute(LogConstant.LASTEXCEPTION_HEADER, ex);
		}
		logRequestResult(request, response,(Date) request.getAttribute("startDate"), new Date());
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AsyncHandlerInterceptor.super.afterConcurrentHandlingStarted(request, response, handler);
	}

	/**
	 * 写 log request
	 */
	private void logRequestResult(HttpServletRequest req, HttpServletResponse resp, Date begin, Date end) //NOSONAR
			throws ServletException, IOException, UnsupportedEncodingException {

		String body = MyHttpRequestHolder.getBody();
		if (StringUtil.isEmptyWithTrim(body)){
			String reqContentType = MyHttpRequestHolder.getContentType();
			reqContentType = reqContentType==null?"":reqContentType.toLowerCase().trim();
			boolean isJsonBody = reqContentType.startsWith("application/json");
			if (!isJsonBody){
				body = "transformed as JSON:"+JSON.toJSONString(req.getParameterMap());
			}
		}


		String errCode =  req.getAttribute(LogConstant.ERRCODE_HEADER) == null ? null : req.getAttribute(LogConstant.ERRCODE_HEADER).toString();
		if (errCode == null) {
			errCode = MyHttpRequestHolder.get(LogConstant.ERRCODE_HEADER);
		}
		if (errCode == null) {
			errCode = "" + resp.getStatus();
		}
		String errMsg = (String) req.getAttribute(LogConstant.ERRDESC_HEADER);
		if (errMsg == null) {
			errMsg = MyHttpRequestHolder.get(LogConstant.ERRDESC_HEADER);
		}
		String finalRespJsonStr = (String) req.getAttribute(LogConstant.RESP_BODY_HEADER);
		if(!StringUtil.isEmptyWithTrim(finalRespJsonStr) && finalRespJsonStr.length() > MAX_RESPONSE) {
			finalRespJsonStr = finalRespJsonStr.substring(0, MAX_RESPONSE) + "...";
		}

		Exception lastExecException = (Exception) req.getAttribute(LogConstant.LASTEXCEPTION_HEADER);
		if (lastExecException == null) {
			lastExecException = MyHttpRequestHolder.get(LogConstant.LASTEXCEPTION_HEADER);
		}
		Date svcStartTime = begin;
		final RequestLog rl = new RequestLog();
		rl.setId(String.format("%X%S", System.currentTimeMillis(), UUID.randomUUID().toString().replace("-", "")));
		rl.setCallTime(svcStartTime);
		rl.setServiceIp(NetUtil.getLocalIP());
		rl.setClientIP(NetUtil.getRealRemoteAddr(req));

		rl.setMethod(req.getMethod());
		String uri = MyHttpRequestHolder.getReqUri();
		if (null != uri) {
			rl.setUri(uri);
		} else {
			rl.setUri(URLDecoder.decode(req.getRequestURI(), "utf-8"));
		}
		rl.setReqFlag(MyHttpRequestHolder.getReqFlag());
		String header = (String) req.getAttribute( "request_hdrs__");
		if (header == null) {
			header = MyHttpRequestHolder.dumpHeader().toString();
			req.setAttribute( "request_hdrs__", header);
		}
		rl.setHeader(header);
		rl.setQueryString(req.getQueryString());
		rl.setBody(body);
		rl.setEndTime(end);
		rl.setErrCode(errCode);
		rl.setErrMsg(errMsg);
		rl.setResponse(finalRespJsonStr);

		if (lastExecException != null) {
			if(lastExecException.getCause() != null) {
				rl.setLastExceptionMsg(StringUtil.getMsgOrClzName(lastExecException.getCause()));
				rl.setLastExceptionStack(StringUtil.getFullStackTrace(lastExecException.getCause()));
			}else {
				rl.setLastExceptionMsg(StringUtil.getMsgOrClzName(lastExecException));
				rl.setLastExceptionStack(StringUtil.getFullStackTrace(lastExecException));
			}

		}

		String json = JSON.toJSONString(rl);
		if (null == logReqElkJson) {
			logger.info(json);
		} else {
			logReqElkJson.info(json);
		}
		

	}


}
