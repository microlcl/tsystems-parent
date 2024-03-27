package com.tsystems.core.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.tsystems.core.constant.LogConstant;


/**
 * Lichunlei
 * 自定义httprequest, 获取相关请求参数
 *
 */
public class MyHttpRequestHolder {
	
	private static final TransmittableThreadLocal<Map<String, Object>> contextHolder = new TransmittableThreadLocal<>();

	private static final String HAS_HTTP_REQUEST = "hasHttpServletRequest";

	public static void set(String key, Object value) {
		if (contextHolder.get() == null) {
			contextHolder.set(new HashMap<>());
		}
		contextHolder.get().put(key, value);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		if (contextHolder.get() == null) {
			contextHolder.set(new HashMap<>());
		}
		if (contextHolder.get().containsKey(key)) {
			return (T) contextHolder.get().get(key);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key, T defaultValue) {
		if (contextHolder.get() == null) {
			contextHolder.set(new HashMap<>());
		}
		if (contextHolder.get().containsKey(key)) {
			return (T) contextHolder.get().get(key);
		}
		return defaultValue;
	}

	public static boolean contains(String key) {
		if (contextHolder.get() == null) {
			contextHolder.set(new HashMap<>());
		}
		return contextHolder.get().containsKey(key);
	}

	public static void clean() {
		contextHolder.remove();
		setAttribute(LogConstant.REQ_ATTR_FLAG, null);
	}



	public static String getBody() {
	    return (String) MyHttpRequestHolder.getAttribute(LogConstant.REQ_ATTR_BODY);
	}

	public static void setBody(String body) {
		MyHttpRequestHolder.setAttribute(LogConstant.REQ_ATTR_BODY, body);
	}

	public static void initData() {
		if (isInit()) {
			return;
		}
		clean();
		set("init", true);
	}

	public static boolean isInit() {
		return get("init", false);
	}

	// 是否是http request请求，定时任务的时候没有
	public static boolean hasHttpServletRequest() {
		if (contains(HAS_HTTP_REQUEST)) {
			return get(HAS_HTTP_REQUEST);
		}
		boolean flag = (RequestContextHolder.getRequestAttributes() != null);
		set(HAS_HTTP_REQUEST, flag);
		return flag;
	}

	// 获取当前HttpServletRequest对象
	public static HttpServletRequest getRequest() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		if (null == attributes) {
			return null;
		}
		return ((ServletRequestAttributes) attributes).getRequest();
	}

	public static String getContentType() {
		String ct = get("cotentType");
		if (null == ct) {
			if (!hasHttpServletRequest()) {
				return null;
			}
			ct = getRequest().getContentType(); //NOSONAR
			set("cotentType", ct);
		}
		return ct;
	}

	public static Object getAttribute(String name) {
	    Object value = MyHttpRequestHolder.get(name);
        if (null == value) {
            HttpServletRequest request = getRequest();
            if (null != request) {
                value = request.getAttribute(name);
            }
            if (value != null) {
                set(name, value);
            }
        }
		return value;
	}

	public static void setAttribute(String name, Object value) {
	    HttpServletRequest request = getRequest();
	    if (null != request) {
	        request.setAttribute(name, value);
	    }
	    set(name, value);
	}

	public static String getReqFlag() {
	    String requestFlag = (String) getAttribute(LogConstant.REQ_ATTR_FLAG);
	    if (null == requestFlag) {
	        HttpServletRequest request = getRequest();
	        if (request != null) {
	            requestFlag = "" + request.hashCode();
	        } else {
	            int hashCode = UUID.randomUUID().toString().hashCode();
	            if (hashCode < 0) {
	                hashCode = -hashCode;
	            }
	            requestFlag = hashCode + "";
	        }
	        setReqFlag(requestFlag);
	    }
	    return requestFlag;
	}

	public static void setReqFlag(String requestFlag) {
	    setAttribute(LogConstant.REQ_ATTR_FLAG, requestFlag);
	}

	public static String getReqUriForwarded() {
		return get("requestUriForwarded");
	}

	public static String getReqUri() {
		return get("requestUri");
	}


	public static StringBuilder dumpHeader() {
		if (!hasHttpServletRequest()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if(getRequest() == null) {
			return sb;
		}
		Enumeration<String> eh = getRequest().getHeaderNames(); //NOSONAR
		while (eh.hasMoreElements()) {
			String hn = eh.nextElement();
			Enumeration<String> hvs = getRequest().getHeaders(hn); //NOSONAR
			while (hvs.hasMoreElements()) {
				sb.append("\t").append(hn).append(": ").append(hvs.nextElement()).append("\n");
			}
		}
		return sb;
	}

}
