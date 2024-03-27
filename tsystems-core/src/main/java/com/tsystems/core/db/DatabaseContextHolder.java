package com.tsystems.core.db;

import com.alibaba.ttl.TransmittableThreadLocal;

public class DatabaseContextHolder {
	private static final TransmittableThreadLocal<String> contextHolder = new TransmittableThreadLocal<String>();

	public static void setDatabaseType(String type) {
		contextHolder.set(type);
	}

	public static String getDatabaseType() {
		return contextHolder.get();
	}

	public static void clearDatabaseType() {
		contextHolder.remove();
	}
}
