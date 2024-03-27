package com.tsystems.core.utils;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetUtil {
	
	private static final String UNKNOWN = "unknown";

	private static final Logger log = LoggerFactory.getLogger(NetUtil.class);

	  private NetUtil() {
	    throw new IllegalStateException("Utility class");
	  }
	  
	public static String getLocalIP() { //NOSONAR
		String ip = "127.0.0.1";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			try {
				Enumeration<NetworkInterface> iterNetwork =
						NetworkInterface.getNetworkInterfaces();
				while (iterNetwork.hasMoreElements()) {
					NetworkInterface network = iterNetwork.nextElement();
					if (!network.isUp() || network.isLoopback()) {
						continue;
					}
					Enumeration<InetAddress> iterAddress = network.getInetAddresses();
					while (iterAddress.hasMoreElements()) {
						InetAddress address = iterAddress.nextElement();
						if (address.isAnyLocalAddress()) {
							continue;
						}
						if (address.isLoopbackAddress()) {
							continue;
						}
						if (address.isMulticastAddress()) {
							continue;
						}
						if (!(address instanceof Inet4Address)) {
							continue;
						}
						ip = address.getHostAddress();
						break;
					}
					if (!ip.isEmpty()) {
						break;
					}
				}
			}
			catch (SocketException e2) {
				log.error("Failed to get local IP [" + e.getMessage() + "], [" + e2
						.getMessage() + "]", e);
				log.error("Failed to get local IP [" + e.getMessage() + "], [" + e2
						.getMessage() + "]", e2);
			}
		}

		return ip;
	}
	
	public static String getRealRemoteAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		if(StringUtil.isEmpty(ip)) { return "";}
		int i = ip.indexOf(",");
		if (i > 0) {
			ip = ip.substring(0, i);
		}
		return ip;
	}
	

	public static String LOCAL_IP;
	public static String LOCAL_IP_FLAG;
	public static String LOCAL_IP_SHORT;
	protected static int LOCAL_IP_FLAG_PREFIX_LEN;

	static {
		LOCAL_IP = NetUtil.getLocalIP();
		if (null != LOCAL_IP) {
			LOCAL_IP_FLAG = LOCAL_IP.replace(".", "_");
			LOCAL_IP_FLAG = "218" + LOCAL_IP_FLAG.substring(LOCAL_IP_FLAG.indexOf("_"));
			LOCAL_IP_SHORT = (LOCAL_IP.substring(LOCAL_IP.indexOf("."))).replace(".", "");
		}

	}


}

