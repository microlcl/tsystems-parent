package com.tsystems.core.config;



import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;

import com.tsystems.core.http.HttpServletRequestReplacedFilter;


/**
 * bean 配置
 *
 */
public class BeanConfig {

	@Value("${log.request.dest:NONE}")
	private String reqLogDest;

	/**
	 * 如果需要log request 的话则需要注册一个filter 用于 拦截 body 的内存，并储存起来用于之后读取
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean httpServletRequestReplacedRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HttpServletRequestReplacedFilter());
		registration.addUrlPatterns("/*");
		registration.setName("httpServletRequestReplacedFilter");
		registration.setOrder(1);
		registration.setAsyncSupported(true);
		return registration;
	}
	/*
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean HttpTraceFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HttpTraceLogFilter());
		registration.addUrlPatterns("/*");
		registration.setName("HttpTraceFilter");
		registration.setAsyncSupported(true);
		return registration;
	}
*/


	@Bean
	@ConditionalOnMissingBean
	public WebMvcConfig initWebMvcConfig() {
		return new WebMvcConfig();
	}


	/**
	 * RequestContextListener
	 * @return
	 */
	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}

	/**
	 * string converter
	 * @return
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		return converter;
	}

}
