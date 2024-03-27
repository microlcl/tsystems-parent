package com.tsystems.core.http;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.http.entity.ContentType;


//@Component
public class HttpServletRequestReplacedFilter implements Filter{


	@Override
	public void destroy() {
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		MyHttpRequestHolder.clean();
		MyHttpRequestHolder.setReqFlag(""+request.hashCode());
		if(isPostMethod((HttpServletRequest)request)
				&& request.getContentType() != null
				&& request.getContentType().startsWith(ContentType.APPLICATION_JSON.getMimeType())
				) {
			RequestWrapper requestWrapper = new RequestWrapper(Thread.currentThread().getId(),(HttpServletRequest) request);
			String charEncoding = requestWrapper.getCharacterEncoding() != null ? requestWrapper.getCharacterEncoding() : "UTF-8";
			String body = requestWrapper.toByteArray() != null ? new String(requestWrapper.toByteArray(), charEncoding) : "";
			
			MyHttpRequestHolder.initData();
			MyHttpRequestHolder.setBody(body);
			
			chain.doFilter(requestWrapper, response);
		} else {
			MyHttpRequestHolder.initData();
			chain.doFilter(request, response);
		}

	}

	private boolean isPostMethod(HttpServletRequest request) {
		return request.getMethod().toUpperCase().equals("POST");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
//		log.info("in filter init");
	}
}
