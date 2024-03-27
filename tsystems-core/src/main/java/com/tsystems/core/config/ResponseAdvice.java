package com.tsystems.core.config;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.tsystems.core.api.ResponseData;
import com.tsystems.core.constant.LogConstant;





//@RestControllerAdvice
//@Component
@ControllerAdvice
//@ResponseBody
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

	@Autowired HttpServletRequest myrequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(body instanceof ResponseData){
        	this.myrequest.setAttribute(LogConstant.RESP_BODY_HEADER, JSON.toJSONString(body));
            return body;
          }

        return body;
    }
    

}
